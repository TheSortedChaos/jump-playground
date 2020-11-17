package de.sorted.chaos.jump.game.game.pipeline

import de.sorted.chaos.jump.game.game.GameState
import de.sorted.chaos.jump.game.graphic.entity.Entity
import de.sorted.chaos.jump.game.graphic.matrix.MatrixStack
import de.sorted.chaos.jump.game.graphic.render.texture.{ TextureRenderer, TexturedEntity }
import org.joml.Matrix4f
import org.lwjgl.glfw.GLFW.{ glfwPollEvents, glfwSwapBuffers }
import org.lwjgl.opengl.GL11.{ glClear, GL_COLOR_BUFFER_BIT, GL_DEPTH_BUFFER_BIT }

object RenderPipeline {

  private val Hero = Entity.TexturedCube
  private val Brix = Entity.TexturedBrix

  def draw(gameState: GameState): Unit = {
    val windowId             = gameState.windowId
    val interpolation        = getInterpolation(gameState) // interpolation between two render states
    val configuration        = gameState.configuration
    val playerState          = gameState.playerState
    val projectionViewMatrix = MatrixStack.getProjectionViewMatrix(configuration, playerState)

    glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT)
    drawHero(projectionViewMatrix, playerState, Hero)
    drawLevel(projectionViewMatrix, Brix)
    glfwSwapBuffers(windowId)
    glfwPollEvents()
  }

  private def getInterpolation(gameState: GameState) = {
    val skipTicks    = gameState.configuration.gameConfiguration.gameLoopTiming.getSkipTicks
    val nextGameTick = gameState.loopState.nextGameTick
    (System.currentTimeMillis() + skipTicks - nextGameTick) / skipTicks.toDouble
  }

  private def drawHero(projectionViewMatrix: Matrix4f, playerState: PlayerState, hero: TexturedEntity): Unit = {
    val modelMatrix = playerState.getModelMatrix
    val mvp         = new Matrix4f(projectionViewMatrix).mul(modelMatrix)
    TextureRenderer.draw(mvp, hero)
  }

  private def drawLevel(projectionViewMatrix: Matrix4f, brix: TexturedEntity): Unit = {
    val brix1Mvp  = new Matrix4f(projectionViewMatrix).mul(new Matrix4f().translate(0.0f, -1.3f, 0.0f))
    val brix2Mvp  = new Matrix4f(projectionViewMatrix).mul(new Matrix4f().translate(-2.0f, -1.3f, 0.0f))
    val brix3Mvp  = new Matrix4f(projectionViewMatrix).mul(new Matrix4f().translate(-4.0f, -1.3f, 0.0f))
    val brix4Mvp  = new Matrix4f(projectionViewMatrix).mul(new Matrix4f().translate(-6.0f, -1.3f, 0.0f))
    val brix5Mvp  = new Matrix4f(projectionViewMatrix).mul(new Matrix4f().translate(-8.0f, -1.3f, 0.0f))
    val brix6Mvp  = new Matrix4f(projectionViewMatrix).mul(new Matrix4f().translate(-10.0f, -1.3f, 0.0f))
    val brix7Mvp  = new Matrix4f(projectionViewMatrix).mul(new Matrix4f().translate(-12.0f, -1.3f, 0.0f))
    val brix8Mvp  = new Matrix4f(projectionViewMatrix).mul(new Matrix4f().translate(-14.0f, -1.3f, 0.0f))
    val brix9Mvp  = new Matrix4f(projectionViewMatrix).mul(new Matrix4f().translate(2.0f, -1.3f, 0.0f))
    val brix10Mvp = new Matrix4f(projectionViewMatrix).mul(new Matrix4f().translate(4.0f, -1.3f, 0.0f))
    val brix11Mvp = new Matrix4f(projectionViewMatrix).mul(new Matrix4f().translate(6.0f, -1.3f, 0.0f))
    val brix12Mvp = new Matrix4f(projectionViewMatrix).mul(new Matrix4f().translate(8.0f, -1.3f, 0.0f))
    val brix13Mvp = new Matrix4f(projectionViewMatrix).mul(new Matrix4f().translate(10.0f, -1.3f, 0.0f))
    val brix14Mvp = new Matrix4f(projectionViewMatrix).mul(new Matrix4f().translate(12.0f, -1.3f, 0.0f))
    val brix15Mvp = new Matrix4f(projectionViewMatrix).mul(new Matrix4f().translate(14.0f, -1.3f, 0.0f))

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
