package me.yangbajing.ps.data.record

import java.time.LocalDateTime

import me.yangbajing.ps.data.implicits.RecordJsonImplicits
import me.yangbajing.ps.types.OwnerStatus._
import play.api.libs.json.{JsValue, Json}

/**
 * 用户
 * Created by jingyang on 15/7/16.
 */
case class User(id: Long,
                attrs: JsValue,
                status: OwnerStatus,
                created_at: LocalDateTime) {
  def nick = attrs.\("nick").asOpt[String].getOrElse("")

  override def toString: String = Json.stringify(Json.toJson(this)(RecordJsonImplicits.__userFormat))
}
