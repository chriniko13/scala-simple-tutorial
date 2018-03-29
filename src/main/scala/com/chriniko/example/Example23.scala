package com.chriniko.example


/*
  Upper type bounds (https://docs.scala-lang.org/tour/upper-type-bounds.html)
 */
class Example23 extends Example {

  override def run(): Unit = {

    // 1
    class PetContainer[P <: Pet](val p: P) {
      override def toString: String = p.toString
    }

    //class PetContainer[P >: Pet](val p: P)


    println(new PetContainer[Dog](new Dog))
    println(new PetContainer[Cat](new Cat))
    //println(new PetContainer[Lion](new Lion)) // Note: this would not compile.

  }

  //-----------------------------------------------------------------------

  abstract class Animal

  abstract class Pet extends Animal

  class Dog extends Pet {
    override def toString: String = this.getClass.getSimpleName
  }

  class Cat extends Pet {
    override def toString: String = this.getClass.getSimpleName
  }

  class Lion extends Animal {
    override def toString: String = this.getClass.getSimpleName
  }


}
