
trait HTMLWritable {
  def toHTML : String
}

case class User(name : String, age : Int, email : String) extends HTMLWritable {
  override def toHTML: String = s"<div> $name ($age yo) <a href=$email /> </div>"
}

User("John", 21, "john@email.com")

/*
    ISSUE IN ABOVE CODE
      - work for only the type WE write
      - ONE implementation out of quite a member
 */

// Option 2 : Pattern Matching
object HTMLSerializerPM {
  def serialiseHTML(values : Any) : Unit = values match {
    case User(a, b, c) =>
//    case java.util.Date =>
    case _ =>
  }
}

/*
      ISSUE IN ABOVE CODE
        - lost type safety
        - need to modify code everytime
        - still ONE implementation
 */


// Option 3 :

trait HTMLSerialize[T] {
  def serialise(value : T) : String
}

object UserSerialise extends HTMLSerialize[User] {
  override def serialise(user: User): String = s"<div> ${user.name} (${user.age} yo) <a href=${user.email} /> </div>"
}

val john = User("John", 21, "john@email.com")
println(UserSerialise.serialise(john))