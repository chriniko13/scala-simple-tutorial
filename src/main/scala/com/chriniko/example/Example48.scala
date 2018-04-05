package com.chriniko.example

import java.util.concurrent.{Executors, ThreadFactory, TimeUnit}

import scala.concurrent.duration.Duration
import scala.concurrent.{Await, ExecutionContext, Future}
import scala.util.Random

/*
  Simple Concurrency Patterns.
 */
class Example48 extends Example {

  override def run(): Unit = {


    // 1
    implicit val personOrdering: Ordering[Person] {
      def compare(x: Person, y: Person): Int
    } = new Ordering[Person] {
      override def compare(x: Person, y: Person): Int = x.id.compareTo(y.id)
    }

    println("peopleDB")
    println(PeopleService.peopleDB.toSeq.sortBy(rec => rec._1))
    println
    println("friendsPeopleDB")
    println(PeopleService.friendsPeopleDb.toSeq.sortBy(rec => rec._1))
    println
    println("peopleInterestsDb")
    println(PeopleService.peopleInterestsDb.toSeq.sortBy(rec => rec._1))
    println


    // 2
    implicit val exCtx: ExecutionContext = ExecutionContext
      .fromExecutor(Executors.newFixedThreadPool(Runtime.getRuntime.availableProcessors(), new ThreadFactory {
        override def newThread(r: Runnable): Thread = {
          println("Example48 -> executionContext")
          val t = new Thread(r)
          t.setName("Example48 -> executionContext")
          t
        }
      }))

    val frontPageContext = for {
      personFuture <- PeopleService.getPerson("1")
      friendsFuture <- PeopleService.friendsOf(personFuture)
      interestsFuture <- PeopleService.interestsOf(personFuture)
    } yield FrontPageContext(personFuture, friendsFuture, interestsFuture)

    val res = Await.result(frontPageContext, Duration(5, TimeUnit.SECONDS))
    println(res)

  }

  //------------------DOMAIN----------------------------------------------------------
  case class Person(id: String, name: String)

  case class Interest(interest: String)

  case class FrontPageContext(person: Person, friends: Seq[Person], interests: Seq[Interest])


  //------------------SERVICE---------------------------------------------------------

  object PeopleService {

    val peopleNoOfRecords = 5

    val exCtx: ExecutionContext = ExecutionContext
      .fromExecutor(Executors.newFixedThreadPool(Runtime.getRuntime.availableProcessors(), new ThreadFactory {
        override def newThread(r: Runnable): Thread = {
          println("PeopleService -> executionContext")
          val t = new Thread(r)
          t.setName("PeopleService -> executionContext")
          t
        }
      }))

    var peopleDB: Map[String, Person] = Map()
    var friendsPeopleDb: Map[Person, Seq[Person]] = Map()
    var peopleInterestsDb: Map[Person, Seq[Interest]] = Map()

    // init peopleDB
    (1 to peopleNoOfRecords).foreach(idx => {
      peopleDB = peopleDB + (idx.toString -> Person(idx.toString, s"Name$idx"))
    })

    // init friendsPeopleDb
    (1 to peopleNoOfRecords).foreach(idx => {

      // pick randomly two people and make them friends of the one who is under processing.
      var personIdxToMakeFriend = -1
      do {
        personIdxToMakeFriend = Random.nextInt(peopleNoOfRecords) + 1
      } while (idx == personIdxToMakeFriend)

      val friends = Seq(peopleDB(personIdxToMakeFriend.toString))

      // do the assignment
      friendsPeopleDb = friendsPeopleDb + (peopleDB(idx.toString) -> friends)

    })

    // init peopleInterestsDb
    {
      val someInterests = Seq(Interest("programming"), Interest("travelling"), Interest("painting"), Interest("piano"), Interest("music"))

      (1 to peopleNoOfRecords).foreach(idx => {

        val person = peopleDB(idx.toString)
        val randomChosenInterests = Seq()

        val randomInterestIdx = Random.nextInt(someInterests.length)

        peopleInterestsDb = peopleInterestsDb + (person -> Seq(someInterests(randomInterestIdx)))

      })
    }

    def getPerson(id: String): Future[Person] = {
      Future({
        peopleDB(id)
      })(exCtx)
    }

    def friendsOf(p: Person): Future[Seq[Person]] = {
      Future({
        friendsPeopleDb(p)
      })(exCtx)
    }

    def interestsOf(p: Person): Future[Seq[Interest]] = {
      Future({
        peopleInterestsDb(p)
      })(exCtx)
    }


  }

}
