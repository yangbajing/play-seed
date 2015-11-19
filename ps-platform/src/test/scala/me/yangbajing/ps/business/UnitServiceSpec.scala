package me.yangbajing.ps.business

import akka.actor.ActorSystem
import me.yangbajing.ps.data.UnitDBSpec

abstract class UnitServiceSpec extends UnitDBSpec {
  implicit val system = ActorSystem("service-spec")

  override protected def beforeAll(): Unit = {
    super.beforeAll()
  }

  override protected def afterAll(): Unit = {
    system.shutdown()
    super.afterAll()
  }
}

