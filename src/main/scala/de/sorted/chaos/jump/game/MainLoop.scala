package de.sorted.chaos.jump.game

import de.sorted.chaos.jump.graphic.Window
import org.lwjgl.glfw.GLFW.{glfwPollEvents, glfwSwapBuffers, glfwWindowShouldClose}
import org.lwjgl.opengl.GL11.{GL_COLOR_BUFFER_BIT, GL_DEPTH_BUFFER_BIT, glClear}

object MainLoop {

  def start(windowId: Long): Unit = {
    while (!glfwWindowShouldClose(windowId)) {
      glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT)

      glfwSwapBuffers(windowId)
      glfwPollEvents()
    }

    Window.destroy(windowId)
  }
}
