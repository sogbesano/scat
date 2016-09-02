import scala.util.parsing.json._

class JsonParser(msg: String) {  

  def toJson(): Map[String, String] = {
    val parsed = JSON.parseFull(msg) match {
      case Some(e) => e
      case None => println("failed to parse")
    }
    parsed.asInstanceOf[Map[String, String]]
  }

  def formatMsg(json: Map[String, String]): String = {
    def getField(json: Map[String, String], field: String): String = json(field)
    getField(json, "time") + " " + getField(json, "username") + ": " + getField(json, "msg")
  } 

}

  

