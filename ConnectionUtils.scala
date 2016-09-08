import java.net.Socket
import java.io.PrintStream
import scala.io.BufferedSource

object ConnectionUtils {
  
  def getInputIterator(socket: Socket): Iterator[String] = new BufferedSource(socket.getInputStream()).getLines()

  def sendMsg(socket: Socket, msg: String): Unit = new PrintStream(socket.getOutputStream(), true).println(msg)

}
