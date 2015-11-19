package me.yangbajing.ps.data.record

import java.time.LocalDateTime

import me.yangbajing.ps.data.MyDriver.api._
import me.yangbajing.ps.types.OwnerStatus.OwnerStatus

/**
 * 保存账号、密码等敏感信息
 * Created by jingyang on 15/7/16.
 */
case class Credential(id: Option[Long],
                      email: Option[String],
                      phone: Option[String],
                      salt: Array[Byte],
                      password: Array[Byte],
                      status: OwnerStatus,
                      created_at: LocalDateTime)

class TableCredential(tag: Tag) extends Table[Credential](tag, "credential") {
  val id = column[Long]("id", O.PrimaryKey, O.AutoInc)
  val email = column[Option[String]]("email")
  val phone = column[Option[String]]("phone")
  val salt = column[Array[Byte]]("salt")
  val password = column[Array[Byte]]("password")
  val status = column[OwnerStatus]("status")
  val created_at = column[LocalDateTime]("created_at")

  def * = (id.?, email, phone, salt, password, status, created_at) <>(Credential.tupled, Credential.unapply)
}
