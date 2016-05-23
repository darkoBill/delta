package io.flow.delta.actors

import db.{BuildsDao, ConfigsDao, EventsDao}
import io.flow.postgresql.Authorization
import io.flow.delta.api.lib.{GithubHelper, Repo}
import io.flow.delta.v0.models.Project
import io.flow.play.actors.ErrorHandler
import io.flow.play.util.Config
import play.api.Logger
import akka.actor.{Actor, ActorSystem}
import scala.util.{Failure, Success, Try}
import scala.concurrent.duration._
import scala.concurrent.Future

object ProjectActor {

  val SyncIfInactiveIntervalMinutes = 15

  trait Message

  object Messages {
    case object Setup extends Message
    case object SyncBuilds extends Message
    case object SyncIfInactive extends Message
  }

  trait Factory {
    def apply(projectId: String): Actor
  }

}

class ProjectActor @javax.inject.Inject() (
  config: Config,
  system: ActorSystem,
  override val configsDao: ConfigsDao,
  @com.google.inject.assistedinject.Assisted projectId: String
) extends Actor with ErrorHandler with DataProject with EventLog {

  private[this] implicit val ec = system.dispatchers.lookup("project-actor-context")

  def receive = {

    case msg @ ProjectActor.Messages.Setup => withVerboseErrorHandler(msg) {
      setProjectId(projectId)

      withProject { project =>
        withRepo { repo =>
          createHooks(project, repo)
        }
      }

      self ! ProjectActor.Messages.SyncBuilds

      system.scheduler.schedule(
        Duration(ProjectActor.SyncIfInactiveIntervalMinutes, "minutes"),
        Duration(ProjectActor.SyncIfInactiveIntervalMinutes, "minutes")
      ) {
        sender ! ProjectActor.Messages.SyncIfInactive
      }
    }

    case msg @ ProjectActor.Messages.SyncBuilds => withVerboseErrorHandler(msg) {
      withProject { project =>
        BuildsDao.findAllByProjectId(Authorization.All, projectId).foreach { build =>
          sender ! MainActor.Messages.BuildDesiredStateUpdated(build.id)
        }
      }
    }

    case msg @ ProjectActor.Messages.SyncIfInactive => withVerboseErrorHandler(msg) {
      withProject { project =>
        EventsDao.findAll(
          projectId = Some(project.id),
          numberMinutesSinceCreation = Some(5),
          limit = 1
        ).headOption match {
          case Some(_) => // No-op as there is recent activity in the event log
          case None => sender ! MainActor.Messages.ProjectSync(project.id)
        }
      }
    }

    case msg: Any => logUnhandledMessage(msg)

  }

  private[this] val HookBaseUrl = config.requiredString("delta.api.host") + "/webhooks/github/"
  private[this] val HookName = "web"
  private[this] val HookEvents = Seq(io.flow.github.v0.models.HookEvent.Push)

  private[this] def createHooks(project: Project, repo: Repo) {
    GithubHelper.apiClientFromUser(project.user.id) match {
      case None => {
        Logger.warn(s"Could not create github client for user[${project.user.id}]")
      }
      case Some(client) => {
        client.hooks.get(repo.owner, repo.project).map { hooks =>
          val targetUrl = HookBaseUrl + project.id

          hooks.find(_.config.url == Some(targetUrl)) match {
            case Some(hook) => {
              // No-op hook exists
            }
            case None => {
              client.hooks.post(
                owner = repo.owner,
                repo = repo.project,
                name = HookName,
                config = io.flow.github.v0.models.HookConfig(
                  url = Some(targetUrl),
                  contentType = Some("json")
                ),
                events = HookEvents,
                active = true
              )
            }.map { hook =>
              Logger.info("Created githib webhook for project[${project.id}]: $hook")
            }.recover {
              case e: Throwable => {
                Logger.error("Project[${project.id}] Error creating hook: " + e)
              }
            }
          }
        }
      }
    }
  }
}
