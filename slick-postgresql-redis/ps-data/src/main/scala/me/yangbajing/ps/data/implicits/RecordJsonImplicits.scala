package me.yangbajing.ps.data.implicits

import me.yangbajing.ps.data.record.User
import play.api.libs.json.{Json, Format}

/**
 * play json format
 * Created by jingyang on 15/7/16.
 */
trait RecordJsonImplicits {
  implicit def __userFormat: Format[User] = Json.format[User]
}

object RecordJsonImplicits extends RecordJsonImplicits
