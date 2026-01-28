//> using scala "2.13.12"
//> using dep "com.typesafe.akka:akka-actor_2.13:2.8.8"

import akka.actor.{Actor, ActorLogging, ActorRef, ActorSystem, Props}

val system = ActorSystem("StoppingActorDemo")

object Parent {
  case class StartChild(name: String)
  case class StopChild(name: String)
  case object Stop
}

class Parent extends Actor with ActorLogging {
  import Parent._

  override def receive: Receive = withChildren(Map.empty)

  def withChildren(children: Map[String, ActorRef]): Receive = {
    case StartChild(name) =>
      log.info(s"Starting child $name")
      val childRef = context.actorOf(Props[Child], name)
      context.become(withChildren(children + (name -> childRef)))

    case StopChild(name) =>
      log.info(s"Stoping child $name")
      val childOption = children.get(name)
      childOption.foreach(childRef => context.stop(childRef))

    case Stop =>
      log.info("Stopping myself")
      context.stop(self)
  }
}

class Child extends Actor with ActorLogging {
  override def receive: Receive = {
    case msg => log.info(msg.toString)
  }
}

import Parent._
val parent = system.actorOf(Props[Parent], "parent")
parent ! StartChild("child1")
val child1 = system.actorSelection("/user/parent/child1")
child1 ! "hi from child1"

parent ! StopChild("child1")
//for (_ <- 1 to 10) child1 ! "Are you there ?"

parent ! StartChild("child2")
val child2 = system.actorSelection("user/parent/child2")
child2 ! "Hi from child2"

parent ! Stop
for (_ <- 1 to 10) child2 ! "Are you there ?"