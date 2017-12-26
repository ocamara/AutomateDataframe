package dataframe

import config.Configuration.Parameters
import org.apache.spark.sql.types._

import scala.collection.mutable

object HandleSchema {

  val types = Map("int" -> IntegerType, "long"->LongType, "float"->FloatType,
    "double"->DoubleType, "time"->TimestampType, "date"->DateType, "string" ->StringType)

  def createField(name:String,typeColumn:String):StructField=
  {
     val typeSpark = types.getOrElse(typeColumn,StringType)
     return StructField(name, typeSpark, true)
  }

  def createSchema(config:Parameters):StructType=
  {
    val columns = config.columns
    val schema = mutable.MutableList[StructField]()
    for (column <- columns)
      {
        val field = createField(column.name,column.typeC)
        schema += field
      }
    return StructType(schema)
  }


}
