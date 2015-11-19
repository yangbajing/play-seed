package utils

import com.typesafe.scalalogging.StrictLogging
import controllers.WebTools
import me.yangbajing.ps.setting.Settings
import me.yangbajing.ps.util.Constants
import play.api.mvc.{Filter, RequestHeader, Result}

import scala.concurrent.Future

class FilterAfterForToken() extends Filter with StrictLogging {
  val matchAll = Settings.filterAfterToken.matchAll
  val paths = Settings.filterAfterToken.paths
  val excludePaths = Settings.filterAfterToken.excludePaths

  override def apply(nextFilter: (RequestHeader) => Future[Result])(rh: RequestHeader): Future[Result] = {
    import play.api.libs.concurrent.Execution.Implicits.defaultContext

    nextFilter(rh).flatMap { result =>
      logger.debug("current path: " + rh.path)
      rh.path match {
        case path if !excludePaths.contains(path) && paths.exists(apiPath => path.startsWith(apiPath)) =>
          val tokenOpt = WebTools.getUserToken(rh)
          logger.debug("before tokenFilter: " + tokenOpt)

          if (matchAll) {
            if (tokenOpt.isEmpty)
              throw new IllegalAccessException("token not exists")
          }

          tokenOpt.map { future =>
            future.map {
              case Right(token) =>
                // XXX 重设token
                result.withCookies(WebTools.createCookie(Constants.PS_TOKEN_OWNER, token.toValue))

              case Left(key) =>
                result.withCookies(WebTools.discardingSession(key))
            }
          } getOrElse Future.successful(result)

        case _ =>
          Future.successful(result)
      }
    }
  }
}

