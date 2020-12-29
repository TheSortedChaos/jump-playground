package de.sorted.chaos.jump.game.utils.datastructure.quadtree

import org.scalatest.{ Matchers, WordSpec }

//noinspection ScalaStyle
class QuadTreeTest extends WordSpec with Matchers {

  "A QuadTree" should {

    "create an empty QuadTree (Node)" in {
      val actual = QuadTree.init[String](50, 50)
      actual shouldBe QuadTree(
        Boundary(
          Position(0.0f, 0.0f),
          Position(50.0f, 50.0f)
        ),
        None,
        None,
        None,
        None,
        None
      )
    }

    "insert ONE data point into an empty QuadTree" in {
      var quadTree = QuadTree.init[String](20, 10)
      val data     = Data[String](Position(10.0f, 5.0f), "first data")
      quadTree.insert(data)

      quadTree shouldBe QuadTree(
        Boundary(
          Position(0.0f, 0.0f),
          Position(20.0f, 10.0f)
        ),
        Some(data),
        None,
        None,
        None,
        None
      )
    }

    "insert TWO data points (different subdivisions) into an empty QuadTree" in {
      val quadTree = QuadTree.init[String](20, 10)
      val data1    = Data[String](Position(5.0f, 2.0f), "1")
      val data2    = Data[String](Position(18.0f, 8.0f), "2")
      quadTree.insert(data1)
      quadTree.insert(data2)

      quadTree shouldBe QuadTree(
        Boundary(
          Position(0.0f, 0.0f),
          Position(20.0f, 10.0f)
        ),
        None,
        Some(
          QuadTree[String](
            Boundary(
              Position(0.0f, 5.0f),
              Position(10.0f, 10.0f)
            ),
            None,
            None,
            None,
            None,
            None
          )
        ),
        Some(
          QuadTree[String](
            Boundary(
              Position(10.0f, 5.0f),
              Position(20.0f, 10.0f)
            ),
            Some(data2),
            None,
            None,
            None,
            None
          )
        ),
        Some(
          QuadTree[String](
            Boundary(
              Position(0.0f, 0.0f),
              Position(10.0f, 5.0f)
            ),
            Some(data1),
            None,
            None,
            None,
            None
          )
        ),
        Some(
          QuadTree[String](
            Boundary(
              Position(10.0f, 0.0f),
              Position(20.0f, 5.0f)
            ),
            None,
            None,
            None,
            None,
            None
          )
        )
      )
    }

    "insert some data points and query a subset of them" in {
      // quadTree has to be slightly bigger -> see comment Boundary#contains
      val quadTree = QuadTree.init[String](21, 11)
      Vector(
        Data[String](Position(5.0f, 0.0f), "0"),
        Data[String](Position(5.0f, 1.0f), "1"),
        Data[String](Position(6.0f, 2.0f), "2"),
        Data[String](Position(7.0f, 3.0f), "3"),
        Data[String](Position(8.0f, 4.0f), "4"),
        Data[String](Position(9.0f, 5.0f), "5"),
        Data[String](Position(10.0f, 6.0f), "6"),
        Data[String](Position(11.0f, 7.0f), "7"),
        Data[String](Position(12.0f, 8.0f), "8"),
        Data[String](Position(13.0f, 9.0f), "9"),
        Data[String](Position(14.0f, 10.0f), "10"),
        Data[String](Position(15.0f, 10.0f), "11"),
        Data[String](Position(1.0f, 1.0f), "out 1"),
        Data[String](Position(1.0f, 9.0f), "out 2"),
        Data[String](Position(19.0f, 1.0f), "out 3"),
        Data[String](Position(19.0f, 9.0f), "out 4")
      ).foreach(x => quadTree.insert(x))
      val queryBoundary = Boundary(
        Position(5.0f, 0.0f),
        Position(15.0f, 10.0f)  // must also be a little bigger
      )
      val actual = quadTree.query(queryBoundary)

      actual should contain theSameElementsAs Vector("0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11")
    }
  }
}
