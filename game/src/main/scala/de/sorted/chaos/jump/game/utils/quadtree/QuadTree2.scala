package de.sorted.chaos.jump.game.utils.quadtree

import de.sorted.chaos.jump.game.utils.quadtree.QuadTree2.queryFound

final case class Node(
    boundary: Boundary,
    var data: Option[Position],
    var northWest: Option[Node],
    var northEast: Option[Node],
    var southWest: Option[Node],
    var southEast: Option[Node]
) {

  def query(boundary: Boundary): Unit = {
      if (this.isDefinedLeaf && this.boundary.intersects(boundary)) {
        queryFound = queryFound :+ this.data.get
      }

      if (this.boundary.intersects(boundary) && this.isNode) {
        this.northWest.get.query(boundary)
        this.northEast.get.query(boundary)
        this.southWest.get.query(boundary)
        this.southEast.get.query(boundary)
      }
    }

  private def isDefinedLeaf = data.isDefined && northWest.isEmpty && northEast.isEmpty && southWest.isEmpty && southEast.isEmpty

  private def isEmptyLeaf = data.isEmpty && northWest.isEmpty && northEast.isEmpty && southWest.isEmpty && southEast.isEmpty

  private def isNode = northWest.isDefined && northEast.isDefined && southWest.isDefined && southEast.isDefined

  private def subdivide(): Unit = {
    val northWestBoundary = this.boundary.getNorthWestSubdivision
    this.northWest = Some(Node(northWestBoundary, None, None, None, None, None))

    val northEastBoundary = this.boundary.getNorthEastSubdivision
    this.northEast = Some(Node(northEastBoundary, None, None, None, None, None))

    val southWestBoundary = this.boundary.getSouthWestSubdivision
    this.southWest = Some(Node(southWestBoundary, None, None, None, None, None))

    val southEastBoundary = this.boundary.getSouthEastSubdivision
    this.southEast = Some(Node(southEastBoundary, None, None, None, None, None))
  }

  def insert(position: Position): Unit =
    if (boundary.contains(position) && this.isEmptyLeaf) {
      data = Some(position)
    } else if (boundary.contains(position) && this.isDefinedLeaf) {
      val remainder = this.data
      this.data = None
      this.insertWithRemainder(Some(position), remainder)
//      this.subdivide()
//      this.northWest.get.insertWithRemainder(Some(position), remainder)
//      this.northEast.get.insertWithRemainder(Some(position), remainder)
//      this.southWest.get.insertWithRemainder(Some(position), remainder)
//      this.southEast.get.insertWithRemainder(Some(position), remainder)
    } else if (boundary.contains(position) && this.isNode) {
      this.northWest.get.insert(position)
      this.northEast.get.insert(position)
      this.southWest.get.insert(position)
      this.southEast.get.insert(position)
    }

  //noinspection ScalaStyle,DuplicatedCode
  def insertWithRemainder(position: Option[Position], remainder: Option[Position]): Unit =
    // Node contains both (position and remainder)
    if (position.isDefined && remainder.isDefined && this.boundary.contains(position.get) && this.boundary.contains(
          remainder.get
        )) {
      this.subdivide()

      // recursion
      this.northWest.get.insertWithRemainder(position, remainder)
      this.northEast.get.insertWithRemainder(position, remainder)
      this.southWest.get.insertWithRemainder(position, remainder)
      this.southEast.get.insertWithRemainder(position, remainder)
    }
    // Node contains position, but not remainder (or remainder is empty)
    else if ((position.isDefined && this.boundary.contains(position.get)) && ((remainder.isDefined && !this.boundary.contains(
               remainder.get
             )) || remainder.isEmpty)) {
      this.data = position
    }
    // Node contains remainder, but not position (or position is empty)
    else if ((remainder.isDefined && this.boundary.contains(remainder.get)) && ((position.isDefined && !this.boundary.contains(
               position.get
             )) || position.isEmpty)) {
      this.data = remainder
    }
}

object QuadTree2 {

  var queryFound  = Vector.empty[Position]

  def getFound: Vector[Position] = queryFound

  def initQuadTree(width: Int, height: Int): Node =
    Node(
      boundary = Boundary(
        bottomLeft = BoundaryPosition(0, 0),
        topRight   = BoundaryPosition(width.toFloat, height.toFloat)
      ),
      None,
      None,
      None,
      None,
      None
    )
}
