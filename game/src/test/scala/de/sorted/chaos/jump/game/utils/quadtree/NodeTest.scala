//package de.sorted.chaos.jump.game.utils.quadtree
//
//import org.scalatest.{ Matchers, WordSpec }
//
////noinspection ScalaStyle
//class NodeTest extends WordSpec with Matchers {
//  "TDD sheet" should {
//
//    "create an empty tree" in {
//      val actual = QuadTree2.initQuadTree(800, 200)
//
//      actual shouldBe Node(
//        boundary = Boundary(
//          bottomLeft = Position(0, 0),
//          topRight   = Position(800, 200)
//        ),
//        Vector.empty,
//        None,
//        None,
//        None,
//        None
//      )
//    }
//
//    "add a point in NorthWest" in {
//      val position = Position(5, 5)
//
//      val actual = QuadTree.initQuadTree(800, 200)
//
//      actual.insert(position)
//
//      actual.insert(Position(7, 7))
//      actual.insert(Position(8, 7))
//      actual.insert(Position(9, 7))
//      actual.insert(Position(19, 7))
//
//      println(actual)
//    }
//  }
//}
