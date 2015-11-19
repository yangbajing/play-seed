package me.yangbajing.ps.data

import me.yangbajing.UnitWordSpec
import me.yangbajing.ps.data.record.Schemas
import org.scalatest.BeforeAndAfterAll

abstract class UnitDBSpec extends UnitWordSpec with BeforeAndAfterAll {
  override protected def afterAll(): Unit = {
    Schemas.db.close()
  }
}
