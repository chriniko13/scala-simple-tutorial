package com.chriniko.example

/*
  Note: Kleisli example (a Kleisli is function composition for Monads)
 */
class Example1 extends Example {


  override def run(): Unit = {

    val resultOldWay = composeOldWay(4)
    println(s"resultOldWay = $resultOldWay")


    val resultNewWay = composeNewWay(4)
    println(s"resultNewWay = $resultNewWay")
  }

  def str(x: Int): Option[String] = Some(x.toString)

  def int(x: String): Option[Int] = Some(x.toInt)

  def dbl(x: Int): Option[Double] = Some(x.toDouble)


  def composeOldWay(x: Int): Option[Double] = {

    for (
      x <- str(x);
      y <- int(x);
      z <- dbl(y)
    ) yield z

  }

  def composeNewWay(x: Int): Option[Double] = {

    import cats.data.Kleisli
    import cats.implicits._ //Brings in a Monadic instance for Option

    val strK = Kleisli(str) //Kleisli[Option, Int, String]
    val intK = Kleisli(int) //Kleisli[Option, String, Int]
    val dblK = Kleisli(dbl) //Kleisli[Option, Int, Double]
    val pipeline = strK andThen intK andThen dblK
    pipeline(x)

  }

}
