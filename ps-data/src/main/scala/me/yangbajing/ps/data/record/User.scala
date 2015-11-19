package me.yangbajing.ps.data.record

import java.time.LocalDateTime

import me.yangbajing.ps.data.MyDriver.api._
import me.yangbajing.ps.data.implicits.RecordJsonImplicits
import play.api.libs.json.{JsValue, Json}

/**
 * 用户
 * Created by jingyang on 15/7/16.
 */
case class User(id: Option[Long],
                attrs: JsValue,
                created_at: LocalDateTime) {
  def nick = attrs.\("nick").asOpt[String].getOrElse("")

  override def toString: String = Json.stringify(Json.toJson(this)(RecordJsonImplicits.__userFormat))
}

class TableUser(tag: Tag) extends Table[User](tag, "user") {
  val id = column[Long]("id", O.PrimaryKey)
  val attrs = column[JsValue]("attrs")
  val created_at = column[LocalDateTime]("created_at")

  def * = (id.?, attrs, created_at) <>(User.tupled, User.unapply)
}
