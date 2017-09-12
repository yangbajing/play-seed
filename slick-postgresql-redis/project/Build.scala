import _root_.sbt.Keys._
import _root_.sbt._
import play.sbt.Play.autoImport._
import play.sbt.PlayScala
import play.twirl.sbt.Import.TwirlKeys

object Build extends Build {

  import BuildSettings._
  import Dependencies._

  override lazy val settings = super.settings :+ {
    shellPrompt := (s => Project.extract(s).currentProject.id + " > ")
  }

  ///////////////////////////////////////////////////////////////
  // playSeed project
  ///////////////////////////////////////////////////////////////
  lazy val playSeed = Project("play-seed-root", file("."))
    .aggregate(psWeb, psBusiness, psData, psUtil)

  ///////////////////////////////////////////////////////////////
  // projects
  ///////////////////////////////////////////////////////////////
  lazy val psWeb = Project("ps-web", file("ps-web"))
    .enablePlugins(PlayScala)
    .dependsOn(psBusiness, psData)
    .settings(basicSettings: _*)
    .settings(
      TwirlKeys.templateImports ++= Seq("me.yangbajing.ps.data.record._", "me.yangbajing.ps.data.domain._"),
      PlayKeys.playDefaultPort := 58082,
      libraryDependencies ++= (
        __compile(_scalaModules) ++
          __compile(_scalaLogging) ++
          __compile(json) ++
          __compile(ws) ++
          __compile(_slf4j) ++
          __compile(_logback))
    )

  lazy val psBusiness = Project("ps-business", file("ps-business"))
    .dependsOn(psData, psUtil % DependsConfigure)
    .settings(basicSettings: _*)
    .settings(
      libraryDependencies ++= (
        __provided(_play) ++
          __compile(_redisClient)))

  lazy val psData = Project("ps-data", file("ps-data"))
    .dependsOn(psUtil % DependsConfigure)
    .settings(basicSettings: _*)
    .settings(
      libraryDependencies ++= (
        __provided(json) ++
          __provided(ws) ++
          __provided(_play) ++
          __compile(_postgresql) ++
          __compile(_hikariCP) ++
          __compile(_slick) ++
          __compile(_slickPg) ++
          __compile(_playSlick)))

  lazy val psUtil = Project("ps-util", file("ps-util"))
    .settings(basicSettings: _*)
    .settings(
      libraryDependencies ++= (
        __provided(_commonCodecs) ++
          __provided(_commonsLang3) ++
          __provided(json) ++
          __provided(ws) ++
          __provided(_play) ++
          __compile(_bouncycastle) ++
          __compile(_akkaActor) ++
          __compile(_akkaSlf4j)))

}

