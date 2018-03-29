package com.chriniko.example

import scala.util.matching.Regex

/*
  Regular Expression Patterns (https://docs.scala-lang.org/tour/regular-expression-patterns.html)
 */
class Example18 extends Example {

  override def run(): Unit = {

    // 1
    val passwordValidationRegex = "[0-9]+".r

    def validatePassword(pass: String): Boolean = {
      passwordValidationRegex.findFirstMatchIn(pass) match {
        case Some(s) => true
        case None => false
      }
    }

    println(validatePassword("abcd"))
    println(validatePassword("abc123"))


    // 2
    val keyValPattern: Regex = "([0-9a-zA-Z-#() ]+): ([0-9a-zA-Z-#() ]+)".r

    val input: String =
      """background-color: #A03300;
        |background-image: url(img/header100.png);
        |background-position: top center;
        |background-repeat: repeat-x;
        |background-size: 2160px 108px;
        |margin: 0;
        |height: 108px;
        |width: 100%;""".stripMargin

    for (patternMatch <- keyValPattern.findAllMatchIn(input))
      println(s"key: ${patternMatch.group(1)} value: ${patternMatch.group(2)}")
  }

}
