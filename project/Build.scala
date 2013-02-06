import sbt._
import PlayProject._
import Keys._

object BuildSettings {
  val buildVersion = "1.0-SNAPSHOT"

  val buildSettings = Defaults.defaultSettings ++ Seq(
    organization := "eu.byjean",
    version := buildVersion,
    scalaVersion := "2.10",
    crossScalaVersions := Seq("2.10"),
    crossVersion := CrossVersion.binary,
    shellPrompt := ShellPrompt.buildShellPrompt
  )
}

// Shell prompt which show the current project,
// git branch and build version
object ShellPrompt {

  object devnull extends ProcessLogger {
    def info(s: => String) {}

    def error(s: => String) {}

    def buffer[T](f: => T): T = f
  }

  def currBranch = (
    ("git status -sb" lines_! devnull headOption)
      getOrElse "-" stripPrefix "## "
    )

  val buildShellPrompt = {
    (state: State) => {
      val currProject = Project.extract(state).currentProject.id
      "%s:%s:%s> ".format(
        currProject, currBranch, BuildSettings.buildVersion
      )
    }
  }
}

object ApplicationBuild extends Build {
  import BuildSettings._
  val appName = "xpua"
  val appVersion = "1.0"

  val byjean = "Byjean releases" at "http://repo.byjean.eu/releases/"
  val byjean_snapshots = "Byjean snapshots" at "http://repo.byjean.eu/snapshots/"
  val typesafe="Typesafe repository releases" at "http://repo.typesafe.com/typesafe/releases/"
  val sgodbillon="sgodbillon" at "https://oss.sonatype.org/content/repositories/snapshots"
  val appDependencies = { Seq(
    "org.xerial" % "sqlite-jdbc" % "3.7.2"
    , "play" %% "play-java-jpa" % "2.1-RC3" // temporary fix for cloudfoundry deploy https://cloudfoundry.atlassian.net/browse/CF-235
    , "org.reactivemongo" %% "play2-reactivemongo" % "0.9-SNAPSHOT"
    , "be.nextlab" %% "neo4j-rest-play-plugin" % "0.0.4-SNAPSHOT"
    , "eu.byjean" %% "play2-oauth2" % "1.0-SNAPSHOT"
    , "org.neo4j" % "neo4j" % "1.8.1"
  ) }

  val main = play.Project (appName, appVersion, appDependencies ).settings(
    resolvers := Seq(
      byjean
    , sgodbillon
    , byjean_snapshots
    , typesafe
    ),

    unmanagedBase <<= baseDirectory { base => base / "lib" }

  )

}
