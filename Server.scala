import java.net._
import java.io.IOException

object Server {

  def main(args: Array[String]): Unit = {
    val conn = new ServerConnection(args(0).toInt, args(1), args(2))
    while(true) {
      try {
        val client = conn.connect()
        println("Client connected")
        sys.addShutdownHook(this.shutdown(conn, client))
        while (true) {
          val msg = conn.getMsg(client)
          println(msg)
          conn.sendMsg(client, msg)  
        }
      } catch {
        case io: IOException => io
      }
    }
  }
  
  def shutdown(conn: ServerConnection, client: Socket): Unit = {
    conn.close(client)
    println("Closing chat")
  } 

}

