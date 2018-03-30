package com.chriniko.example.users {


  case class User(username: String, name: Name)

  case class Name(first: String, initials: Option[String], last: String)


  package administrators {

    case class Admin(rank: Int, name: Name)

  }


  package visitors {

    import java.util.UUID

    case class Visitor(id: UUID, name: Option[Name])

  }

}