package com.chriniko.example

/*
  Named Arguments (https://docs.scala-lang.org/tour/named-arguments.html)
 */
class Example35 extends Example {

  override def run(): Unit = {
    // 1
    def printName(first: String, last: String): Unit = {
      println(first + " " + last)
    }

    printName("John", "Smith")  // Prints "John Smith"
    printName(first = "John", last = "Smith")  // Prints "John Smith"
    printName(last = "Smith", first = "John")  // Prints "John Smith"
  }

}
