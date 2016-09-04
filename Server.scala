import java.net._
import javax.net.ssl.SSLServerSocketFactory
import java.security._
import com.sun.net.ssl.internal.ssl.Provider

object Server {

  def main(args: Array[String]): Unit = {
    println("Chat server running")
    Security.addProvider(new Provider())
    System.setProperty("javax.net.ssl.keyStore", args(1))
    System.setProperty("javax.net.ssl.keyStorePassword", args(2))
    val sslFactory = SSLServerSocketFactory.getDefault()
    val sslSocket = sslFactory.createServerSocket(args(0).toInt)
    while(true) {
      val client = sslSocket.accept()
      new Thread(new ServerConnection(client, args(0).toInt)).start()
    }
  }

}

