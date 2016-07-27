//package me.yangbajing.ps.data.record
//
//import java.time.LocalDateTime
//
//import me.yangbajing.ps.data.PsSqlDriver$
//import me.yangbajing.ps.types.OwnerStatus._
//import play.api.libs.json.JsValue
//
///**
//  * Created by Yang Jing (yangbajing@gmail.com) on 2016-07-27.
//  */
//trait DatabaseModule {
//  val driver: PsSqlDriver
//
//  import driver.api._
//
//  class TableUser(tag: Tag) extends Table[User](tag, "t_user") {
//    val id = column[Long]("id", O.PrimaryKey)
//    val attrs = column[JsValue]("attrs")
//    val status = column[OwnerStatus]("status")
//    val created_at = column[LocalDateTime]("created_at")
//
//    def * = (id, attrs, status, created_at) <> (User.tupled, User.unapply)
//  }
//
//  class TableCredential(tag: Tag) extends Table[Credential](tag, "t_credential") {
//    val id = column[Long]("id", O.PrimaryKey, O.AutoInc)
//    val email = column[Option[String]]("email")
//    val phone = column[Option[String]]("phone")
//    val salt = column[Array[Byte]]("salt")
//    val password = column[Array[Byte]]("password")
//    val created_at = column[LocalDateTime]("created_at")
//
//    def * = (id.?, email, phone, salt, password, created_at) <> (Credential.tupled, Credential.unapply)
//  }
//
//  def tCredential = TableQuery[TableCredential]
//
//  def tUser = TableQuery[TableUser]
//
//  def schema =
//    tCredential.schema ++
//      tUser.schema
//}
