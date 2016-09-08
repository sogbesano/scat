import javax.net.ssl.SSLServerSocket
import javax.net.ssl.SSLServerSocketFactory
import java.net.Socket
import java.net.ServerSocket
import java.security._
import java.io._
import scala.io._
import com.sun.net.ssl.internal.ssl.Provider
import javax.net.ssl._
import com.sun.net.ssl._
import java.util.NoSuchElementException

class ServerConnection(client: Socket, uuid: String) extends Runnable {

  override def run(): Unit = {
    try {
      while(true) {
        val msg = this.getMsg(this.client)
	println(msg)
        Server.clients.values.foreach((cli: Socket) => this.sendMsg(cli, msg))
      }	
    } catch {
      case io: IOException => io
      case ioe: NoSuchElementException => Server.clients = Server.clients - uuid
    }
  }

  private def getMsg(client: Socket): String = {
    new BufferedSource(client.getInputStream()).getLines().next()
  }
  
  private def sendMsg(cli: Socket, msg: String): Unit = {
    new PrintStream(cli.getOutputStream(), true).println(msg)
  }

}
