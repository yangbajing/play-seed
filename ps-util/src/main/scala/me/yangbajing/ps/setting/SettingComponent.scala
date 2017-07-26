package me.yangbajing.ps.setting

import com.typesafe.config.{ ConfigFactory, Config }
import scala.collection.JavaConverters._
import scala.util.Try

import javax.inject.Singleton

/**
 * 配置文件
 * Created by jingyang on 15/7/16.
 */
@Singleton
class SettingComponent {
  val cache = {
    val conf = ConfigFactory.load().getConfig("play-seed.cache")
    CacheSetting(
      getString(conf, "host").get,
      getInt(conf, "port").get,
      getInt(conf, "database").get)
  }

  val filterAfterToken = {
    val conf = ConfigFactory.load().getConfig("play-seed.filter-after-token")
    FilterAfterToken(
      getBoolean(conf, "match-all", false),
      getStringList(conf, "paths"),
      getStringList(conf, "exclude-paths"))
  }

  private def getStringList(conf: Config, name: String): Seq[String] = try {
    conf.getStringList(name).asScala
  } catch {
    case _: Exception =>
      Nil
  }

  private def getString(conf: Config, name: String, default: => String): String = getString(conf, name).getOrElse(default)

  private def getString(conf: Config, name: String): Try[String] = Try(conf.getString(name))

  private def getBoolean(conf: Config, name: String, default: => Boolean): Boolean =
    getBoolean(conf, name).getOrElse(default)

  private def getBoolean(conf: Config, name: String): Try[Boolean] = Try(conf.getBoolean(name))

  private def getInt(conf: Config, name: String, default: => Int): Int = getInt(conf, name).getOrElse(default)

  private def getInt(conf: Config, name: String): Try[Int] = Try(conf.getInt(name))
}

case class Setting(db: Config)

case class FilterAfterToken(matchAll: Boolean, paths: Seq[String], excludePaths: Seq[String])

case class CacheSetting(host: String, port: Int, database: Int)
