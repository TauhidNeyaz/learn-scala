import scala.annotation.tailrec
// HOF

val superFn : (Int, (String, (Int => Boolean)) => Int) => (Int, Int) = ???
// Hard to read this HOF

// fn that applies a fn n times over value x
// nTimes(f, n, x)
// nTimes(f, 3, x)  =  f( f( f(x) ) )
// nTimes(f, n, x) = f(f(....f(x))) = nTimes(f, n - 1, f(x))


@tailrec
def nTimes(f: Int => Int, n : Int, x : Int) : Int = {
  if (n <= 0) x
  else nTimes(f, n - 1, f(x))
}
val plusOne = (x : Int) => x + 1
println(nTimes(plusOne, 10, 1)) // will plus one 10 times


def nTimesBetter(f: Int => Int, n : Int) : (Int => Int) =
  if (n <= 0) (x : Int) => x
  else (x : Int) => nTimesBetter(f, n - 1)(f(x))

println(nTimesBetter(plusOne, 10)(1))


// Functions with multiple parameters

def formatter(c: String)(x: Double) : String = c.format(x)

val standardFormat : (Double => String) = formatter("%4.3f")
val preciseFormat : (Double => String) = formatter("%10.8f")

println(standardFormat(Math.PI))
println(standardFormat(Math.PI))



//    1. toCurry(f : (Int, Int) => Int) => (Int => Int => Int)
//       fromCurry(f: (Int => Int => Int)) => (Int, Int) => Int

//    2. compose(f, g) => x => f(g(x))
//       andThen(f, g) => x => g(f(x))


def toCurry(f: (Int, Int) => Int): (Int => Int => Int) =
  x => y => f(x, y)

def fromCurry(f: (Int => Int => Int)) : (Int, Int) => Int =
  (x, y) => f(x)(y)


def compose[A,B,T] (f: A => B, g: T => A) : T => B =
  x => f(g(x))

def andThen[A,B,C] (f: A => B, g: B => C) : A => C =
  x => g(f(x))


def superAdder2 : (Int => Int => Int) = toCurry(_ + _)
println(superAdder2(10)(20))

val add2 = (x : Int) => x + 2
val times3 = (x : Int) => x * 3

val composed = compose(add2, times3)
val ordered = andThen(add2, times3)

println(composed(4))
println(ordered(4))