import Dependencies._
import sbt.Keys._
import sbt._

object BuildSettings {

  val DependsConfigure = "compile->compile;test->test"

  def basicSettings = Seq(
    version := "2.0.0",
    homepage := Some(new URL("https://github.com/yangbajing/play-seed")),
    organization := "me.yangbajing",
    organizationHomepage := Some(new URL("http://www.yangbajing.me")),
    startYear := Some(2015),
    scalaVersion := "2.11.8",
    scalacOptions := Seq(
      "-encoding", "utf8",
      //"-Ylog-classpath",
      "-feature",
      "-unchecked",
      "-deprecation"/*,
      "-explaintypes",
      "-Yno-adapted-args",
      "-Ywarn-dead-code",
      "-Ywarn-unused"*/
    ),
    javacOptions := Seq(
      "-encoding", "utf8",
      "-deprecation",
      "-Xlint:unchecked",
      "-Xlint:deprecation"
    ),
    libraryDependencies ++= (
      __compile(_slf4j) ++
        __compile(_logback) ++
        __compile(_typesafeConfig) ++
        __compile(_scalaLogging) ++
        __compile(_scala) ++
        __test(_playTest) ++
        __test(_scalatestplusPlay) ++
        __test(_scalatest)),
    libraryDependencies ~= {
      _ map {
        case m if m.organization == "com.typesafe.play" =>
          m.exclude("org.scala-lang", "scala-library").
//            exclude("commons-logging", "commons-logging").
//            exclude("com.google.guava", "guava").
//            exclude("org.asynchttpclient", "async-http-client").
//            exclude("com.typesafe.akka", "akka-actor").
//            exclude("com.typesafe.akka", "akka-slf4j").
            //            exclude("org.scala-lang", "scala-compiler").
            //            exclude("org.scala-lang", "scala-reflect").
            exclude("org.scala-lang.modules", "scala-xml").
            exclude("org.scala-lang.modules", "scala-parser-combinators").
            excludeAll()
        case m => m
      }
    },
    sources in(Compile, doc) := Seq.empty,
    publishArtifact in(Compile, packageDoc) := false,
    publishArtifact in packageDoc := false,
    fork := true
  )

  lazy val noPublishing = Seq(
    publish := (),
    publishLocal := ()
  )

}

