import scala.annotation.tailrec

def aFunction(a:String, b:Int) : String = {
  a + " " + b
}
println(aFunction("Hello", 12))

/*
Definition of a function
  def name (parameter1:type, parameter2:type) : functionReturnType = {
    code block
  } Expression
 */


def repeatFunction(aString:String, aInt:Int) : String = {
  if (aInt == 1) aString
  else aString + repeatFunction(aString, aInt - 1)
}
println(repeatFunction("Hello", 3))


// Functions in Function
def aBigFunction(num:Int) : Int = {
  def aSmallFunction(x:Int, y:Int) : Int = {
    x + y
  }
  def anotherFunction(x:Int, y:Int) : Int = {
    x * y
  }
  num + aSmallFunction(5, 7) + anotherFunction(3, 6)
}
println(aBigFunction(10))



// Que 1. Greeting
def greeting(name:String, age:Int) : String = {
  "Hello My Name is " + name + " I am " + age + " year old"
}

println(greeting("Tauhid", 21))


// Que 2. Print factorial
def factorial(num:Int) : Int = {
  if (num == 1) 1
  else num * factorial((num - 1))
}

println(factorial(5))

// Que 3. Print Fibonacci
def fibonacci(num: Int) : Int = {
  if (num <= 2) 1
  else fibonacci(num - 1) + fibonacci(num - 2)
}

println(fibonacci(7))


// Que 4. check Prime

def isPrime(num: Int): Boolean = {
  @tailrec
  def isFactor(x: Int): Boolean = {
    if (x <= 1) true
    else num % x != 0 && isFactor(x - 1)
  }
  isFactor(num / 2)
}

println(isPrime(7))
println(isPrime(1003))
println(isPrime(57))