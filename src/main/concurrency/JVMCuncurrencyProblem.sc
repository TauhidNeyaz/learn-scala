
def runInParallel() : Unit = {
  var x = 0

  val thread1 = new Thread(() => {
    x = 1
  })

  val thread2 = new Thread(() => {
    x = 2
  })

  // race condition
  thread1.start()
  thread2.start()
  println(x)
}

//runInParallel()

case class BankAccount(var amount : Int)

def buy(account : BankAccount, price : Int, item : String) : Unit = {
  account.amount -= price
}

def buySafe(account: BankAccount, price: Int, item: String) : Unit = {
  account.synchronized {
    account.amount -= price
  }
}

def Bank(): Unit = {
  (1  to 10000).foreach { _ =>
    val account = BankAccount(50000)
    val thread1 = new Thread(() => buy(account, 3000, "shoe"))
    val thread2 = new Thread(() => buy(account, 4000, "iPhone"))

    thread1.start()
    thread2.start()
    thread1.join()
    thread2.join()

    if (account.amount != 43000) println("Woo! Broken Bank")
  }
}

//Bank()



/**
    Create inception thread :
      thread 1
        -> thread 2
          -> thread 3
            ....
    each thread print a message : Hello from thread $i
    Print all the message in reverse order
 */

def inceptionThread(maxThread: Int, i : Int = 1) : Thread = {
  new Thread(() => {
    if (i < maxThread) {
      val newThread = inceptionThread(maxThread, i + 1)
      newThread.start()
      newThread.join()
    }
    println(s"Hello from Thread $i")
  })
}

inceptionThread(10).start()

// What will be the Min / Max value of x ??
def minMaxX(): Unit = {
  var x = 0
  val threads = (1 to 100).map(_ => new Thread(() => x += 1))
  threads.foreach(_.start())
  println(x)
}