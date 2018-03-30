package com.chriniko.example


/*
  Packages and Imports (https://docs.scala-lang.org/tour/packages-and-imports.html)

  Good practises for package name: <top-level-domain>.<domain-name>.<project-name>
 */
class Example36 extends Example {

  override def run(): Unit = {

    // 1
    import com.chriniko.example.users._

    println(User("user@user.com", Name("first", None, "last")))

    import com.chriniko.example.users.administrators._

    println(Admin(3, Name("first", Some("initials"), "last")))

    import java.util.UUID
    import _root_.com.chriniko.example.users.{visitors => vs}
    println(vs.Visitor(UUID.randomUUID(), None))
  }

}
