
class Person(name: String, val age: Int) {
  // methods
  def greet(name: String): Unit = {
    println(s"${this.name} says : Hello $name")
  }

  // overloading
  def greet() : Unit = println(s"Hi, ${this.name}")

  // multiple constructor
  def this(name: String) = this("Another Name", 0)
}

var person = new Person("Tauhid", 21)
person.greet("Asad")
person.greet()