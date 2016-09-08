import javax.net.ssl.SSLSocket
import javax.net.ssl.SSLSocketFactory
import javax.net.SocketFactory
import java.net.Socket
import java.net.InetAddress
import java.net.InetSocketAddress
import java.security._
import java.io._
import scala.io._
import java.util.GregorianCalendar
import java.util.Calendar
import java.util.Date
import com.sun.net.ssl.internal.ssl.Provider
import scala.util.parsing.json._

class ClientConnection(server: Socket, user: User) extends Runnable {

  override def run(): Unit = {
     while(true) {
       val txMsg = StdIn.readLine()
       if (txMsg != null) 
         this.sendMsg(this.server, user, txMsg)
     }
  }

  def getMsg(server: Socket): String = new BufferedSource(server.getInputStream()).getLines().next()

  def sendMsg(server: Socket, user: User, msg: String): Unit = {
    new PrintStream(server.getOutputStream(), true).println(this.toMinifiedJson(user.username, msg))
  }  

  private def toMinifiedJson(user: String, msg: String): String = {
    s"""{"time":"${this.getTime()}","username":"$user","msg":"$msg"}"""
  }

  private def getTime(): String = {
    val cal = Calendar.getInstance()
    cal.setTime(new Date())
    "(" + cal.get(Calendar.HOUR_OF_DAY) + ":" + cal.get(Calendar.MINUTE) + ":" + cal.get(Calendar.SECOND) + ")"
  }

}
