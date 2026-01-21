

final class intro$_ {
def args = intro_sc.args$
def scriptPath = """src/main/concurrency/intro.sc"""
/*<script>*/
import java.util.concurrent.{Executor, Executors}

/*
    interface Runnable {
        public void run()
    }

 */

val aThread = new Thread(new Runnable {
  override def run(): Unit = println("Hello Thread ...")
})

//aThread.start() // gives the signal to JVM to start a JVM thread
aThread.join() // blocks until aThread finish running

val sayHello   = new Thread(() => (1 to 5).foreach(_ => println("Hello")))
val sayGoodBye = new Thread(() => (1 to 5).foreach(_ => println("GoodBye")))

sayGoodBye.start()
sayHello.start()
// each runs produce different result order

// executor

val pool = Executors.newFixedThreadPool(10)
pool.execute(() => println("Printed by thread pool"))

pool.execute(() => {
  Thread.sleep(1000)
  println("done after 1 sec")
})

pool.execute(() => {
  Thread.sleep(1000)
  println("almost done")
  Thread.sleep(1000)
  println("done after 2 sec")
})

pool.shutdown()
/*</script>*/ /*<generated>*//*</generated>*/
}

object intro_sc {
  private var args$opt0 = Option.empty[Array[String]]
  def args$set(args: Array[String]): Unit = {
    args$opt0 = Some(args)
  }
  def args$opt: Option[Array[String]] = args$opt0
  def args$: Array[String] = args$opt.getOrElse {
    sys.error("No arguments passed to this script")
  }

  lazy val script = new intro$_

  def main(args: Array[String]): Unit = {
    args$set(args)
    val _ = script.hashCode() // hashCode to clear scalac warning about pure expression in statement position
  }
}

export intro_sc.script as `intro`

