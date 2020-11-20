package de.sorted.chaos.jump.game.game.pipeline

import org.joml.{ AABBf, Intersectionf }

final case class Level(boundingBoxes: Vector[AABBf], gravityForJump: Float, gravityForFall: Float) {

  def entityIntersectWithLevelBoundingBox(entityBoundingBox: AABBf): Boolean =
    this.boundingBoxes
      .map(box => Intersectionf.testAabAab(box, entityBoundingBox))
      .reduce((x, y) => x || y)
}

object Level {

  def init: Level =
    Level(
      Vector(
        new AABBf(-15.0f, -1.55f, -2.0f, 15.0f, -1.05f, 2.0f),
        new AABBf(-9.0f, -1.05f, -1.0f, -7.0f, 0.95f, 1.0f),
        new AABBf(7.0f, -1.05f, -1.0f, 9.0f, 0.95f, 1.0f),
        new AABBf(-4.0f, 1.95f, -1.0f, -2.0f, 3.95f, 1.0f)
      ),
      gravityForJump = 18.0f,
      gravityForFall = 35.0f
    )
}
