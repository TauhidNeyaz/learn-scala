//> using scala "2.13.12"
//> using dep "com.typesafe.akka:akka-actor_2.13:2.8.8"

import akka.actor.ActorSystem
import akka.testkit.{TestKit, ImplicitSender}


class BasicSpecs extends TestKit(ActorSystem("basicSpecs"))
  with ImplicitSender