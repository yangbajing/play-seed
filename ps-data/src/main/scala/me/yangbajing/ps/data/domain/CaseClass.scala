package me.yangbajing.ps.data.domain

import play.api.libs.json.Json

/**
 * Created by Yang Jing (yangbajing@gmail.com) on 2016-01-04.
 */
case class CaseClass(
  a: String,
  b: String = "",
  c: String = "",
  d: String = "",
  e: String = "",
  f: String = "",
  g: String = "",
  h: String = "",
  i: String = "",
  j: String = "",
  k: String = "",
  //                     l: String = "",
  //                     m: String = "",
  //                     n: String = "",
  //                     o: String = "",
  //                     p: String = "",
  //                     q: String = "",
  //                     r: String = "",
  //                     s: String = "",
  //                     t: String = "",
  //                     u: String = "",
  //                     v: String = "",
  //                     w: String = "",
  //                     x: String = "",
  //                     y: String = "",
  z: String = "") {
  def this() {
    this("")
  }
}

object CaseClass {
  implicit val jsonFormats = Json.format[CaseClass]
}
