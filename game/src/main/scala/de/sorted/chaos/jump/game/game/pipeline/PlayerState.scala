package de.sorted.chaos.jump.game.game.pipeline

import de.sorted.chaos.jump.game.configuration.Configuration
import de.sorted.chaos.jump.game.game.entity.MovableEntity
import de.sorted.chaos.jump.game.input.{Direction, PlayerModificationState, Velocity}
import org.slf4j.LoggerFactory

object PlayerState {

  private val Log  = LoggerFactory.getLogger(this.getClass)

  //noinspection ScalaStyle
  def modify(
      previousPlayerState: MovableEntity,
      level: Level,
      playerModifiers: PlayerModificationState,
      configuration: Configuration
  ): MovableEntity = {

    // println(s"direction = ${playerModifiers.direction} velocity = ${playerModifiers.velocity} jump = ${playerModifiers.jump}")

    val velocity = configuration.gameConfiguration.playerMovement.velocityX
    val gravity = configuration.gameConfiguration.playerMovement.gravity
    val velocityY = configuration.gameConfiguration.playerMovement.velocityY

    val horizontal = (playerModifiers.direction, playerModifiers.velocity) match {
      case (Direction.LEFT, Velocity.WALK)  => previousPlayerState.moveHorizontal(level, -velocity)
      case (Direction.RIGHT, Velocity.WALK) => previousPlayerState.moveHorizontal(level, velocity)
      case _                                => previousPlayerState.idle
    }

    val jump = if (playerModifiers.jump) {
      MovableEntity(
        horizontal.alignment,
        horizontal.timings.startJumpTimer(horizontal.alignment.position.y),
        horizontal.boundingBox
      )
    } else {
      horizontal
    }

    val result = if (jump.timings.jumpTimer.isDefined) {
      jump.jump(level, gravity, velocityY)
    } else {
      jump.fall(level, gravity)
    }


    result
//    val newYRotation = (playerModifiers.direction, previousPlayerState.rotation.y) match {
//      case (Direction.LEFT, rotY) if rotY > -90.0f && rotY <= 90.0f  => turnLeft(previousPlayerState, configuration)
//      case (Direction.RIGHT, rotY) if rotY < 90.0f && rotY >= -90.0f => turnRight(previousPlayerState, configuration)
//      case (Direction.TO_SCREEN, rotY) if rotY < 0.0f                => turnRight(previousPlayerState, configuration)
//      case (Direction.TO_SCREEN, rotY) if rotY > 0.0f                => turnLeft(previousPlayerState, configuration)
//      case _                                                         => previousPlayerState.rotation.y
//    }
//
//    val newXPosition = (newYRotation, playerModifiers.direction, playerModifiers.velocity) match {
//      case (-90.0f, Direction.LEFT, Velocity.WALK) => walkLeft(previousPlayerState, deltaTime, configuration, level)
//      case (90.0f, Direction.RIGHT, Velocity.WALK) => walkRight(previousPlayerState, deltaTime, configuration, level)
//      case _                                       => previousPlayerState.position.x
//    }
//
//    val newJumpTimer = if (playerModifiers.jump && previousPlayerState.jumpTimer.jumpAvailable) {
//      JumpTimer(
//        timestamp          = System.currentTimeMillis(),
//        jumpStartTimestamp = System.currentTimeMillis(),
//        jumpStartY         = previousPlayerState.position.y,
//        jumpAvailable      = false
//      )
//    } else if (previousPlayerState.position.y < 0.001f) {
//      JumpTimer.init
//    } else {
//      previousPlayerState.jumpTimer
//    }
//
//    val newYPosition = if (!newJumpTimer.jumpAvailable) {
//      executeJump(newJumpTimer, configuration)
//    } else {
//      0.0f
//    }
//
//    val newPlayerState = PlayerState(
//      timestamp = now,
//      newJumpTimer,
//      position = new Vector3f(newXPosition, newYPosition, previousPlayerState.position.z),
//      rotation = new Vector3f(previousPlayerState.rotation.x, newYRotation, previousPlayerState.rotation.z),
//      scale    = previousPlayerState.scale
//    )
//
//    if (level.entityIntersectWithBoundingBox(newPlayerState.getBoundingBox)) {
//      previousPlayerState
//    } else {
//      newPlayerState
//    }
  }

//  private def walkLeft(previousPlayerState: PlayerState, deltaTime: Long, configuration: Configuration, level:  Level) = {
//    val velocityX = configuration.gameConfiguration.playerMovement.velocityX
//    val x         = previousPlayerState.position.x
//
//    x - velocityX * deltaTime
//  }
//
//  private def walkRight(previousPlayerState: PlayerState, deltaTime: Long, configuration: Configuration, level: Level) = {
//    val velocityX = configuration.gameConfiguration.playerMovement.velocityX
//    val x         = previousPlayerState.position.x
//
//    x + velocityX * deltaTime
//  }
//
//  private def turnLeft(previousPlayerState: PlayerState, configuration: Configuration) = {
//    val rotY         = previousPlayerState.rotation.y
//    val rotationStep = configuration.gameConfiguration.playerMovement.turnRotationStep
//
//    rotY - rotationStep
//  }
//
//  private def turnRight(previousPlayerState: PlayerState, configuration: Configuration) = {
//    val rotY         = previousPlayerState.rotation.y
//    val rotationStep = configuration.gameConfiguration.playerMovement.turnRotationStep
//
//    rotY + rotationStep
//  }
//
//  private def executeJump(jumpTimer: JumpTimer, configuration: Configuration) = {
//    val velocityY = configuration.gameConfiguration.playerMovement.velocityY
//    val gravity   = configuration.gameConfiguration.playerMovement.gravity
//    val y         = jumpTimer.jumpStartY
//    val deltaTime = System.currentTimeMillis() - jumpTimer.jumpStartTimestamp
//
//    velocityY * deltaTime - (gravity / 2.0f * deltaTime * deltaTime) + 0.001f + y
//  }
}
