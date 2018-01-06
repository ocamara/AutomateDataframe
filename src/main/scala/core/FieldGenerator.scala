package core

import common.Schema.Column
import common.{Conversion, Utils}

import scala.collection.mutable._

object FieldGenerator {

  var lastJsonField = Array("")

  private def generateDependentField(jsonField:Array[String], column:Column):Object=
  {
    val part =  Utils.getValue(jsonField,column.index.get,true)
    return convertTo(part,column)
  }

  private def generateIndependentField(field:String, column:Column):Object=
  {
    val part = Utils.deleteOFFString(field,column.offChars)
    return convertTo(part,column)
  }

  private def convertTo(value:String,column:Column):Object=
  {
    val result = Utils.deleteWhiteSpaces(value)
    if (column.typeC == "int")
      return Conversion.toInt(result).asInstanceOf[Object]

    else if (column.typeC == "double")
      return Conversion.toDouble(result).asInstanceOf[Object]

    else if (column.typeC == "float")
      return Conversion.toFloat(result).asInstanceOf[Object]

    else if (column.typeC == "long")
      return Conversion.toLong(result).asInstanceOf[Object]

    else if (column.typeC == "date")
      return Conversion.toDate(result,column.format.get)

    else if (column.typeC == "time")
      return Conversion.toTimestamp(result,column.format.get)

    else if (column.typeC == "json")
      return value

    return value
   }

  def generateFields(parts:Array[String], columns:Array[Column]):MutableList[Object] =
  {
    val result = MutableList[Object]()
    val size = columns.length
    var index = 0
    for (i <- Range(0,size))
    {
      val column = columns(i)

      if (column.typeC != "json" &&  column.parent == None)
        index = index +1

      val field = FieldGenerator.generateField(column,parts,index)

      if(column.typeC != "json")
        result += field
    }
    return result
  }

  private def generateField(column:Column,parts:Array[String],index:Int):Object=
  {
    val part = getFieldInParts(column,parts,index)
    var field:Object = ""

    /* json field */
    if (column.typeC == "json")
      lastJsonField = Conversion.jsonToList(part).toMap.values.toArray

      /* dependent column */
    else if (column.parent != None)
      field = FieldGenerator.generateDependentField(lastJsonField,column)

      /* independent column */
    else
      field = FieldGenerator.generateIndependentField(part,column)

    return field
  }

  def getFieldInParts(column:Column,parts:Array[String],index:Int):String=
  {
    if (column.typeC != "json")
      return   Utils.getValue(parts, index, true)

    return Utils.getValue(parts, index)

  }

}
