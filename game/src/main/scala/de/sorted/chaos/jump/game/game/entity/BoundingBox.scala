package de.sorted.chaos.jump.game.game.entity

final case class BoundingBox(xRange: Float, yRange: Float, zRange: Float)

object BoundingBox {

  private[entity] def initDefault: BoundingBox =
    BoundingBox(1.0f, 1.0f, 1.0f)
}
