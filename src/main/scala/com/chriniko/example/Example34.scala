package com.chriniko.example

/*
  Default Parameter Values (https://docs.scala-lang.org/tour/default-parameter-values.html)
 */
class Example34 extends Example {

  override def run(): Unit = {

    // 1
    def log(msg: String, level: String = "INFO"): Unit = println(s"[$level] $msg")

    log("scala rocks!")

    log("exception occurred", "ERROR")
    log(msg = "exception occurred", level = "ERROR")
    log(level = "ERROR", msg = "exception occurred")

  }

}
