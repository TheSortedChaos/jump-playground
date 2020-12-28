//package de.sorted.chaos.jump.game.utils.quadtree
//
//final case class Position(x: Int, y: Int)
//
//final case class Boundary(bottomLeft: Position, topRight: Position) {
//
//  def contains(position: Position): Boolean =
//    position.x > this.bottomLeft.x && position.x < this.topRight.x &&
//    position.y > this.bottomLeft.y && position.y < this.topRight.y
//}
//
////noinspection ScalaStyle
//final case class Node(
//    boundary: Boundary,
//    var northWest: Option[Node],
//    var northEast: Option[Node],
//    var southWest: Option[Node],
//    var southEast: Option[Node]
//) {
//
//  def isSubdivided: Boolean = northWest.isDefined && northEast.isDefined && southWest.isDefined && southEast.isDefined
//
//  def insert(position: Position): Node =
//    this match {
//      case Node(_, None, None, None, None) =>
//        val left   = this.boundary.bottomLeft.x
//        val top    = this.boundary.topRight.y
//        val right  = this.boundary.topRight.x
//        val bottom = this.boundary.bottomLeft.y
//
//        this.northWest = Some(
//          Node(
//            boundary = Boundary(
//              bottomLeft = Position(left, (top - bottom) / 2),
//              topRight   = Position((right - left) / 2, top)
//            ),
//            northWest = None,
//            northEast = None,
//            southWest = None,
//            southEast = None
//          )
//        )
//        this.northEast = Some(
//          Node(
//            boundary = Boundary(
//              bottomLeft = Position((right - left) / 2, (top - bottom) / 2),
//              topRight   = Position(right, top)
//            ),
//            northWest = None,
//            northEast = None,
//            southWest = None,
//            southEast = None
//          )
//        )
//        this.southWest = Some(
//          Node(
//            boundary = Boundary(
//              bottomLeft = Position(left, bottom),
//              topRight   = Position((right - left) / 2, (top - bottom) / 2)
//            ),
//            northWest = None,
//            northEast = None,
//            southWest = None,
//            southEast = None
//          )
//        )
//        this.southEast = Some(
//          Node(
//            boundary = Boundary(
//              bottomLeft = Position((right - left) / 2, bottom),
//              topRight   = Position(right, (top - bottom) / 2)
//            ),
//            northWest = None,
//            northEast = None,
//            southWest = None,
//            southEast = None
//          )
//        )
//        this
//
//      case Node(_, Some(nw), Some(ne), Some(sw), Some(se)) =>
//        if (nw.boundary.contains(position)) {
//          nw.insert(position)
//        } else if (ne.boundary.contains(position)) {
//          ne.insert(position)
//        } else if (sw.boundary.contains(position)) {
//          sw.insert(position)
//        } else {
//          se.insert(position)
//        }
//    }
//}
//
//object QuadTree {
//
//  def root(maxX: Int, maxY: Int): Node =
//    Node(
//      boundary = Boundary(
//        bottomLeft = Position(0, 0),
//        topRight   = Position(maxX, maxY)
//      ),
//      northWest = None,
//      northEast = None,
//      southWest = None,
//      southEast = None
//    )
//}
