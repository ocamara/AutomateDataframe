package common

import java.io.FileInputStream

import common.Schema.{Column, Config}
import org.apache.spark.SparkContext
import org.apache.spark.sql.SparkSession
/* play */
import play.api.libs.json._

import scala.io._
/* lift */
import net.liftweb._

object Configuration
{
  def getSpark(): SparkSession =
  {
    val spark = SparkSession
      .builder()
      .master("local")
      .appName("AutomateCreationDataframe")
      .getOrCreate()

   return spark
  }

  def getSparkContext(): SparkContext=
  {
    return getSpark().sparkContext
  }



/*  def getConfigWithOrgJsonAPI(fileName:String):Parameters = {

    object Column {
      implicit val columnJsonFormat = Json.format[Column]
    }


    object Parameters {
      implicit val parametersJsonFormat = Json.format[Parameters]
    }

    val jsonString = Json.parse(new FileInputStream(fileName))
    val jsonObject = jsonString.as[Parameters]
    return jsonObject
  }
  */

  def getConfigWithLiftAPI(filePath:String): Config =  {

    implicit val formats = json.DefaultFormats // Brings in default date formats etc.

    val source = Source.fromFile(filePath)
    val lines = try source.mkString finally source.close()

    val content = json.parse(lines)
    val config = content.extract[Config]

    return config
  }

  def getConfig(fileConfig:String): Config =
  {
    val config = Configuration.getConfigWithLiftAPI(fileConfig)
    return config
  }
}
