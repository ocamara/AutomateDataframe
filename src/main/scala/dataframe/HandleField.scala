package dataframe

import common.{Conversion, Util}
import common.Util.deleteString
import config.Configuration.Column
import org.apache.spark.sql.catalyst.expressions.UnixTimestamp

object HandleField {

  def manageColumn(index:Int, parts: Array[String], column:Column):Object=
  {
    var part =  Util.getValue(parts, index, true)
    if (column.typeC == "json")
      part = Util.getValue(parts,index)

    val offChars = column.offChars
    var tempo = ""
    for(delete <- offChars)
    {
      tempo = deleteString(part, delete)
      part = tempo
    }

      return convertTo(part,column)
  }

  def convertTo(value:String,column:Column):Object=
  {
    val result = Util.deleteWhiteSpaces(value)
    if (column.typeC == "int")
      return result.toInt.asInstanceOf[Object]

    else if (column.typeC == "double")
      return result.toDouble.asInstanceOf[Object]

    else if (column.typeC == "float")
      return result.toFloat.asInstanceOf[Object]

    else if (column.typeC == "long")
      return result.toLong.asInstanceOf[Object]

    else if (column.typeC =="date")
      return Conversion.toDate(result,column.format.get)

    else if (column.typeC =="time")
      return Conversion.toTimestamp(result,column.format.get)

    else if (column.typeC == "json")
      return value

    return value
   }

}
