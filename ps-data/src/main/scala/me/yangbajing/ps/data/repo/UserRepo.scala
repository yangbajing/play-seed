package me.yangbajing.ps.data.repo

import java.time.LocalDateTime
import javax.inject.Inject

import me.yangbajing.ps.data.record.{ Credential, Schemas, User }
import me.yangbajing.ps.types.OwnerStatus
import me.yangbajing.ps.util.Utils
import play.api.libs.json.{ JsObject, JsValue }

import scala.concurrent.{ ExecutionContext, Future }

/**
 * User数据库访问
 * Created by jingyang on 15/7/16.
 */
object UserRepo {
  type UserInfo = (User, Option[String], Option[String])
}

class UserRepo @Inject() (schemas: Schemas)(implicit ec: ExecutionContext) {

  import schemas.profile.api._
  import schemas._

  import UserRepo.UserInfo

  def findOneById(id: Long): Future[Option[UserInfo]] = {
    val q = for {
      c <- tCredential if c.id === id
      u <- tUser if u.id === c.id
    } yield (u, c.email, c.phone)
    run(q.result).map(_.headOption)
  }

  def update(id: Long, attrs: JsValue): Future[Int] = {
    val action = tUser.filter(_.id === id).map(_.attrs).update(attrs).transactionally
    run(action)
  }

  def register(email: Option[String], phone: Option[String], pwd: String): Future[UserInfo] = {
    require(email.isDefined || phone.isDefined, "email和phone至少指定一个")

    val (salt, password) = Utils.generatePassword(pwd)
    val c = Credential(None, email, phone, salt, password, LocalDateTime.now())
    val u = for {
      cid <- (tCredential returning tCredential.map(_.id)) += c
      ret <- tUser += User(cid, JsObject(Nil), OwnerStatus.ACTIVE, c.created_at)
    } yield (User(cid, JsObject(Nil), OwnerStatus.ACTIVE, c.created_at), email, phone)
    run(u.transactionally)
  }

  def signinByEmail(email: String, pwd: String) = {
    signinBy(Some(email), None, pwd)
  }

  def signinByPhone(phone: String, pwd: String) = {
    signinBy(None, Some(phone), pwd)
  }

  private def signinBy(email: Option[String], phone: Option[String], pwd: String): Future[Option[UserInfo]] = {
    require(email.isDefined || phone.isDefined, "email和phone至少指定一个")

    val q = for {
      c <- tCredential if c.email === email || c.phone === phone
      u <- tUser if u.id === c.id
    } yield (u, c)

    run(q.result).map {
      case (u, c) +: _ =>
        require(java.util.Arrays.equals(Utils.generatePassword(c.salt, pwd), c.password), "密码不匹配")
        Some((u, c.email, c.phone))

      case _ =>
        None
    }
  }

}
