package io.flow.delta.actors

import db.{EventsDao, SettingsDao}
import akka.actor.Actor
import io.flow.delta.v0.models.{Project, Settings}
import io.flow.play.actors.Util
import io.flow.postgresql.{Authorization, OrderBy}
import play.api.Logger
import play.libs.Akka
import scala.concurrent.Await
import scala.concurrent.duration.Duration
import scala.util.{Failure, Success, Try}

object SupervisorActor {

  trait Message

  object Messages {
    case class Data(id: String) extends Message
    case object PursueExpectedState extends Message
  }

  val All = Seq(
    functions.SyncMasterSha,
    functions.TagMaster,
    functions.SetExpectedState,
    functions.BuildDockerImage
  )

}

class SupervisorActor extends Actor with Util with DataProject with EventLog {

  override val logPrefix = "Supervisor"

  implicit val supervisorActorExecutionContext = Akka.system.dispatchers.lookup("supervisor-actor-context")

  private[this] val MinutesUntilInactive = 2
  private[this] val SuccessfulCompletionMessage = "completed PursueExpectedState"

  def receive = {

    case msg @ SupervisorActor.Messages.Data(id) => withVerboseErrorHandler(msg) {
      setDataProject(id)
    }

    /**
      * For any project that is not active (defined by not having an
      * event logged in last n seconds), we send a message to bring
      * that project to its expected state.
      */
    case msg @ SupervisorActor.Messages.PursueExpectedState => withVerboseErrorHandler(msg) {
      withProject { project =>

        isActive(project.id) match {
          case true => {
            Logger.info(s"SupervisorActor: Project[${project.id}] is already active")
          }
          case false => {
            val settings = SettingsDao.findByProjectIdOrDefault(Authorization.All, project.id)
            log.started("PursueExpectedState")
            run(project, settings, SupervisorActor.All)
            log.message(SuccessfulCompletionMessage)
          }
        }
      }
    }

  }

  /**
   * A project is considered active if:
   * 
   *   - it has had at least one log entry written in the past MinutesUntilInactive minutes
   *   - the last log entry writtin was not the successful completion of the supervisor
   *     actor loop (SuccessfulCompletionMessage)
   * 
   * Otherwise, the project is not active
   */
  private[this] def isActive(projectId: String): Boolean = {
    EventsDao.findAll(
      projectId = Some(projectId),
      numberMinutesSinceCreation = Some(MinutesUntilInactive),
      limit = 1,
      orderBy = OrderBy("-events.created_at")
    ).headOption match {
      case None => {
        false
      }
      case Some(event) => {
        event.summary != SuccessfulCompletionMessage
      }
    }
  }

  /**
    * Sequentially runs through the list of functions. If any of the
    * functions returns a SupervisorResult.Changed or
    * SupervisorResult.Error, returns that result. Otherwise will
    * return NoChange at the end of all the functions.
    */
  private[this] def run(project: Project, settings: Settings, functions: Seq[SupervisorFunction]) {
    functions.headOption match {
      case None => {
        SupervisorResult.NoChange("All functions returned without modification")
      }
      case Some(f) => {
        f.isEnabled(settings) match {
          case false => {
            log.skipped(format(f, "is disabled in the project settings"))
            run(project, settings, functions.drop(1))
          }
          case true => {
            log.started(format(f))
            Try(
              // TODO: Remove the await
              Await.result(
                f.run(project),
                Duration(10, "minutes")
              )
            ) match {
              case Success(result) => {
                result match {
                  case SupervisorResult.Change(desc) => {
                    log.changed(format(f, desc))
                  }
                  case SupervisorResult.NoChange(desc)=> {
                    log.completed(format(f, desc))
                    run(project, settings, functions.drop(1))
                  }
                  case SupervisorResult.Error(desc, ex)=> {
                    log.completed(format(f, desc), Some(ex))
                  }
                }
              }

              case Failure(ex) => {
                log.completed(format(f, ex.getMessage), Some(ex))
              }
            }
          }
        }
      }
    }
  }

  /**
    * Prepend the description with the class name of the
    * function. This lets us have automatic messages like
    * "TagMaster: xxx"
    */
  private[this] def format(f: Any, desc: String): String = {
    format(f) + ": " + desc
  }

  private[this] def format(f: Any): String = {
    val name = f.getClass.getName
    val idx = name.lastIndexOf(".")  // Remove classpath to just get function name
    name.substring(idx + 1).dropRight(1) // Remove trailing $
  }

}