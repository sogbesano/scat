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

class ServerConnection(port: Int, keystore: String, keystorePassword: String) {

  def connect(): Socket = {
    Security.addProvider(new Provider())
    System.setProperty("javax.net.ssl.keyStore", keystore)
    System.setProperty("javax.net.ssl.keyStorePassword", keystorePassword)
    //System.setProperty("javax.net.debug", "all")
    val sslFactory = SSLServerSocketFactory.getDefault()
    val sslSocket = sslFactory.createServerSocket(port).asInstanceOf[SSLServerSocket]
    sslSocket.accept() 
  }  
  
  def getMsg(client: Socket): String = {
    //try {
      new BufferedSource(client.getInputStream()).getLines().next()
    //} catch {
    //  case io: NoSuchElementException => "error caught"
    //}
  }
  
  def sendMsg(client: Socket, msg: String): Unit = {
    val out = new PrintStream(client.getOutputStream())
    out.println(msg)
    out.flush()
  }

  def close(client: Socket): Unit = client.close() 

}
