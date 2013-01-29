import sbt._
import PlayProject._
import Keys._

object ApplicationBuild extends Build {

  val appName = "xpua"
  val appVersion = "1.0"

  val byjean = "Byjean releases" at "http://repo.byjean.eu/releases/"
  val byjean_snapshots = "Byjean snapshots" at "http://repo.byjean.eu/snapshots/"
  val typesafe="Typesafe repository releases" at "http://repo.typesafe.com/typesafe/releases/"
  val appDependencies = { Seq(
  "play" %% "play-java-jpa" % "2.1-RC2",
  "eu.byjean" %% "play2-oauth2" % "1.0-SNAPSHOT"
  ) }

  val main = play.Project (appName, appVersion, appDependencies ).settings(
    resolvers := Seq(
      byjean
    , byjean_snapshots
    , typesafe
    )
  )

}
