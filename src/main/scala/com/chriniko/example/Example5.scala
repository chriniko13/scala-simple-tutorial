package com.chriniko.example

/*

  Unified Types (https://docs.scala-lang.org/tour/unified-types.html)

 */
class Example5 extends Example {

  override def run(): Unit = {

    // 1
    val list: List[Any] = List(
      "a string",
      732, // an integer
      'c', // a character
      true, // a boolean value
      () => "an anonymous function returning a string"
    )

    list.foreach(println)


    // 2
    val x: Long = 987654321
    val y: Float = x  // 9.8765434E8 (note that some precision is lost in this case)
    println(y)

    val face: Char = '☺'
    val number: Int = face  // 9786
    println(number)

  }

}
