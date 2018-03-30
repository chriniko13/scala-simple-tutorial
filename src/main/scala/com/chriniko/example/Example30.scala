package com.chriniko.example

/*
 Type Inference (https://docs.scala-lang.org/tour/type-inference.html)
 */
class Example30 extends Example {

  override def run(): Unit = {

    // 1
    val lang = "Scala"
    println(lang.getClass)

    // 2
    def squareOf(x: Int) = x * x

    println(squareOf(2).getClass)

    // 3 recursive method needs result type.
    def factorial(n: Int): Int = {
      if (n == 0) 1
      else n * factorial(n - 1)
    }

    println(factorial(4))
    println(factorial(4).getClass)


    // 4
    case class MyPair[A, B](x: A, y: B)
    val p = MyPair(1, "scala") // type: MyPair[Int, String]
    println(p.getClass)


    def id[T](x: T) = x

    val q = id(1) // type: Int
    println(q.getClass)

    // 5
    println(Seq(1, 3, 4).map(x => x * 2))

  }

}
