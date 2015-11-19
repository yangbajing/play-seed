package me.yangbajing.ps.data.domain

import me.yangbajing.ps.data.implicits.JsonImplicits
import me.yangbajing.ps.util.Constants
import play.api.libs.json.Json

case class OwnerToken(ownerId: Long, email: Option[String], phone: Option[String], nick: Option[String]) {
  def toValue: String = ownerId.toString + Constants.SPLIT_CHAR +
    email.getOrElse("") + Constants.SPLIT_CHAR +
    phone.getOrElse("") + Constants.SPLIT_CHAR +
    nick.getOrElse("") + Constants.SPLIT_CHAR

  override def toString: String = Json.stringify(Json.toJson(this)(JsonImplicits.__userTokenFormats))
}
