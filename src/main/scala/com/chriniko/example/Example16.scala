package com.chriniko.example

/*
  Pattern Matching (https://docs.scala-lang.org/tour/pattern-matching.html)
 */
import scala.util.Random

class Example16 extends Example {

  override def run(): Unit = {

    // 1
    val x = Random.nextInt(10)
    val result = x match {
      case 1 => "1"
      case 2 => "2"
      case _ => "> 2"
    }
    println(result)

    // 2
    println
    val someSms = SMS("12345", "Are you there?")
    val someVoiceRecording = VoiceRecording("Tom", "voicerecording.org/id/123")

    println(showNotification(someSms))
    println(showNotification(someVoiceRecording))
    println

    // 3
    println
    val importantPeopleInfo = Seq("867-5309", "jenny@gmail.com")

    val someSms2 = SMS("867-5309", "Are you there?")
    val someVoiceRecording2 = VoiceRecording("Tom", "voicerecording.org/id/123")
    val importantEmail = Email("jenny@gmail.com", "Drinks tonight?", "I'm free after 5!")
    val importantSms = SMS("867-5309", "I'm here! Where are you?")

    println(showImportantNotification(someSms2, importantPeopleInfo))
    println(showImportantNotification(someVoiceRecording2, importantPeopleInfo))
    println(showImportantNotification(importantEmail, importantPeopleInfo))
    println(showImportantNotification(importantSms, importantPeopleInfo))


    // 4
    def printModel(device: Device): String = {
      device match {
        case d: Computer => s"computer device, model:${d.model}"
        case d: Phone => s"phone device, model: ${d.model}"
      }
    }

    println
    println(printModel(Computer("dell")))
    println(printModel(Phone("lg")))

    // 5
    println
    println(findPlaceToSit(Couch()))
    println(findPlaceToSit(Chair()))

  }


  //------------------------------------------------------------------------------------------
  abstract class Notification

  case class Email(sender: String, title: String, body: String) extends Notification

  case class SMS(caller: String, message: String) extends Notification

  case class VoiceRecording(contactName: String, link: String) extends Notification


  def showNotification(notification: Notification): String = {
    notification match {
      case Email(sender, title, _) => s"You got an email from $sender with title: $title"
      case SMS(caller, message) => s"You got an SMS from $caller! Message: $message"
      case VoiceRecording(contactName, link) => s"You received a Voice Recording from $contactName! Click the link to hear it: $link"
      case _ => s"System error occurred!"
    }
  }

  def showImportantNotification(notification: Notification, importantPeopleInfo: Seq[String]): String = {
    notification match {
      case Email(email, _, _) if importantPeopleInfo.contains(email) => "You got an email from special someone!"
      case SMS(number, _) if importantPeopleInfo.contains(number) => "You got an SMS from special someone!"
      case other => showNotification(other)
    }
  }

  //------------------------------------------------------------------------------------------
  abstract class Device

  case class Phone(model: String) extends Device

  case class Computer(model: String) extends Device

  //------------------------------------------------------------------------------------------

  sealed abstract class Furniture

  case class Couch() extends Furniture

  case class Chair() extends Furniture

  def findPlaceToSit(piece: Furniture): String = piece match {
    case a: Couch => "Lie on the couch"
    case b: Chair => "Sit on the chair"
  }
}
