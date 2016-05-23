package db

import io.flow.delta.actors.MainActor
import io.flow.delta.config.v0.models.{Build => BuildConfig}
import io.flow.delta.v0.models.{Build, Status}
import io.flow.postgresql.{Authorization, Query, OrderBy, Pager}
import io.flow.common.v0.models.UserReference
import anorm._
import play.api.db._
import play.api.Play.current
import play.api.libs.json._

object BuildsDao {

  private[db] val Master = "master"

  private[this] val BaseQuery = Query(s"""
    select builds.id,
           builds.name,
           builds.status,
           builds.dockerfile_path,
           builds.project_id,
           projects.name as project_name,
           projects.uri as project_uri,
           projects.organization_id as project_organization_id
      from builds
      join projects on projects.id = builds.project_id
  """)

  def findAllByProjectId(auth: Authorization, projectId: String): Seq[Build] = {
    Pager.create { offset =>
      findAll(auth, projectId = Some(projectId), offset = offset)
    }.toSeq
  }
  
  def findByProjectIdAndName(auth: Authorization, projectId: String, name: String): Option[Build] = {
    findAll(auth, projectId = Some(projectId), name = Some(name), limit = 1).headOption
  }

  def findById(auth: Authorization, id: String): Option[Build] = {
    findAll(auth, ids = Some(Seq(id)), limit = 1).headOption
  }

  def findAll(
    auth: Authorization,
    ids: Option[Seq[String]] = None,
    projectId: Option[String] = None,
    name: Option[String] = None,
    orderBy: OrderBy = OrderBy("lower(projects.name), lower(builds.name)"),
    limit: Long = 25,
    offset: Long = 0
  ): Seq[Build] = {

    DB.withConnection { implicit c =>
      Standards.query(
        BaseQuery,
        tableName = "builds",
        auth = Filters(auth).organizations("projects.organization_id"),
        ids = ids,
        orderBy = orderBy.sql,
        limit = limit,
        offset = offset
      ).
        equals("builds.project_id", projectId).
        optionalText(
          "builds.name",
          name,
          columnFunctions = Seq(Query.Function.Lower),
          valueFunctions = Seq(Query.Function.Lower, Query.Function.Trim)
        ).
        as(
          io.flow.delta.v0.anorm.parsers.Build.parser().*
        )
    }
  }

}

case class BuildsWriteDao @javax.inject.Inject() (
  imagesWriteDao: ImagesWriteDao,
  @javax.inject.Named("main-actor") mainActor: akka.actor.ActorRef
) {

  private[this] val UpsertQuery = """
    insert into builds
    (id, project_id, name, status, dockerfile_path, position, updated_by_user_id)
    values
    ({id}, {project_id}, {name}, {status}, {dockerfile_path}, {position}, {updated_by_user_id})
    on conflict(project_id, name)
    do update
          set dockerfile_path = {dockerfile_path},
              position = {position},
              updated_by_user_id = {updated_by_user_id}
  """

  private[this] val UpdateQuery = """
    update builds
       set name = {name},
           dockerfile_path = {dockerfile_path},
           updated_by_user_id = {updated_by_user_id}
     where id = {id}
  """

  private[this] val MaxPositionQuery = """
    select max(position) as position from builds where project_id = {project_id}
  """

  private[this] val idGenerator = io.flow.play.util.IdGenerator("bld")

  def upsert(createdBy: UserReference, projectId: String, status: Status, config: BuildConfig): Build = {
    DB.withConnection { implicit c =>
      upsert(c, createdBy, projectId, status, config)
    }

    val build = BuildsDao.findByProjectIdAndName(Authorization.All, projectId, config.name).getOrElse {
      sys.error(s"Failed to create build for projectId[$projectId] name[${config.name}]")
    }

    mainActor ! MainActor.Messages.BuildCreated(build.id)

    build
  }

  private[db] def upsert(implicit c: java.sql.Connection, createdBy: UserReference, projectId: String, status: Status, config: BuildConfig) {
    SQL(UpsertQuery).on(
      'id -> idGenerator.randomId(),
      'project_id -> projectId,
      'name -> config.name.trim,
      'status -> status.toString,
      'dockerfile_path -> config.dockerfile.trim,
      'position -> nextPosition(projectId),
      'updated_by_user_id -> createdBy.id
    ).execute()
  }

  private[this] def nextPosition(projectId: String)(
    implicit c: java.sql.Connection
  ): Long = {
    SQL(MaxPositionQuery).on(
      'project_id -> projectId
    ).as(SqlParser.get[Option[Long]]("position").single).headOption match {
      case None => 0
      case Some(n) => n + 1
    }
  }

  def update(createdBy: UserReference, build: Build, config: BuildConfig): Build = {
    DB.withConnection { implicit c =>
      SQL(UpdateQuery).on(
        'id -> build.id,
        'name -> config.name.trim,
        'dockerfile_path -> config.dockerfile.trim,
        'updated_by_user_id -> createdBy.id
      ).execute()
    }

    mainActor ! MainActor.Messages.BuildUpdated(build.id)

    BuildsDao.findById(Authorization.All, build.id).getOrElse {
      sys.error("Failed to update build")
    }
  }

  def delete(deletedBy: UserReference, build: Build) {
    Pager.create { offset =>
      ImagesDao.findAll(buildId = Some(build.id), offset = offset)
    }.foreach { image =>
      imagesWriteDao.delete(deletedBy, image)
    }

    Delete.delete("builds", deletedBy.id, build.id)
    mainActor ! MainActor.Messages.BuildDeleted(build.id)
  }

}
