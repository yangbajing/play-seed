package controllers

import com.typesafe.scalalogging.LazyLogging
import me.yangbajing.ps.data.implicits.JsonImplicits
import play.api.mvc.ControllerHelpers

trait BaseController extends ControllerHelpers with JsonImplicits with LazyLogging {
}
