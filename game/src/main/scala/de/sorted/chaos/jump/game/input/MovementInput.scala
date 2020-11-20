package de.sorted.chaos.jump.game.input

import org.lwjgl.glfw.GLFW

object MovementInput {

  def processInput(windowId: Long): PlayerModificationState =
    (pressLeft(windowId), pressRight(windowId), pressJump(windowId)) match {
      case (true, false, false) =>
        PlayerModificationState(
          direction = Direction.LEFT,
          velocity  = Velocity.WALK,
          jump      = false
        )
      case (false, true, false) =>
        PlayerModificationState(
          direction = Direction.RIGHT,
          velocity  = Velocity.WALK,
          jump      = false
        )
      case (false, false, true) =>
        PlayerModificationState(
          direction = Direction.TO_SCREEN,
          velocity  = Velocity.STAND,
          jump      = true
        )
      case (true, false, true) =>
        PlayerModificationState(
          direction = Direction.LEFT,
          velocity  = Velocity.WALK,
          jump      = true
        )
      case (false, true, true) =>
        PlayerModificationState(
          direction = Direction.RIGHT,
          velocity  = Velocity.WALK,
          jump      = true
        )
      case _ => PlayerModificationState.idle
    }

  private def pressLeft(windowId: Long) = GLFW.glfwGetKey(windowId, GLFW.GLFW_KEY_A) == GLFW.GLFW_PRESS

  private def pressRight(windowId: Long) = GLFW.glfwGetKey(windowId, GLFW.GLFW_KEY_D) == GLFW.GLFW_PRESS

  private def pressJump(windowId: Long) = GLFW.glfwGetKey(windowId, GLFW.GLFW_KEY_SPACE) == GLFW.GLFW_PRESS
}
