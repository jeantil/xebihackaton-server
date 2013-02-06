package controllers

import play.api.mvc._
import lib.mvc.AuthenticatedAction

trait Application extends Controller {
  def index = AuthenticatedAction { request =>
    Redirect(routes.Assets.at("index.html"))
  }
}
object Application extends Application
