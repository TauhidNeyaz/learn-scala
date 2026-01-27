//> using scala "2.13.12"
//> using dep "com.typesafe.akka:akka-actor_2.13:2.8.8"

import akka.actor.{Actor, ActorRef, ActorSystem, Props}

class SimpleActor extends Actor {
  override def receive: Receive = {

    case message : String => println(s"[simple actor] I have received $message")
    case number : Int => println(s"[simple actor] I have received $number")
    case specialMessage(content) => println(s"[simple actor] I have received some SPECIAL $content")
    case SendMessageToYourself(content) => self ! content
    case sayHi(ref) => ref ! "Hi"
    case "Hi" => context.sender() ! "Hello there ..."

  }
}

val system = ActorSystem("SimpleActor")

val simpleActor = system.actorOf(Props[SimpleActor], "simpleActor")

// 1. message can be of any type
//      a) message must be IMMUTABLE
//      b) message must be SERIALIZABLE

//simpleActor ! "Hello Scala"
//simpleActor ! 42

case class specialMessage(content : String)
//simpleActor ! specialMessage("some special message ...")

// 2. actor have the information about their context and about themselves
//     context.self === `this` in OOPs

case class SendMessageToYourself(content : String)
//simpleActor ! SendMessageToYourself("Hi, I'm an actor ....")

// 3.

val alice = system.actorOf(Props[SimpleActor], "alice")
val bob = system.actorOf(Props[SimpleActor], "bob")

case class sayHi(ref: ActorRef)

//alice ! sayHi(bob)
//
//alice ! "Hi"