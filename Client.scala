import java.net._
import scala.io._
import java.io._
import java.security._
import javax.net.ssl.SSLSocket
import javax.net.ssl.SSLSocketFactory
import com.sun.net.ssl.internal.ssl.Provider

object Client {

  var msgAcc = ""

  def main(args: Array[String]): Unit = {
    val sslFactory = SSLSocketFactory.getDefault()
    val server = sslFactory.createSocket(InetAddress.getByName(args(0)), args(1).toInt)
    println("Enter a username")
    val user = new User(StdIn.readLine())
    val conn = new ClientConnection(server, user)
    println("Welcome to the chat " + user.username)
    sys.addShutdownHook(this.shutdown(server))
    new Thread(conn).start()
    val inIterator = ConnectionUtils.getInputIterator(server) 
    while (inIterator.hasNext) {
      val rxMsg = inIterator.next()
      val parser = new JsonParser(rxMsg)
      val formattedMsg = parser.formatMsg(parser.toJson()) 
      println(formattedMsg)
      msgAcc = msgAcc + formattedMsg + "\n"
    }
  }
 
  def shutdown(server: Socket): Unit = {
    server.close()
    val fileWriter = new BufferedWriter(new FileWriter(new File("history.txt"), true))
    fileWriter.write(msgAcc) 
    fileWriter.close()
    println("Leaving chat, thanks for using")
  }
    
}

