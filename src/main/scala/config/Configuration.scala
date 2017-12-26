package config

import java.io.FileInputStream

import play.api.libs.json.Json

object Configuration
{

  case class Column(name:String,typeC:String, offChars:Array[String], format:Option[String])

  object Column {
    implicit val columnJsonFormat = Json.format[Column]
  }

  case class Parameters(separator:String,columns:Array[Column])

  object Parameters {
    implicit val ParametersJsonFormat = Json.format[Parameters]
  }

  def getConfig(fileName:String):Parameters={
    val jsonString = Json.parse(new FileInputStream(fileName))
    val jsonObject = jsonString.as[Parameters]
    return jsonObject
  }

}
