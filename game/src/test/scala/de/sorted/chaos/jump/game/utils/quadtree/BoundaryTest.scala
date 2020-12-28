//package de.sorted.chaos.jump.game.utils.quadtree
//
//import org.scalatest.{ Matchers, WordSpec }
//
////noinspection ScalaStyle
//class BoundaryTest extends WordSpec with Matchers {
//
//  private val BottomLeft = Position(0, 0)
//  private val TopRight   = Position(800, 200)
//  private val Original   = Boundary(BottomLeft, TopRight)
//
//  "Boundary" should {
//
//    "create a North-West subdivision" in {
//      val actual = Original.getNorthWestSubdivision
//      actual shouldBe Boundary(
//        Position(0, 100),
//        Position(400, 200)
//      )
//    }
//
//    "create a North-East subdivision" in {
//      val actual = Original.getNorthEastSubdivision
//      actual shouldBe Boundary(
//        Position(400, 100),
//        Position(800, 200)
//      )
//    }
//
//    "create a South-West subdivision" in {
//      val actual = Original.getSouthWestSubdivision
//      actual shouldBe Boundary(
//        Position(0, 0),
//        Position(400, 100)
//      )
//    }
//
//    "create a South-East subdivision" in {
//      val actual = Original.getSouthEastSubdivision
//      actual shouldBe Boundary(
//        Position(400, 0),
//        Position(800, 100)
//      )
//    }
//
//    "contains a Position" in {
//      val position = Position(400, 100)
//      val actual = Original.contains(position)
//      actual shouldBe true
//    }
//
//    "contains NOT a (above) Position" in {
//      val position = Position(400, 201)
//      val actual = Original.contains(position)
//      actual shouldBe false
//    }
//
//    "contains NOT a (right) Position" in {
//      val position = Position(801, 100)
//      val actual = Original.contains(position)
//      actual shouldBe false
//    }
//
//    "contains NOT a (below) Position" in {
//      val position = Position(400, -1)
//      val actual = Original.contains(position)
//      actual shouldBe false
//    }
//
//    "contains NOT a (left) Position" in {
//      val position = Position(-1, 100)
//      val actual = Original.contains(position)
//      actual shouldBe false
//    }
//  }
//}
