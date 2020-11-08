package de.sorted.chaos.jump.game.graphic.matrix

import org.joml.Matrix4f

/**
  * The matrix has to be returned, because every modification doesn't return the result
  * see also: https://github.com/JOML-CI/JOML/wiki/Common-Pitfalls<
  */
object ViewMatrix {
  val matrix: Matrix4f = new Matrix4f(new Matrix4f().setLookAt(0.0f, 8.0f, 18.0f, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f))
}
