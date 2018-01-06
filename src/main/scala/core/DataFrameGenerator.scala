package core

import common.Schema.Config
import common.{Configuration, Conversion, Utils}
import org.apache.spark.sql.{DataFrame, Row, SparkSession}

import scala.collection.mutable

object DataFrameGenerator {

  val spark = Configuration.getSpark()
  val sc = Configuration.getSparkContext()


  def generateDataFrame(config:Config, fileName:String):DataFrame =
  {
    val rawData = sc.textFile(fileName)

    val header = rawData.first()
    val tail = rawData.filter(line => line != header)

    val lines = tail.filter(line => line.trim != "")
    val rows = lines.map(line => RowGenerator.generateRow(config,line))

    val schema = SchemaGenerator.generateSchema(config)

    val df = spark.createDataFrame(rows,schema)

    return df
  }
}
