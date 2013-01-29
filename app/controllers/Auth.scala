package controllers

import play.api.mvc._
import play.api.libs.json._

object Auth extends Controller {

  import play.module.oauth2.GoogleOAuth2
  import scala.concurrent.Future
  import scala.concurrent.ExecutionContext.Implicits.global

  case class User(id: String
                  , email: String
                  , name: String)

  implicit val userReads = Json.reads[User]

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
              user <- GoogleOAuth2.authenticate(code)
            } yield Redirect(routes.Application.index()).withSession("login" -> user.id)
        } getOrElse Future.successful(Unauthorized)
      }
  }

  protected def params[T](key: String)(implicit request: Request[T]) = request.queryString.get(key).flatMap(_.headOption)
}