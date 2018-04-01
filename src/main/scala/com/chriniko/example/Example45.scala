package com.chriniko.example

import org.scalactic.{Bad, Good, Or}
import scala.util.Try

/*
  Easing Into Functional Error Handling in Scala
 */
class Example45 extends Example {

  override def run(): Unit = {

    // 1, traditional way using exceptions.
    try {
      println
      val res1 = CoffeeServiceTrad.purchaseCoffee(3)
      println(res1)
    } catch {
      case e: Exception => println(s"Error occurred: $e")
    }


    // 2, using option.
    println
    val res2 = CoffeeServiceOpt.purchaseCoffee(3)
    println(res2)


    // 3, using try.
    println
    val res3 = CoffeeServiceTry.purchaseCoffee(3)
    println(res3)


    // 4, using either.
    println
    val res4 = CoffeeServiceEither.purchaseCoffee(3)
    println(res4)


    // 5, using org.scalactic.Or (“the good value or a bad value/error” == left-biased)
    println
    val res5 = CoffeeServiceScalacticOr.purchaseCoffee(1)
    println(res5)
  }


  //----------------------------------------------------------------------------

  class Coffee {
    override def toString: String = this.getClass.getSimpleName
  }

  class Beans

  object CoffeeServiceTrad {
    val price = 1.5

    def purchaseCoffee(money: Int): Coffee = {
      brewCoffee(buyBeans(money))
    }

    def buyBeans(money: Int): Beans = {
      if (money < price)
        throw new Exception(s"Not enough money to buy beans for coffee, need $price")
      else new Beans
    }

    def brewCoffee(beans: Beans): Coffee = {

      Math.random() match {
        case r if r < 0.25 => throw new Exception("Faulty grinder failed to grind beans!")
        case _ => new Coffee
      }
    }

  }


  object CoffeeServiceOpt {
    val price = 1.5

    def purchaseCoffee(money: Int): Option[Coffee] = {
      buyBeans(money).flatMap(b => brewCoffee(b))
    }

    def buyBeans(money: Int): Option[Beans] = {
      if (money < price) None
      else Some(new Beans)
    }

    def brewCoffee(beans: Beans): Option[Coffee] = {

      Math.random() match {
        case r if r < 0.25 => None
        case _ => Some(new Coffee)
      }
    }
  }


  object CoffeeServiceTry {

    val price = 1.5

    def purchaseCoffee(money: Int): Try[Coffee] = {
      buyBeans(money).flatMap(b => brewCoffee(b))
    }

    def buyBeans(money: Int): Try[Beans] = {
      Try {
        if (money < price)
          throw new Exception(s"Not enough money to buy beans for coffee, need $price")
        else new Beans
      }
    }

    def brewCoffee(beans: Beans): Try[Coffee] = {
      Try {
        Math.random() match {
          case r if r < 0.25 => throw new Exception("Faulty grinder failed to grind beans!")
          case _ => new Coffee
        }
      }
    }

  }


  object CoffeeServiceEither {

    //ADT
    sealed trait CoffeeServiceError

    final case class GrinderError(reason: String) extends CoffeeServiceError

    final case class NotEnoughMoneyError() extends CoffeeServiceError

    val price = 1.5

    def purchaseCoffee(money: Int): Either[CoffeeServiceError, Coffee] = {
      buyBeans(money).flatMap(b => brewCoffee(b))
      //buyBeans(money).right.flatMap(b => brewCoffee(b))
    }

    def buyBeans(money: Int): Either[NotEnoughMoneyError, Beans] = {
      if (money < price) Left(NotEnoughMoneyError())
      else Right(new Beans)
    }

    def brewCoffee(beans: Beans): Either[GrinderError, Coffee] = {

      Math.random() match {
        case r if r < 0.25 => Left(GrinderError("Faulty grinder failed to grind beans!"))
        case _ => Right(new Coffee)
      }
    }
  }


  object CoffeeServiceScalacticOr {

    //ADT
    sealed trait CoffeeServiceError

    final case class GrinderError() extends CoffeeServiceError

    final case class NotEnoughMoneyError() extends CoffeeServiceError

    val price = 1.5

    def purchaseCoffee(money: Int): Coffee Or CoffeeServiceError = {
      buyBeans(money).flatMap(b => brewCoffee(b))
    }

    def buyBeans(money: Int): Beans Or NotEnoughMoneyError = {
      if (money < price) Bad(NotEnoughMoneyError())
      else Good(new Beans)
    }

    def brewCoffee(beans: Beans): Coffee Or GrinderError = {

      Math.random() match {
        case r if r < 0.25 => Bad(GrinderError())
        case _ => Good(new Coffee)
      }
    }


  }

}
