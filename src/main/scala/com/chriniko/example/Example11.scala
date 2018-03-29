package com.chriniko.example

import scala.collection.mutable.ArrayBuffer

/*

  Traits example (https://docs.scala-lang.org/tour/traits.html)

 */
class Example11 extends Example {

  override def run(): Unit = {


    // 1
    val iterator = new IntIterator(10) with RichIterator[Int]
    println(iterator.next)
    println(iterator.next)
    println(iterator.next)
    println(iterator.collect)
    println

    // 2
    val pets: ArrayBuffer[Pet] = ArrayBuffer[Pet]()
    pets += new Dog("bruno")
    pets += new Cat("mary")

    pets.foreach(println)
  }


  //---------------------------------------------------------------------

  trait Iterator[T] {

    def hasNext: Boolean

    def next: T
  }

  trait RichIterator[T] extends Iterator[T] {

    def collect: List[T] = {

      var r = List[T]()

      while (hasNext) {
        r = r ::: List[T](next)
      }

      r
    }

  }

  class IntIterator(val upperBound: Int) extends Iterator[Int] {

    private var current = 0

    override def hasNext: Boolean = {
      current < upperBound
    }

    override def next: Int = {
      current += 1
      current
    }
  }

  //---------------------------------------------------------------------


  trait Pet {
    val name: String

    override def toString: String = s"${this.getClass.getSimpleName} --- name = $name"
  }

  class Dog(n: String) extends Pet {
    override val name: String = n
  }

  class Cat(val name: String) extends Pet

}
