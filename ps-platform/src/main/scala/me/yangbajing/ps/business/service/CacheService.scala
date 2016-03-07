package me.yangbajing.ps.business.service

import javax.inject.{Inject, Singleton}

import akka.actor.ActorSystem
import akka.util.Timeout
import com.redis.RedisClient
import me.yangbajing.ps.setting.Settings

import scala.concurrent.Future
import scala.concurrent.duration._

/**
  * 缓存服务
  * Created by jingyang on 15/7/17.
  */
@Singleton
class CacheService @Inject()(implicit val actorSystem: ActorSystem) {
  private implicit val timeout = Timeout(10.seconds)

  val client = RedisClient(Settings.cache.host, Settings.cache.port)

  def remove(key: String) = client.del(key)

  def getString(key: String): Future[Option[String]] = client.get(key)

  def set(key: String, value: String, duration: Duration): Future[Boolean] =
    client.setex(key, duration.toSeconds.toInt, value)

}
