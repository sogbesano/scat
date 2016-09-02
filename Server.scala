import java.net._

object Server {

  def main(args: Array[String]): Unit = {
    val conn = new ServerConnection(args(0).toInt, args(1), args(2))
    val client = conn.connect()
    sys.addShutdownHook(this.shutdown(conn, client))
    while (true) {
      val msg = conn.getMsg(client)
      println(msg)
      conn.sendMsg(client, msg)  
    }
  }

  def shutdown(conn: ServerConnection, client: Socket): Unit = {
    conn.close(client)
    println("Closing chat")
  } 

}

