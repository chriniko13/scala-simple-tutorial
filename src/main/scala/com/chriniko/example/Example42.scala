package com.chriniko.example

import java.util.concurrent.locks.ReentrantReadWriteLock

/*
  Concurrency and Synchronization
 */
class Example42 extends Example {

  override def run(): Unit = {

    // 1
    import scala.concurrent.ExecutionContext.Implicits._
    import scala.concurrent.duration._
    import scala.concurrent.{Await, Future}


    val workers = (1 to 20)
      .map(idx =>
        Future[Unit] {
          //UsersDB.save(User(idx, s"user$idx", manager = false)) //Note: 1st option
          //UsersDB.save_SB(User(idx, s"user$idx", manager = false)) //Note: 2nd option
          UsersDB.save_RWL(User(idx, s"user$idx", manager = false)) //Note: 3rd option

        }
      )
      .collect {
        case v => v
      }

    workers.foreach(worker => Await.result(worker, 5 seconds))

    println(workers)

    println(s"noOfRecords = ${UsersDB.noOfRecords()}")
  }

  //--------------------------------------------------------------------

  object UsersDB {

    private val lock = new ReentrantReadWriteLock()

    // Use with caution! Can be accessed by multiple threads.
    private var users: Map[Int, User] = Map[Int, User]()

    // --- non protected methods from multiple threads access ---
    def find(id: Int): Option[User] = users.get(id)

    def save(user: User): Unit = users = users + (user.id -> user)

    def noOfRecords(): Int = users.size

    // --- protected methods with synchronized block ---
    def find_SB(id: Int): Option[User] = {
      this.synchronized {
        users.get(id)
      }
    }

    def save_SB(user: User): Unit = {
      this.synchronized {
        users += user.id -> user
      }
    }

    def noOfRecords_SB(): Int = {
      this.synchronized {
        users.size
      }
    }

    // --- protected methods with read-write lock ---
    def find_RWL(id: Int): Option[User] = {
      lock.readLock().lock()
      try {
        users.get(id)
      } finally {
        lock.readLock().unlock()
      }
    }

    def save_RWL(user: User): Unit = {
      lock.writeLock().lock()
      try {
        users += user.id -> user
      } finally {
        lock.writeLock().unlock()
      }
    }

    def noOfRecords_RWL(): Int = {
      lock.readLock().lock()
      try {
        users.size
      } finally {
        lock.readLock().unlock()
      }
    }
  }

  case class User(id: Int, name: String, manager: Boolean)


}
