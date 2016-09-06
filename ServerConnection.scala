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

class ServerConnection(client: Socket, clients: List[Socket], port: Int) extends Runnable {

  override def run(): Unit = {
    try {
      println("Client connected")
      println("number of clients connected = " + this.clients.length)
      while(true) {
        val msg = this.getMsg(this.client)
	println(msg)
        for(i <- 0 until this.clients.length) {
          val cli = this.clients(i)
	  this.sendMsg(cli, msg)
        }
      }	
    } catch {
      case io: IOException => io
      case ioe: NoSuchElementException => ioe
    }
  }

  def getMsg(client: Socket): String = {
    new BufferedSource(client.getInputStream()).getLines().next()
  }
  
  def sendMsg(cli: Socket, msg: String): Unit = {
    val out = new PrintStream(cli.getOutputStream())
    out.println(msg)
    out.flush()
  }

}
