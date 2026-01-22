import scala.collection.mutable
import scala.util.Random

/*
    Producer Consumer Problem
    producer -> [ x ] -> consume
 */

class simpleContainer {
  private var value : Int = 0

  def isEmpty: Boolean = value == 0

  def get() : Int = {
    val res = value
    value = 0
    res
  }

  def set(x : Int) : Unit = value = x
}

def naiveProducerConsumer(): Unit = {
  val container = new simpleContainer()

  val consumer = new Thread(() => {
    println("[consumer] waiting ...")
    while (container.isEmpty) {
      println("[consumer] still waiting ...")
    }
    println(s"[consumer] I have a value ${container.get()}")
  })

  val producer = new Thread(() => {
    println("[producer] computing ...")
    Thread.sleep(500)
    val x = 10
    println("[producer] I got a value, setting it ...")
    container.set(x)
  })

  consumer.start()
  producer.start()
}
//naiveProducerConsumer()


def smartProducerConsumer() : Unit = {
  val container = new simpleContainer()

  val consumer = new Thread(() => {
    println("[consumer] waiting for value ...")
    container.synchronized {
      container.wait()
    }
    println("[consumer] I got some value " + container.get())
  })

  val producer = new Thread(() => {
    println("[producer] Computing...")
    Thread.sleep(2000)
    val value = 42
    container.synchronized {
      println("[producer] setting a value")
      container.set(value)
      container.notify()
    }
  })

  consumer.start()
  producer.start()
}

//smartProducerConsumer()


/*
      Producer Consumer in a Stream
      producer -> [a, b, c] -> consumer
 */

def producerConsumerStream() : Unit = {
  val buffer : mutable.Queue[Int] = new mutable.Queue[Int]
  val maxSize = 3

  val consumer = new Thread(() => {
    val random = new Random()

    while (true) {
      buffer.synchronized {
        if (buffer.isEmpty) {
          println("[consumer] buffer empty, waiting ... ")
          buffer.wait()
        }

        val x = buffer.dequeue()
        println("[consumer] I got a value" + x)

        buffer.notify()
      }

      Thread.sleep(random.nextInt(500))
    }
  })

  val producer = new Thread(() => {
    val random = new Random()
    var value = 1
    while (true) {
      buffer.synchronized {
        if (buffer.size == maxSize) {
          println("[producer] Buffer full , waiting ... ")
          buffer.wait()
        }

        println("[producer] Producing ... " + value)
        buffer.enqueue(value)
        value += 1

        buffer.notify()
      }
    }
  })

  consumer.start()
  producer.start()
}

producerConsumerStream()

