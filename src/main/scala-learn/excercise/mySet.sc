import scala.annotation.tailrec

trait mySet[A] extends (A => Boolean) {

  def apply(ele: A): Boolean = contains(ele)

  def contains(ele: A): Boolean
  def +(ele: A): mySet[A]
  def ++(anotherSet: mySet[A]): mySet[A]

  def map[B](f: A => B): mySet[B]
  def flatMap[B](f: A => mySet[B]): mySet[B]
  def filter(predicate: A => Boolean): mySet[A]
  def foreach(f: A => Unit): Unit

  def -(ele : A) : mySet[A]
  def --(anotherSet : mySet[A]) : mySet[A]
  def &(anotherSet : mySet[A]) : mySet[A]

  def unary_! : mySet[A]
}

class emptySet[A] extends mySet[A] {

  override def contains(ele: A): Boolean = false
  override def +(ele: A): mySet[A] = new nonEmptySet[A](ele, this)  // add
  override def ++(anotherSet: mySet[A]): mySet[A] = anotherSet      // concat two set

  override def map[B](f: A => B): mySet[B] = new emptySet[B]
  override def flatMap[B](f: A => mySet[B]): mySet[B] = new emptySet[B]
  override def filter(predicate: A => Boolean): mySet[A] = this
  override def foreach(f: A => Unit): Unit = ()

  override def -(ele : A) : mySet[A] = this                    // remove
  override def --(anotherSet: mySet[A]): mySet[A] = anotherSet        // diff of two set
  override def &(anotherSet: mySet[A]): mySet[A] = this        // intersection of 2 set

  // negation emptySet is All possible A => All integers
  override def unary_! : mySet[A] = new propertyBasedSet[A](_ => true) // infinite set
}

class propertyBasedSet[A](property : A => Boolean) extends mySet[A] {
  def contains(ele: A): Boolean = property(ele)

  //{x in A such that property(x)} + ele = { x in A such that property(x) | ele == x}
  def +(ele: A): mySet[A] =
    new propertyBasedSet[A](x => property(x) || x == ele)

  def ++(anotherSet: mySet[A]): mySet[A] =
    new propertyBasedSet[A](x => property(x) || anotherSet(x))

  def map[B](f: A => B): mySet[B]               = politelyFail
  def flatMap[B](f: A => mySet[B]): mySet[B]    = politelyFail
  def foreach(f: A => Unit): Unit               = politelyFail

  def filter(predicate: A => Boolean): mySet[A] =
    new propertyBasedSet[A](x => predicate(x) && property(x))

  def -(ele: A): mySet[A] = filter(x => x != ele)
  def --(anotherSet: mySet[A]): mySet[A] = filter(!anotherSet)
  def &(anotherSet: mySet[A]): mySet[A] = filter(anotherSet)

  def unary_! : mySet[A] =
    new propertyBasedSet[A](x => !property(x))

  def politelyFail: Nothing = throw new IllegalArgumentException("Really deep rabbit hole ...")
}

class nonEmptySet[A](head: A, tail: mySet[A]) extends mySet[A] {

  override def contains(ele: A): Boolean =
    ele == head || tail.contains(ele)

  override def +(ele: A): mySet[A] =
    if (this contains ele) this
    else new nonEmptySet[A](ele, this)

  /*
  [1 2 3] ++ [4 5]
  [2 3] ++ [4 5] + 1
  [3] ++ [4 5] + 1 + 2
  [] ++ [4 5] + 1 + 2 + 3 emptySet return anotherSet
  [4 5] + 1 + 2 + 3 = [4 5 1 2 3] order does not matter
  */

  override def ++(anotherSet: mySet[A]): mySet[A] =
    tail ++ anotherSet + head

  override def map[B](f: A => B): mySet[B] =
    (tail map f) + f(head)

  override def flatMap[B](f: A => mySet[B]): mySet[B] =
    (tail flatMap f) ++ f(head)

  override def filter(predicate: A => Boolean): mySet[A] = {
    val filteredTail = tail filter predicate
    if (predicate(head)) filteredTail + head
    else filteredTail
  }

  override def foreach(f: A => Unit): Unit = {
    f(head)
    tail foreach f
  }

  override def -(ele : A) : mySet[A] =
    if (ele == head) tail
    else tail - ele + head

  override def --(anotherSet:  mySet[A]): mySet[A] =
    filter(x => !anotherSet.contains(x))

  override def &(anotherSet:  mySet[A]): mySet[A] = {
    filter(x => anotherSet.contains(x))
    // filter(x => anotherSet(x)) same as above
    // filter(anotherSet)   same as above
  }

  // All the natural numbers which are not there in current set
  override def unary_! : mySet[A] =
    new propertyBasedSet[A](x => !this.contains(x))
}

object mySet {

  /* val s = mySet(1, 2, 3)
    = buildSet(Seq(1, 2, 3), [])
    = buildSet(Seq(2, 3), [] + 1)
    = buildSet(Seq(3), [1] + 2)
    = buildSet(Seq(), [1 2] + 3)
    = [1 2 3]
    */

  def apply[A](values: A*): mySet[A] = {
    @tailrec
    def buildSet(valSeq: Seq[A], acc: mySet[A]): mySet[A] =
      if (valSeq.isEmpty) acc
      else buildSet(valSeq.tail, acc + valSeq.head)

    buildSet(values.toSeq, new emptySet[A])
  }
}

// TEST
val s = mySet(1, 2, 3, 4, 5)
s + 5 ++ mySet(-1, -2, 2) map(x => x * 10) filter (_ % 2 == 0) foreach println

val negative = !s //  s.unary_! = all the natural number != (1, 2, 3, 4, 5)
println(negative(6))
println(negative(2))

val negativeEven = negative.filter(_ % 2 == 0)
println(negativeEven(7))
println(negativeEven(10))