import java.net.Socket
import scala.io.BufferedSource

object ConnectionUtils {
  
  def getInputIterator(server: Socket): Iterator[String] = new BufferedSource(server.getInputStream()).getLines()

}
