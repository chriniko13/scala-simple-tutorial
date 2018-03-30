package com.chriniko.example

/*
  Operators (https://docs.scala-lang.org/tour/operators.html)
 */
class Example31 extends Example {

  override def run(): Unit = {

    // 1
    val vector1 = Vec(1.0, 1.0)
    val vector2 = Vec(2.0, 2.0)

    val vector3 = vector1 + vector2
    println(vector3)

    // 2
    println
    println(MyBool(false) and MyBool(false))
    println(MyBool(true) and MyBool(false))
    println(MyBool(false) and MyBool(true))
    println(MyBool(true) and MyBool(true))

    println(MyBool(true) or MyBool(true))
    println(MyBool(false) or MyBool(true))

    println
    println(MyBool(true).xor(MyBool(false), MyBool(true)))
    println(MyBool(true).xor(MyBool(true), MyBool(true)))
    println(MyBool(false).xor(MyBool(true), MyBool(true)))

  }

  //---------------------------------------------------
  case class Vec(x: Double, y: Double) {
    def +(that: Vec) = Vec(this.x + that.x, this.y + that.y)
  }

  case class MyBool(input: Boolean) {
    def and(that: MyBool): MyBool = if (input) that else this

    def or(that: MyBool): MyBool = if (input) this else that

    def negate: MyBool = MyBool(!input)

    def not(x: MyBool): MyBool = x.negate

    def xor(x: MyBool, y: MyBool): MyBool = (x or y) and not(x and y)

  }

}
