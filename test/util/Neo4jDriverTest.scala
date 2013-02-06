package util

import org.specs2.mutable.Specification
import model.model.User

class Neo4jDriverTest extends Specification {
  "XPUA database" should {
    "add user" in {
      Neo4jDriver.saveUser(User("1", "toto", None, "toto@titi.com"))

      val user: Option[User] = Neo4jDriver.findUserById("1")

      user must not be None
    }
  }
}
