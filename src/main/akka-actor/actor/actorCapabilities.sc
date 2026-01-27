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



/*
    EXERCISE
      1. Create a counter actor
        - Increment
        - Decrement
        - Print

       2. Bank as an Actor
        - Deposit
        - Withdraw
        - Statement
        replies with
        - Success / Failure
 */

// 1. Counter
object Counter {
  case object Increment
  case object Decrement
  case object Print
}

class Counter extends Actor {
  var count = 0
  import Counter._

  override def receive: Receive = {
    case Increment => count += 1
    case Decrement => count -= 1
    case Print => println(s"[counter] Current count is $count")
  }
}

val myCounter = system.actorOf(Props[Counter], "myCounter")

(1 to 10).foreach(_ => myCounter ! Counter.Increment)
(1 to 6).foreach(_ => myCounter ! Counter.Decrement)
myCounter ! Counter.Print



// 2. Bank

object BankAccount {
  case class Deposit(amount : Int)
  case class Withdraw(amount : Int)
  case object Statement

  case class TransactionSuccess(message : String)
  case class TransactionFailure(message : String)
}

class BankAccount extends Actor {
  var amounts = 0;

  import BankAccount._

  override def receive: Receive = {
    case Deposit(amount) =>
      if (amount < 0) sender() ! TransactionFailure("Invalid amount")
      else {
        amounts += amount
        sender() ! TransactionSuccess(s"Account deposited by amount $amount")
      }

    case Withdraw(amount) =>
      if (amount < 0) sender() ! TransactionFailure("Invalid amount")
      else if (amount > amounts) sender() ! TransactionFailure("insufficient amount")
      else {
        amounts -= amount
        sender() ! TransactionSuccess(s"Account withdrew amount $amount")
      }

    case Statement => sender() ! s"Your current amount is $amounts"
  }

}

object Person {
  case class LiveTheLife(account: ActorRef)
}

class Person extends Actor {
  import Person._
  import BankAccount._

  override def receive: Receive = {
    case LiveTheLife(account) =>
      account ! Deposit(10000)
      account ! Withdraw(90000)
      account ! Withdraw(500)
      account ! Statement

    case message => println(message)
  }
}

val bankAccount = system.actorOf(Props[BankAccount], "bank")
val person = system.actorOf(Props[Person], "Alice")

person ! Person.LiveTheLife(bankAccount)

