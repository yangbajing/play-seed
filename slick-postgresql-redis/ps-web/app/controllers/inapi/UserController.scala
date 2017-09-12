package controllers.inapi

import javax.inject.Inject

import controllers.{BaseController, WebTools}
import me.yangbajing.ps.business.service.UserService
import me.yangbajing.ps.data.domain.{OwnerToken, RegisterParam}
import me.yangbajing.ps.util.Constants
import play.api.libs.json.{JsObject, Json}
import play.api.mvc.Action

/**
  * User API
  * Created by jingyang on 15/7/16.
  */
class UserController @Inject()(userService: UserService,
                               webTools: WebTools) extends BaseController {

  def signup = Action.async(parse.json.map(_.as[RegisterParam])) { request =>
    userService.register(request.body).map { case (user, email, phone) =>
      val bo = Json.toJson(user).asInstanceOf[JsObject] ++ Json.obj("email" -> email, "phone" -> phone)
      Ok(bo)
    }
  }

  def signin() = Action.async(parse.json.map(_.as[RegisterParam])) { request =>
    userService.signin(request.body).map {
      case Some((user, email, phone)) =>
        val bo = Json.toJson(user).asInstanceOf[JsObject] ++ Json.obj("email" -> email, "phone" -> phone)
        val cookie = webTools.createSession(OwnerToken(user.id, email, phone, (user.attrs \ Constants.NICK).asOpt[String]))
        Ok(bo).withCookies(cookie)
      case None =>
        NotFound
    }.recover {
      case e: Exception =>
        logger.debug(e.toString, e)
        Unauthorized
    }
  }

}
