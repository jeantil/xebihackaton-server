package controllers

import lib.mvc.AuthenticatedAction
import play.api.mvc._
import play.api.libs.json.Json._

import model.model.{User, Position}
import model.model.Formats._

trait Users extends Controller {
  def update(id:String)= AuthenticatedAction { request =>
    val user=User(id, "John Doe", Some(Position(48.8753,2.3112)), "jdoe@xebia.fr")

    Ok(toJson(user))
  }

  def removeArtist(idUser:String,idArtist:String) = AuthenticatedAction { request =>
    NoContent
  }

  def addArtist(idUser:String,idArtist:String) = AuthenticatedAction { request =>
    Ok
  }
}

object Users extends Users