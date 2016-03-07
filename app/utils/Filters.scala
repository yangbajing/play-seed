package utils

import javax.inject.Inject

import akka.stream.Materializer
import com.typesafe.scalalogging.StrictLogging
import controllers.WebTools
import me.yangbajing.ps.setting.Settings
import me.yangbajing.ps.util.Constants
import play.api.http.HttpFilters
import play.api.mvc.{EssentialFilter, Filter, RequestHeader, Result}

import scala.concurrent.{ExecutionContext, Future}

class Filters @Inject()(authFilter: FilterAfterForToken) extends HttpFilters {
  override def filters: Seq[EssentialFilter] =
    Seq(authFilter)
}

class FilterAfterForToken @Inject()(webTools: WebTools,
                                    implicit val mat: Materializer,
                                    implicit val ec: ExecutionContext
                                   ) extends Filter with StrictLogging {
  val matchAll = Settings.filterAfterToken.matchAll
  val paths = Settings.filterAfterToken.paths
  val excludePaths = Settings.filterAfterToken.excludePaths

  override def apply(nextFilter: (RequestHeader) => Future[Result])(rh: RequestHeader): Future[Result] = {
    nextFilter(rh).flatMap { result =>
      logger.debug("current path: " + rh.path)
      rh.path match {
        case path if !excludePaths.contains(path) && paths.exists(apiPath => path.startsWith(apiPath)) =>
          val tokenOpt = webTools.getUserToken(rh)
          logger.debug("before tokenFilter: " + tokenOpt)

          if (matchAll) {
            if (tokenOpt.isEmpty)
              throw new IllegalAccessException("token not exists")
          }

          tokenOpt.map { future =>
            future.map {
              case Right(token) =>
                // XXX 重设token
                result.withCookies(webTools.createCookie(Constants.PS_TOKEN_OWNER, token.toValue))

              case Left(key) =>
                result.withCookies(webTools.discardingSession(key))
            }
          } getOrElse Future.successful(result)

        case _ =>
          Future.successful(result)
      }
    }
  }

}

