package com.chriniko.example

/*
  Note: Kleisli example (a Kleisli is function composition for Monads)
 */
class Example2 extends Example {

  override def run(): Unit = {

    import cats.data.Kleisli
    import cats.implicits._ //Brings in a Monadic instance for Option


    val readNameK = Kleisli(readName)
    val readAgeK = Kleisli(readAge)

    val personCreator: Kleisli[Option, Config, Person]
    = for {
      name <- readNameK
      age <- readAgeK
    } yield Person(name, age)


    println(personCreator(Config("chri niko", 26)))
    println(personCreator(Config("chri", 26)))
    println(personCreator(Config("chri", 0)))
    println(personCreator(Config("chri", 151)))

  }


  final case class Name(first: String, last: String)

  final case class Age(age: Int)

  final case class Person(name: Name, age: Age)

  final case class Config(name: String, age: Int)

  def readName(config: Config): Option[Name] = {
    val parts = config.name.split(" ")
    if (parts.length == 2) Some(Name(parts(0), parts(1))) else None
  }

  def readAge(config: Config): Option[Age] = {
    val age = config.age

    age match {
      case _age if _age >= 1 && _age <= 150 => Some(Age(age))
      case _ => None
    }
  }

}
