package core

import common.Schema.{Column, Config}
import common.Configuration
import common.{Conversion, Utils}
import org.apache.commons.configuration.Configuration
import org.apache.spark.sql.Row

import scala.collection.mutable

object RowGenerator {

  def generateRow(config:Config, line:String):Row=
  {
    val parts = line.split(config.separator)
    val columns = config.columns
    val fields = FieldGenerator.generateFields(parts, columns)
    return Row.fromSeq(fields.toSeq)
  }


}
