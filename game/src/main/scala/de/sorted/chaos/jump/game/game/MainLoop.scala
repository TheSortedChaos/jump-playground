package de.sorted.chaos.jump.game.game

import de.sorted.chaos.jump.game.configuration.Configuration
import de.sorted.chaos.jump.game.graphic.entity.Entity
import de.sorted.chaos.jump.game.graphic.matrix.{ ProjectionMatrix, ViewMatrix }
import de.sorted.chaos.jump.game.graphic.render.texture.{ TextureRenderer, TexturedEntity }
import de.sorted.chaos.jump.game.graphic.{ FramesPerSecond, Window }
import de.sorted.chaos.jump.game.input.{ PlayerAlignment, PlayerMovement }
import org.joml.{ Matrix4f, Vector3f }
import org.lwjgl.glfw.GLFW.{ glfwPollEvents, glfwSwapBuffers, glfwWindowShouldClose }
import org.lwjgl.opengl.GL11.{ glClear, GL_COLOR_BUFFER_BIT, GL_DEPTH_BUFFER_BIT }

object MainLoop {

  def start(windowId: Long, configuration: Configuration): Unit = {
    val hero            = Entity.TexturedCube
    val brix            = Entity.TexturedBrix
    val projection      = ProjectionMatrix.get(configuration.graphic)
    var playerAlignment = PlayerAlignment(new Vector3f(), new Vector3f(), new Vector3f())
    val fps             = new FramesPerSecond()

    while (!glfwWindowShouldClose(windowId)) {
      glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT)

      playerAlignment = PlayerMovement.processInput(windowId, configuration.game, playerAlignment)
      val modelMatrix = playerAlignment.getModelMatrix

      val view = ViewMatrix.matrix
      val pv   = new Matrix4f(projection).mul(new Matrix4f(view))
      val mvp  = new Matrix4f(pv).mul(modelMatrix)

      TextureRenderer.draw(mvp, hero)
      drawLevel(pv, brix)

      glfwSwapBuffers(windowId)
      glfwPollEvents()
      fps.process()
    }

    Window.destroy(windowId)
  }

  private def drawLevel(pv: Matrix4f, brix: TexturedEntity): Unit = {
    val brix1Mvp  = new Matrix4f(pv).mul(new Matrix4f().translate(0.0f, -1.3f, 0.0f))
    val brix2Mvp  = new Matrix4f(pv).mul(new Matrix4f().translate(-2.0f, -1.3f, 0.0f))
    val brix3Mvp  = new Matrix4f(pv).mul(new Matrix4f().translate(-4.0f, -1.3f, 0.0f))
    val brix4Mvp  = new Matrix4f(pv).mul(new Matrix4f().translate(-6.0f, -1.3f, 0.0f))
    val brix5Mvp  = new Matrix4f(pv).mul(new Matrix4f().translate(-8.0f, -1.3f, 0.0f))
    val brix6Mvp  = new Matrix4f(pv).mul(new Matrix4f().translate(-10.0f, -1.3f, 0.0f))
    val brix7Mvp  = new Matrix4f(pv).mul(new Matrix4f().translate(-12.0f, -1.3f, 0.0f))
    val brix8Mvp  = new Matrix4f(pv).mul(new Matrix4f().translate(-14.0f, -1.3f, 0.0f))
    val brix9Mvp  = new Matrix4f(pv).mul(new Matrix4f().translate(2.0f, -1.3f, 0.0f))
    val brix10Mvp = new Matrix4f(pv).mul(new Matrix4f().translate(4.0f, -1.3f, 0.0f))
    val brix11Mvp = new Matrix4f(pv).mul(new Matrix4f().translate(6.0f, -1.3f, 0.0f))
    val brix12Mvp = new Matrix4f(pv).mul(new Matrix4f().translate(8.0f, -1.3f, 0.0f))
    val brix13Mvp = new Matrix4f(pv).mul(new Matrix4f().translate(10.0f, -1.3f, 0.0f))
    val brix14Mvp = new Matrix4f(pv).mul(new Matrix4f().translate(12.0f, -1.3f, 0.0f))
    val brix15Mvp = new Matrix4f(pv).mul(new Matrix4f().translate(14.0f, -1.3f, 0.0f))

    TextureRenderer.draw(brix1Mvp, brix)
    TextureRenderer.draw(brix2Mvp, brix)
    TextureRenderer.draw(brix3Mvp, brix)
    TextureRenderer.draw(brix4Mvp, brix)
    TextureRenderer.draw(brix5Mvp, brix)
    TextureRenderer.draw(brix6Mvp, brix)
    TextureRenderer.draw(brix7Mvp, brix)
    TextureRenderer.draw(brix8Mvp, brix)
    TextureRenderer.draw(brix9Mvp, brix)
    TextureRenderer.draw(brix10Mvp, brix)
    TextureRenderer.draw(brix11Mvp, brix)
    TextureRenderer.draw(brix12Mvp, brix)
    TextureRenderer.draw(brix13Mvp, brix)
    TextureRenderer.draw(brix14Mvp, brix)
    TextureRenderer.draw(brix15Mvp, brix)
  }
}
