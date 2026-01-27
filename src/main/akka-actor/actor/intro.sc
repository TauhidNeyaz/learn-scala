

import akka.actor.{Actor, ActorSystem, Props}

// part 1 - actor system
val actorSystem = ActorSystem("firstActorSystem")
println(actorSystem.name)

// part 2 - create actor

class WordCoundActor extends Actor {
  var totalWords = 0

  def receive: PartialFunction[Any, Unit] = {
    case message: String =>
      println(s"[actor] I have received a message $message")
      totalWords += message.split(" ").length
    case msg => println(s"I cannot understand ${msg.toString}")
  }
}

// part 3 - instantiate actor
// val wordCounter = new WordCoundActor   -> It's not work like this

val wordCounter = actorSystem.actorOf(Props[WordCoundActor], "wordCounter")

// part 4 - communicate !
wordCounter ! "Hello Scala"