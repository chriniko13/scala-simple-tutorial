package com.chriniko.example

/*

  Class Composition with Mixins (https://docs.scala-lang.org/tour/mixin-class-composition.html)

 */
class Example7 extends Example {

  override def run(): Unit = {


    // 1
    val d = new D
    println(d.message)
    println(d.loudMessage)


    // 2
    println
    class StringRichIterator(s: String) extends StringIterator(s) with RichIterator
    val it = new StringRichIterator("chriniko")
    it.foreach()
  }

  //--------------------------------------------------------------------------
  abstract class A {
    val message: String
  }

  class B extends A {
    override val message: String = "I am an instance of class B"
  }

  trait C extends A {
    def loudMessage: String = message.toUpperCase
  }

  class D extends B with C

  //--------------------------------------------------------------------------
  abstract class AbsIterator {
    type T

    def hasNext: Boolean

    def next: T
  }

  class StringIterator(s: String) extends AbsIterator {

    override type T = Char

    private var i: Int = 0

    override def hasNext: Boolean = i < s.length

    override def next: Char = {
      val ch = s.charAt(i)
      i += 1
      ch
    }
  }

  trait RichIterator extends AbsIterator {

    def foreach(): Unit = while (hasNext) println(next)

  }

}
