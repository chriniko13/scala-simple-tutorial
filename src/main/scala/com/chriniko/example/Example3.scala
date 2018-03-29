package com.chriniko.example

/*
  Note: Functor example.

  When a value is wrapped in a context, you can't apply a normal function to it:
  This is where fmap comes in.
  fmap knows how to apply functions to values that are wrapped in a context.

  > fmap (+3) (Just 2)
    Just 5

 */
class Example3 extends Example {


  override def run(): Unit = {

    // --- 1st example ---
    println(Option(4).map(_ + 10))


    // --- 2nd example ---
    println(Functor(2).fmap(_ * 2))

  }

  case class Functor[T](t: T) {

    def fmap(function: T => T): Functor[T] = Functor(function(this.t))

  }

}
