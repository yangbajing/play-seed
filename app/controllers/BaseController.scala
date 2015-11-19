package controllers

import me.yangbajing.ps.data.implicits.JsonImplicits
import play.api.Play
import play.api.libs.concurrent.Execution
import play.api.mvc.Controller

trait BaseController extends JsonImplicits {
  this: Controller =>

  implicit def __ec = Execution.Implicits.defaultContext

  implicit def __app = Play.current
}
