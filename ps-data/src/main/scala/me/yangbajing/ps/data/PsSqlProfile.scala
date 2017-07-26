package me.yangbajing.ps.data

import com.github.tminglei.slickpg._
import com.github.tminglei.slickpg.utils.SimpleArrayUtils
import me.yangbajing.ps.types.OwnerStatus
import play.api.libs.json.{ JsValue, Json }
import slick.jdbc.JdbcProfile

/**
 * Slick Postgres JDBC Profile
 * Created by jingyang on 15/7/16.
 */

trait CustomColumnTypes {
  this: JdbcProfile =>

  trait CustomColumnsImplicits {
    self: API =>
    implicit val __ownerStatusColumnType = MappedColumnType.base[OwnerStatus.Value, String](_.toString, OwnerStatus.withName)
  }

}

trait PsSqlProfile
  extends ExPostgresProfile
  with PgDate2Support
  with PgHStoreSupport
  with PgPlayJsonSupport
  with PgArraySupport
  //with PgRangeSupport
  //with PgSearchSupport
  //with PgPostGISSupport
  with CustomColumnTypes {

  object MyAPI
    extends API
    with DateTimeImplicits
    with ArrayImplicits
    with PlayJsonImplicits
    with HStoreImplicits
    with CustomColumnsImplicits {
    implicit val strListTypeMapper = new SimpleArrayJdbcType[String]("text").to(_.toList)
    implicit val playJsonArrayTypeMapper = new AdvancedArrayJdbcType[JsValue](
      pgjson,
      (s) => SimpleArrayUtils.fromString[JsValue](Json.parse)(s).orNull,
      (v) => SimpleArrayUtils.mkString[JsValue](_.toString())(v))
      .to(_.toList)
  }

  override def pgjson: String = "jsonb"

  override val api = MyAPI

  val plainAPI = new API with Date2DateTimePlainImplicits with SimpleArrayPlainImplicits with PlayJsonPlainImplicits with SimpleHStorePlainImplicits with CustomColumnsImplicits

}

object PsSqlProfile extends PsSqlProfile
