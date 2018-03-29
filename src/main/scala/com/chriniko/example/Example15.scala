package com.chriniko.example

/*
  Case Class (https://docs.scala-lang.org/tour/case-classes.html)
 */
class Example15 extends Example {

  override def run(): Unit = {

    // 1
    val emp = Employee("emp 123")
    println(emp)
    println(emp.name)

    // 2
    println(Employee("emp 123") == Employee("emp 123"))

    // 3
    println(emp.copy())
  }

  case class Employee(name: String)

}
