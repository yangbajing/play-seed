package me.yangbajing

import org.scalatest.{EitherValues, OptionValues, Matchers, WordSpec}
import org.scalatest.concurrent.Futures

abstract class UnitWordSpec extends WordSpec with Matchers with OptionValues with EitherValues with Futures {

}
