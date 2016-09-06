import java.net._
import javax.net.ssl.SSLServerSocketFactory
import java.security._
import com.sun.net.ssl.internal.ssl.Provider

object Server {
 
  var clients = List[Socket]()

  def main(args: Array[String]): Unit = {
    println("Chat server running")
    Security.addProvider(new Provider())
    System.setProperty("javax.net.ssl.keyStore", args(1))
    System.setProperty("javax.net.ssl.keyStorePassword", args(2))
    val sslFactory = SSLServerSocketFactory.getDefault()
    val sslSocket = sslFactory.createServerSocket(args(0).toInt)
    while(true) {
      val client = sslSocket.accept()
      clients = client :: clients
      println("number clis = " + clients.length)
      sys.addShutdownHook(this.shutdown(client))
      new Thread(new ServerConnection(client, clients, args(0).toInt)).start()
    }
  }

  def shutdown(client: Socket): Unit = {
    clients = clients.tail
    client.close()
    println("Server shutting down")
  }

}

