package com.chriniko.example

import scala.annotation.tailrec

/*
 Implicit Parameters (https://docs.scala-lang.org/tour/implicit-parameters.html)
 */
class Example27 extends Example {

  override def run(): Unit = {

    // 1
    ImplicitTest.doTest()

  }


  //---------------------------------------------------------

  abstract class Monoid[A] {

    def add(x: A, y: A): A

    def unit(): A
  }

  object ImplicitTest {

    implicit val intMonoid: Monoid[Int] = new Monoid[Int] {

      override def add(x: Int, y: Int): Int = x + y

      override def unit(): Int = 0
    }

    implicit val stringMonoid: Monoid[String] = new Monoid[String] {

      override def add(x: String, y: String): String = x concat y

      override def unit(): String = ""
    }


    def sum[A](elems: List[A])(implicit m: Monoid[A]): A = {

      if (elems == Nil) m.unit()
      else m.add(elems.head, sum(elems.tail))

    }

    def sum_tr[A](elems: List[A])(implicit m: Monoid[A]): A = {

      @tailrec
      def sumHelper(elems: List[A], acc: A): A = {

        if (elems.isEmpty) acc
        else sumHelper(elems.drop(2), m.add(acc, m.add(elems.head, elems(1))))
      }

      sumHelper(elems, m.unit())
    }

    def doTest(): Unit = {

      val ints = List(1, 2, 3, 4)
      println(s"sum(ints) == ${sum(ints)}")
      println(s"sum_tr(ints) == ${sum_tr(ints)}")

      val strings = List("a", "b", "c", "d")
      println(s"sum(strings) == ${sum(strings)}")
      println(s"sum_tr(strings) == ${sum_tr(strings)}")

    }
  }

}
