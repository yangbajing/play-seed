package controllers

import javax.inject.Inject

import me.yangbajing.ps.business.service.CacheService
import me.yangbajing.ps.data.domain.OwnerToken
import play.api.mvc._

import scala.concurrent.{ ExecutionContext, Future }

case class UserTokenRequest[A](userToken: OwnerToken, request: Request[A]) extends WrappedRequest[A](request)

class UserAction @Inject() (
  cacheService: CacheService,
  webTools: WebTools,
  val parser: BodyParsers.Default)(
  implicit
  val executionContext: ExecutionContext) extends ActionBuilder[UserTokenRequest, AnyContent] with ActionRefiner[Request, UserTokenRequest] {

  override protected def refine[A](request: Request[A]): Future[Either[Result, UserTokenRequest[A]]] = {
    val result = webTools.getUserToken(request)
      .map {
        case Right(token) =>
          Right(UserTokenRequest(token, request))
        case Left(key) =>
          cacheService.remove(key)
          Left(Results.Redirect(controllers.routes.PageController.signin()))
      }
      .getOrElse(Left(Results.Redirect(controllers.routes.PageController.signin())))
    Future.successful(result)
  }

}
