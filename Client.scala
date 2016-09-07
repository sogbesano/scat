import java.net._
import scala.io._
import java.io._
import java.security._
import java.util.NoSuchElementException
import javax.net.ssl.SSLSocket
import javax.net.ssl.SSLSocketFactory
import com.sun.net.ssl.internal.ssl.Provider

object Client {

  var msgAcc = ""

  def main(args: Array[String]): Unit = {
    val sslFactory = SSLSocketFactory.getDefault()
    val server = sslFactory.createSocket(InetAddress.getByName(args(0)), args(1).toInt).asInstanceOf[SSLSocket]
    println("Enter a username")
    val user = new User(StdIn.readLine())
    val conn = new ClientConnection(server, user)
    println("Welcome to the chat " + user.username)
    sys.addShutdownHook(this.shutdown(conn, server))
    new Thread(conn).start()
    while (true) {
      val rxMsg = conn.getMsg(server)
      val parser = new JsonParser(rxMsg)
      val formattedMsg = parser.formatMsg(parser.toJson()) 
      println(formattedMsg)
      msgAcc = msgAcc + formattedMsg + "\n" 
    }
  }
 
  def shutdown(conn: ClientConnection, server: Socket): Unit = {
    //send client dc flag to server
    conn.close(server)
    val fileWriter = new BufferedWriter(new FileWriter(new File("history.txt"), true))
    fileWriter.write(msgAcc) 
    fileWriter.close()
    println("Leaving chat, thanks for using")
  }
    
}

