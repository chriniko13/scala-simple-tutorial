package com.chriniko.example

/*
  Nested Functions (https://docs.scala-lang.org/tour/nested-functions.html)
 */
class Example13 extends Example {

  override def run(): Unit = {

    // 1

    def factorial(n : Int) : Int = {
      def factorialInternal(n: Int, acc: Int) : Int = {

        if (n <= 1) acc else factorialInternal(n - 1, acc * n)
      }

      factorialInternal(n, 1)
    }

    println("Factorial of 0: " + factorial(0))
    println("Factorial of 1: " + factorial(1))
    println("Factorial of 2: " + factorial(2))
    println("Factorial of 3: " + factorial(3))
    println("Factorial of 4: " + factorial(4))

  }

}
