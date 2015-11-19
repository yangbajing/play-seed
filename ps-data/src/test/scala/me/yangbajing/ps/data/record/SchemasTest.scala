package me.yangbajing.ps.data.record

import me.yangbajing.ps.data.MyDriver.api._
import me.yangbajing.ps.data.UnitDBSpec
import me.yangbajing.ps.data.record.Schemas._

/**
 * Schemas Test
 * Created by jingyang on 15/7/17.
 */
class SchemasTest extends UnitDBSpec {
  "Schemas ddl" should {
    "drop tables" in {
      db.run(schema.reverse.reduce(_ ++ _).drop.transactionally)
    }

    "create tables" in {
      db.run(schema.reduce(_ ++ _).create.transactionally)
    }
  }
}
