package me.yangbajing.ps.business

import akka.actor.ActorSystem
import me.yangbajing.ps.data.UnitDBSpec

import scala.concurrent.Await
import scala.concurrent.duration._

abstract class UnitServiceSpec extends UnitDBSpec {
  implicit val system = ActorSystem("service-spec")

  override protected def beforeAll(): Unit = {
    super.beforeAll()
  }

  override protected def afterAll(): Unit = {
    try {
      Await.result(system.terminate(), 10.seconds)
      super.afterAll()
    } catch {
      case e: Exception =>
        e.printStackTrace()
        System.exit(3)
    }
  }
}

