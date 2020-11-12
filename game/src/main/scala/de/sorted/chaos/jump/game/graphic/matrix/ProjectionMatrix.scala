package de.sorted.chaos.jump.game.graphic.matrix

import de.sorted.chaos.jump.game.configuration.Graphic
import org.joml.Matrix4f

import scala.math.toRadians

object ProjectionMatrix {
  def get(configuration: Graphic): Matrix4f = {
    val fov         = configuration.camera.fieldOfView
    val near        = configuration.camera.nearPlane
    val far         = configuration.camera.farPlane
    val aspectRatio = configuration.screen.width / configuration.screen.height.floatValue()

    val matrix = new Matrix4f()
    matrix.setPerspective(toRadians(fov).toFloat, aspectRatio, near, far)

    matrix
  }
}
