package de.sorted.chaos.jump.game.game.entity.movable

import de.sorted.chaos.jump.game.game.pipeline.Level

object VerticalMovementProcessor {

  private val Delta = 0.1f

  def next(entity: MovableEntity, level: Level, jumpVelocity: Float, isJumping: Boolean): MovableEntity = {
    val distanceToBottom = MoveSensor.shootRayToGround(entity, level)
    val distanceToUp     = MoveSensor.shootRayToSky(entity, level)
    val jumpEntity       = maybeStartJump(entity, isJumping, distanceToBottom)

    (jumpEntity.timings.jumpTimer.isDefined, distanceToBottom, distanceToUp) match {
      case (false, distance, _) if distance <= Delta => entity.resetTimings
      case (false, distance, _) if distance > Delta  => falling(entity, level.gravityForFall)
      case (true, _, distance) if distance <= Delta  => entity.startFallTiming
      case (true, _, distance) if distance > Delta   => jumping(entity, level.gravityForJump, jumpVelocity)
      case _                                         => entity
    }
  }

  private def maybeStartJump(entity: MovableEntity, isJumping: Boolean, distanceToGround: Float) =
    if (isJumping && distanceToGround <= Delta && entity.timings.jumpTimer.isEmpty) {
      MovableEntity(
        alignment   = entity.alignment,
        timings     = entity.timings.startJumpTimer(entity.alignment.position.y),
        boundingBox = entity.boundingBox
      )
    } else {
      entity
    }

  private def jumping(entity: MovableEntity, jumpGravity: Float, velocity: Float) = {
    val (deltaTime, newTiming) = getJumpDeltaTime(entity)
    val timeToMaxHeight        = velocity / jumpGravity * 1000 // in ms

    if (deltaTime > timeToMaxHeight) {
      entity.startFallTiming
    } else {
      val startY       = newTiming.jumpTimer.get.startY
      val newY         = startY + velocity / 1000.0f * deltaTime - (jumpGravity / 1000000.0f / 2.0f * deltaTime * deltaTime)
      val newAlignment = entity.alignment.updatePositionY(newY)

      MovableEntity(
        alignment   = newAlignment,
        timings     = newTiming,
        boundingBox = entity.boundingBox
      )
    }
  }

  private def getJumpDeltaTime(entity: MovableEntity) =
    if (entity.timings.jumpTimer.isEmpty) {
      (0L, entity.timings.startJumpTimer(entity.alignment.position.y))
    } else {
      entity.timings.getJumpDeltaTime
    }

  private def falling(entity: MovableEntity, fallGravity: Float) = {
    val (deltaTime, newTiming) = getFallDeltaTime(entity)
    val startY                 = newTiming.fallTimer.get.startY
    val newY                   = startY - (fallGravity / 1000000.0f / 2.0f * deltaTime * deltaTime)
    val newAlignment           = entity.alignment.updatePositionY(newY)

    MovableEntity(
      alignment   = newAlignment,
      timings     = newTiming,
      boundingBox = entity.boundingBox
    )
  }

  private def getFallDeltaTime(entity: MovableEntity) =
    if (entity.timings.fallTimer.isEmpty) {
      (0L, entity.timings.startFallTimer(entity.alignment.position.y))
    } else {
      entity.timings.getFallDeltaTime
    }
}
