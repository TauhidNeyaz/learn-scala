//> using scala "2.13.12"
//> using dep "com.typesafe.akka:akka-actor_2.13:2.8.8"

import akka.actor.{Actor, ActorSystem, Props}

// part 1 - actor system
val actorSystem = ActorSystem("firstActorSystem")
println(actorSystem.name)

// part 2 - actor
class WordCountActor extends Actor {
  def receive: Receive = {
    case message: String =>
      println(s"[actor] I have received: $message")
  }
}

// part 3 - instantiate actor
val wordCounter =
  actorSystem.actorOf(Props[WordCountActor], "wordCounter")

// part 4 - communicate
wordCounter ! "Hello Scala"
