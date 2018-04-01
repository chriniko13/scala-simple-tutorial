package com.chriniko.example

import scala.annotation.tailrec

/*
  Algebraic Data Types
 */
class Example46 extends Example {

  override def run(): Unit = {

    // 1
    // Animal = Cat +(or) Dog , this is the type sum/co-product
    sealed trait Animal
    case object Cat extends Animal
    case object Dog extends Animal

    // 2
    // Student = String * Int , this is the product sum
    case class Student(name: String, age: Int)


    // 3
    // Wagon = String *(and) Int
    // Train = Locomotive +(or) Wagon *(and) Train
    //
    // we will write it a little better:
    //
    // Wagon = String * Int
    // Nexus = Wagon * Train
    // Train = Locomotive + Nexus
    sealed trait Train
    final case class Wagon(model: String, passengers: Int)
    final case class Nexus(wagon: Wagon, train: Train) extends Train
    final object Locomotive extends Train {
      override def toString: String = "Locomotive"
    }

    val train1 =
      Nexus(
        Wagon("wagon-3", 10),
        Nexus(
          Wagon("wagon-2", 35),
          Nexus(
            Wagon("wagon-1", 50),
            Locomotive
          )
        )
      )

    println
    println(train1)
    println

    def prettyPrintTrain(train: Train): String = {
      @tailrec
      def prettyPrintTrainHelper(t: Train, acc: String): String = {
        t match {
          case e@Nexus(_, _) =>
            prettyPrintTrainHelper(e.train, acc = acc + s"{Wagon[model=${e.wagon.model}, passengers=${e.wagon.passengers}]}-")
          case _ => acc + "---{Locomotive}"
        }
      }

      prettyPrintTrainHelper(train, "")
    }

    println(prettyPrintTrain(train1))


    // 4
    // List[T] = EmptyList[T] + NonEmptyList[T]
    // NonEmptyList = T * List[T]
    sealed trait List[+T]
    final case class NonEmptyList[+T](head: T, tail: List[T]) extends List[T]
    final case object EmptyList extends List[Nothing]

    val l1 = NonEmptyList[Int](3, NonEmptyList[Int](2, NonEmptyList[Int](1, EmptyList)))
    println
    println(l1)

    def prettyPrintList[T](list: List[T]): String = {
      @tailrec
      def prettyPrintListHelper(l: List[T], acc: String): String = {

        l match {
          case e@NonEmptyList(h, t) => prettyPrintListHelper(t, acc = acc + s" $h ")
          case _ => acc
        }

      }

      prettyPrintListHelper(list, "")
    }

    println
    println(prettyPrintList(l1))


    // 5
    // Employee = first * initials * last
    // EmployeeRecord = Employee * EmployeesBook
    // EmployeesBook = EmptyRecord + EmployeeRecord

    sealed trait EmployeesBook
    final case class Employee(first: String, initials: String, last: String)
    final object EmptyRecord extends EmployeesBook {
      override def toString: String = "EmptyRecord"
    }
    final case class EmployeeRecord(emp: Employee, empsBook: EmployeesBook) extends EmployeesBook

    val emps =
      EmployeeRecord(
        Employee("first3", "initials3", "last3"),
        EmployeeRecord(
          Employee("first2", "initials2", "last2"),
          EmployeeRecord(
            Employee("first1", "initials1", "last1"),
            EmptyRecord
          )
        )
      )

    def prettyPrintEmps(empsBook: EmployeesBook): String = {

      @tailrec
      def prettyPrintEmpsHelper(b: EmployeesBook, acc: String): String = {
        b match {
          case e@EmployeeRecord(emp, book) => prettyPrintEmpsHelper(e.empsBook, acc = acc + s"${e.emp} ")
          case _ => acc
        }
      }

      prettyPrintEmpsHelper(empsBook, "")
    }

    println
    println(emps)
    println
    println(prettyPrintEmps(emps))


    // 6
    // EvenNum = Zero + NextEvenNum
    // NextEvenNum = (EvenNum add 2)
    sealed abstract class EvenNum(val num: Int)
    final object Zero extends EvenNum(0)
    final case class NextEvenNum(previous: EvenNum) extends EvenNum(previous.num + 2)

    val test1 = NextEvenNum(NextEvenNum(NextEvenNum(NextEvenNum(Zero))))
    println
    println(test1.num)

    def findEvenNum(i: Int): Int = {
      var res = NextEvenNum(Zero)
      for (num <- 1 until i)
        res = NextEvenNum(res)
      res.num
    }

    println
    println(findEvenNum(4))


  }


}
