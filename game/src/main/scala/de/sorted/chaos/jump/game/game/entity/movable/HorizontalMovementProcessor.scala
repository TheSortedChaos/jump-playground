package de.sorted.chaos.jump.game.game.entity.movable

import de.sorted.chaos.jump.game.game.entity.{Alignment, BoundingBox, movable}
import de.sorted.chaos.jump.game.game.pipeline.Level
import org.joml.AABBf

object HorizontalMovementProcessor {

  def next(entity: MovableEntity, level: Level, horizontalVelocity: Float): MovableEntity = {
    val (deltaTime, newTiming) = entity.timings.getDeltaTime
    val newAlignment = updatePositionX(entity, horizontalVelocity, deltaTime)
    val boundingBox = HorizontalMovementProcessor.getBoundingBox(newAlignment, entity.boundingBox)

    if (level.entityIntersectWithLevelBoundingBox(boundingBox)) {
      MovableEntity(
        alignment   = entity.alignment,
        timings     = newTiming,
        boundingBox = entity.boundingBox
      )
    } else {
      movable.MovableEntity(
        alignment   = newAlignment,
        timings     = newTiming,
        boundingBox = entity.boundingBox
      )
    }
  }

  private def updatePositionX(movableEntity: MovableEntity, horizontalVelocity: Float, deltaTime: Long) = {
    val lastPosition           = movableEntity.alignment.position.x
    val newPosition            = lastPosition + (horizontalVelocity / 1000.0f * deltaTime)

    if (horizontalVelocity < 0.0f) {
      movableEntity.alignment.turnLeft.updatePositionX(newPosition)
    } else {
      movableEntity.alignment.turnRight.updatePositionX(newPosition)
    }
  }

  private def getBoundingBox(alignment: Alignment, boundingBox: BoundingBox) =
    new AABBf(
      alignment.position.x - boundingBox.xRange,
      alignment.position.y - boundingBox.yRange,
      alignment.position.z - boundingBox.zRange,
      alignment.position.x + boundingBox.xRange,
      alignment.position.y + boundingBox.yRange,
      alignment.position.z + boundingBox.zRange
    )
}
