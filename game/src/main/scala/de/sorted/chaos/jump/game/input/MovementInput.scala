package de.sorted.chaos.jump.game.input

import org.lwjgl.glfw.GLFW

object MovementInput {

  var LastJump = 0L

  def processInput(windowId: Long): PlayerModificationState =
    (pressLeft(windowId), pressRight(windowId), pressJump(windowId)) match {
      case (true, false, false) =>
        PlayerModificationState(
          direction = Direction.LEFT,
          velocity  = Velocity.WALK,
          jump      = false,
          jumpStart = LastJump
        )
      case (false, true, false) =>
        PlayerModificationState(
          direction = Direction.RIGHT,
          velocity  = Velocity.WALK,
          jump      = false,
          jumpStart = LastJump
        )
      case (false, false, true) =>
        LastJump = System.currentTimeMillis()
        PlayerModificationState(
          direction = Direction.TO_SCREEN,
          velocity  = Velocity.STAND,
          jump      = true,
          jumpStart = LastJump
        )
      case (true, false, true) =>
        LastJump = System.currentTimeMillis()
        PlayerModificationState(
          direction = Direction.LEFT,
          velocity  = Velocity.WALK,
          jump      = true,
          jumpStart = LastJump
        )
      case (false, true, true) =>
        LastJump = System.currentTimeMillis()
        PlayerModificationState(
          direction = Direction.RIGHT,
          velocity  = Velocity.WALK,
          jump      = true,
          jumpStart = LastJump
        )
      case _ => PlayerModificationState.init(LastJump)
    }

  private def pressLeft(windowId: Long) = GLFW.glfwGetKey(windowId, GLFW.GLFW_KEY_A) == GLFW.GLFW_PRESS

  private def pressRight(windowId: Long) = GLFW.glfwGetKey(windowId, GLFW.GLFW_KEY_D) == GLFW.GLFW_PRESS

  private def pressJump(windowId: Long) = GLFW.glfwGetKey(windowId, GLFW.GLFW_KEY_SPACE) == GLFW.GLFW_PRESS
}
