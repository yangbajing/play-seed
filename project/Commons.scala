import play.core.PlayVersion
import sbt.Keys._
import sbt._

object Commons {

  import Dependencies._

  def basicSettings = Seq(
    version := "3.0.0",
    homepage := Some(new URL("https://github.com/yangbajing/play-seed")),
    organization := "me.yangbajing",
    organizationHomepage := Some(new URL("http://www.yangbajing.me")),
    startYear := Some(2015),
    scalaVersion := "2.11.11",
    scalacOptions := Seq(
      "-encoding", "utf8",
      //"-Ylog-classpath",
      "-feature",
      "-unchecked",
      "-deprecation" /*,
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

object Formatting {

  import scalariform.formatter.preferences._
  import com.typesafe.sbt.SbtScalariform
  import com.typesafe.sbt.SbtScalariform.ScalariformKeys

  val BuildConfig = config("build") extend Compile
  val BuildSbtConfig = config("buildsbt") extend Compile

//  val formattingPreferences = {
//    ScalariformKeys.preferences.value
//      .setPreference(DanglingCloseParenthesis, Preserve)
//      .setPreference(IndentSpaces, 2)
//      .setPreference(DoubleIndentConstructorArguments, true)
//      .setPreference(NewlineAtEndOfFile, true)
//      .setPreference(SpacesAroundMultiImports, false)
//  }

  // invoke: build:scalariformFormat
  val buildFileSettings: Seq[Setting[_]] = SbtScalariform.scalariformSettings ++
//    inConfig(BuildConfig)(SbtScalariform.configScalariformSettings) ++
//    inConfig(BuildSbtConfig)(SbtScalariform.configScalariformSettings) ++
    Seq(
//      scalaSource in BuildConfig := baseDirectory.value / "project",
//      scalaSource in BuildSbtConfig := baseDirectory.value,
//      includeFilter in(BuildConfig, ScalariformKeys.format) := ("*.scala": FileFilter),
//      includeFilter in(BuildSbtConfig, ScalariformKeys.format) := ("*.sbt": FileFilter),
//      ScalariformKeys.format in BuildConfig := {
//        val x = (ScalariformKeys.format in BuildSbtConfig).value
//        (ScalariformKeys.format in BuildConfig).value
//      },
      ScalariformKeys.preferences := ScalariformKeys.preferences.value
        .setPreference(SpacesAroundMultiImports, false)
        .setPreference(DanglingCloseParenthesis, Preserve)
        .setPreference(IndentSpaces, 2)
        .setPreference(DoubleIndentConstructorArguments, true)
        .setPreference(NewlineAtEndOfFile, true)
    )

}

object Dependencies {

  val DependsConfigure = "compile->compile;test->test"

  def __compile(dep: ModuleID): Seq[ModuleID] = __compile(Seq(dep))

  def __compile(deps: Seq[ModuleID]): Seq[ModuleID] = deps map (_ % "compile")

  def __provided(dep: ModuleID): Seq[ModuleID] = __provided(Seq(dep))

  def __provided(deps: Seq[ModuleID]): Seq[ModuleID] = deps map (_ % "provided")

  def __test(dep: ModuleID): Seq[ModuleID] = __test(Seq(dep))

  def __test(deps: Seq[ModuleID]): Seq[ModuleID] = deps map (_ % "test")

  def __runtime(dep: ModuleID): Seq[ModuleID] = __runtime(Seq(dep))

  def __runtime(deps: Seq[ModuleID]): Seq[ModuleID] = deps map (_ % "runtime")

  def __container(dep: ModuleID): Seq[ModuleID] = __container(Seq(dep))

  def __container(deps: Seq[ModuleID]): Seq[ModuleID] = deps map (_ % "container")

  val _scalaModules = Seq(
    "org.scala-lang.modules" %% "scala-xml" % "1.0.6",
    "org.scala-lang.modules" %% "scala-parser-combinators" % "1.0.6"
  ).map(_.exclude("org.scala-lang", "scala-library").
    exclude("org.scala-lang", "scala-compiler").
    exclude("org.scala-lang", "scala-reflect"))

  private val verScala = "2.11.11"
  val _scala = Seq(
    "org.scala-lang" % "scala-library" % verScala,
    "org.scala-lang" % "scala-compiler" % verScala,
    "org.scala-lang" % "scala-reflect" % verScala
  )

  private val verAkka = "2.5.3"
  val _akkaActor = "com.typesafe.akka" %% "akka-actor" % verAkka
  val _akkaRemote = "com.typesafe.akka" %% "akka-remote" % verAkka
  val _akkaSlf4j = "com.typesafe.akka" %% "akka-slf4j" % verAkka
  val _akkaTestkit = Seq(
    "com.typesafe.akka" %% "akka-testkit" % verAkka)

  val _akkaStream = "com.typesafe.akka" %% "akka-stream" % verAkka

  val slickVersion = "3.2.1"
  val _slick = Seq(
    "com.typesafe.slick" %% "slick" % slickVersion,
    ("com.typesafe.slick" %% "slick-hikaricp" % slickVersion).exclude("com.zaxxer", "HikariCP-java6")
  )

  val verSlickPg = "0.15.2"
  val _slickPg = Seq(
    "com.github.tminglei" %% "slick-pg" % verSlickPg,
    ("com.github.tminglei" %% "slick-pg_play-json" % verSlickPg).exclude("com.typesafe.play", "play-json_2.11")
  )

  val _play = "com.typesafe.play" %% "play" % PlayVersion.current
  val _playJson = "com.typesafe.play" %% "play-json" % PlayVersion.current
  val _playTest = "com.typesafe.play" %% "play-test" % PlayVersion.current
  val _scalatestplusPlay = ("org.scalatestplus.play" %% "scalatestplus-play" % "3.1.1").excludeAll(ExclusionRule("com.typesafe.play"))

  val _playSlick = ("com.typesafe.play" %% "play-slick" % "3.0.0").excludeAll(ExclusionRule("com.typesafe.slick"))

  val _scalatest = "org.scalatest" %% "scalatest" % "3.0.3"

  val _redisClient = ("net.debasishg" %% "redisclient" % "3.4").excludeAll(ExclusionRule("com.typesafe.akka"))

  val _typesafeConfig = "com.typesafe" % "config" % "1.3.1"

  val _scalaLogging = ("com.typesafe.scala-logging" %% "scala-logging" % "3.7.2").exclude("org.scala-lang", "scala-library").exclude("org.scala-lang", "scala-reflect")

  val _slf4j = "org.slf4j" % "slf4j-api" % "1.7.25"

  val _logback = "ch.qos.logback" % "logback-classic" % "1.2.3"

  val _bouncycastle = "org.bouncycastle" % "bcprov-jdk15on" % "1.57"

  val _commonsLang3 = "org.apache.commons" % "commons-lang3" % "3.6"

  val _commonCodecs = "commons-codec" % "commons-codec" % "1.10"

  val _postgresql = "org.postgresql" % "postgresql" % "42.1.3"

  val _hikariCP = "com.zaxxer" % "HikariCP" % "2.6.3"

  val _patchca = "com.github.bingoohuang" % "patchca" % "0.0.1"
}
