import java.net.Socket
import scala.io.BufferedSource

object ConnectionUtils {
  
  def getInputIterator(socket: Socket): Iterator[String] = new BufferedSource(socket.getInputStream()).getLines()

}
