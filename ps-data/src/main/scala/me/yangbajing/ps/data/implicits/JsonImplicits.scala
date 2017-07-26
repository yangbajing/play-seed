package me.yangbajing.ps.data.implicits

import me.yangbajing.ps.data.domain.{ RegisterParam, OwnerToken }
import play.api.libs.json.{ Format, Json }

trait JsonImplicits extends RecordJsonImplicits {
  implicit def __registerParamFormats: Format[RegisterParam] = Json.format[RegisterParam]
  implicit def __userTokenFormats: Format[OwnerToken] = Json.format[OwnerToken]
}

object JsonImplicits extends JsonImplicits
