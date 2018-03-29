package com.chriniko.example

import java.util.UUID


/*
  Option, Some, None pattern.
 */
class Example9 extends Example {

  override def run(): Unit = {

    // 1
    println(getById("2"))

    // 2
    val properKey = db.keys.take(1).toList.head
    val record = getById(properKey)
    println(record)

    // 3
    val result = Seq(1, 2, 3, 4, properKey).map(idx => db.get(idx.toString)).toList
    println(result)
    println(result.flatten)


    // 4
    val bag = List("1", "2", "foo", "3", "bar")
    val sum = bag.flatMap(toInt).sum
    println(sum)


    // 5
    val r = List.range(1, 10)
    val records = for (idx <- r) yield getById(idx.toString)
    println(records)
    println(records.flatten)

  }

  def toInt(input: String): Option[Int] = {

    try {
      Option(Integer.parseInt(input))
    } catch {
      case _: Throwable => None
    }
  }


  //-----------------------------------------------------------------------
  val db: Map[String, Employee] = Range(1, 10)
    .map(idx => {
      val id = UUID.randomUUID().toString
      id -> Employee(id, s"Name---$idx")
    })
    .toMap


  case class Employee(id: String, name: String)


  def getById(id: String): Option[Employee] = db.get(id)

  //-----------------------------------------------------------------------

}
