package me.yangbajing.ps.data.record

import java.util.concurrent.TimeUnit
import javax.inject.{Inject, Singleton}

import me.yangbajing.ps.data.MyDriver
import play.api.db.slick.DatabaseConfigProvider

import scala.concurrent.Await
import scala.concurrent.duration.{Duration, FiniteDuration}

@Singleton
class Schemas @Inject()(databaseConfigProvider: DatabaseConfigProvider) extends DatabaseModule {
  val databaseConfig = databaseConfigProvider.get[MyDriver]
  val driver: MyDriver = databaseConfig.driver

  import driver.api._

  def exec[R](action: DBIOAction[R, NoStream, Nothing])(implicit duration: FiniteDuration = Duration(10, TimeUnit.SECONDS)) = {
    Await.result(databaseConfig.db.run(action), duration)
  }

  def run[R](action: DBIOAction[R, NoStream, Nothing]) = {
    databaseConfig.db.run(action)
  }

}
