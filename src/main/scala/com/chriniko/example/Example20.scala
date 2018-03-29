package com.chriniko.example

import scala.collection.immutable

/*
  For Comprehensions (https://docs.scala-lang.org/tour/for-comprehensions.html)
 */
class Example20 extends Example {

  override def run(): Unit = {

    // 1
    val emps = Seq(
      Employee("john", 24, Seniority.Junior),
      Employee("Travis", 28, Seniority.Medior),
      Employee("Kelly", 33, Seniority.Medior),
      Employee("Jennifer", 44, Seniority.Senior),
      Employee("Dennis", 23, Seniority.Junior)
    )

    val names = for (
      emp <- emps
      if emp.age >= 25 && emp.seniority >= Seniority.Senior
    ) yield emp.name

    names.foreach(println)


    // 2
    println

    def foo(n: Int, v: Int): immutable.IndexedSeq[Tuple2[Int, Int]] =
      for (i <- 0 until n;
           j <- i until n if i + j == v)
        yield (i, j)

    println(foo(10, 10))
    println(foo(20, 10))

  }


  object Seniority extends Enumeration {
    val Junior, Medior, Senior = Value
  }

  case class Employee(name: String, age: Int, seniority: Seniority.Value)

}
