package com.chriniko.example

/*

  Classes (https://docs.scala-lang.org/tour/classes.html)

 */
class Example6 extends Example {


  override def run(): Unit = {

    // 1
    val u1 = new User()
    println(u1)

    // 2
    val u2 = new User("MyName")
    println(u2)

    // 3
    val u3 = new User(email = "chriniko@mail.com", name = "ChriNiko")
    println(u3)

    // 4
    val p = new Point
    p.x = 12
    println(p.x)
    p.x = 102
  }


  class User(var name: String = "_name_", var email: String = "_email_") {

    override def toString: String = s"name = $name, email = $email"

  }

  class Point {
    private var _x = 0
    private var _y = 0
    private val bound = 100

    def x = _x

    def x_=(newValue: Int): Unit = {
      if (newValue < bound) _x = newValue else printWarning
    }

    def y = _y

    def y_=(newValue: Int): Unit = {
      if (newValue < bound) _y = newValue else printWarning
    }

    private def printWarning = println("WARNING: Out of bounds")
  }


}
