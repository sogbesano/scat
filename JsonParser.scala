import scala.util.parsing.json._
import java.lang.IllegalArgumentException

class JsonParser(msg: String) {  

  def toJson(): Map[String, String] = {
    JSON.parseFull(msg) match {
      case Some(e) => e.asInstanceOf[Map[String, String]]
      case _ => throw new IllegalArgumentException
    }
  }

  def formatMsg(json: Map[String, String]): String = s"${json("time")} ${json("username")}: ${json("msg")}"
   

}

  

