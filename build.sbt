import Commons._
import Dependencies._
import _root_.sbt.Keys._
import _root_.sbt._
import play.sbt.PlayScala
import play.twirl.sbt.Import.TwirlKeys

///////////////////////////////////////////////////////////////
// root project
///////////////////////////////////////////////////////////////
lazy val root = Project("play-seed-root", file("."))
  .aggregate(psWeb, psBusiness, psData, psUtil)
  .settings(Formatting.buildFileSettings: _*)
  .settings(
    shellPrompt in ThisBuild := { state => Project.extract(state).currentRef.project + "> " }
  )

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
        __compile(_playJson) ++
        __compile(ws) ++
        __compile(guice) ++
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
      __provided(_playJson) ++
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
        __provided(_playJson) ++
        __provided(ws) ++
        __provided(_play) ++
        __compile(_bouncycastle) ++
        __compile(_akkaActor) ++
        __compile(_akkaSlf4j)))
