package com.chriniko.example

object App {

  def main(args: Array[String]): Unit = {

    val examples: Array[Example] = Array(
      new Example1, //Kleisli
      new Example2, //Kleisli
      new Example3, //Functor
      new Example4, //Basics
      new Example5, //Unified Types
      new Example6, //Classes
      new Example7, //Class Composition
      new Example8, //Try Pattern
      new Example9, //Option, Some, None pattern
      new Example10, //Either, Left, Right pattern
      new Example11, //Traits
      new Example12, //Higher Order Functions
      new Example13, //Nested Functions
      new Example14, //Multiple Parameters List (Currying)
      new Example15, //Case class
      new Example16, //Pattern Matching
      new Example17, //Singleton Objects
      new Example18, //Regular Expression Patterns
      new Example19, //Extractor Objects
      new Example20, //For Comprehensions
      new Example21, //Generic Classes
      new Example22, //Variances
      new Example23, //Upper type bounds
      new Example24, //Lower type bounds
      new Example25, //Inner classes
      new Example26, //Compound Types
      new Example27, //Implicit Parameters
      new Example28, //Implicit Conversions
      new Example29, //Polymorphic Methods
      new Example30, //Type Inference
      new Example31, //Operators
      new Example32, //By-Name parameters
      new Example33, //Annotations
      new Example34, //Default Parameter values
      new Example35, //Named arguments
      new Example36, //Packages and imports
      new Example37, //Abstract Types
      new Example38, //Self Types
      new Example39, //Hallmarks of Functional Programming
      new Example40, //Map Shuffle Reduce Pattern
      new Example41, //Companion Objects
      new Example42, //Concurrency and Synchronization
      new Example43, //Designing Fail Fast Error Handling
      new Example44, //Robust Error Handling in Scala
      new Example45, //Easing Into Functional Error Handling in Scala
      new Example46, //Algebraic Data Types & Structural Recursion
      new Example47, //Uniting Church and State: FP and OO Together (One of the best in order to understand in depth some concepts)
      new Example48, //Simple Concurrency Patterns
    )

    val exampleToRun = 48

    require(exampleToRun >= 1 && exampleToRun <= examples.length)

    examples(exampleToRun - 1) run()

  }

}
