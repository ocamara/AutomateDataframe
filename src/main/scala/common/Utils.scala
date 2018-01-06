package common

object Utils {

  def deleteString(value:String, off:String):String=
  {
    return value.replace(off,"")
  }


  def deleteOFFString(field:String,tab:Array[String]):String=
  {
    var tempo = ""
    var part = field
    for(delete <- tab)
    {
      tempo = deleteString(part, delete)
      part = tempo
    }
    return part
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
          return Utils.deleteString(tab(index),"\"")
    }
    catch
    {
      case _ => return ""
    }
   }


}
