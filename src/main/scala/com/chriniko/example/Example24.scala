package com.chriniko.example

/*
  Lower type bounds (http://docs.scala-lang.org/tour/lower-type-bounds.html)
 */
class Example24 extends Example {

  override def run(): Unit = {

    // 1

    val ints: Node[Int] = ListNode[Int](1, Nil[Int]())
    println(ints)

    println

    val ints2 = ints.prepend(2)
    println(ints2)
    println(ints)
    println(ints2.head)
    println(ints2.tail)


    // 2
    println

    trait Bird
    case class AfricanSwallow() extends Bird
    case class EuropeanSwallow() extends Bird

    val africanSwallowList= ListNode[AfricanSwallow](AfricanSwallow(), Nil())

    val birdList: Node[Bird] = africanSwallowList

    birdList.prepend(new EuropeanSwallow)

    println(birdList)
  }

  //--------------------------------------------------------------------------------------

  trait Node[+B] /*covariant*/ {
    def prepend[U >: B](elem: U): Node[U]

    def head: B

    def tail: Node[B]
  }


  case class ListNode[+B](h: B, t: Node[B]) extends Node[B] {

    override def prepend[U >: B](elem: U): Node[U] = ListNode(elem, this)

    def head: B = h

    def tail: Node[B] = t
  }

  case class Nil[+B]() extends Node[B] {
    def prepend[U >: B](elem: U): ListNode[U] = ListNode(elem, this)

    override def head: B = ???

    override def tail: Node[B] = ???
  }

}
