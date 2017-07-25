import sbt._
import play.core.PlayVersion

object Dependencies {
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

  private val verAkka = "2.4.19"
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
    "com.github.tminglei" %% "slick-pg_date2" % verSlickPg,
    ("com.github.tminglei" %% "slick-pg_play-json" % verSlickPg).exclude("com.typesafe.play", "play-json_2.11")
  )

  val _play = "com.typesafe.play" %% "play" % PlayVersion.current
  val _playTest = "com.typesafe.play" %% "play-test" % PlayVersion.current
  val _scalatestplusPlay = ("org.scalatestplus.play" %% "scalatestplus-play" % "1.5.1").excludeAll(ExclusionRule("com.typesafe.play"))

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

