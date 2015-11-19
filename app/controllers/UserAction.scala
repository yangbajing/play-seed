package controllers

import me.yangbajing.ps.business.service.CacheService
import me.yangbajing.ps.data.domain.OwnerToken
import play.api.mvc._

import scala.concurrent.Future

case class UserTokenRequest[A](userToken: OwnerToken, request: Request[A]) extends WrappedRequest[A](request)

object UserAction extends ActionBuilder[UserTokenRequest] with ActionRefiner[Request, UserTokenRequest] {
  override protected def refine[A](request: Request[A]): Future[Either[Result, UserTokenRequest[A]]] = {
    import CacheService.timeout

    WebTools.getUserToken(request).map { future =>
      future.map {
        case Right(token) =>
          Right(UserTokenRequest(token, request))
        case Left(key) =>
          CacheService().remove(key)
          //println(Constants.PS_TOKEN_OWNER + ": " + key + " timeout ")
          Left(Results.Redirect(controllers.routes.PageController.signin()))
      }(executionContext)
    }.getOrElse {
      Future.successful(Left(Results.Redirect(controllers.routes.PageController.signin())))
    }
  }
}
