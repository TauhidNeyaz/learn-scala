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