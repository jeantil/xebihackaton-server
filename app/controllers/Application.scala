package controllers

import play.api.mvc._
import lib.mvc.AuthenticatedAction

trait Application extends Controller {
  def index = AuthenticatedAction { request =>
    Ok("index")
  }
}
object Application extends Application
