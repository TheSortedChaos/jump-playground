package de.sorted.chaos.jump.game.graphic.matrix

import de.sorted.chaos.jump.game.input.PlayerAlignment
import org.joml.Matrix4f

object Camera {

  private[matrix] def getViewMatrix(playerAlignment: PlayerAlignment) =
    new Matrix4f().setLookAt(
      0.0f,
      8.0f,
      18.0f,
      playerAlignment.offset.x,
      playerAlignment.offset.y,
      playerAlignment.offset.z,
      0.0f,
      1.0f,
      0.0f
    )
}
