trait myFunction[A, B] {
  def apply(element : A) : B
}

val doubler = new myFunction[Int, Int] {
  override def apply(element: Int): Int = element * 2
}

println(doubler(10))


// function type => Function[A, B]
val StringToIntConverter = new Function1[String, Int] {
  override def apply(v1: String): Int = v1.toInt
}
val x = (StringToIntConverter("12"))
println(x.getClass)

val adder = new Function2[Int, Int, Int] {
  override def apply(a : Int, b : Int) : Int = a + b
}
println(adder(5, 7))
// Function type Function2[A, B, R] === (A, B) => R
// ALL SCALA FUNCTION ARE OBJECTS


// 1.   A function which takes 2 string and concat them using type fn

val concat = new Function2[String, String, String] {
  override def apply(v1: String, v2: String): String = v1 + v2
}
println(concat("Tauhid", "Neyaz"))

// 2.   define a fn which take an int and return a fn which takes an int input

val supperAdder : Function1[Int, Function1[Int, Int]] = new Function1[Int, Function1[Int, Int]] {
  override def apply(x: Int) : Function1[Int, Int] = new Function1[Int, Int] {
    override def apply(y: Int): Int = x + y
  }
}

println(supperAdder(3)(4)) // curried fn
