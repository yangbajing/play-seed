package me.yangbajing

import org.scalatest.concurrent.ScalaFutures
import org.scalatest.time.{Millis, Seconds, Span}
import org.scalatestplus.play.PlaySpec

/**
  * Created by Yang Jing (yangbajing@gmail.com) on 2016-07-27.
  */
trait UnitPlaySpec extends PlaySpec with  ScalaFutures {

  override implicit def patienceConfig: PatienceConfig =
    PatienceConfig(timeout = scaled(Span(90, Seconds)), interval = scaled(Span(100, Millis)))

}

