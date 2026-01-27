import scala.sys.SystemProperties

def callByValue(value : Long) : Unit = {
  println("Call by Value : " + value)
  println("Call by Value : " + value)
}

def callByName(value: => Long) : Unit = {
  println("Call by Name : " + value)
  println("Call by Name : " + value)
}

callByValue(System.nanoTime())
callByName(System.nanoTime())
