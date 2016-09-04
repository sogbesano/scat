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

class ServerConnection(client: Socket, port: Int) extends Runnable {

  override def run(): Unit = {
    try {
      println("Client connected")
      sys.addShutdownHook(this.shutdown(client))
      while(true) {
        val msg = this.getMsg(client)
	println(msg)
	this.sendMsg(client, msg)
      }	
    } catch {
      case io: IOException => io
      case ioe: NoSuchElementException => ioe
    }
  }


  def shutdown(client: Socket): Unit = {
    this.close(client)
    println("Chat server shutting down")
  }

  def getMsg(client: Socket): String = {
    new BufferedSource(client.getInputStream()).getLines().next()
  }
  
  def sendMsg(client: Socket, msg: String): Unit = {
    val out = new PrintStream(client.getOutputStream())
    out.println(msg)
    out.flush()
  }

  def close(client: Socket): Unit = client.close() 

}
