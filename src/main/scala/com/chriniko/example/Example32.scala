package com.chriniko.example

import java.util.concurrent.TimeUnit

/*
  By-name parameters (https://docs.scala-lang.org/tour/by-name-parameters.html)
 */
class Example32 extends Example {

  override def run(): Unit = {

    // 1
    def whileLoop(condition: => Boolean)(body: => Unit): Unit = {
      if (condition) {
        body
        whileLoop(condition)(body)
      }
    }

    var i = 0
    whileLoop(i < 10) {
      println(i + 1)
      i += 1
    }

    // 2
    println

    def timer[T](blockOfCodeToMeasure: => T): Tuple2[T, Long] = {
      val startTime = System.nanoTime
      val res = blockOfCodeToMeasure
      val estimatedTime = System.nanoTime - startTime
      Tuple2(res, TimeUnit.MILLISECONDS.convert(estimatedTime, TimeUnit.NANOSECONDS))
    }

    val (result, time) = timer {
      Thread.sleep(4000)
      //"ok"
      //1
      //2.34
      List(1, 2, 3)
    }
    println(s"result = $result, time = $time")

    // 3
    var assertionsEnabled = true

    def myAssert(p: () => Boolean /* a function input parameter */): Unit = {
      if (assertionsEnabled && !p())
        throw new AssertionError()
    }

    myAssert(() => 5 > 3)


    def myAssertWithName(p: => Boolean) /* a by-name parameter */ : Unit = {
      if (assertionsEnabled && !p)
        throw new AssertionError()
    }

    myAssertWithName(5 > 3)

    // 4
    println

    def withinTx[T](block: => T) = {
      println("Begin TX")
      val res = block
      println("End TX")
      res
    }

    val r: Unit = withinTx {
      println("Performing Operation")
    }
    println(r)
  }

}
