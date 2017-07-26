package me.yangbajing.ps.data.record

import java.time.LocalDateTime

/**
 * 保存账号、密码等敏感信息
 * Created by jingyang on 15/7/16.
 */
case class Credential(
  id: Option[Long],
  email: Option[String],
  phone: Option[String],
  salt: Array[Byte],
  password: Array[Byte],
  created_at: LocalDateTime)
