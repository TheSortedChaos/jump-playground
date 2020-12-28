package de.sorted.chaos.jump.game.utils.quadtree

final case class Position(x: Int, y: Int)

final case class BoundaryPosition(x: Float, y: Float)

final case class Boundary(bottomLeft: BoundaryPosition, topRight: BoundaryPosition) {

  //noinspection ScalaStyle
  def intersects(other: Boundary): Boolean = {
    if (this.bottomLeft.x > other.topRight.x || other.bottomLeft.x > this.topRight.x) {
      return false
    }
    if (this.bottomLeft.y > other.topRight.y || other.bottomLeft.y > this.topRight.y) {
      return false
    }

    return true
  }

  def contains(position: Position): Boolean =
    position.x >= this.bottomLeft.x && position.x < this.topRight.x &&
    position.y >= this.bottomLeft.y && position.y < this.topRight.y

  def getNorthWestSubdivision: Boundary =
    Boundary(
      bottomLeft = BoundaryPosition(
        x = this.bottomLeft.x,
        y = this.getVerticalMid
      ),
      topRight = BoundaryPosition(
        x = this.getHorizontalMid,
        y = this.topRight.y
      )
    )

  def getNorthEastSubdivision: Boundary =
    Boundary(
      bottomLeft = BoundaryPosition(
        x = this.getHorizontalMid,
        y = this.getVerticalMid
      ),
      topRight = BoundaryPosition(
        x = this.topRight.x,
        y = this.topRight.y
      )
    )

  def getSouthWestSubdivision: Boundary =
    Boundary(
      bottomLeft = BoundaryPosition(
        x = this.bottomLeft.x,
        y = this.bottomLeft.y
      ),
      topRight = BoundaryPosition(
        x = this.getHorizontalMid,
        y = this.getVerticalMid
      )
    )

  def getSouthEastSubdivision: Boundary =
    Boundary(
      bottomLeft = BoundaryPosition(
        x = this.getHorizontalMid,
        y = this.bottomLeft.y
      ),
      topRight = BoundaryPosition(
        x = this.topRight.x,
        y = this.getVerticalMid
      )
    )

  private def getHorizontalMid = ((this.topRight.x - this.bottomLeft.x) / 2.0f) + this.bottomLeft.x

  private def getVerticalMid = ((this.topRight.y - this.bottomLeft.y) / 2.0f) + this.bottomLeft.y
}
