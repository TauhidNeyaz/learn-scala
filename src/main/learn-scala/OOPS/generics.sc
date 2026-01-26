import scala.language.postfixOps

// Generics Class
class List[A] {
  // use the type A
  def add[B >: A](element : B) : MyList[B] = ???
}

val listInteger = new List[Int]
val listString = new List[String]

class Map[Key, Value] {

}

val mapIntInt = new Map[Int, Int]
val mapIntString = new Map[Int, String]


// Generic Methods
object MyList {
  def empty[A] : MyList[A] = ???
}

val emptyListOfInt = MyList.empty[Int]


// variance problem
class Animal
class Dog extends Animal
class Cat extends Animal

// QUE : Can a List[Dog] OR List[Cat] extends List[Animal] ??

// 1. Yes, List[Cat] extends List[Animal] = COVARIANCE
class CovarianceList[+A] {
  val animal : Animal = new Cat
  val animalList : CovarianceList[Animal] = new CovarianceList[Cat]
  // What if I add animalList.add(new Dog) ??? => We return a list of Animal
}

// 2. No  = INVARIANCE
class InvarianceList[A] {
  val invariantAnimalList : InvarianceList[Animal] = new InvarianceList[Animal]
}

// Hell, No! CONTRAVARIANCE
class ContravarianceList[-A] {
  val animal : Animal = new Cat
  val animalList : ContravarianceList[Cat] = new ContravarianceList[Animal]
}


class Trainer[-A] {
  val trainer : Trainer[Cat] = new Trainer[Animal]
}


// Type Bound
class Cage[A <: Animal] (animal : A)  //  <: -> subtype => A is subtype of Animal
val cage = new Cage(new Dog)


