package com.chriniko.example

/*
  Companion Objects
 */
class Example41 extends Example {

  override def run(): Unit = {

    // 1
    val s1 = Student("chri", None, "niko")
    println(s1)

  }

  //-----------------------------------------------------------
  class Student(val first: String, val initials: Option[String], val last: String) {

    override def toString: String = s"$first${initials.map(i => s" $i ").getOrElse(" ")}$last"
  }

  object Student {
    def apply(first: String, initials: Option[String], last: String): Student = {
      new Student(first, initials, last)
    }

  }

}
