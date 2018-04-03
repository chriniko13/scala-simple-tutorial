package com.chriniko.example


/*

  Uniting Church and State: FP and OO Together

  -----------------------------------------------------------------
  Operations: what we want to do (add, subtract, etc.)
  Actions: how we want to do it (calculate, pretty print, etc.)

  Separate describing what you want from how you do it

  Separate operations from actions

  Side effects happen in actions
  ------------------------------------------------------------------

  FP ---> Church Encoding ---> OO
  FP <--- Reification <--- OO

  Constructors become method calls
  Operator type becomes action type

  -------------------------------------------------------------------

  Type classes are Church encodings of free structures

  Free Structures ----> Church Encoding ----> Type Classes
  Free Structures <---- Reification <---- Type Classes


 */
class Example47 extends Example {

  override def run(): Unit = {

    // 1 - OOP
    val c = new Calculator
    import c._

    val res1 = add(literal(1.0), subtract(literal(3.0), literal(2.0)))
    println(res1)

    println
    val tC = new TrigonometricCalculator
    import tC._
    println(sin(2.5))

    // 2 - FP
    println
    val exp = Add(Literal(1.0), Subtract(Literal(3.0), Literal(2.0)))
    println(pretty(Add(Literal(1.0), Subtract(Literal(3.0), Literal(2.0)))) + " = " + eval(exp))


    // 3 - Free Structures and Type Classes
    import scala.language.higherKinds

    // OOP (type classes)
    trait Monad[F[_]] /* higher kinded types */ {
      def flatMap[A, B](fa: F[A])(f: A => F[B]): F[B]

      def pure[A](x: A): F[A]
    }

    // FP (free structures) --- free monad
    sealed trait Monad2[F[_], A]
    final case class FlatMap[F[_], A, B](fa: Monad2[F, A], f: A => Monad2[F, B]) extends Monad2[F, B]
    final case class Pure[F[_], A](v: A) extends Monad2[F, A]

    println
    println(expression(PrettyPrinter))
  }


  //-------------------------------OO APPROACH-----------------------------------------------------
  // Can easily add new operators (case classes)
  // Cannot easily add new actions (interpreters)
  class Calculator {
    def literal(v: Double): Double = v

    def add(a: Double, b: Double): Double = a + b

    def subtract(a: Double, b: Double): Double = a - b

    def multiply(a: Double, b: Double): Double = a * b

    def divide(a: Double, b: Double): Double = a / b
  }

  class TrigonometricCalculator extends Calculator {

    def sin(a: Double): Double = Math.sin(a)

    def cos(a: Double): Double = Math.cos(a)
  }

  //---------------------------------FP APPROACH-----------------------------------------------------
  // Represent operations as data (ADT)
  // Cannot easily add new operators (case classes)
  // Can easily add new actions (interpreters)
  sealed trait Calculation

  final case class Literal(v: Double) extends Calculation

  final case class Add(c1: Calculation, c2: Calculation) extends Calculation

  final case class Subtract(c1: Calculation, c2: Calculation) extends Calculation

  final case class Multiply(c1: Calculation, c2: Calculation) extends Calculation

  final case class Divide(c1: Calculation, c2: Calculation) extends Calculation

  // structural recursion.
  def eval(c: Calculation): Double = {
    c match {
      case Literal(v) => v
      case Add(c1, c2) => eval(c1) + eval(c2)
      case Subtract(c1, c2) => eval(c1) - eval(c2)
      case Multiply(c1, c2) => eval(c1) * eval(c2)
      case Divide(c1, c2) => eval(c1) / eval(c2)
    }
  }

  // structural recursion.
  def pretty(c: Calculation): String =
    c match {
      case Literal(v) => v.toString
      case Add(a, b) => s"${pretty(a)} + ${pretty(b)}"
      case Subtract(a, b) => s"${pretty(a)} - ${pretty(b)}"
      case Multiply(a, b) => s"${pretty(a)} * ${pretty(b)}"
      case Divide(a, b) => s"${pretty(a)} / ${pretty(b)}"
    }

  //----------------------OO APPROACH WITH AN EXTRA BIT OF ABSTRACTION - TYPE CLASSES------------------------------------------
  trait Calculator2[T] {
    def literal(v: Double): T

    def add(a: T, b: T): T

    def subtract(a: T, b: T): T

    def multiply(a: T, b: T): T

    def divide(a: T, b: T): T
  }

  object PrettyPrinter extends Calculator2[String] {
    def literal(v: Double): String = v.toString

    def add(a: String, b: String): String = s"($a + $b)"

    def subtract(a: String, b: String): String = s"($a - $b)"

    def multiply(a: String, b: String): String = s"($a * $b)"

    def divide(a: String, b: String): String = s"($a / $b)"
  }

  //Hint: When we use, delay choice of action.
  def expression[A](calculator: Calculator2[A]): A = {
    import calculator._

    add(literal(1.0),
      subtract(literal(3.0), literal(2.0)))
  }

}
