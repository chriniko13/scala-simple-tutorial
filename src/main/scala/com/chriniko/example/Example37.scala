package com.chriniko.example

/*
  Abstract Types (https://docs.scala-lang.org/tour/abstract-types.html)
 */
class Example37 extends Example {

  override def run(): Unit = {

    // 1
    val buf = newIntSeqBuf(7, 8)
    println("length = " + buf.length)
    println("content = " + buf.element)

    // 2
    println
    val buf2 = newIntSeqBuf2(7, 8)
    println("length = " + buf2.length)
    println("content = " + buf2.element)
  }

  //------------------------------------------------------
  trait Buffer {
    type T
    val element: T
  }

  abstract class SeqBuffer extends Buffer {
    type U
    type T <: Seq[U]

    def length(): Int = element.length
  }

  abstract class IntSeqBuffer extends SeqBuffer {
    type U = Int
  }

  def newIntSeqBuf(elem1: Int, elem2: Int): IntSeqBuffer = new IntSeqBuffer {
    override type T = List[U]
    override val element: T = List(elem1, elem2)
  }

  //---------------------------------------------------------
  abstract class Buffer2[+T] {
    val element: T
  }

  abstract class SeqBuffer2[U, +T <: Seq[U]] extends Buffer2[T] { //POINT1: remove + here and see what happens, then see POINT2
    def length: Int = element.length
  }

  def newIntSeqBuf2(e1: Int, e2: Int): SeqBuffer2[Int, Seq[Int]] =
    new SeqBuffer2[Int, List[Int]] { //POINT2, if you remove + you will see an error here, replace List with Seq :), you've learned a lot.
      val element = List(e1, e2)
    }

}
