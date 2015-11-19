package controllers

import play.api.http.Writeable
import play.api.mvc.{Action, Controller}

import scala.concurrent.Future

/**
 * 页面
 * Created by jingyang on 15/7/17.
 */
object PageController extends Controller with BaseController {
  def signin = signPage(views.html.account.signin())

  def signup = signPage(views.html.account.signup())

  def homeMain = UserAction { request =>
    Ok(views.html.home.main(request.userToken))
  }

  private def signPage[C](content: C)(implicit writeable: Writeable[C]) = Action.async { implicit request =>
    WebTools.getUserToken.map { future =>
      future.map {
        case Right(_) =>
          Redirect(controllers.routes.PageController.homeMain())
        case Left(key) =>
          Ok(content).withCookies(WebTools.discardingSession(key))
      }
    } getOrElse Future.successful(Ok(content))
  }
}
