package db

import io.flow.postgresql.Authorization
import org.scalatest._
import play.api.test._
import play.api.test.Helpers._
import org.scalatestplus.play._
import java.util.UUID

class TagsDaoSpec extends PlaySpec with OneAppPerSuite with Helpers {

  import scala.concurrent.ExecutionContext.Implicits.global

  "create" in {
    val project = createProject()
    val hash = createTestKey()
    val form = createTagForm(project).copy(name = "0.0.1", hash = hash)
    val tag = rightOrErrors(TagsDao.create(systemUser, form))
    tag.project.id must be(project.id)
    tag.name must be("0.0.1")
    tag.hash must be(hash)
  }

  "upsert" in {
    val project = createProject()
    val name = "0.0.1"
    val hash = createTestKey()

    val tag = TagsDao.upsert(systemUser, project.id, name, hash)
    tag.name must be(name)
    tag.hash must be(hash)

    val tag2 = TagsDao.upsert(systemUser, project.id, name, hash)
    tag2.id must be(tag.id)
    tag2.name must be(name)
    tag2.hash must be(hash)

    val other = createTestKey()
    val tag3 = TagsDao.upsert(systemUser, project.id, name, other)
    tag3.id must be(tag2.id)
    tag3.name must be(name)
    tag3.hash must be(other)
  }

  "delete" in {
    val tag = createTag()
    TagsDao.delete(systemUser, tag)
    TagsDao.findById(Authorization.All, tag.id) must be(None)
  }

  "findById" in {
    val tag = createTag()
    TagsDao.findById(Authorization.All, tag.id).map(_.id) must be(
      Some(tag.id)
    )

    TagsDao.findById(Authorization.All, UUID.randomUUID.toString) must be(None)
  }

  "findLatestByProjectId" in {
    val project = createProject()
    TagsDao.findLatestByProjectId(Authorization.All, project.id) must be(None)

    val tag1 = createTag(createTagForm(project).copy(name = "0.0.1"))
    TagsDao.findLatestByProjectId(Authorization.All, project.id).map(_.id) must be(Some(tag1.id))

    val tag2 = createTag(createTagForm(project).copy(name = "0.0.2"))
    TagsDao.findLatestByProjectId(Authorization.All, project.id).map(_.id) must be(Some(tag2.id))
  }
  
  "findByProjectIdAndName" in {
    val project = createProject()

    val form1 = createTagForm(project).copy(name = "0.0.2")
    val tag1 = rightOrErrors(TagsDao.create(systemUser, form1))

    val form2 = createTagForm(project).copy(name = "0.0.3")
    val tag2 = rightOrErrors(TagsDao.create(systemUser, form2))

    TagsDao.findByProjectIdAndName(Authorization.All, project.id, "0.0.2").map(_.id) must be(Some(tag1.id))
    TagsDao.findByProjectIdAndName(Authorization.All, project.id, "0.0.3").map(_.id) must be(Some(tag2.id))
    TagsDao.findByProjectIdAndName(Authorization.All, project.id, "other") must be(None)
  }

  "findAll by ids" in {
    val tag1 = createTag()
    val tag2 = createTag()

    TagsDao.findAll(Authorization.All, ids = Some(Seq(tag1.id, tag2.id))).map(_.id).sorted must be(
      Seq(tag1.id, tag2.id).sorted
    )

    TagsDao.findAll(Authorization.All, ids = Some(Nil)) must be(Nil)
    TagsDao.findAll(Authorization.All, ids = Some(Seq(UUID.randomUUID.toString))) must be(Nil)
    TagsDao.findAll(Authorization.All, ids = Some(Seq(tag1.id, UUID.randomUUID.toString))).map(_.id) must be(Seq(tag1.id))
  }

  "findAll by projectId" in {
    val project1 = createProject()
    val project2 = createProject()

    val tag1 = createTag(createTagForm(project1))
    val tag2 = createTag(createTagForm(project2))

    TagsDao.findAll(Authorization.All, projectId = Some(project1.id)).map(_.id).sorted must be(
      Seq(tag1.id)
    )

    TagsDao.findAll(Authorization.All, projectId = Some(project2.id)).map(_.id).sorted must be(
      Seq(tag2.id)
    )

    TagsDao.findAll(Authorization.All, projectId = Some(createTestKey())) must be(Nil)
  }

  "validate" must {

    "require hash" in {
      TagsDao.validate(systemUser, createTagForm().copy(hash = "   ")) must be(
        Seq("Hash cannot be empty")
      )
    }

    "require name" in {
      TagsDao.validate(systemUser, createTagForm().copy(name = "   ")) must be(
        Seq("Name cannot be empty")
      )
    }

    "validate name is semver" in {
      TagsDao.validate(systemUser, createTagForm().copy(name = "release")) must be(
        Seq("Name must match semver pattern (e.g. 0.1.2)")
      )
    }

    "validate project exists" in {
      TagsDao.validate(systemUser, createTagForm().copy(projectId = createTestKey())) must be(
        Seq("Project not found")
      )
    }
 
    "validate existing record" in {
      val form = createTagForm()
      val tag = createTag(form)

      TagsDao.validate(systemUser, form) must be(
        Seq("Project already has a tag with this name")
      )

      TagsDao.validate(systemUser, form.copy(name = "9.1.2")) must be(Nil)
    }

  }

  "authorization for tags" in {
    val org = createOrganization()
    val project = createProject(org)
    val user = createUser()
    createMembership(createMembershipForm(org = org, user = user))

    val tag = createTag(createTagForm(project), user = user)

    TagsDao.findAll(Authorization.PublicOnly, ids = Some(Seq(tag.id))) must be(Nil)
    TagsDao.findAll(Authorization.All, ids = Some(Seq(tag.id))).map(_.id) must be(Seq(tag.id))
    TagsDao.findAll(Authorization.Organization(org.id), ids = Some(Seq(tag.id))).map(_.id) must be(Seq(tag.id))
    TagsDao.findAll(Authorization.Organization(createOrganization().id), ids = Some(Seq(tag.id))) must be(Nil)
    TagsDao.findAll(Authorization.User(user.id), ids = Some(Seq(tag.id))).map(_.id) must be(Seq(tag.id))
    TagsDao.findAll(Authorization.User(createUser().id), ids = Some(Seq(tag.id))) must be(Nil)
  }

}