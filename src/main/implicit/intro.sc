
implicit val reverseOrder : Ordering[Int] = Ordering.fromLessThan(_ > _)
implicit val normalOrder  : Ordering[Int] = Ordering.fromLessThan(_ < _)

//println(List(3, 42, 1, 6, 7, 8).sorted)

/*
    Implicits
      - val / var
      - objects
      - accessor methods (def with no parenthesis)

    By defaults sorted has an implicit definition which sort in normal order
    When we define an implicit order by ourselves it gives preference over that

    If we define multiple implicit, compiler confuse which to pick ??
 */

case class Person(name : String, age : Int)

val persons = List(
  Person("John", 21),
  Person("Mick", 23),
  Person("Alice", 19),
  Person("Bob", 32)
)

//implicit val alpha : Ordering[Person] = Ordering.fromLessThan((a, b) => a.name.compareTo(b.name) < 0)

// It will cause an error ... compiler does not have the implicit for type Person , NEED TO DEFINE
//println(persons.sorted)

/*
      Implicit scope
        - normal scope (LOCAL SCOPE)
        - imported scope
        - companion object of all type involved in method signature
          - List
          - Ordering
          - all types involved = A or supertype

      def sorted[B >: A] (implicit ord : Ordering[B]) : List[B]
 */

object sortByName {
  implicit val name : Ordering[Person] = Ordering.fromLessThan((a, b) => a.name.compareTo(b.name) < 0)
}

object sortByAge {
  implicit val age : Ordering[Person] = Ordering.fromLessThan((a, b) => a.age < b.age)
}

import sortByAge.age
//println(persons.sorted)


/*
      You have Purchase define Order
        - by totalPrice
        - unit
        - unit price
 */

case class Purchase(nUnit : Int, unitPrice : Double)

object sortByTotalPrice {
  implicit val totalPrice : Ordering[Purchase] = Ordering.fromLessThan((a, b) => a.unitPrice * a.nUnit < b.unitPrice * b.nUnit)
}
object sortByUnit {
  implicit val unit : Ordering[Purchase] = Ordering.fromLessThan((a, b) => a.nUnit < b.nUnit)
}
object sortByUnitPrice {
  implicit val unitPrice : Ordering[Purchase] = Ordering.fromLessThan((a, b) => a.unitPrice < b.unitPrice)
}

val purchases = List(
  Purchase(10, 123.43),
  Purchase(12, 238.23),
  Purchase(34, 90.23),
  Purchase(19, 213.21)
)

import sortByTotalPrice.totalPrice
println(purchases.sorted)