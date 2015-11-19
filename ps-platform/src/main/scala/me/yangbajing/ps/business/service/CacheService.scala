package me.yangbajing.ps.business.service

import java.util.concurrent.TimeUnit

import akka.actor.ActorRefFactory
import akka.util.Timeout
import com.redis.RedisClient
import me.yangbajing.ps.setting.{CacheSetting, Settings}

import scala.concurrent.Future
import scala.concurrent.duration.Duration

/**
 * 缓存服务
 * Created by jingyang on 15/7/17.
 */
object CacheService {
  @volatile var _service: CacheService = _

  def init(refFactory: ActorRefFactory): Unit = {
    _service = new CacheService(Settings.cache)(refFactory)
  }

  def apply(): CacheService = _service

  implicit val timeout = Timeout(10, TimeUnit.SECONDS)
}

class CacheService private(cacheSetting: CacheSetting)(implicit refFactory: ActorRefFactory) {

  val client = RedisClient(cacheSetting.host, cacheSetting.port)

  def remove(key: String)(implicit timeout: Timeout) = client.del(key)

  def getString(key: String)(implicit timeout: Timeout): Future[Option[String]] = client.get(key)

  def set(key: String, value: String, duration: Duration)(implicit timeout: Timeout): Future[Boolean] =
    client.setex(key, duration.toSeconds.toInt, value)

}
