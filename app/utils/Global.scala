package utils

import com.typesafe.scalalogging.StrictLogging
import controllers.WebTools
import me.yangbajing.ps.business.service.CacheService
import me.yangbajing.ps.data.record.Schemas
import play.api.libs.concurrent.Akka
import play.api.libs.json.Json
import play.api.mvc.{RequestHeader, Results, WithFilters}
import play.api.{Application, GlobalSettings}

import scala.concurrent.Future

/**
 * 全局配置
 * Created by jingyang on 15/7/17.
 */
object Global extends WithFilters(new FilterAfterForToken()) with GlobalSettings with StrictLogging {

  override def onStart(app: Application) = {
    logger.info("Global.onStarting:")
    CacheService.init(Akka.system(app))
  }

  override def onStop(app: Application) = {
    Schemas.db.close()
    logger.info("Global.onStopped:")
  }

  override def onError(request: RequestHeader, ex: Throwable) = {
    request.path match {
      case s if s.startsWith("/inapi/") =>
        logger.error(ex.toString, ex)
        Future.successful {
          Results.InternalServerError(Json.obj("code" -> -1, "message" -> ex.getLocalizedMessage)).
            withCookies(WebTools.discardingSession())
        }

      case _ =>
        super.onError(request, ex)
    }
  }

  //  override def onBadRequest(request: RequestHeader, error: String) = super.onBadRequest(request, error)
  //
  //  override def onRequestReceived(request: RequestHeader) = super.onRequestReceived(request)
  //
  //  override def onRouteRequest(request: RequestHeader) = {
  //    // TODO save visit history
  //
  //    super.onRouteRequest(request)
  //  }
}
