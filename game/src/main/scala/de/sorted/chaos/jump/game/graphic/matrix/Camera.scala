package de.sorted.chaos.jump.game.graphic.matrix

import de.sorted.chaos.jump.game.game.entity.movable.MovableEntity
import org.joml.Matrix4f

object Camera {

  private[matrix] def getViewMatrix(player: MovableEntity) =
    new Matrix4f().setLookAt(
      player.alignment.position.x,
      8.0f,
      30.0f,
      player.alignment.position.x,
      0.0f,
      player.alignment.position.z,
      0.0f,
      1.0f,
      0.0f
    )
}
