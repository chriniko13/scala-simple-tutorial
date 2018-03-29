package com.chriniko.example

/*

  Singleton Objects (https://docs.scala-lang.org/tour/singleton-objects.html)

  Note: If a class or object has a companion, both must be defined in the same file.

 */
class Example17 extends Example {

  override def run(): Unit = {

    // 1
    Logger.sayHello()

    // 2
    println(new Circle(5.0).area)

    // 3
    val result = Email.fromString("chriniko@learning-scala.com")
    println(result)


    def printRegisteredEmail(email: Option[Email]): Unit = {
      email match {
        case Some(user) => println(s"Registered an email with username: ${user.username} and domain: ${user.domainName}")
        case None => println("Invalid email!")
      }
    }

    printRegisteredEmail(result)
    printRegisteredEmail(Email.fromString("badmail"))

  }

  //------------------------------------------------------------
  object Logger {
    def sayHello(): Unit = println("Hello there!")
  }

  //------------------------------------------------------------
  // COMPANION OBJECTS

  class Circle(radius: Double) {
    def area: Double = Circle.calcRadius(radius)
  }

  object Circle {
    private def calcRadius(radius: Double): Double = {
      scala.math.Pi * scala.math.pow(radius, 2.0)
    }
  }

  //------------------------------------------------------------
  // COMPANION OBJECTS

  class Email(val username: String, val domainName: String) {
    override def toString: String = s"$username --- $domainName"
  }

  object Email {
    // factory method
    def fromString(emailString: String): Option[Email] = {
      emailString.split("@").toArray match {
        case Array(username, domainName) => Some(new Email(username, domainName))
        case _ => None
      }
    }
  }

}
