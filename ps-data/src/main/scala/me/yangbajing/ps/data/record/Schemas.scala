package me.yangbajing.ps.data.record

import me.yangbajing.ps.data.MyDriver.api._

object Schemas {
  val db = Database.forConfig("play-seed.db")

  def tCredential = TableQuery[TableCredential]

  def tUser = TableQuery[TableUser]

  def schema =
    Seq(tCredential.schema,
      tUser.schema)
}
