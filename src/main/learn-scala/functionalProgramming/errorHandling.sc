import scala.util.{Failure, Success, Try}

val aSuccess = Success(10)
val aFailure = Failure(RuntimeException("SUPER FAILURE"))

println(aSuccess)
println(aFailure)

def unSafeMethod () : String = throw new RuntimeException("NO STRING RETURN")
val result = Try(unSafeMethod())
println(result)
println(result.isSuccess)

def backupMethod () : String = "A Valid String"
val fallbackTry = Try(unSafeMethod()).orElse(Try(backupMethod()))
println(fallbackTry)
println(fallbackTry.isSuccess)