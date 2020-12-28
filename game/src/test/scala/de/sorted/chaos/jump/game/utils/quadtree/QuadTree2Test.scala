package de.sorted.chaos.jump.game.utils.quadtree

import org.scalatest.{ Matchers, WordSpec }

//noinspection ScalaStyle
class QuadTree2Test extends WordSpec with Matchers {

  private var Subject = QuadTree2.initQuadTree(800, 200)

  "TDD TestSheet" should {

//    "initialize a quadTree" in {
//      val actual = QuadTree2.initQuadTree(800, 200)
//      actual shouldBe Node(
//        boundary = Boundary(
//          bottomLeft = Position(0, 0),
//          topRight   = Position(800, 200)
//        ),
//        None,
//        None,
//        None,
//        None,
//        None
//      )
//    }
//
//    "insert one point into empty quadTree" in {
//      val position = Position(100, 50)
//      Subject.insert(position)
//      Subject shouldBe Node(
//        boundary = Boundary(
//          bottomLeft = Position(0, 0),
//          topRight   = Position(800, 200)
//        ),
//        Some(position),
//        None,
//        None,
//        None,
//        None
//      )
//    }
//
//    "insert two points into empty quadTree (different regions after one split)" in {
//      val position  = Position(100, 50)
//      val position2 = Position(600, 150)
//      Subject.insert(position)
//      Subject.insert(position2)
//      Subject shouldBe Node(
//        boundary = Boundary(
//          bottomLeft = Position(0, 0),
//          topRight   = Position(800, 200)
//        ),
//        None,
//        Some(
//          Node(
//            Boundary(
//              Position(0, 100),
//              Position(400, 200)
//            ),
//            None,
//            None,
//            None,
//            None,
//            None
//          )
//        ),
//        Some(
//          Node(
//            Boundary(
//              Position(400, 100),
//              Position(800, 200)
//            ),
//            Some(position2),
//            None,
//            None,
//            None,
//            None
//          )
//        ),
//        Some(
//          Node(
//            Boundary(
//              Position(0, 0),
//              Position(400, 100)
//            ),
//            Some(position),
//            None,
//            None,
//            None,
//            None
//          )
//        ),
//        Some(
//          Node(
//            Boundary(
//              Position(400, 0),
//              Position(800, 100)
//            ),
//            None,
//            None,
//            None,
//            None,
//            None
//          )
//        )
//      )
//    }

    "insert two points into empty quadTree (different regions after multiple split)" in {

      val subject = QuadTree2.initQuadTree(10, 10)
      val positions = Vector(
        Position(4, 4),
        Position(5, 4),
        Position(6, 4),
        Position(4, 5),
        Position(5, 5),
        Position(6, 5),
        Position(4, 6),
        Position(5, 6),
        Position(6, 6),
        Position(1, 1),
        Position(10, 10),
        Position(10, 1),
        Position(1, 10)
      )

      positions.foreach(pos => subject.insert(pos))

      val queryBoundary = Boundary(BoundaryPosition(4.0f, 4.0f), BoundaryPosition(6.0f,6.0f))
      val temp = subject.query(queryBoundary)

      val actual = QuadTree2.getFound

      println(temp)
      println(actual)
    }
  }
}
