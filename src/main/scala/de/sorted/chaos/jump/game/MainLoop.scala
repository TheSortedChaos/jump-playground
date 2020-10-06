package de.sorted.chaos.jump.game

import de.sorted.chaos.jump.configuration.Configuration
import de.sorted.chaos.jump.graphic.Window
import de.sorted.chaos.jump.graphic.entity.Entity
import de.sorted.chaos.jump.graphic.matrix.{ProjectionMatrix, ViewMatrix}
import de.sorted.chaos.jump.graphic.render.texture.TextureRenderer
import org.joml.Matrix4f
import org.lwjgl.glfw.GLFW.{glfwPollEvents, glfwSwapBuffers, glfwWindowShouldClose}
import org.lwjgl.opengl.GL11.{GL_COLOR_BUFFER_BIT, GL_DEPTH_BUFFER_BIT, glClear}

object MainLoop {

  def start(windowId: Long, configuration: Configuration): Unit = {
    val cube = Entity.TexturedCube
    val projection = ProjectionMatrix.get(configuration)
    var rotation = 0.0f


    while (!glfwWindowShouldClose(windowId)) {
      glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT)


      rotation = nextRotationStep(rotation)

      val view = ViewMatrix.matrix
      val pv = new Matrix4f(projection).mul(new Matrix4f(view))
      val mvp =  new Matrix4f(pv).mul(new Matrix4f().translate(0.0f, 0.0f, 0.0f).rotateX(rotation).rotateY(rotation))

      TextureRenderer.draw(mvp, cube)


      glfwSwapBuffers(windowId)
      glfwPollEvents()
    }

    Window.destroy(windowId)
  }

  private def nextRotationStep(rotation: Float) = {
    val newRotation = rotation + Math.toRadians(0.5).toFloat
    if (newRotation > 360.0f) 0.0f else newRotation
  }
}
