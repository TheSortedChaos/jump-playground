package de.sorted.chaos.jump.game.game.pipeline

import de.sorted.chaos.jump.game.configuration.Configuration
import de.sorted.chaos.jump.game.input.PlayerModification
import org.joml.{ Matrix4f, Vector3f }

final case class PlayerState(timestamp: Long, position: Vector3f, rotation: Vector3f, scale: Vector3f) {

  def getModelMatrix: Matrix4f =
    new Matrix4f()
      .translation(position)
      .rotateX(Math.toRadians(rotation.x).toFloat)
      .rotateY(Math.toRadians(rotation.y).toFloat)
      .rotateZ(Math.toRadians(rotation.z).toFloat)
      .scale(scale)
}

object PlayerState {

  def init: PlayerState = PlayerState(
    timestamp = System.currentTimeMillis(),
    position  = new Vector3f(0.0f, 0.0f, 0.0f),
    rotation  = new Vector3f(0.0f, 0.0f, 0.0f),
    scale     = new Vector3f(1.0f, 1.0f, 1.0f)
  )

  //noinspection ScalaStyle
  def modify(previousPlayerState: PlayerState, playerModifiers: PlayerModification, configuration: Configuration): PlayerState = {
    val timestamp        = previousPlayerState.timestamp
    val now              = System.currentTimeMillis()
    val deltaTime        = now - timestamp
    val turnRotationStep = configuration.gameConfiguration.playerMovement.turnRotationStep

    val (nextRotation, nextPosition) = (previousPlayerState.rotation.y, playerModifiers.velocity.x) match {
      case (r, v) if (r == 90.0f || r == -90.0f) && v != 0.0f => movePlayer(previousPlayerState, playerModifiers, deltaTime)
      case (r, v) if r < 90.0f && v > 0.0f                    => rotatePlayerToRight(previousPlayerState, turnRotationStep)
      case (r, v) if r > -90.0f && v < 0.0f                   => rotatePlayerToLeft(previousPlayerState, turnRotationStep)
      case (r, v) if r > 0.0f && v == 0.0f                    => rotatePlayerToLeft(previousPlayerState, turnRotationStep)
      case (r, v) if r < 0.0f && v == 0.0f                    => rotatePlayerToRight(previousPlayerState, turnRotationStep)
      case _                                                  => (previousPlayerState.rotation, previousPlayerState.position)
    }

    PlayerState(
      timestamp = now,
      position  = nextPosition,
      rotation  = nextRotation,
      scale     = previousPlayerState.scale
    )
  }

  private def movePlayer(previousPlayerState: PlayerState, playerModification: PlayerModification, deltaTime: Long) = {
    val rotation = previousPlayerState.rotation
    val velocity = playerModification.velocity
    val position = previousPlayerState.position

    (rotation, new Vector3f(velocity.mul(deltaTime).add(position)))
  }

  private def rotatePlayerToRight(previousPlayerState: PlayerState, turnRotationStep: Float) = {
    val rotation = previousPlayerState.rotation

    (new Vector3f(rotation.add(new Vector3f(0.0f, turnRotationStep, 0.0f))), previousPlayerState.position)
  }

  private def rotatePlayerToLeft(previousPlayerState: PlayerState, turnRotationStep: Float) = {
    val rotation = previousPlayerState.rotation

    (new Vector3f(rotation.add(new Vector3f(0.0f, -turnRotationStep, 0.0f))), previousPlayerState.position)
  }
}
