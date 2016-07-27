package me.yangbajing

import org.scalatest._
import org.scalatest.concurrent.ScalaFutures
import org.scalatest.time.{Millis, Seconds, Span}

trait UnitSpec extends WordSpec with MustMatchers with OptionValues with ScalaFutures {

  override implicit def patienceConfig: PatienceConfig =
    PatienceConfig(timeout = scaled(Span(90, Seconds)), interval = scaled(Span(100, Millis)))

}

