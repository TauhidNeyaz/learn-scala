import scala.annotation.tailrec

def factorial(num:Int) : Int = {
  if (num == 1) 1
  else num * factorial(num - 1)
}

println(factorial(10))
//println(factorial(100000)) // StackOverflow

def anotherWay(num : Int) : BigInt = {
  @tailrec
  def helper(x : Int, accumulator : BigInt) : BigInt = {
    if (x == 1) accumulator
    else helper(x - 1,  x * accumulator) // TAIL RECURSION
  }
  helper(num, 1)
}

println(anotherWay(10))
println(anotherWay(10000)) // It will work
