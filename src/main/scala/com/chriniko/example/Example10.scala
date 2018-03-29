package com.chriniko.example

import java.util.UUID
import java.util.concurrent.{Executors, LinkedBlockingDeque}

import scala.concurrent.ExecutionContext

/*
  Either, Left, Right pattern.
 */
class Example10 extends Example {

  override def run(): Unit = {

    // 1
    val properKey = db.keys.take(1).toList.head
    println(getById(properKey).getOrElse(None))

    // 2
    println(getById("xxx"))

    // 3
    println
    val listToPublish = for {
      x <- getById(properKey)
      y <- getById(properKey)
    } yield List(x, y)
    println(listToPublish)
    println

    // 4
    println
    val listToPublish2 = for {
      x <- getById("a")
      y <- getById("b")
    } yield List(x, y)
    println(listToPublish2)
    println

    // 5
    getById(properKey) match {
      case Left(x) => println(s"error message: $x")
      case Right(x) => EventPublisher.publish(EmployeeSearchEvent(x))
    }
    EventListener.listen()

  }


  //-----------------------------------------------------------------------
  val db: Map[String, Employee] = Range(1, 10)
    .map(idx => {
      val id = UUID.randomUUID().toString
      id -> Employee(id, s"Name---$idx")
    })
    .toMap


  case class Employee(id: String, name: String)


  def getById(id: String): Either[String, Employee] = {

    if (db.get(id).isEmpty) Left("No Employee with provided id")
    else Right(db(id))

  }


  //-----------------------------------------------------------------------
  abstract class Event()

  case class EmployeeSearchEvent(emp: Employee) extends Event

  object EventBus {

    private val queue = new LinkedBlockingDeque[Event]()

    def addEvent(ev: Event): Unit = {
      queue.add(ev)
    }

    def consumeEvent(): Event = {
      queue.poll()
    }
  }


  object EventListener {

    private val executionContext = ExecutionContext.fromExecutorService(Executors.newFixedThreadPool(1))

    def listen(): Unit = {

      executionContext.submit(new Runnable {
        override def run(): Unit = {
          while (true) {
            val eventToBeConsumed = EventBus.consumeEvent()
            println(s"eventToBeConsumed = $eventToBeConsumed")
            Thread.sleep(5000)
          }
        }
      })

    }

  }

  object EventPublisher {
    def publish(ev: Event): Unit = {
      EventBus.addEvent(ev)
    }
  }

  //-----------------------------------------------------------------------


}
