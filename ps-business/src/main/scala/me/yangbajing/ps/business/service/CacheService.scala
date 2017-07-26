package me.yangbajing.ps.business.service

import javax.inject.{ Inject, Singleton }

import akka.actor.ActorSystem
import com.redis.RedisClientPool
import me.yangbajing.ps.setting.SettingComponent

import scala.concurrent.duration._

/**
 * 缓存服务
 * Created by jingyang on 15/7/17.
 */
@Singleton
class CacheService @Inject() (settingComponent: SettingComponent)(implicit actorSystem: ActorSystem) {
  private val pool =
    new RedisClientPool(settingComponent.cache.host, settingComponent.cache.port, settingComponent.cache.database)

  def remove(key: String) = pool.withClient(client => client.del(key))

  def getString(key: String): Option[String] = pool.withClient(client => client.get(key))

  def set(key: String, value: String, duration: Duration): Boolean =
    pool.withClient(client => client.setex(key, duration.toSeconds.toInt, value))

}
