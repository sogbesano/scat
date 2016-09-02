import java.net._
import java.io.IOException

object Server {

  def main(args: Array[String]): Unit = {
    println("Chat server running")
    val conn = new ServerConnection(args(0).toInt, args(1), args(2))
    while(true) {
      try {
        var running = true
        val client = conn.connect()
        println("Client connected")
        sys.addShutdownHook(this.shutdown(conn, client))
        while (running) {
          val msg = conn.getMsg(client)
	  if (msg != null && msg != "/exit") {
          println(msg)
          conn.sendMsg(client, msg) 
	  } else if (msg == "/exit") {
            running = false
	  }
        }
      } catch {
        case io: IOException => io
      }
    }
  }
  
  def shutdown(conn: ServerConnection, client: Socket): Unit = {
    conn.close(client)
    println("Chat server shutting down")
  } 

}

