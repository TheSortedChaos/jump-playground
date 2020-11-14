package de.sorted.chaos.jump.game.graphic.matrix

import de.sorted.chaos.jump.game.configuration.Configuration
import de.sorted.chaos.jump.game.game.pipeline.PlayerState
import org.joml.Matrix4f

object MatrixStack {

  def getProjectionViewMatrix(configuration: Configuration, playerState: PlayerState): Matrix4f = {
    val graphicConfig    = configuration.graphicConfiguration
    val projectionMatrix = ProjectionMatrix.get(graphicConfig)
    val viewMatrix       = Camera.getViewMatrix(playerState)

    new Matrix4f(projectionMatrix).mul(new Matrix4f(viewMatrix))
  }
}
