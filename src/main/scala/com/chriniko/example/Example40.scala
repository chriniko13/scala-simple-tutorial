package com.chriniko.example

/*
  Map Shuffle Reduce Pattern
 */
class Example40 extends Example {

  override def run(): Unit = {

    // 1
    val result = (1 to 20)
      .map(x => x * x)
      .groupBy(x => x % 5)
      .par
      .map(entry => entry._2.sum)
      .reduce(_ + _)

    println(result)

  }

}
