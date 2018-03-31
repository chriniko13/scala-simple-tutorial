package com.chriniko.example

/*
  Hallmarks of Functional Programming (mapping, filtering, folding, reducing)
 */
class Example39 extends Example {

  override def run(): Unit = {

    // 1
    val lst = List(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
    println(lst.map(x => x * x))

    println(lst.filter(x => x % 3 == 0))

    println(lst.reduce((x, y) => x + y))

    println(lst.fold(0)((x, y) => x + y))

    println(lst.fold(0)(numAdd()))

    println(lst.reduceLeft((x, y) => x * y))

    println(lst.reduceRight((x, y) => x * y))

    println(lst.foldLeft(1)((x, y) => x * y))

    println(lst.foldRight(1)((x, y) => x * y))

  }

  def numAdd(): (Int, Int) => Int = (x, y) => x + y

}
