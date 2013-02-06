package controllers

import play.api.mvc._
import play.api.libs.json._

object Auth extends Controller {

  import model.model.User
  import model.model.Formats.userReads

  import play.module.oauth2.GoogleOAuth2
  import scala.concurrent.Future
  import scala.concurrent.ExecutionContext.Implicits.global

  def signin = Action {
    Redirect(GoogleOAuth2.signIn)
  }

  def signout = Action {
    Redirect(routes.Application.index()).withNewSession
  }

  def callback = Action {
    implicit request =>
      Async {
        params("code").map {
          code =>
            for {
              user <- GoogleOAuth2.authenticate[User](code)
            } yield Redirect(routes.Application.index()).withSession("login" -> user.id)
        } getOrElse Future.successful(Unauthorized)
      }
  }

  protected def params[T](key: String)(implicit request: Request[T]) = request.queryString.get(key).flatMap(_.headOption)
}