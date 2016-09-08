import java.net._
import javax.net.ssl.SSLServerSocketFactory
import com.sun.net.ssl.internal.ssl.Provider

object Server {
 
  var clients = List[Socket]()

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
      clients = client :: clients
      println(s"Number of clients connected = ${clients.length}")
      new Thread(new ServerConnection(client)).start()
    }
  }

  def shutdown(): Unit = {
    clients.foreach((s: Socket) => s.close())
    println("Server shutting down")
  }

}

