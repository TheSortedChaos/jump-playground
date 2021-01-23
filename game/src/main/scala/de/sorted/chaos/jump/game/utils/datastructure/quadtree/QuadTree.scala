package de.sorted.chaos.jump.game.utils.datastructure.quadtree

import scala.collection.mutable

final case class Data[T](position: Position, data: T)

/**
  * This is a simple QuadTree implementation. It's kinda messy but good to go for the moment.
  * This implementation stores data only at the leaves (no list of data in the nodes) and it's full mutable :/
  * It will be used e.g. Frustum Culling (for static level objects), bounding boxes (for physics), etc
  */
final case class QuadTree[T]( // is a/one Node
    boundary: Boundary,
    var data: Option[Data[T]],
    var northWest: Option[QuadTree[T]],
    var northEast: Option[QuadTree[T]],
    var southWest: Option[QuadTree[T]],
    var southEast: Option[QuadTree[T]]
) {

  def query(queryBoundary: Boundary, result: mutable.ListBuffer[T] = mutable.ListBuffer.empty[T]): Vector[T] = {
    if (this.isDefinedLeaf && queryBoundary.includesPosition(this.data.get.position)) {
      result += this.data.get.data
    }

    if (this.boundary.intersects(queryBoundary) && this.isNode) {
      // recursion
      this.northWest.get.query(queryBoundary, result)
      this.northEast.get.query(queryBoundary, result)
      this.southWest.get.query(queryBoundary, result)
      this.southEast.get.query(queryBoundary, result)
    }

    result.toVector
  }

  def insert(data: Data[T]): Unit =
    if (boundary.contains(data.position) && this.isEmptyLeaf) {
      this.data = Some(data)
    } else if (boundary.contains(data.position) && this.isDefinedLeaf) {
      val remainder = this.data
      this.data = None
      this.insertWithRemainder(Some(data), remainder)
    } else if (boundary.contains(data.position) && this.isNode) {
      // recursion
      this.northWest.get.insert(data)
      this.northEast.get.insert(data)
      this.southWest.get.insert(data)
      this.southEast.get.insert(data)
    }

  private def insertWithRemainder(data: Option[Data[T]], remainder: Option[Data[T]]): Unit =
    if (nodeContainsDataAndRemainder(data, remainder)) {
      this.subdivide()

      // recursion
      this.northWest.get.insertWithRemainder(data, remainder)
      this.northEast.get.insertWithRemainder(data, remainder)
      this.southWest.get.insertWithRemainder(data, remainder)
      this.southEast.get.insertWithRemainder(data, remainder)
    } else if (nodeContainsDataButNotRemainder(data, remainder)) {
      this.data = data
    } else if (nodeContainsRemainderButNotData(data, remainder)) {
      this.data = remainder
    }

  //noinspection DuplicatedCode (same as line below, but I would loss the 'naming' - swith parameters)
  private def nodeContainsRemainderButNotData(data: Option[Data[T]], remainder: Option[Data[T]]) =
    (remainder.isDefined && this.boundary
      .contains(remainder.get.position)) && ((data.isDefined && !this.boundary.contains(data.get.position)) || data.isEmpty)

  //noinspection DuplicatedCode (same as line above, but I would loss the 'naming' - switch parameters)
  private def nodeContainsDataButNotRemainder(data: Option[Data[T]], remainder: Option[Data[T]]) =
    (data.isDefined && this.boundary.contains(data.get.position)) && ((remainder.isDefined && !this.boundary
      .contains(remainder.get.position)) || remainder.isEmpty)

  private def nodeContainsDataAndRemainder(data: Option[Data[T]], remainder: Option[Data[T]]) =
    data.isDefined && remainder.isDefined && this.boundary.contains(data.get.position) && this.boundary
      .contains(remainder.get.position)

  private def subdivide(): Unit = {
    val northWestBoundary = this.boundary.createNorthWest
    this.northWest = Some(QuadTree(northWestBoundary, None, None, None, None, None))

    val northEastBoundary = this.boundary.createNorthEast
    this.northEast = Some(QuadTree(northEastBoundary, None, None, None, None, None))

    val southWestBoundary = this.boundary.createSouthWest
    this.southWest = Some(QuadTree(southWestBoundary, None, None, None, None, None))

    val southEastBoundary = this.boundary.createSouthEast
    this.southEast = Some(QuadTree(southEastBoundary, None, None, None, None, None))
  }

  private def isDefinedLeaf = data.isDefined && northWest.isEmpty && northEast.isEmpty && southWest.isEmpty && southEast.isEmpty

  private def isEmptyLeaf = data.isEmpty && northWest.isEmpty && northEast.isEmpty && southWest.isEmpty && southEast.isEmpty

  private def isNode = northWest.isDefined && northEast.isDefined && southWest.isDefined && southEast.isDefined
}

object QuadTree {

  def init[T](maxX: Int, maxY: Int): QuadTree[T] = QuadTree(
    Boundary(
      Position(0.0f, 0.0f),
      Position(maxX.toFloat, maxY.toFloat)
    ),
    None,
    None,
    None,
    None,
    None
  )
}
