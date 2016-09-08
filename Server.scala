import java.net._
import javax.net.ssl.SSLServerSocketFactory
import com.sun.net.ssl.internal.ssl.Provider
import java.util.UUID

object Server {
 
  var clients: Map[String, Socket] = Map.empty

  def main(args: Array[String]): Unit = {
    println("Chat server running")
    System.setProperty("javax.net.ssl.keyStore", args(1))
    System.setProperty("javax.net.ssl.keyStorePassword", args(2))
    val sslFactory = SSLServerSocketFactory.getDefault()
    val sslSocket = sslFactory.createServerSocket(args(0).toInt)
    sys.addShutdownHook(this.shutdown())
    while(true) {
      val client = sslSocket.accept()
      println("Client connected")
      val uuid = UUID.randomUUID.toString
      this.clients = this.clients + (uuid -> client)
      println(s"Number of clients connected = ${clients.size}")
      new Thread(new ServerConnection(client, uuid)).start()
    }
  }

  def shutdown(): Unit = {
    this.clients.values.foreach((s: Socket) => s.close())
    println("Server shutting down")
  }

}

