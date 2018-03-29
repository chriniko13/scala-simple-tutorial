package com.chriniko.example

import scala.util.Try


/*
    Try Pattern.
 */
class Example8 extends Example {

  override def run(): Unit = {

    // 1
    val extractor = new Extractor
    val result = extractor.get("https://spring.io/")
    println(result)

    // 2
    val result2 = extractor.get("https://spring.io.bad.url/")
    println(result2)

  }


  class Extractor {

    def get(url: String): Try[String] = {
      Try {

        val result: Array[String] = scala.io.Source.fromURL(url).mkString.split("\n")

        Range(1, 3).map(idx => result(idx)).reduce((acc, elem) => acc + elem)

      }
    }

  }

}
