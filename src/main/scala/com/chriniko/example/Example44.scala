package com.chriniko.example

import scala.util.Random
import scalaz.\/

/*
  Robust Error Handling in Scala
 */
class Example44 extends Example {

  override def run(): Unit = {

    // 1, usage of DatabaseService
    println("\n#DatabaseService Test#")
    val result = DatabaseService.find("chriniko")
    println(result)


    // 2, usage of AuthenticationService
    println("\n#AuthenticationService Test#")
    val result2 = AuthenticationService.login("chriniko", "12345")
    println(result2)
  }

  //---------------------TYPES------------------------------------------------------------------------------------------
  type Username = String
  type Host = String
  type Password = String

  //----------------------HTTP------------------------------------------------------------------------------------------
  object HttpService {

    //TODO add implementation...
  }

  //----------------------AUTHENTICATION--------------------------------------------------------------------------------
  object AuthenticationService {

    //ADT
    sealed trait ServiceError

    final case class NotFound(username: Username) extends ServiceError

    final case class BadPassword(username: Username, password: Password) extends ServiceError

    final case class DatabaseError(error: DatabaseService.DatabaseError) extends ServiceError

    def login(username: Username, password: Password): \/[ServiceError, User] = {

      // Note: monad composition with for comprehension
      for {
        user <- // Note: if an error occurred in database layer, map to the correct service error.
        DatabaseService.find(username).leftMap {
          case DatabaseService.NotFound(user) => NotFound(user)
          case error@DatabaseService.CouldNotAuthenticate(user, pass) => DatabaseError(error)
          case error@DatabaseService.CouldNotConnect(host) => DatabaseError(error)
        }

        user <- validatePassword(user, password)
      } yield user


    }

    def validatePassword(user: User, password: Password): \/[ServiceError, User] = {
      if (user.password == password) \/ right user
      else \/ left BadPassword(user.username, password)
    }

  }

  //----------------------DATABASE--------------------------------------------------------------------------------------
  object DatabaseService {

    //ADT
    sealed trait DatabaseError

    final case class NotFound(username: Username) extends DatabaseError

    final case class CouldNotConnect(host: Host) extends DatabaseError

    final case class CouldNotAuthenticate(username: Username, password: Password) extends DatabaseError

    // YourErrorType \/ YourReturnType.
    // DISJUNCTION == + == OR (in algebra)
    def find(username: Username): \/[DatabaseError, User] = {
      //simulate network and system errors
      Random.nextInt(3) match {
        case 0 => \/ left CouldNotConnect("db01")
        case 1 => \/ left CouldNotAuthenticate("user1", "1ncharge")
        case 2 =>
          username match {
            case "chriniko" => \/ right User("chriniko", "niko", "chri", "1234")
            case other => \/ left NotFound(other)
          }
      }
    }
  }

  //------------------DOMAIN------------------------------------------------------------------------------------------
  final case class User(username: Username, firstName: String, lastName: String, password: Password)

}
