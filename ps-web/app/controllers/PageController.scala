package controllers

import javax.inject.{ Inject, Singleton }

import play.api.Environment
import play.api.http.Writeable
import play.api.mvc.DefaultActionBuilder

/**
 * 页面
 * Created by jingyang on 15/7/17.
 */
@Singleton
class PageController @Inject() (
  webTools: WebTools,
  environment: Environment,
  userAction: UserAction,
  action: DefaultActionBuilder) extends BaseController {

  def signin = signPage(views.html.account.signin())

  def signup = signPage(views.html.account.signup())

  def homeMain = userAction { request =>
    Ok(views.html.home.main(request.userToken))
  }

  private def signPage[C](content: C)(implicit writeable: Writeable[C]) = action { implicit request =>
    webTools.getUserToken
      .map {
        case Right(_) =>
          Redirect(controllers.routes.PageController.homeMain())
        case Left(key) =>
          Ok(content).withCookies(webTools.discardingSession(key))
      }
      .getOrElse(Ok(content))
  }

}
