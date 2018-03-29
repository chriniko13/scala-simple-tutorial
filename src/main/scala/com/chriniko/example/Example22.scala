package com.chriniko.example

/*
  Variances (https://docs.scala-lang.org/tour/variances.html)
 */
class Example22 extends Example {

  override def run(): Unit = {

    // 1 (Covariance example +)
    def printAnimalNames(animals: List[Animal]): Unit = {
      animals.foreach {
        animal => println(animal.name)
      }
    }

    val cats: List[Cat] = List(Cat("Whiskers"), Cat("Tom"))
    val dogs: List[Dog] = List(Dog("Fido"), Dog("Rex"))

    printAnimalNames(dogs)
    printAnimalNames(cats)
    printAnimalNames(cats ++ dogs)


    // 2 (Contravariance example -)
    println

    val animalPrinter = new AnimalPrinter
    val dogPrinter = new DogPrinter
    val catPrinter = new CatPrinter

    animalPrinter.print(Dog("dog"))
    animalPrinter.print(Cat("cat"))

    dogPrinter.print(Dog("dog"))
    //dogPrinter.print(Cat("cat"))

    catPrinter.print(Cat("cat"))
    //catPrinter.print(Dog("dog"))


    // 3 (Invariance example -)
    println

    val catContainer: Container[Cat] = new Container[Cat](Cat("phoenix"))
    println(catContainer.a)

    //val animalContainer: Container[Animal] = catContainer
    //animalContainer.a = Dog("ares")

    val cat: Cat = catContainer.a
  }

  //---------------------COVARIANCE----------------------------------------
  sealed abstract class Animal {
    def name: String
  }

  case class Dog(n: String) extends Animal {
    override def name: String = n
  }

  case class Cat(name: String) extends Animal

  //---------------------CONTRAVARIANCE-------------------------------------
  abstract class Printer[-A] {
    def print(value: A): Unit
  }

  class AnimalPrinter extends Printer[Animal] {
    override def print(value: Animal): Unit = {
      println(value)
    }
  }

  class DogPrinter extends Printer[Dog] {
    override def print(value: Dog): Unit = {
      println(value)
    }
  }

  class CatPrinter extends Printer[Cat] {
    override def print(value: Cat): Unit = {
      println(value)
    }
  }

  //---------------------INVARIANCE----------------------------------------
  class Container[A](var a : A)

}
