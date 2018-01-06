package common
import java.text.SimpleDateFormat
import java.util.Date
import java.sql.Timestamp
import java.sql.Date

import play.api.libs.json.{JsObject, JsValue, Json}

object Conversion {

  def parseJson(jsonString:String):Object=
  {
    try
    {
      return Json.parse(jsonString)
    }
    catch
    {
      case _ => return None
    }
  }

  def toList(ref:String,jsonObject:JsObject):List[(String,String)] =
  {
    val keys = jsonObject.keys
    var concat:List[(String,String)] = List()
    val jsonMap = jsonObject.value
    for(key <-keys)
    {
      val value = jsonMap.get(key).get
      val valueType = value.getClass.getSimpleName
      if (valueType != "JsObject")
        concat = concat ::: List((ref+key,value.toString()))
      else
        concat = concat ::: toList(ref+key,value.as[JsObject])
    }
    return concat
  }

  def jsonToList(jsonString:String):List[(String,String)]=
  {
    try {
      val value = parseJson(jsonString)
      return toList("", value.asInstanceOf[JsObject])
    }
    catch
      {
        case _ => return List((" "," "))
      }
  }

  def toUtilDate(date:String, format:String):java.util.Date=
    {
      try {
        val dateFormat = new SimpleDateFormat(format)
        val result = dateFormat.parse(date)
        return result
      }
      catch
        {
          case _ => {
            val defaultDate = new java.util.Date()
            return defaultDate
          }
        }
    }

  def toDate(stringDate:String, format:String):java.sql.Date=
  {
    val date = toUtilDate(stringDate,format)
    val result = new java.sql.Date(date.getTime)
    return result
  }

  def toTimestamp(stringDate:String, format:String):java.sql.Timestamp=
  {
    val date = toUtilDate(stringDate,format)
    return new java.sql.Timestamp(date.getTime)
  }

  def toFloat(value:String):Float=
  {
    try
      return value.toFloat
    catch
    {
      case _ => return 0
    }
  }

  def toInt(value:String):Int=
  {
    try
      return value.toInt
    catch
      {
        case _ => return 0
      }
  }

  def toLong(value:String):Long=
  {
    println("long ",value)
    try
      return value.toLong
    catch
      {
        case _ => return 0
      }
  }

  def toDouble(value:String):Double=
  {
    println("double ",value)
    try
      return value.toDouble
    catch
      {
        case _ => return 0
      }
  }

}
