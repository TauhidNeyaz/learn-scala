
case class Person(name : String , age : Int)

// 1. class parameters are fields
val sam = new Person("Sam", 21)
println(sam.name)
println(sam.age)

// 2. sensible toString
println(sam)

// 3. equals and hashCode implemented OOTB
val sam2 = new Person("Sam", 21)
println(sam == sam2) // true

// 4. CCs have handy copy methods
val sam3 = sam.copy(age = 25)
println(sam3)

// 5. CCs are serializable
// Used in AKKA

// 6.