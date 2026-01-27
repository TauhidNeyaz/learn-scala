var x = 3
x += 10 // works with +=, -=, *=, /= .... side-effect
println(x)

// Instruction VS Expression

var aBool = true
var condition = if (aBool) 10 else 20
println(condition)
println(if(aBool) 10 else 20)

var i = 0
var loops = while (i < 10) {
  println(i)
  i += 1
}


// EVERYTHING in Scala is Expression,
// Expression always evaluate and return something , But Expression like print, assigning value doesn't return anything
// meaningfully which is called Unit same as Void


var aWeiredValue = (y = 3)
println(aWeiredValue)


var aCodeBlock = { // String (last evaluated line)
  var p = 3
  var q = p + 4
  if (q > p) "Hello" else "Bye"
}

println(aCodeBlock)

var aVoidBlock = { // Unit (last evaluated line)
  println("void block")
}