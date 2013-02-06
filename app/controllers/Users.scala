package controllers

import lib.mvc.AuthenticatedAction
import play.api.mvc._
import play.api.libs.json.Json._

import model.{User, Position}
import model.Formats._
import model.{Position, User}

trait Users extends Controller {
  def update(id:String)= AuthenticatedAction { request =>
    val user=User(None,id, "John Doe", Some(Position(48.8753,2.3112)), "jdoe@xebia.fr")

    Ok(toJson(user))
  }

  def removeArtist(idUser:String,idArtist:String) = AuthenticatedAction { request =>
    NoContent
  }

  def addArtist(idUser:String,idArtist:String) = AuthenticatedAction { request =>
    Ok
  }

  def current = AuthenticatedAction { request =>
    import reactivemongo.bson.BSONObjectID
    val user=User(Some(BSONObjectID.generate), "12345", "John Doe", Some(Position(48.8753,2.3112)), "jdoe@xebia.fr")

    Ok(toJson(user))
  }
}

object Users extends Users