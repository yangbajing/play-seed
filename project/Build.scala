import _root_.sbt.Keys._
import _root_.sbt._
import com.thoughtworks.sbtApiMappings.ApiMappings
import play.routes.compiler.InjectedRoutesGenerator
import play.sbt.Play.autoImport._
import play.sbt.PlayScala
import play.sbt.routes.RoutesKeys

object Build extends Build {
  final val DependsConfigure = "test->test;compile->compile"

  import BuildSettings._
  import Dependencies._

  override lazy val settings = super.settings :+ {
    shellPrompt := (s => Project.extract(s).currentProject.id + " > ")
  }

  ///////////////////////////////////////////////////////////////
  // playSeed project
  ///////////////////////////////////////////////////////////////
  lazy val playSeed = Project("playSeed", file("."))
    .enablePlugins(PlayScala)
    .enablePlugins(ApiMappings)
    .aggregate(psPlatform, psUtil, psData)
    .dependsOn(psPlatform % DependsConfigure, psData % DependsConfigure, psUtil % DependsConfigure)
    .settings(basicSettings: _*)
    .settings(
      description := "play-seed",
      //TwirlKeys.templateImports ++= Seq("me.yangbajing.ps.data.record._", "me.yangbajing.ps.data.domain._"),
      PlayKeys.playDefaultPort := 58082,
      RoutesKeys.routesGenerator := InjectedRoutesGenerator,
      libraryDependencies ++= (
        __compile(_scala) ++
          __compile(_scalaModules) ++
          __compile(_scalaLogging) ++
          __compile(_slf4j) ++
          __compile(_logback)),
      libraryDependencies ~= {
        _ map {
          case m if m.organization == "com.typesafe.play" =>
            m.exclude("commons-logging", "commons-logging").
              exclude("com.typesafe.akka", "akka-actor").
              exclude("com.typesafe.akka", "akka-slf4j").
              exclude("org.scala-lang", "scala-library").
              exclude("org.scala-lang", "scala-compiler").
              exclude("org.scala-lang", "scala-reflect").
              exclude("org.scala-lang.modules", "scala-xml").
              exclude("org.scala-lang.modules", "scala-parser-combinators").
              excludeAll()
          case m => m
        }
      })

  ///////////////////////////////////////////////////////////////
  // projects
  ///////////////////////////////////////////////////////////////
  lazy val psPlatform = Project("ps-platform", file("ps-platform"))
    .enablePlugins(ApiMappings)
    .dependsOn(psData % DependsConfigure, psUtil % DependsConfigure)
    .settings(basicSettings: _*)
    .settings(
      description := "ps-platform",
      scalacOptions += "-Ywarn-unused-import",
      libraryDependencies ++= (
        __provided(cache) ++
          __provided(_play) ++
          __compile(_redisClient) ++
          __compile(_akkaActor) ++
          __compile(_akkaSlf4j)))

  lazy val psData = Project("ps-data", file("ps-data"))
    .enablePlugins(ApiMappings)
    .dependsOn(psUtil % DependsConfigure)
    .settings(basicSettings: _*)
    .settings(
      description := "ps-data",
      scalacOptions += "-Ywarn-unused-import",
      libraryDependencies ++= (
        __provided(_commonCodecs) ++
          __provided(_commonsLang3) ++
          __provided(_playJson) ++
          __provided(_akkaActor) ++
          __provided(_akkaSlf4j) ++
          __compile(_slick) ++
          __compile(_postgresql) ++
          __compile(_hikariCP) ++
          __compile(_slickPg)))

  lazy val psUtil = Project("ps-util", file("ps-util"))
    .enablePlugins(ApiMappings)
    .settings(basicSettings: _*)
    .settings(
      description := "ps-util",
      scalacOptions += "-Ywarn-unused-import",
      libraryDependencies ++= (
        __provided(_commonCodecs) ++
          __provided(_commonsLang3) ++
          __provided(_playJson) ++
          __compile(_bouncycastle)))

}

