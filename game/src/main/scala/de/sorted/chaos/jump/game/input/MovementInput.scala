package de.sorted.chaos.jump.game.input

import de.sorted.chaos.jump.game.configuration.GameConfiguration
import org.joml.Vector3f
import org.lwjgl.glfw.GLFW

object MovementInput {

  def processInput(windowId: Long, configuration: GameConfiguration): PlayerModification = {
    val velocityX   = configuration.playerMovement.velocityX
    val playerModifiers = PlayerModification.init
    var newVelocity = new Vector3f(0.0f, 0.0f, 0.0f)

    if (GLFW.glfwGetKey(windowId, GLFW.GLFW_KEY_A) == GLFW.GLFW_PRESS) {
      newVelocity = newVelocity.add(-velocityX, 0.0f, 0.0f)
    } else if (GLFW.glfwGetKey(windowId, GLFW.GLFW_KEY_D) == GLFW.GLFW_PRESS) {
      newVelocity = newVelocity.add(velocityX, 0.0f, 0.0f)
    } else if (GLFW.glfwGetKey(windowId, GLFW.GLFW_KEY_W) == GLFW.GLFW_PRESS) {
      // rotation.x = 20.0f
    } else if (GLFW.glfwGetKey(windowId, GLFW.GLFW_KEY_S) == GLFW.GLFW_PRESS) {
      // nothing
    }

    playerModifiers.setVelocity(newVelocity)
  }
}
