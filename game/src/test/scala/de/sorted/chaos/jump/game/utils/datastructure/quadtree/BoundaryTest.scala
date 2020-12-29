package de.sorted.chaos.jump.game.utils.datastructure.quadtree

import org.scalatest.{ Matchers, WordSpec }

class BoundaryTest extends WordSpec with Matchers {

  "A Boundary" should {

    val original = Boundary(
      Position(10.0f, 10.0f),
      Position(20.0f, 20.0f)
    )

    "create a 'NorthWest' subdivision from itself" in {
      val actual = original.createNorthWest
      actual shouldBe Boundary(
        Position(10.0f, 15.0f),
        Position(15.0f, 20.0f)
      )
    }

    "create a 'NorthEast' subdivision from itself" in {
      val actual = original.createNorthEast
      actual shouldBe Boundary(
        Position(15.0f, 15.0f),
        Position(20.0f, 20.0f)
      )
    }

    "create a 'SouthWest' subdivision from itself" in {
      val actual = original.createSouthWest
      actual shouldBe Boundary(
        Position(10.0f, 10.0f),
        Position(15.0f, 15.0f)
      )
    }

    "create a 'SouthEast' subdivision from itself" in {
      val actual = original.createSouthEast
      actual shouldBe Boundary(
        Position(15.0f, 10.0f),
        Position(20.0f, 15.0f)
      )
    }

    "check if it contains a position" in {
      val position = Position(15.0f, 15.0f)
      val actual   = original.contains(position)
      actual shouldBe true
    }

    "check if it NOT contains position" in {
      val position = Position(5.0f, 5.0f)
      val actual   = original.contains(position)
      actual shouldBe false
    }

    "check if it includes a position" in {
      val position = Position(15.0f, 15.0f)
      val actual   = original.includesPosition(position)
      actual shouldBe true
    }

    "check if it NOT includes position" in {
      val position = Position(5.0f, 5.0f)
      val actual   = original.includesPosition(position)
      actual shouldBe false
    }

    "check contains position (edge case)" in {
      val position = Position(20.0f, 20.0f)
      val actual   = original.contains(position)
      actual shouldBe false
    }

    "check includes position (edge case)" in {
      val position = Position(20.0f, 20.0f)
      val actual   = original.includesPosition(position)
      actual shouldBe true
    }

    "check if 'bottom-left' position is included (edge case)" in {
      val position = Position(10.0f, 10.0f)
      val actual   = original.contains(position)
      actual shouldBe true
    }

    "check if 'top-right' position is NOT included (edge case)" in {
      val position = Position(20.0f, 20.0f)
      val actual   = original.contains(position)
      actual shouldBe false
    }

    "check if it is intersecting with another boundary" in {
      val other = Boundary(
        Position(5.0f, 15.0f),
        Position(15.0f, 25.0f)
      )
      val actual = original.intersects(other)
      actual shouldBe true
    }

    "check if it is NOT intersecting with another (above) boundary" in {
      val other = Boundary(
        Position(10.0f, 20.1f),
        Position(20.0f, 30.0f)
      )
      val actual = original.intersects(other)
      actual shouldBe false
    }

    "check if it is NOT intersecting with another (right) boundary" in {
      val other = Boundary(
        Position(20.1f, 10.0f),
        Position(30.0f, 20.0f)
      )
      val actual = original.intersects(other)
      actual shouldBe false
    }

    "check if it is NOT intersecting with another (below) boundary" in {
      val other = Boundary(
        Position(10.0f, 0.0f),
        Position(20.0f, 9.9f)
      )
      val actual = original.intersects(other)
      actual shouldBe false
    }

    "check if it is NOT intersecting with another (left) boundary" in {
      val other = Boundary(
        Position(0.0f, 10.0f),
        Position(9.9f, 20.0f)
      )
      val actual = original.intersects(other)
      actual shouldBe false
    }
  }
}
