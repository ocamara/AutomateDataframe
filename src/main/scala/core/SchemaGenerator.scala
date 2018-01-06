package core

import common.Schema.Config
import org.apache.spark.sql.types._

import scala.collection.mutable

object SchemaGenerator {

  val types = Map(
                  "int" -> IntegerType,
                  "long"->LongType,
                  "float"->FloatType,
                  "double"->DoubleType,
                  "time"->TimestampType,
                  "date"->DateType,
                  "string" ->StringType
               )

  private def generateSchemaField(name:String,typeColumn:String):StructField=
  {
     val typeSpark = types.getOrElse(typeColumn,StringType)
     return StructField(name, typeSpark, true)
  }

  def generateSchema(config:Config):StructType=
  {
    val columns = config.columns
    val schema = mutable.MutableList[StructField]()
    for (column <- columns)
      {
        if (column.typeC != "json")
        {
          val field = generateSchemaField(column.name, column.typeC)
          schema += field
        }
      }
    return StructType(schema)
  }


}
