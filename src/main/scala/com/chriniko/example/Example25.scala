package com.chriniko.example

/*
  Inner classes (http://docs.scala-lang.org/tour/inner-classes.html)
 */
class Example25 extends Example {

  override def run(): Unit = {

    // 1
    val graph1: Graph = new Graph

    val node1: graph1.Node = graph1.newNode()
    val node2: graph1.Node = graph1.newNode()
    val node3: graph1.Node = graph1.newNode()

    node1.connectTo(node2)
    node3.connectTo(node1)


    // 2
    val graphOne: Graph = new Graph
    val nodeOne: graphOne.Node = graphOne.newNode()
    val nodeTwo: graphOne.Node = graphOne.newNode()
    nodeOne.connectTo(nodeTwo) // legal

    val graphTwo: Graph = new Graph
    val nodeThree: graphTwo.Node = graphTwo.newNode()
    /*
      If we now have two graphs,
      the type system of Scala does not allow us to mix nodes
      defined within one graph with the nodes of another graph,
      since the nodes of the other graph have a different type.
      Here is an illegal program:
     */
    //nodeOne.connectTo(nodeThree) // illegal


    // 3
    val graphOneM: GraphModified = new GraphModified
    val nodeOneM: GraphModified#Node = graphOneM.newNode()
    val nodeTwoM: GraphModified#Node = graphOneM.newNode()
    nodeOneM.connectTo(nodeTwoM) // legal

    val graphTwoM: GraphModified = new GraphModified
    val nodeThreeM: GraphModified#Node = graphTwoM.newNode()
    nodeOneM.connectTo(nodeThreeM) // now it is legal!!!
  }

  //--------------------------------------------------------------------

  class Graph {

    class Node {

      var connectedNodes: List[Node] = Nil

      def connectTo(node: Node): Unit = {
        if (!connectedNodes.exists(cN => cN.equals(node))) {
          connectedNodes = node :: connectedNodes
        }
      }
    }

    var nodes: List[Node] = Nil

    def newNode(): Node = {
      val res = new Node
      nodes = res :: nodes
      res
    }

  }

  //--------------------------------------------------------------------

  class GraphModified {

    class Node {

      var connectedNodes: List[GraphModified#Node] = Nil

      def connectTo(node: GraphModified#Node): Unit = {
        if (!connectedNodes.exists(cN => cN.equals(node))) {
          connectedNodes = node :: connectedNodes
        }
      }
    }

    var nodes: List[Node] = Nil

    def newNode(): Node = {
      val res = new Node
      nodes = res :: nodes
      res
    }

  }

}
