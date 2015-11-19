package me.yangbajing.ps.business.service

import me.yangbajing.ps.data.domain.RegisterParam
import me.yangbajing.ps.data.repo.UserRepo

import scala.concurrent.{Future, ExecutionContext}

/**
 * 用户服务
 * Created by jingyang on 15/7/16.
 */
object UserService {
  def apply()(implicit ec: ExecutionContext): UserService = new UserService()
}

class UserService private()(implicit ec: ExecutionContext) {
  def signin(param: RegisterParam): Future[Option[UserRepo.UserInfo]] = {
    if (param.email.isDefined)
      userRepo.signinByEmail(param.email.get, param.password)
    else if (param.phone.isDefined)
      userRepo.signinByPhone(param.phone.get, param.password)
    else
      throw new IllegalArgumentException("email和phone至少传一个")
  }

  def register(param: RegisterParam): Future[UserRepo.UserInfo] = {
    userRepo.register(param.email, param.phone, param.password)
  }

  val userRepo = UserRepo()
}
