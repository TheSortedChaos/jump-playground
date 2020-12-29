package de.sorted.chaos.jump.game.utils.datastructure.quadtree

final case class Position(x: Float, y: Float)

final case class Boundary(bottomLeft: Position, topRight: Position) {

  def intersects(other: Boundary): Boolean =
    if (this.bottomLeft.x > other.topRight.x || other.bottomLeft.x > this.topRight.x) {
      false
    } else if (this.bottomLeft.y > other.topRight.y || other.bottomLeft.y > this.topRight.y) {
      false
    } else {
      true
    }

  // used for query, can't use contains see below comment
  def includesPosition(position: Position): Boolean =
    position.x >= this.bottomLeft.x && position.x <= this.topRight.x &&
      position.y >= this.bottomLeft.y && position.y <= this.topRight.y

  // Here I have created a little problem and I should think about, sometime ;)
  // I can't include both positions, because this would add one data point multiple times
  // With the current approach there is also a little problem:
  //    If a data point is added exactly on topRight of the initial Tree -> the data point is not included to the Tree
  //    Workaround -> make the initial Tree a little bit bigger during initialization
  def contains(position: Position): Boolean =
    position.x >= this.bottomLeft.x && position.x < this.topRight.x &&
    position.y >= this.bottomLeft.y && position.y < this.topRight.y

  def createNorthWest: Boundary =
    Boundary(
      bottomLeft = Position(
        x = this.bottomLeft.x,
        y = this.getVerticalMid
      ),
      topRight = Position(
        x = this.getHorizontalMid,
        y = this.topRight.y
      )
    )

  def createNorthEast: Boundary =
    Boundary(
      bottomLeft = Position(
        x = this.getHorizontalMid,
        y = this.getVerticalMid
      ),
      topRight = Position(
        x = this.topRight.x,
        y = this.topRight.y
      )
    )

  def createSouthWest: Boundary =
    Boundary(
      bottomLeft = Position(
        x = this.bottomLeft.x,
        y = this.bottomLeft.y
      ),
      topRight = Position(
        x = this.getHorizontalMid,
        y = this.getVerticalMid
      )
    )

  def createSouthEast: Boundary =
    Boundary(
      bottomLeft = Position(
        x = this.getHorizontalMid,
        y = this.bottomLeft.y
      ),
      topRight = Position(
        x = this.topRight.x,
        y = this.getVerticalMid
      )
    )

  private def getHorizontalMid = ((this.topRight.x - this.bottomLeft.x) / 2.0f) + this.bottomLeft.x

  private def getVerticalMid = ((this.topRight.y - this.bottomLeft.y) / 2.0f) + this.bottomLeft.y
}
