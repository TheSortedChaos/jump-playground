package de.sorted.chaos.jump.game.game.pipeline

import de.sorted.chaos.jump.game.configuration.Configuration
import de.sorted.chaos.jump.game.input.{ Direction, PlayerModificationState, Velocity }
import org.joml.{ Matrix4f, Vector3f }
import org.slf4j.LoggerFactory

final case class PlayerState(timestamp: Long, jumpStart: Long, position: Vector3f, rotation: Vector3f, scale: Vector3f) {

  def getModelMatrix: Matrix4f =
    new Matrix4f()
      .translation(position)
      .rotateX(Math.toRadians(rotation.x).toFloat)
      .rotateY(Math.toRadians(rotation.y).toFloat)
      .rotateZ(Math.toRadians(rotation.z).toFloat)
      .scale(scale)
}

object PlayerState {

  private val Log = LoggerFactory.getLogger(this.getClass)

  def init: PlayerState = PlayerState(
    timestamp = System.currentTimeMillis(),
    jumpStart = 0L,
    position  = new Vector3f(0.0f, 0.0f, 0.0f),
    rotation  = new Vector3f(0.0f, 0.0f, 0.0f),
    scale     = new Vector3f(1.0f, 1.0f, 1.0f)
  )

  //noinspection ScalaStyle
  def modify(
      previousPlayerState: PlayerState,
      playerModifiers: PlayerModificationState,
      configuration: Configuration
  ): PlayerState = {
    val timestamp = previousPlayerState.timestamp
    val now       = System.currentTimeMillis()
    val deltaTime = now - timestamp

    val newYRotation = (playerModifiers.direction, previousPlayerState.rotation.y) match {
      case (Direction.LEFT, rotY) if rotY > -90.0f && rotY <= 90.0f => turnLeft(previousPlayerState, configuration)
      case (Direction.RIGHT, rotY) if rotY < 90.0f && rotY >= -90.0f => turnRight(previousPlayerState, configuration)
      case (Direction.TO_SCREEN, rotY) if rotY < 0.0f              => turnRight(previousPlayerState, configuration)
      case (Direction.TO_SCREEN, rotY) if rotY > 0.0f              => turnLeft(previousPlayerState, configuration)
      case _                                                       => previousPlayerState.rotation.y
    }

    val newXPosition = (newYRotation, playerModifiers.direction, playerModifiers.velocity) match {
      case (-90.0f, Direction.LEFT, Velocity.WALK) => walkLeft(previousPlayerState, deltaTime, configuration)
      case (90.0f, Direction.RIGHT, Velocity.WALK) => walkRight(previousPlayerState, deltaTime, configuration)
      case _                                       => previousPlayerState.position.x
    }

  //  val jumpStart = if (playerModifiers.jump) System.currentTimeMillis() else previousPlayerState.jumpStart
  //  Log.info("jump: {}", playerModifiers.jumpStart)
    val ground        = 0.0f
    val inAir         = previousPlayerState.position.y > 0.0
    val deltaJumpTime = System.currentTimeMillis() - playerModifiers.jumpStart
    val newYPosition = (ground, inAir, playerModifiers.jump) match {
      case (0.0f, false, true)  => jumpUp(previousPlayerState, deltaJumpTime, configuration)
      case (0.0f, true, true)   => jumpUp(previousPlayerState, deltaJumpTime, configuration)
      case (0.0f, true, false)  => jumpUp(previousPlayerState, deltaJumpTime, configuration)
      case (0.0f, false, false) => 0.0f
      case _                    => previousPlayerState.position.y
    }

    PlayerState(
      timestamp = now,
      0L,
      position  = new Vector3f(newXPosition, newYPosition, previousPlayerState.position.z),
      rotation  = new Vector3f(previousPlayerState.rotation.x, newYRotation, previousPlayerState.rotation.z),
      scale     = previousPlayerState.scale
    )
  }

  private def walkLeft(previousPlayerState: PlayerState, deltaTime: Long, configuration: Configuration) = {
    val velocityX = configuration.gameConfiguration.playerMovement.velocityX
    val x         = previousPlayerState.position.x

    x - velocityX * deltaTime
  }

  private def walkRight(previousPlayerState: PlayerState, deltaTime: Long, configuration: Configuration) = {
    val velocityX = configuration.gameConfiguration.playerMovement.velocityX
    val x         = previousPlayerState.position.x

    x + velocityX * deltaTime
  }

  private def turnLeft(previousPlayerState: PlayerState, configuration: Configuration) = {
    val rotY         = previousPlayerState.rotation.y
    val rotationStep = configuration.gameConfiguration.playerMovement.turnRotationStep

    rotY - rotationStep
  }

  private def turnRight(previousPlayerState: PlayerState, configuration: Configuration) = {
    val rotY         = previousPlayerState.rotation.y
    val rotationStep = configuration.gameConfiguration.playerMovement.turnRotationStep

    rotY + rotationStep
  }

  private def jumpUp(previousPlayerState: PlayerState, deltaTime: Long, configuration: Configuration) = {
    val velocityY = configuration.gameConfiguration.playerMovement.velocityY
    val gravity   = configuration.gameConfiguration.playerMovement.gravity
//    val y = previousPlayerState.position.y

    val newY = velocityY * deltaTime - (gravity / 2.0f * deltaTime * deltaTime) +0.001f
    val a = velocityY * deltaTime
    val b = (gravity / 2.0f * deltaTime * deltaTime) +0.001f
    Log.info(
      "{} = {} * {} - ({} / 2.0f * {} * {})   ---- {} - {}",
      newY,
      velocityY,
      deltaTime,
      gravity,
      deltaTime,
      deltaTime,
      a,
      b
    )

    newY
  }
}
