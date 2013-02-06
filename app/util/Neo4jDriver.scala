package util

import model.model.User

import org.neo4j.graphdb.{Node, Transaction, GraphDatabaseService}
import org.neo4j.graphdb.factory.GraphDatabaseFactory
import org.neo4j.cypher.javacompat.{ExecutionResult, ExecutionEngine}

object Neo4jDriver {

  // TODO use true database
  implicit val graphDb: GraphDatabaseService = {
    val DB_NAME: String = "xpua.gph"
    new GraphDatabaseFactory().newEmbeddedDatabase(DB_NAME)
  }

  private def inTransactionWithResult[A <: AnyRef](process: GraphDatabaseService => A): Option[A] = {
    val tx: Transaction = graphDb.beginTx()

    try {
      val result = process(graphDb)

      tx.success()

      Some(result)
    } catch {
      case _: Exception => tx.failure(); None
    } finally {
      tx.finish()
    }
  }

  private def inTransaction(process: GraphDatabaseService => Unit) {
    val tx: Transaction = graphDb.beginTx()

    try {
      process(graphDb)

      tx.success()
    } catch {
      case _: Exception => tx.failure()
    } finally {
      tx.finish()
    }
  }

  def findUserById(id: String): Option[User] = {
    inTransactionWithResult {
      graphDb =>
        val engine: ExecutionEngine = new ExecutionEngine(graphDb)

        val result: ExecutionResult = engine.execute(s"START user=node:node_auto_index(id = ${id}) RETURN user")

        val userNode: Node = result.columnAs("user").next()

        User(
          userNode.getProperty("id").asInstanceOf[String]
          , userNode.getProperty("name").asInstanceOf[String]
          , None
          , userNode.getProperty("email").asInstanceOf[String]
        )
    }
  }

  def saveUser(user: User) {
    inTransaction {
      graphDb =>
        val userNode: Node = graphDb.createNode()

        userNode.setProperty("id", user.id)
        userNode.setProperty("name", user.name)
        userNode.setProperty("email", user.email)
        null
    }
  }

}
