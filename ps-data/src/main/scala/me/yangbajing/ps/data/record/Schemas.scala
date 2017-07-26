package me.yangbajing.ps.data.record

import java.time.LocalDateTime
import java.util.concurrent.TimeUnit
import javax.inject.{ Inject, Singleton }

import me.yangbajing.ps.data.PsSqlProfile
import me.yangbajing.ps.types.OwnerStatus.OwnerStatus
import play.api.db.slick.DatabaseConfigProvider
import play.api.libs.json.JsValue

import scala.concurrent.Await
import scala.concurrent.duration.{ Duration, FiniteDuration }

@Singleton
class Schemas @Inject() (databaseConfigProvider: DatabaseConfigProvider) /* extends DatabaseModule*/ {
  val databaseConfig = databaseConfigProvider.get[PsSqlProfile]
  val profile: PsSqlProfile = databaseConfig.profile

  import profile.api._

  def exec[R](action: DBIOAction[R, NoStream, Nothing])(implicit duration: FiniteDuration = Duration(10, TimeUnit.SECONDS)) = {
    Await.result(databaseConfig.db.run(action), duration)
  }

  def run[R](action: DBIOAction[R, NoStream, Nothing]) = {
    databaseConfig.db.run(action)
  }

  class TableUser(tag: Tag) extends Table[User](tag, "t_user") {
    val id = column[Long]("id", O.PrimaryKey)
    val attrs = column[JsValue]("attrs")
    val status = column[OwnerStatus]("status")
    val created_at = column[LocalDateTime]("created_at")

    def * = (id, attrs, status, created_at) <> (User.tupled, User.unapply)
  }

  class TableCredential(tag: Tag) extends Table[Credential](tag, "t_credential") {
    val id = column[Long]("id", O.PrimaryKey, O.AutoInc)
    val email = column[Option[String]]("email")
    val phone = column[Option[String]]("phone")
    val salt = column[Array[Byte]]("salt")
    val password = column[Array[Byte]]("password")
    val created_at = column[LocalDateTime]("created_at")

    def * = (id.?, email, phone, salt, password, created_at) <> (Credential.tupled, Credential.unapply)
  }

  def tCredential = TableQuery[TableCredential]

  def tUser = TableQuery[TableUser]

  def schema =
    tCredential.schema ++
      tUser.schema

}
