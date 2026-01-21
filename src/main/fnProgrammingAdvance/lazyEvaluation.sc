// lazy DELAYS the evaluation of values
lazy val x : Int = throw new RuntimeException

// lazy evaluate one when it uses for the very first time
lazy val y : Int = {
  println("Lazy ...")
  42
}

println(y)