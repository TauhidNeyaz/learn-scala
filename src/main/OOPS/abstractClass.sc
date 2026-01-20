abstract class Animal {
  val creatureType : String
  def eat() : Unit
}

class Dog extends Animal {
  override val creatureType: String = "Canine"
  override def eat() : Unit = println("crunch crunch ...")
}

/*
abstract class are basic definition without implementation , we cannot instance abstract class
it only use to extend to some other subclasses and in that sub clas we need to define the members and methods
 */


// Traits

trait Carnivore {
  def eat(animal : Animal) : Unit
}

trait ColdBlooded {

}

class Crocodile extends Animal with Carnivore with ColdBlooded {
  override val creatureType: String = "croc"
  override def eat(): Unit = println("nom nom ...")
  override def eat(animal: Animal): Unit = println(s"I'm a Crorcdile and I'm eating ${animal.creatureType}")
}

val dog = new Dog
val crocodile = new Crocodile
crocodile.eat(dog)

/*

ABSTRACTS  VS  TRAITS

1.  traits do not have constructor parameter (but possible in scala 3)
2.  scala has single class inheritance, but we can extend multiple traits
3.  Use Case : trait => Behavior   abstract class => Things

 */
