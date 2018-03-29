package com.chriniko.example

/*
  Multiple Parameters List (Currying) (https://docs.scala-lang.org/tour/multiple-parameter-lists.html)
 */
class Example14 extends Example {

  override def run(): Unit = {

    // 1
    val numbers = List(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
    println(numbers.foldLeft(0)((acc, elem) => acc + elem))

    // 2
    val numbersFolding = numbers.foldLeft(1) _
    println(numbersFolding((acc, elem) => acc * elem))
    println(numbersFolding(_ * _))

    // 3
    println
    val partialCollector = numbers.foldLeft(List[Int]()) _
    println(partialCollector((acc, elem) => acc :+ elem * 2))

    // 4
    println
    implicit val ctx: Context = Context()

    def execute(arg: Int)(implicit context: Context): Unit = {
      context.print(arg)
    }

    execute(1)
  }

  case class Context() {
    def print(arg: Int): Unit = println(arg)
  }

}
