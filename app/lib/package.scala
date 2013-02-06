package lib


package object mvc {

  trait AuthenticatedActionBuilder {

    import play.api.mvc._
    import play.api.mvc.BodyParsers._
    import play.api.mvc.Results.Redirect
    import play.module.oauth2.GoogleOAuth2

    def authenticate[A](request: Request[A]): Option[String] = request.session.get("login")

    def apply[A](bodyParser: BodyParser[A])(block: Request[A] => Result): Action[A] = new Action[A] {
      def parser = bodyParser

      def apply(f: Request[A]) = {
        block(f)

      }
    }

    def apply(block: Request[AnyContent] => Result): Action[AnyContent] = apply(parse.anyContent)(block)

    def apply(block: => Result): Action[AnyContent] = apply(_ => block)
  }

  object AuthenticatedAction extends AuthenticatedActionBuilder

}
