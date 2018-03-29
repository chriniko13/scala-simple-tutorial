package com.chriniko.example

/*

  Basics (https://docs.scala-lang.org/tour/basics.html)

 */
class Example4 extends Example {

  override def run(): Unit = {

    // 1
    val res = {
      val x = 1 + 1
      x
    }
    println(res)

    // 2
    println(((x: Int) => x + 1) (2))

    // 3
    val mult2 = (x: Int, y: Int) => (x + y) * 2
    println(mult2(1, 2))

    // 4
    def add(x: Int, y: Int) = x + y

    println(add(1, 1))

    // 5
    def add_Currying(x: Int)(y: Int) = x + y

    val someFunc = add_Currying(1) _
    println(someFunc(2))

    // 6
    println(new Greeter("c", "o").greet("hrinik"))

    // 7
    val p1 = Point(1, 2)
    val p2 = Point(1, 2)
    if (p1 == p2) {
      println("same")
    } else {
      println("not same")
    }

    // 8
    object IdFactory {
      private var counter = 0

      def create(): Int = {
        counter += 1
        counter
      }
    }
    println(IdFactory.create())

    // 9
    val pub = new MyPublisher
    println(pub.publish("chriniko"))
    println(pub.publish("chriniko"))

  }


  //-----------------------------------------------------------------------------------

  class Greeter(prefix: String, suffix: String) {

    def greet(name: String): Unit = {
      println(prefix + name + suffix)
    }

  }

  //-----------------------------------------------------------------------------------

  case class Point(x: Int, y: Int)

  //-----------------------------------------------------------------------------------

  trait Publisher {
    private var count: Int = 0
    def publish(s: String) : Unit = {
      count += 1
      println(s"$s --- count = $count")
    }
  }

  class MyPublisher extends Publisher {

    override def publish(s: String): Unit = {
      super.publish(s)
      println("bye!")
    }

  }

  //-----------------------------------------------------------------------------------


}
