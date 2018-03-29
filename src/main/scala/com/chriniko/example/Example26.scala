package com.chriniko.example

/*
  Compound Types (https://docs.scala-lang.org/tour/compound-types.html)
 */
class Example26 extends Example {

  override def run(): Unit = {

    // 1
    def cloneAndReset(obj: Cloneable with Resetable): Cloneable = {
      val cloned = obj.clone()
      obj.reset()
      cloned
    }

    class Data()

    val data = new Data() with Cloneable with Resetable {
      override def reset(): Unit = println("Reset...")
    }

    cloneAndReset(data)

  }


  //--------------------------------------------------------
  trait Cloneable extends java.lang.Cloneable {
    override def clone(): Cloneable = {
      super.clone().asInstanceOf[Cloneable]
    }
  }

  trait Resetable {
    def reset(): Unit
  }

}
