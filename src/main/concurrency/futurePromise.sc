import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration.DurationInt
import scala.concurrent.{Await, Future}
import scala.util.{Failure, Success}


def getVal : Int = {
  Thread.sleep(2000)
  42
}

val aFuture = Future {
  getVal
}

println(aFuture.value)
println("waiting on the Future...")

aFuture.onComplete {
  case Success(value) => println("The value we get is " + value)
  case Failure(exception) => println("Failed with" + exception)
}

Thread.sleep(3000)



// Online Banking App

case class User(name : String)
case class Transaction(sender : String, receiver : String, amount : Double, status : String)

object BankingSystem {
  val name : String = "Bank of Scala"

  def fetchUser(name : String) : Future[User] = Future {
    // simulate fetching from DB
    Thread.sleep(500)
    User(name)
  }

  def createTransaction(user : User, merchantName : String, amount : Double) : Future[Transaction] = Future {
    // simulate some process
    Thread.sleep(1000)
    Transaction(user.name, merchantName, amount , "SUCCESS")
  }

  def purchase(username : String, item : String, merchantName : String, cost : Double) : String = {
    // fetch the user
    // create a transaction
    // wait for transaction to finish

    val transactionStatusFuture = for {
      users <- fetchUser(username)
      transaction <- createTransaction(users, merchantName, cost)
    } yield transaction.status

    Await.result(transactionStatusFuture, 2.seconds)
  }
}

println(BankingSystem.purchase("Tauhid", "PS5", "Sony", 3000))



