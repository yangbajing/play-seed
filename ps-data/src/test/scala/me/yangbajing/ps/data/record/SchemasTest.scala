package me.yangbajing.ps.data.record

import me.yangbajing.UnitPlaySpec
import org.scalatestplus.play.OneAppPerSuite

/**
  * Schemas Test
  * Created by jingyang on 15/7/17.
  */
class SchemasTest extends UnitPlaySpec with OneAppPerSuite {
  "Schemas ddl" should {
    val schemas = app.injector.instanceOf[Schemas]
    import schemas.driver.api._

    "drop tables" in {
      val result = schemas.run(schemas.schema.drop.transactionally).futureValue
      result.mustBe(())
    }

    "create tables" in {
      val result = schemas.run(schemas.schema.create.transactionally).futureValue
      result.mustBe(())
    }
  }
}
