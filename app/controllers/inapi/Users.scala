package controllers.inapi

import com.typesafe.scalalogging.StrictLogging
import controllers.{BaseController, WebTools}
import me.yangbajing.ps.business.service.UserService
import me.yangbajing.ps.data.domain.{OwnerToken, RegisterParam}
import me.yangbajing.ps.util.Constants
import play.api.libs.json.{JsObject, Json}
import play.api.mvc.{Action, Controller}

/**
 * User API
 * Created by jingyang on 15/7/16.
 */
object Users extends Controller with BaseController with StrictLogging {

  def signup = Action.async(parse.json.map(_.as[RegisterParam])) { request =>
    UserService().register(request.body).map { case (user, email, phone) =>
      val bo = Json.toJson(user).asInstanceOf[JsObject] ++ Json.obj("email" -> email, "phone" -> phone)
      Ok(bo)
    }
  }

  def signin() = Action.async(parse.json.map(_.as[RegisterParam])) { request =>
    UserService().signin(request.body).map {
      case Some((user, email, phone)) =>
        val bo = Json.toJson(user).asInstanceOf[JsObject] ++ Json.obj("email" -> email, "phone" -> phone)
        val cookie = WebTools.createSession(OwnerToken(user.id.get, email, phone, user.attrs.\(Constants.NICK).asOpt[String]))
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
