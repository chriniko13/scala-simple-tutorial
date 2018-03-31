package com.chriniko.example



/*
  Designing Fail Fast Error Handling

  Our first goal is to stop as soon as we encounter an error, or in other words, fail-fast.
  Sometimes we want to accumulate all errors – for example when validating user input – but this is a different
   problem and leads to a different solution.

  Our second goal is to guarantee we handle every error we intend to handle.
   As every programmer knows, if you want something to happen every time you get a computer to do it.
    In the context of Scala this means using the type system to guarantee
    that code that does not implement error handling will not compile.

 There are two corollaries of our second goal:

  if there are errors we don’t care to handle, perhaps because they are so unlikely,
  or we cannot take any action other than crashing, don’t model them; and

  if we add or remove an error type that we do want to handle,
  the compiler must force us to update the code.

 */
class Example43 extends Example {

  override def run(): Unit = {

    // 1 (We know that an error has happened, but we don’t know what error it is, so Option is out of the game.)
    val r1 = for {
      x <- Option(1)
      y <- Option.empty[Int]
      z <- Option(3)
    } yield x + y + z
    println(r1)


    // 2 (Either is a nice candidate, but left and right projections make it not very easy to use)
    println

    def acquireConnection(u: UserName, p: PassWord): Either[String, Connection] = {
      u match {

        case "chriniko" => p match {
          case "1234" => Right(new Connection)
          case _ => Left("incorrect password")
        }

        case _ => Left("incorrect username")
      }
    }

    val r2 = for {
      x <- acquireConnection("chriniko", "1234").right
      y <- acquireConnection("chriniko", "12345").right //play with 1234 and 12345
      z <- acquireConnection("chriniko", "1234").right
    } yield List(x, y, z)

    println(r2)


    // 3 (So our preferred way is Scalaz’s \/ (disjunction) type, which is right-biased.)
    // YourErrorType \/ YourReturnType.
    // DISJUNCTION == + == OR (in algebra)
    println

    import scalaz.\/

    def acquireConnection_2(u: String, p: String): \/[String, Connection] = {
      u match {

        case "chriniko" => p match {
          case "1234" => \/.right(new Connection)
          case _ => \/.left("incorrect password")
        }

        case _ => \/.left("incorrect username")
      }
    }

    val r3 = for { // monad composition
      x <- acquireConnection_2("chriniko", "1234")
      y <- acquireConnection_2("chriniko", "12345") //play with 1234 and 12345
      z <- acquireConnection_2("chriniko", "1234")
    } yield List(x, y, z)

    println(r3)


    // 4
    println

    import scala.util.Random
    import scalaz.{-\/, \/-}

    sealed trait DatabaseError
    final case class NotFound() extends DatabaseError
    final case class CouldNotConnect() extends DatabaseError
    final case class CouldNotAuthenticate() extends DatabaseError
    final case class UnknownError(t: Throwable, s: String) extends DatabaseError

    def getDBConnection(connInfo: (String, String)): \/[DatabaseError, Connection] = {

      val firstPass = connInfo match {

        case ("chriniko", pass) => pass match {
          case "1234" => \/.right(new Connection)
          case _ => \/.left(CouldNotAuthenticate())
        }

        case _ => \/.left(CouldNotAuthenticate())
      }

      Random.nextInt(3) match {
        case 0 => firstPass
        case 1 => \/.left(CouldNotConnect())
        case 2 => \/.left(NotFound())
      }

    }

    val r4 = for {
      x <-getDBConnection(("chriniko", "1234"))
    } yield x

    println(r4)

    r4 match {
      case -\/(error : DatabaseError) => {
        println(s"error during get db connection error, error == $error")
        error match {
          case err: NotFound => println("database not found error")
          case err: CouldNotConnect => println("database could not connect error")
          case err: CouldNotAuthenticate => println("database could not authenticate error")
          //case err: UnknownError => println("database not found error")

        }
      }
      case \/-(conn: Connection) => println("db connection established")
    }
  }

  //----------------------------------------------------------------------------

  type UserName = String
  type PassWord = String

  class Connection() {
    //Dummy
    override def toString: UserName = "db-connection"
  }

}
