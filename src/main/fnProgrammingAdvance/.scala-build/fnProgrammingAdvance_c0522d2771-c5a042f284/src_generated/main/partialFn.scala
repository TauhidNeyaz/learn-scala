

final class partialFn$_ {
def args = partialFn_sc.args$
def scriptPath = """src/main/fnProgrammingAdvance/partialFn.sc"""
/*<script>*/
val aFn = (x : Int) => x + 1 // Function[Int, Int] === Int => Int

// If we want a fn which only work for some specific part of Int
val fussyFn = (x : Int) =>
  if (x == 1) 10
  else if (x == 5) 20
  else if (x == 10) 30
  else throw new FunctionNotApplicableException

class FunctionNotApplicableException extends RuntimeException

val goodFussyFn = (x : Int) => x match {
  case 1 => 10
  case 5 => 20
  case 10 => 30
  case _  => -1
}

val aPartialFn : PartialFunction[Int, Int] = {
  case 1 => 10
  case 5 => 20
  case 10 => 30
}

//println(aPartialFn(2))
println(aPartialFn.isDefinedAt(3))

// lift
val lift = aPartialFn.lift
println(lift(5))
println(lift(9))


val pfChain = aPartialFn.orElse[Int, Int]  {
  case 2 => 100
}

println(pfChain(2))
println(pfChain(5))

/*
NOTE : In Partial Function can only have ONE parameters
 */


// Que 1. Construct a PF yourself
val manualPF = new PartialFunction[Int, Int] {
  override def apply(x: Int): Int = x match {
    case 1 => 10
    case 5 => 20
    case 10 => 30
  }

  override def isDefinedAt(x: Int): Boolean =
    x == 1 || x == 5 || x == 10
}

// Que 2. Dummy chatbot

val chatbot : PartialFunction[String, String] = {
  case "Hello" => "Hi, I'm chatty..."
  case "Play" => "I'm playing ..."
  case "Stop" => "hahaha ...."
}

scala.io.Source.stdin.getLines().foreach(lines => println("chatbot says : " + chatbot(lines)))
/*</script>*/ /*<generated>*//*</generated>*/
}

object partialFn_sc {
  private var args$opt0 = Option.empty[Array[String]]
  def args$set(args: Array[String]): Unit = {
    args$opt0 = Some(args)
  }
  def args$opt: Option[Array[String]] = args$opt0
  def args$: Array[String] = args$opt.getOrElse {
    sys.error("No arguments passed to this script")
  }

  lazy val script = new partialFn$_

  def main(args: Array[String]): Unit = {
    args$set(args)
    val _ = script.hashCode() // hashCode to clear scalac warning about pure expression in statement position
  }
}

export partialFn_sc.script as `partialFn`

