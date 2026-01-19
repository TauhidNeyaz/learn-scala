
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


class Writer(val name: String, val surname: String, val age: Int) {
  def fullName() : String = {
    s"$name $surname"
  }
}

class Novel(val name: String, val year: Int, val author: Writer) {
  def authorAge() : Int = {
    author.age
  }

  def isWrittenBy: String = {
    author.fullName()
  }
}

val writer1 = new Writer("Sam", "Jackson", 34)
val novel = new Novel("The No Book", 2001, writer1)

println(writer1.fullName())
println(novel.authorAge())
println(novel.isWrittenBy)



class Counter(val counter: Int = 0) {
  def increment = {
    println("Inc ...")
    new Counter(counter + 1)
  }
  def decrement = {
    println("Dec ...")
    new Counter(counter - 1)
  }

  def increment(byVal : Int) : Counter = {
    if (byVal < 0) this
    else increment.increment(byVal - 1)
  }

  def decrement(byVal: Int): Counter = {
    if (byVal < 0) this
    else decrement.decrement(byVal - 1)
  }

  def print(): Unit = println(counter)
}

var counter = new Counter
counter.increment.print()
counter.increment(5).print()
