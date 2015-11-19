package me.yangbajing.ps.data

import me.yangbajing.ps.types.OwnerStatus
import me.yangbajing.ps.util.Constants
import com.github.tminglei.slickpg._
import com.github.tminglei.slickpg.utils.SimpleArrayUtils
import play.api.libs.json.{JsValue, Json}
import slick.driver.JdbcDriver

/**
 * Slick JDBC Driver
 * Created by jingyang on 15/7/16.
 */

trait MyColumnTypes {
  this: JdbcDriver =>

  trait MyColumnsAPI {
    self: API =>
    implicit val __ownerStatusColumnType = MappedColumnType.base[OwnerStatus.Value, String](_.toString, OwnerStatus.withName)

  }

}

trait MyDriver
  extends ExPostgresDriver
  with PgDate2Support
  with PgHStoreSupport
  with PgPlayJsonSupport
  with PgArraySupport
  //with PgRangeSupport
  //with PgSearchSupport
  //with PgPostGISSupport
  with MyColumnTypes {
  override val pgjson = "jsonb"
  override val api = MyAPI

  object MyAPI
    extends API
    with DateTimeImplicits
    with HStoreImplicits
    with JsonImplicits
    with ArrayImplicits
    //  with RangeImplicits
    //  with SearchImplicits
    //  with PostGISImplicits
    with MyColumnsAPI {
    implicit val strListTypeMapper = new SimpleArrayJdbcType[String]("text").to(_.toList)
    implicit val json4sJsonArrayTypeMapper =
      new AdvancedArrayJdbcType[JsValue](pgjson,
        (s) => SimpleArrayUtils.fromString[JsValue](Json.parse)(s).orNull,
        (v) => SimpleArrayUtils.mkString[JsValue](_.toString())(v)
      ).to(_.toList)

  }
}

object MyDriver extends MyDriver {
}
