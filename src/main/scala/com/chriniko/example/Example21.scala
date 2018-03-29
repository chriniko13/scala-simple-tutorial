package com.chriniko.example

/*
  Generic Classes (https://docs.scala-lang.org/tour/generic-classes.html)
 */
class Example21 extends Example {

  override def run(): Unit = {

    // 1
    val intStack = new Stack[Int]

    Range.inclusive(1, 5).foreach(intStack.push)

    println(intStack.peek())

    println

    while (intStack.hasNext) {
      println(intStack.pop())
    }


    // 2
    println
    class Fruit
    class Apple extends Fruit
    class Banana extends Fruit

    val stack = new Stack[Fruit]
    val apple = new Apple
    val banana = new Banana

    stack.push(apple)
    stack.push(banana)

    println(stack.pop())
  }

  class Stack[A] {
    private var elems: List[A] = Nil

    def hasNext: Boolean = {
      elems != Nil
    }

    def push(elem: A): Unit = {
      elems = elem :: elems
    }

    def peek(): A = elems.head

    def pop(): A = {
      val currentHead = elems.head
      elems = elems.tail
      currentHead
    }
  }

}
