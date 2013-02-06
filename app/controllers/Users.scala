package controllers

import lib.mvc.AuthenticatedAction
import play.api.mvc._
import play.api.libs.json.Json._

import model._
import model.Formats._

import model.{Position, User}
import scala.concurrent._
import ExecutionContext.Implicits.global

trait Users extends Controller {
  def update(id:String)= AuthenticatedAction { request =>
    val user=User(None,id, "John Doe", Some(Position(48.8753,2.3112)), "jdoe@xebia.fr")
    Ok(toJson(user))
  }

  def removeArtist(idUser: String, idArtist: String) = AuthenticatedAction {
    request =>
      NoContent
  }

  def addArtist(idUser: String, idArtist: String) = AuthenticatedAction {
    request =>
      Ok
  }


  def current = AuthenticatedAction { request =>
    val id = request.session.get("login").get
    Async {
      for {
        user <- User.findByEmail(id).map( _.map{x=>Ok(toJson(x))}.getOrElse(Unauthorized))
      } yield user
    }
  }


  def fanOfArtist(idOfArtist: Long) = AuthenticatedAction {
    request =>

      val users = Seq(
        User(None, "1", "Justin Bieber", Some(Position(48.8753, 2.3112)),"justin@bieber.com"),
        User(None, "2", "Justin Bieber 2", Some(Position(48.8733, 2.3122)),"justin@bieber.com"),
        User(None, "3", "Justin Bieber 3", Some(Position(48.8773, 2.3102)),"justin@bieber.com")
      )

      Ok(toJson(users))

  }
}

object Users extends Users