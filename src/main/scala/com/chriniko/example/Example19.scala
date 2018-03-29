package com.chriniko.example

import scala.util.Random

/*

  Extractor Objects (https://docs.scala-lang.org/tour/extractor-objects.html)

 */
class Example19 extends Example {

  override def run(): Unit = {

    // 1
    val r = CustomerID("chriniko")
    println(r)

    val r2 = CustomerID.apply("chriniko")
    println(r2)

    r2 match {
      case CustomerID(name) => println(name)
    }


    // 2
    val CustomerID(_name) = CustomerID("bruno")
    println(_name)


    // 3
    val name2 = CustomerID.unapply(CustomerID("bruno")).get
    println(name2)
  }


  object CustomerID {
    private val delim = " --- "

    def apply(name: String): String = s"$name$delim${Random.nextGaussian}"

    def unapply(customerId: String): Option[String] = {
      val r: Array[String] = customerId.split(delim)
      r match {
        case Array(a, b) => Some(a)
        case _ => None
      }
    }
  }

}
