package utils

import javax.inject.{Inject, Provider}

import com.typesafe.scalalogging.StrictLogging
import controllers.WebTools
import play.api.http.DefaultHttpErrorHandler
import play.api.libs.json.Json
import play.api.mvc.{RequestHeader, Result, Results}
import play.api.routing.Router
import play.api.{Configuration, Environment, OptionalSourceMapper}

import scala.concurrent.Future

/**
  * Created by Yang Jing (yangbajing@gmail.com) on 2016-03-07.
  */
class ErrorHandler @Inject()(webTools: WebTools,
                             env: Environment,
                             config: Configuration,
                             sourceMapper: OptionalSourceMapper,
                             router: Provider[Router]
                            ) extends DefaultHttpErrorHandler(env, config, sourceMapper, router) with StrictLogging {

  override def onServerError(request: RequestHeader, ex: Throwable): Future[Result] = {
    request.path match {
      case s if s.startsWith("/inapi/") =>
        logger.error(ex.toString, ex)
        Future.successful {
          Results.InternalServerError(Json.obj("code" -> -1, "message" -> ex.getLocalizedMessage)).
            withCookies(webTools.discardingSession())
        }

      case _ =>
        super.onServerError(request, ex)
    }
  }

}
