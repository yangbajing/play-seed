package me.yangbajing.ps.util

import java.nio.charset.Charset

/**
 * 常量
 * Created by jingyang on 15/7/16.
 */
object Constants {
  val COOKIE_MAX_AGE = 30 // 秒

  val CACHE_KEY_TOKEN_PREFIX = "ps:to:"

  val NICK = "nick"

  val SPLIT_CHAR = '-'

  val SALT_LENGTH = 12

  val CHARSET = Charset.forName("UTF-8")

  val PS_TOKEN_OWNER = "ps-to"
}
