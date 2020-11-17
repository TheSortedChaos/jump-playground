package de.sorted.chaos.jump.game.graphic.matrix

import de.sorted.chaos.jump.game.game.pipeline.PlayerState
import org.joml.Matrix4f

object Camera {

  private[matrix] def getViewMatrix(playerState: PlayerState) =
    new Matrix4f().setLookAt(
      0.0f,
      8.0f,
      18.0f,
      playerState.position.x,
      0.0f,
      playerState.position.z,
      0.0f,
      1.0f,
      0.0f
    )
}
