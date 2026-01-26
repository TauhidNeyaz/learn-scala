import java.util.Date

/*
    User, Post , Feed
    JSON Serialise
 */

case class User(name : String, age : Int, email : String)
case class Post(content : String, createdAt : Date)
case class Feed(user : User, posts : List[Post])

/*
    1. intermediate data type : String, Int, List, Date
    2. type classes for conversion to intermediate data types
    3. serialize to JSON
 */



sealed trait JSONValues {
  def stringify : String
}

// Step 1. intermediate data type
final case class JSONString(values : String) extends JSONValues {
  override def stringify: String =
    "\"" + values + "\""
}

final case class JSONInt(values : Int) extends JSONValues {
  override def stringify: String =
    values.toString
}

final case class JSONArray(values : List[JSONValues]) extends JSONValues {
  override def stringify: String =
    values.map(_.stringify).mkString("[", ",", "]")
}


final case class JSONObject(values : Map[String, JSONValues]) extends JSONValues {
  /*
        {
          name : "John"
          age : 22
          friends : [...]
          posts {
                content : "hi there ..."
                date : ...
          }
        }
   */

  override def stringify: String =
    values.map {
      case (key, value) => "\"" + key + "\" : " + value.stringify
    }
      .mkString("{", ",", "}")
}

val data = JSONObject(Map(
  "user" -> JSONString("John"),
  "posts" -> JSONArray(List(
    JSONString("The first post"), JSONInt(442)
  ))
))

println(data.stringify)


// Step 2. Type classes

trait JSONConverter[T] {
  def convert(value : T) : JSONValues
}

implicit object StringConverter extends JSONConverter[String] {
  override def convert(value: String): JSONValues = JSONString(value)
}

implicit object IntConverter extends JSONConverter[Int] {
  override def convert(value: Int): JSONValues = JSONInt(value)
}

implicit object UserConverter extends JSONConverter[User] {
  override def convert(user: User): JSONValues =
    JSONObject(Map(
      "user" -> JSONString(user.name),
      "age" -> JSONInt(user.age),
      "email" -> JSONString(user.email)
    ))
}

implicit object PostConverter extends JSONConverter[Post] {
  override def convert(post: Post): JSONValues =
    JSONObject(Map(
      "content" -> JSONString(post.content),
      "createdAt" -> JSONString(post.createdAt.toString)
    ))
}

implicit object FeedConverter extends JSONConverter[Feed] {
  override def convert(feed: Feed): JSONValues =
    JSONObject(Map(
      "user" -> UserConverter.convert(feed.user), // TODO
      "posts" -> JSONArray(feed.posts.map(PostConverter.convert)) // TODO
    ))
}

implicit class JSONOps[T](value : T) {
  def toJSON(implicit converter : JSONConverter[T]) : JSONValues =
    converter.convert(value)
}

var now = new Date(System.currentTimeMillis())

val john = User("John", 21, "john@gmail.com")
val feed = Feed(john, List(
  Post("Hello", now),
  Post("Woo...", now)
))

println(feed.toJSON.stringify)





