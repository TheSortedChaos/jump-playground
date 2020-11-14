package de.sorted.chaos.jump.game.input

import de.sorted.chaos.jump.game.configuration.GameConfiguration
import org.joml.{ Matrix4f, Vector3f }
import org.lwjgl.glfw.GLFW

final case class PlayerAlignment(offset: Vector3f, rotation: Vector3f, scale: Vector3f) {
  def getModelMatrix: Matrix4f =
    new Matrix4f()
      .translation(offset)
      .rotateX(Math.toRadians(rotation.x).toFloat)
      .rotateY(Math.toRadians(rotation.y).toFloat)
      .rotateZ(Math.toRadians(rotation.z).toFloat)
      .scale(scale)
}

object PlayerMovement {

  private val RotationStep = 10.0f

  //noinspection ScalaStyle
  def processInput(windowId: Long, configuration: GameConfiguration, playerAlignment: PlayerAlignment): PlayerAlignment = {
    val offset   = new Vector3f(playerAlignment.offset)
    val rotation = new Vector3f(playerAlignment.rotation)
    val scale    = new Vector3f(1.0f, 1.0f, 1.0f)

    if (GLFW.glfwGetKey(windowId, GLFW.GLFW_KEY_A) == GLFW.GLFW_PRESS) {
      if (rotation.y > -90.0f) {
        rotation.y = rotation.y - RotationStep
      } else {
        offset.x = offset.x - configuration.playerMovement.deltaX
      }
    } else if (GLFW.glfwGetKey(windowId, GLFW.GLFW_KEY_D) == GLFW.GLFW_PRESS) {
      if (rotation.y < 90.0f) {
        rotation.y = rotation.y + RotationStep
      } else {
        offset.x = offset.x + configuration.playerMovement.deltaX
      }
    } else if (GLFW.glfwGetKey(windowId, GLFW.GLFW_KEY_D) != GLFW.GLFW_PRESS && GLFW.glfwGetKey(windowId, GLFW.GLFW_KEY_D) != GLFW.GLFW_PRESS) {
      if (rotation.y < 0.0f) {
        rotation.y = rotation.y + RotationStep
      }
      if (rotation.y > 0.0f) {
        rotation.y = rotation.y - RotationStep
      }
    } else if (GLFW.glfwGetKey(windowId, GLFW.GLFW_KEY_W) == GLFW.GLFW_PRESS) {
      // rotation.x = 20.0f
    } else if (GLFW.glfwGetKey(windowId, GLFW.GLFW_KEY_S) == GLFW.GLFW_PRESS) {
      // nothing
    }
    PlayerAlignment(offset, rotation, scale)
  }
}
