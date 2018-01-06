package common

import play.api.libs.json.Json

object Schema {

  case class Column(name: String, typeC: String, offChars: Array[String], format: Option[String], parent: Option[String], index: Option[Int])

  case class Config(separator: String, columns: Array[Column])

}