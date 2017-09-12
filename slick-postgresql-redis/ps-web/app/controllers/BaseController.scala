package controllers

import com.typesafe.scalalogging.LazyLogging
import me.yangbajing.ps.data.implicits.JsonImplicits
import play.api.libs.concurrent.Execution
import play.api.mvc.Controller

trait BaseController extends Controller with JsonImplicits with LazyLogging {
  this: Controller =>

  implicit def __ec = Execution.Implicits.defaultContext
}
