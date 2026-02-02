
import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.Http.IncomingConnection
import akka.stream.scaladsl.Sink

implicit val system: ActorSystem = ActorSystem("LowLevelAPI")
import system.dispatcher

val serverSource = Http().bind("localhost", 8000)

val connectionSink = Sink.foreach[IncomingConnection] { connection =>
  println(s"Accepted incoming connection from: ${connection.remoteAddress}")
}

serverSource.runWith(connectionSink)
