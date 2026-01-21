
// list
val list = List(1, 2, 3, 4)
println(list)
println(list.head)
println(list.tail)

// map
println(list.map(_ + 1))
println(list.map(_ + "is a number"))


// filter
println(list.filter(_ % 2 == 0))


// flatMap
val toPair = (x : Int) => List(x, x + 1)
println(list.flatMap(toPair))

println(list.filter(_ % 2 == 0).map(_ + 10))

val number = List(1, 2, 3, 4)
val chars  = List('a', 'b', 'c', 'd')
val colors = List("black", "white")

val combination = number.flatMap(x => chars.map(c => "" + x + c))
println(combination)

val combo = number.flatMap(x => chars.flatMap(ch => colors.map(col => "" + x + ch + col))) // hard to read
println(combo)

// easy to read
val forCombination = for {
  x   <- number
  ch  <- chars
  col <- colors
} yield "" + x + ch + col
println(forCombination)



// foreach
number.foreach(println)