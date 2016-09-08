import javax.net.ssl.SSLServerSocket
import javax.net.ssl.SSLServerSocketFactory
import java.net.Socket
import java.net.ServerSocket
import java.io._
import scala.io._
import com.sun.net.ssl.internal.ssl.Provider
import javax.net.ssl._
import com.sun.net.ssl._

class ServerConnection(client: Socket, uuid: String) extends Runnable {

  override def run(): Unit = {
    val inIterator = ConnectionUtils.getInputIterator(this.client)
    while(inIterator.hasNext) {
      val msg = inIterator.next()
      println(msg)
      Server.clients.values.foreach((cli: Socket) => ConnectionUtils.sendMsg(cli, msg))
    }
    Server.clients = Server.clients - uuid
  }

}
