package me.yangbajing.ps.data.domain

import me.yangbajing.UnitSpec
import play.api.libs.json.Json

/**
  * Created by Yang Jing (yangbajing@gmail.com) on 2016-01-04.
  */
class CaseClassTest extends UnitSpec {
  "CaseClassTest" should {
    val cc = CaseClass("哈哈")

    "json marshal" in {
      val jv = Json.toJson(cc)
      jv.\("a").as[String] mustBe cc.a
    }

    "json unmarshal" in {
      val s = """{"b":"BB","c":"CC}"""
      val jv = Json.parse(s)
      jv.\("a").asOpt[String] mustBe None
      jv.\("c").as[String] mustBe "CC"
    }
  }

}
