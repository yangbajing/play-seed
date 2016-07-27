import Dependencies._
import sbt.Keys._
import sbt._

object BuildSettings {

  lazy val basicSettings = Seq(
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
      "-deprecation",
      "-explaintypes",
      "-Yno-adapted-args",
      "-Ywarn-dead-code",
      "-Ywarn-unused"
    ),
    javacOptions := Seq(
      "-encoding", "utf8",
      "-deprecation",
      "-Xlint:unchecked",
      "-Xlint:deprecation"
    ),
    resolvers ++= Seq(
      "Typesafe Repository" at "http://repo.typesafe.com/typesafe/releases/",
      "releases" at "http://oss.sonatype.org/content/repositories/releases",
      "maven.mirrorid" at "http://mirrors.ibiblio.org/pub/mirrors/maven2/",
      "snapshots" at "http://oss.sonatype.org/content/repositories/snapshots",
      "Typesafe Snapshots" at "http://repo.typesafe.com/typesafe/snapshots/"),
    libraryDependencies ++= (
      __provided(_slf4j) ++
        __provided(_logback) ++
        __provided(_typesafeConfig) ++
        __provided(_scalaLogging) ++
        __provided(_scala) ++
        __test(_playTest) ++
        __test(_scalatestplusPlay) ++
        __test(_scalatest)),
    sources in(Compile, doc) := Seq.empty,
    publishArtifact in(Compile, packageDoc) := false,
    offline := true
  )

  lazy val noPublishing = Seq(
    publish :=(),
    publishLocal :=()
  )

}

