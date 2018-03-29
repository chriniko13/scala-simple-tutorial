package com.chriniko.example

import scala.language.implicitConversions

/*
  Implicit conversions (https://docs.scala-lang.org/tour/implicit-conversions.html)
 */
class Example28 extends Example {

  override def run(): Unit = {
    Test.doTest()
  }

  //------------------------------------------------------------------------
  object Test {

    def doTest(): Unit = {
      // 1
      val a = new Complex(4.0, 5.0)
      val b = new Complex(2.0, 3.0)

      println(a) // 4.0 + 5.0i
      println(a + b) // 6.0 + 8.0i
      println(a - b) // 2.0 + 2.0i

      // 2
      println
      val c = new Complex(2.0, 2.0)
      println(c + 2.5)

      println
      import Complex._
      println(2.5 + c) // Note: does not compile, needs implicit conversion to be defined.

      println
      val d = (1.0, 2.0) + 2.5
      println(d)

      println
      println(~(1.0, 2.0))
    }

  }

  //------------------------------------------------------------------------
  class Complex(val real: Double, val imag: Double) {

    def +(that: Complex): Complex =
      new Complex(this.real + that.real, this.imag + that.imag)

    def -(that: Complex): Complex =
      new Complex(this.real - that.real, this.imag - that.imag)

    def +(real: Double): Complex =
      new Complex(this.real + real, this.imag)

    def unary_~ : Double = Math.sqrt(real * real + imag * imag)


    override def toString: String = real + " + " + imag + "i"
  }

  // companion object.
  object Complex {

    implicit def Double2Complex(realPart: Double): Complex =
      new Complex(realPart, 0.0)

    implicit def Tuple2Complex(tuple2: Tuple2[Double, Double]): Complex =
      new Complex(tuple2._1, tuple2._2)

  }

  //------------------------------------------------------------------------


}
