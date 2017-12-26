package index

import java.text.SimpleDateFormat

import common.Conversion
import dataframe.HandleDataFrame

import scala.collection.immutable.Range

object Main
{
  def main(args:Array[String])= {
    val file = "src/main/resources/data.txt"
    val df = HandleDataFrame.createDataFrame(file)
    df.show()
    df.printSchema()
  }
}