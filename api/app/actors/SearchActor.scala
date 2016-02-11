package io.flow.delta.actors

import io.flow.delta.v0.models.{BinarySummary, LibrarySummary, ProjectSummary}
import db.{Authorization, BinariesDao, ItemForm, ItemsDao, LibrariesDao, ProjectsDao}
import play.api.Logger
import akka.actor.Actor

object SearchActor {

  sealed trait Message

  object Messages {
    case class SyncProject(id: String) extends Message
  }

}

class SearchActor extends Actor with Util {

  def receive = {

    case m @ SearchActor.Messages.SyncProject(id) => withVerboseErrorHandler(m) {
      ProjectsDao.findById(Authorization.All, id) match {
        case None => ItemsDao.softDeleteByObjectId(Authorization.All, MainActor.SystemUser, id)
        case Some(project) => ItemsDao.replaceProject(MainActor.SystemUser, project)
      }
    }

    case m: Any => logUnhandledMessage(m)
  }

}
