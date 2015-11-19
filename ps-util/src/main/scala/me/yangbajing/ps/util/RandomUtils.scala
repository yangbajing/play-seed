package me.yangbajing.ps.util

import java.security.SecureRandom

/**
 * 随机资源工具
 * Created by jingyang on 15/7/16.
 */
object RandomUtils {
  private val LOW = 33
  private val HIGH = 127
  private val RANDOM_STRING = (0 to 9).mkString + ('a' to 'z').mkString + ('A' to 'Z').mkString + ",.?!~`@#$%^&*()_-+=|[]{};:<>/"
  private val RANDOM_STRING_LENGTH = RANDOM_STRING.length

  val random = new SecureRandom()

  def randomString(size: Int): String = {
    assert(size > 0, s"size: $size must be > 0")
    (0 until size).map(_ => RANDOM_STRING.charAt(random.nextInt(RANDOM_STRING_LENGTH))).mkString
  }

  def nextPrintableChar(): Char = {
    (random.nextInt(HIGH - LOW) + LOW).toChar
  }

  def randomNextInt(begin: Int, bound: Int) = {
    random.nextInt(bound - begin) + begin
  }

}
