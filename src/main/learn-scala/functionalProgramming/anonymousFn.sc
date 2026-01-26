
// Anonymous Function (LAMBDA)

val doubler : Int => Int = (x : Int) => x * 2
val doublerClone = (x : Int) => x * 2
println(doubler(10))
// Both the doubler and doublerClone do the same things (syntactic sugar)

// multiple parameters
val add = (x : Int, y : Int) => x + y
val addClone : (Int, Int) => Int = (x : Int, y : Int) => x + y
println(add(10, 23))

// No parameters
val DoSomething = () => println("Doing...")
println(DoSomething())


// write LAMBA using curly braces

val stringToInt = { (str : String) =>
  str.toInt
}
println(stringToInt("100") + 12)


// MORE syntactic sugar

val inc : Int => Int = (x : Int) => x + 1
val fancyInc : Int => Int = _ + 1

println(fancyInc(10))

val fancyAdder : (Int, Int) => Int = _ + _
println(fancyAdder(10, 11))

val superAdder = (x : Int) => (y : Int) => x + y
println(superAdder(29)(10))

val some = (x : Int , y : Int) => {
  val p = 4
  val q = 8
  if (x % 2 == 0 && y % 2 == 0) (x + y) * 2
  else (x - y) * 2
}
println(some(2, 2))