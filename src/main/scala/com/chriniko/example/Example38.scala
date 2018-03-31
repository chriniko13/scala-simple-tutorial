package com.chriniko.example

/*
  Self types (https://docs.scala-lang.org/tour/self-types.html)
 */
class Example38 extends Example {

  override def run(): Unit = {

    // 1
    val verifiedTweeter = new VerifiedTweeter("chriniko")
    verifiedTweeter.tweet("Scala Rocks!")
  }

  //-------------------------------------------------------------------
  trait User {
    def username: String
  }

  trait Tweeter {

    this: User => // reassign this

    def tweet(tweetText: String): Unit = println(s"$username --- $tweetText")
  }

  class VerifiedTweeter(val _username: String) extends Tweeter with User {
    override def username: String = s"real ${_username}"
  }

}
