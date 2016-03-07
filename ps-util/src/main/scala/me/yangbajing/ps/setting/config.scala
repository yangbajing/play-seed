//package me.yangbajing.ps.setting
//
//import com.wacai.config.annotation._
//
///**
// * Created by Yang Jing (yangbajing@gmail.com) on 2015-12-15.
// */
//@conf
//trait config {
//  val `filter-after-token` = new {
//    val match_all = false
//    val path = List("/inapi")
//    val exclude_paths = List(
//      "/api/user/signin",
//      "/api/user/signup",
//      "/api/user/signout"
//    )
//  }
//}
//
//object Configs extends config
