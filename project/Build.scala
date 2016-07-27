import _root_.sbt.Keys._
import _root_.sbt._
import com.thoughtworks.sbtApiMappings.ApiMappings
import play.sbt.Play.autoImport._
import play.sbt.PlayScala
import play.twirl.sbt.Import.TwirlKeys

object Build extends Build {
  final val DependsConfigure = "compile->compile;test->test"

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
    .enablePlugins(ApiMappings)
    .dependsOn(psBusiness % DependsConfigure, psData % DependsConfigure, psUtil % DependsConfigure)
    .settings(basicSettings: _*)
    .settings(
      description := "play-web",
      TwirlKeys.templateImports ++= Seq("me.yangbajing.ps.data.record._", "me.yangbajing.ps.data.domain._"),
      PlayKeys.playDefaultPort := 58082,
      libraryDependencies ++= (
        __compile(_scala) ++
          __compile(_scalaModules) ++
          __compile(_scalaLogging) ++
          __compile(json) ++
          __compile(ws) ++
          __compile(_slf4j) ++
          __compile(_logback)),
      libraryDependencies ~= {
        _ map {
          case m if m.organization == "com.typesafe.play" =>
            m.exclude("commons-logging", "commons-logging").
              exclude("com.typesafe.akka", "akka-actor").
              exclude("com.typesafe.akka", "akka-slf4j").
              exclude("org.scala-lang.modules", "scala-xml").
              exclude("org.scala-lang.modules", "scala-parser-combinators").
              excludeAll()
          case m => m
        }
      })

  lazy val psBusiness = Project("ps-business", file("ps-business"))
    .enablePlugins(ApiMappings)
    .dependsOn(psData % DependsConfigure, psUtil % DependsConfigure)
    .settings(basicSettings: _*)
    .settings(
      description := "ps-business",
      scalacOptions += "-Ywarn-unused-import",
      libraryDependencies ++= (
          __provided(_play) ++
          __compile(_redisClient)))

  lazy val psData = Project("ps-data", file("ps-data"))
    .enablePlugins(ApiMappings)
    .dependsOn(psUtil % DependsConfigure)
    .settings(basicSettings: _*)
    .settings(
      description := "ps-data",
      scalacOptions += "-Ywarn-unused-import",
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
    .enablePlugins(ApiMappings)
    .settings(basicSettings: _*)
    .settings(
      description := "ps-util",
      scalacOptions += "-Ywarn-unused-import",
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

