package me.yangbajing.ps.types

import me.yangbajing.ps.util.enums.JsValueEnumUtils
import play.api.libs.json.Format

/**
 * 用户状态
 * Created by jingyang on 15/7/16.
 */
object OwnerStatus extends Enumeration {
  type OwnerStatus = Value

  val ACTIVE = Value("A")
  val INACTIVE = Value("I")
  val DELETE = Value("D")

  implicit def __ownerStatusFormats: Format[OwnerStatus] = JsValueEnumUtils.enumFormat(OwnerStatus)
}
