import scala.util.parsing.json._
import java.lang.IllegalArgumentException

class JsonParser(msg: String) {  

  def toJson(): Map[String, String] = {
    JSON.parseFull(msg) match {
      case Some(e) => e.asInstanceOf[Map[String, String]]
      case _ => throw new IllegalArgumentException
    }
  }

  def formatMsg(json: Map[String, String]): String = {
    def getField(json: Map[String, String], field: String): String = json(field)
    getField(json, "time") + " " + getField(json, "username") + ": " + getField(json, "msg")
  } 

}

  

