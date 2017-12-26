package dataframe

import common.Util
import config.Configuration
import org.apache.spark.sql.{DataFrame, Row, SparkSession}

import scala.collection.mutable

object HandleDataFrame {

  val spark = SparkSession.builder().master("local").appName("AutomateCreationDataframe").getOrCreate()
  val sc = spark.sparkContext
  val fileConfig = "src/main/resources/config.json"
  val config = Configuration.getConfig(fileConfig)


  def createDataFrame(fileName:String):DataFrame=
  {
    val rawData = sc.textFile(fileName)
    val header = rawData.first()
    val tail = rawData.filter(line => line != header)
    val rows = tail.map(line => generateRow(line))
    val schema = HandleSchema.createSchema(config)
    val df = spark.createDataFrame(rows,schema)
    return df
  }

  def generateRow(line:String):Row=
  {
     val parts = line.split(config.separator)
     val columns = config.columns
     val size = columns.length
     val result = mutable.MutableList[Object]()

     for (i <- Range(0,size))
       {
         val partWithoutOffChars = HandleField.manageColumn(i,parts,columns(i))
         result += partWithoutOffChars
       }

     return Row.fromSeq(result.toSeq)
  }

}
