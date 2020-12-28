//package de.sorted.chaos.jump.game.utils.quadtree
//
//
//
///**
//  * I'm doing here the easy version of a QuadTree. This means every Node can hold up to 4 positions. There is no resorting.
//  * if the 4 positions are full the Node will get new branches (Good to go for now).
//  * In my opinion a 'real' QuadTree should only have data in the leaves. I got this idea from:
//  *   https://www.youtube.com/watch?v=OJxEcs0w_kE&t=1947s&ab_channel=TheCodingTrain
//  *   https://www.youtube.com/watch?v=QQx_NmCIuCY&ab_channel=TheCodingTrain
//  *
//  *   Also: This data structure is a mutable data structure
//  */
//final case class Node(
//    boundary: Boundary,
//    var positions: Vector[Position],
//    var northWest: Option[Node],
//    var northEast: Option[Node],
//    var southWest: Option[Node],
//    var southEast: Option[Node]
//) {
//
//  def insert(position: Position): Unit = {
//    import QuadTree._
//
//    if (this.boundary.contains(position) && this.positions.size == Size) {
//      val (northWest, northEast, southWest, southEast) = this.subdivide
//
//      this.northWest = northWest
//      this.northEast = northEast
//      this.southWest = southWest
//      this.southEast = southEast
//
//      // recursion
//      this.northWest.get.insert(position)
//      this.northEast.get.insert(position)
//      this.southWest.get.insert(position)
//      this.southEast.get.insert(position)
//    }
//
//    if (this.boundary.contains(position) && this.positions.size < Size) {
//      this.positions = positions :+ position
//    }
//  }
//
//  def isSubdivided: Boolean = northWest.isDefined && northEast.isDefined && southWest.isDefined && southEast.isDefined
//
//  private def subdivide = {
//    val northWestBoundary = this.boundary.getNorthWestSubdivision
//    val northWestNode = Node(
//      northWestBoundary,
//      Vector.empty,
//      None,
//      None,
//      None,
//      None
//    )
//    val northEastBoundary = this.boundary.getNorthEastSubdivision
//    val northEastNode = Node(
//      northEastBoundary,
//      Vector.empty,
//      None,
//      None,
//      None,
//      None
//    )
//    val southWestBoundary = this.boundary.getSouthWestSubdivision
//    val southWestNode = Node(
//      southWestBoundary,
//      Vector.empty,
//      None,
//      None,
//      None,
//      None
//    )
//    val southEastBoundary = this.boundary.getSouthEastSubdivision
//    val southEastNode = Node(
//      southEastBoundary,
//      Vector.empty,
//      None,
//      None,
//      None,
//      None
//    )
//
//    (Some(northWestNode), Some(northEastNode), Some(southWestNode), Some(southEastNode))
//  }
//}
//
//object QuadTree {
//
//  private[quadtree] val Size = 4
//
//  def initQuadTree(width: Int, height: Int): Node =
//    Node(
//      boundary = Boundary(
//        bottomLeft = Position(0, 0),
//        topRight   = Position(width, height)
//      ),
//      Vector.empty,
//      None,
//      None,
//      None,
//      None
//    )
//}
