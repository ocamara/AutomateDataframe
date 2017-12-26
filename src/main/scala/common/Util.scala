package common

object Util {

  def deleteString(value:String, delete:String):String=
  {
    return value.replace(delete,"")
  }

  def deleteWhiteSpaces(value:String):String=
  {
    return deleteString(value," ")
  }

  def getValue(tab:Array[String], index:Int, isDelete:Boolean=false):String=
  {
    try
    {
      if(! isDelete)
          return tab(index)
      else
          return Util.deleteString(tab(index),"\"")
    }
    catch
    {
      case _ => return ""
    }
   }


}
