package controllers

import org.specs2.mutable.{Around, Specification}
import play.api.test.{FakeApplication, FakeRequest}
import play.api.mvc.Result
import org.specs2.execute.AsResult
import play.api.test.Helpers._

class ApplicationSpec extends Specification{
  object runningApplication extends Around {
    override def around[T](t: => T)(implicit evidence$1: AsResult[T]) = {
      running(FakeApplication()) {
        evidence$1.asResult(t)
      }
    }

  }
  "An application" should {
    "be ok on index " in runningApplication {
      val result: Result = Application.index(FakeRequest().withSession("login"->"notempty"))
      1===1
    }
  }
}
