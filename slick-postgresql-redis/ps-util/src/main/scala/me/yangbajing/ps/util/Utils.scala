package me.yangbajing.ps.util

import org.apache.commons.codec.digest.DigestUtils

/**
 * 工具
 * Created by jingyang on 15/7/16.
 */
object Utils {
  def option[T <: CharSequence](v: T): Option[T] = Option(v).filterNot(_.length() == 0)

  @inline
  def generatePassword(pwd: String): (Array[Byte], Array[Byte]) =
    generatePassword(pwd.getBytes(Constants.CHARSET))

  def generatePassword(pwd: Array[Byte]): (Array[Byte], Array[Byte]) = {
    val salt = Array.ofDim[Byte](Constants.SALT_LENGTH)
    RandomUtils.random.nextBytes(salt)
    val digest = DigestUtils.getSha256Digest
    digest.update(salt)
    digest.update(pwd)
    (salt, digest.digest())
  }

  @inline
  def generatePassword(salt: Array[Byte], pwd: String): Array[Byte] =
    generatePassword(salt, pwd.getBytes(Constants.CHARSET))

  def generatePassword(salt: Array[Byte], pwd: Array[Byte]): Array[Byte] = {
    val digest = DigestUtils.getSha256Digest
    digest.update(salt)
    digest.update(pwd)
    digest.digest()
  }

}
