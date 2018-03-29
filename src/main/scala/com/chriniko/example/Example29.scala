package com.chriniko.example

/*
  Polymorphic Methods (https://docs.scala-lang.org/tour/polymorphic-methods.html)
 */
class Example29 extends Example {

  override def run(): Unit = {

    // 1
    def listOfDuplicates[A](input: A, times: Int): List[A] = {
      if (times < 1) Nil
      else input :: listOfDuplicates(input, times - 1)
    }

    println(listOfDuplicates[Int](4, 10))
    println(listOfDuplicates[String]("a", 5))
    println(listOfDuplicates[String]("a", 5).reduce(_ concat _))

  }

}
