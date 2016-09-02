import java.net._
import scala.io._
import java.io._
import java.security._

object Client {

  var msgAcc = ""

  def main(args: Array[String]): Unit = {
    val conn = new ClientConnection(InetAddress.getByName(args(0)), args(1).toInt)
    val server = conn.connect()
    println("Enter a username")
    val user = new User(StdIn.readLine())
    println("Welcome to the chat " + user.username)
    sys.addShutdownHook(this.shutdown(conn, server))
    while (true) {
    val txMsg = StdIn.readLine()
    if (txMsg != null) {
      conn.sendMsg(server, user, txMsg)
      val rxMsg = conn.getMsg(server)
      val parser = new JsonParser(rxMsg)
      val formattedMsg = parser.formatMsg(parser.toJson()) 
      println(formattedMsg)
      msgAcc = msgAcc + formattedMsg + "\n"
      }
    }
  }
 
  def shutdown(conn: ClientConnection, server: Socket): Unit = {
    conn.close(server)
    val fileWriter = new BufferedWriter(new FileWriter(new File("history.txt"), true))
    fileWriter.write(msgAcc) 
    fileWriter.close()
    println("Leaving chat, thanks for using")
  }
    
}

