package me.yangbajing.ps.data.domain

import me.yangbajing.UnitWordSpec
import play.api.libs.json.Json

/**
  * Created by Yang Jing (yangbajing@gmail.com) on 2016-01-04.
  */
class CaseClassTest extends UnitWordSpec {
  "CaseClassTest" should {
    val cc = CaseClass("哈哈")

    "json marshal" in {
      val jv = Json.toJson(cc)
      jv.\("a").as[String] shouldBe cc.a
    }

    "json unmarshal" in {
      val s = """{"b":"BB","c":"CC}"""
      val jv = Json.parse(s)
      jv.\("a").asOpt[String] shouldBe None
      jv.\("c").as[String] shouldBe "CC"
    }
  }

}
