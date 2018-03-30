package com.chriniko.example

import scala.annotation.tailrec

/*
  Annotations (https://docs.scala-lang.org/tour/annotations.html)
 */
class Example33 extends Example {

  override def run(): Unit = {

    // 1
    @deprecated
    def sayHello(): Unit = println("hello")

    sayHello()


    // 2
    def factorial(x: Int): Int = {

      @tailrec
      def factorialHelper(x: Int, accumulator: Int): Int = {
        if (x == 1) accumulator
        else factorialHelper(x - 1, accumulator * x)
      }

      factorialHelper(x, 1)
    }

    println(factorial(4))


    // 3
    Test1.doTest()

  }


  object Test1 {

    case class SimpleAnnotation() extends scala.annotation.StaticAnnotation

    @SimpleAnnotation
    case class SimpleClass()

    //
    // Use reflection to determine if the class has the annotation
    //   1. Get the ClassSymbol for the class we want to check
    //   2. Get the list of annotations from the ClassSymbol
    //   3. Get the expected annotation type to match
    //   4. Check the type of annotation equals the expected type
    //
    def doTest(): Unit = {
      println("\n")

      import scala.reflect.runtime.{universe => ru}

      val simpleClassSymbol = ru.typeOf[SimpleClass].typeSymbol.asClass
      val annotations = simpleClassSymbol.annotations
      val simpleAnnotationType = ru.typeOf[SimpleAnnotation]

      annotations.find(a => a.tree.tpe == simpleAnnotationType) match {
        case Some(an) => println(s"find expected annotation = $an")
        case None => println("error occurred")
      }


    }
  }

}
