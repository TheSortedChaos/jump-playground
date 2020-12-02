package de.sorted.chaos.jump.game.game.pipeline

import de.sorted.chaos.jump.game.game.GameState
import de.sorted.chaos.jump.game.game.entity.movable.MovableEntity
import de.sorted.chaos.jump.game.graphic.entity.Entity
import de.sorted.chaos.jump.game.graphic.matrix.MatrixStack
import de.sorted.chaos.jump.game.graphic.render.texture.{ TextureRenderer, TexturedEntity }
import org.joml.Matrix4f
import org.lwjgl.glfw.GLFW.{ glfwPollEvents, glfwSwapBuffers }
import org.lwjgl.opengl.GL11.{ glClear, GL_COLOR_BUFFER_BIT, GL_DEPTH_BUFFER_BIT }

object RenderPipeline {

  private val Hero  = Entity.Block
  private val Ground  = Entity.Ground
  private val Pillar = Entity.Pillar

  def draw(gameState: GameState): Unit = {
    val windowId             = gameState.windowId
    val interpolation        = getInterpolation(gameState) // interpolation between two render states
    val configuration        = gameState.configuration
    val playerState          = gameState.playerState
    val projectionViewMatrix = MatrixStack.getProjectionViewMatrix(configuration, playerState)

    glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT)
    drawHero(projectionViewMatrix, playerState, Hero)
    drawLevel(projectionViewMatrix, Ground, Pillar)
    glfwSwapBuffers(windowId)
    glfwPollEvents()
  }

  private def getInterpolation(gameState: GameState) = {
    val skipTicks    = gameState.configuration.gameConfiguration.gameLoopTiming.getSkipTicks
    val nextGameTick = gameState.loopState.nextGameTick
    (System.currentTimeMillis() + skipTicks - nextGameTick) / skipTicks.toDouble
  }

  private def drawHero(projectionViewMatrix: Matrix4f, player: MovableEntity, hero: TexturedEntity): Unit = {
    val modelMatrix = player.getModelMatrix
    val mvp         = new Matrix4f(projectionViewMatrix).mul(modelMatrix)
    TextureRenderer.draw(mvp, hero)
  }

  private def drawLevel(projectionViewMatrix: Matrix4f, ground: TexturedEntity, block: TexturedEntity): Unit = {
    val brix1Mvp = new Matrix4f(projectionViewMatrix).mul(new Matrix4f().translate(0.0f, -2.0f, 0.0f))
    val brix2Mvp = new Matrix4f(projectionViewMatrix).mul(new Matrix4f().translate(-2.0f, -2.0f, 0.0f))
    val brix3Mvp = new Matrix4f(projectionViewMatrix).mul(new Matrix4f().translate(2.0f, -2.0f, 0.0f))
    val brix4Mvp = new Matrix4f(projectionViewMatrix).mul(new Matrix4f().translate(-4.0f, -2.0f, 0.0f))
    val brix5Mvp = new Matrix4f(projectionViewMatrix).mul(new Matrix4f().translate(4.0f, -2.0f, 0.0f))
    val brix6Mvp = new Matrix4f(projectionViewMatrix).mul(new Matrix4f().translate(-6.0f, -2.0f, 0.0f))
    val brix7Mvp = new Matrix4f(projectionViewMatrix).mul(new Matrix4f().translate(6.0f, -2.0f, 0.0f))
    val brix8Mvp = new Matrix4f(projectionViewMatrix).mul(new Matrix4f().translate(-8.0f, -2.0f, 0.0f))
    val brix9Mvp = new Matrix4f(projectionViewMatrix).mul(new Matrix4f().translate(8.0f, -2.0f, 0.0f))
//    val brix10Mvp = new Matrix4f(projectionViewMatrix).mul(new Matrix4f().translate(4.0f, -2.0f, 0.0f))
//    val brix11Mvp = new Matrix4f(projectionViewMatrix).mul(new Matrix4f().translate(6.0f, -2.0f, 0.0f))
//    val brix12Mvp = new Matrix4f(projectionViewMatrix).mul(new Matrix4f().translate(8.0f, -2.0f, 0.0f))
//    val brix13Mvp = new Matrix4f(projectionViewMatrix).mul(new Matrix4f().translate(10.0f, -2.0f, 0.0f))
//    val brix14Mvp = new Matrix4f(projectionViewMatrix).mul(new Matrix4f().translate(12.0f, -2.0f, 0.0f))
//    val brix15Mvp = new Matrix4f(projectionViewMatrix).mul(new Matrix4f().translate(14.0f, -2.0f, 0.0f))

    TextureRenderer.draw(brix1Mvp, ground)
    TextureRenderer.draw(brix2Mvp, ground)
    TextureRenderer.draw(brix3Mvp, ground)
    TextureRenderer.draw(brix4Mvp, ground)
    TextureRenderer.draw(brix5Mvp, ground)
    TextureRenderer.draw(brix6Mvp, ground)
    TextureRenderer.draw(brix7Mvp, ground)
    TextureRenderer.draw(brix8Mvp, ground)
    TextureRenderer.draw(brix9Mvp, ground)
//    TextureRenderer.draw(brix10Mvp, ground)
//    TextureRenderer.draw(brix11Mvp, ground)
//    TextureRenderer.draw(brix12Mvp, ground)
//    TextureRenderer.draw(brix13Mvp, ground)
//    TextureRenderer.draw(brix14Mvp, ground)
//    TextureRenderer.draw(brix15Mvp, ground)

    val block1Mvp = new Matrix4f(projectionViewMatrix).mul(new Matrix4f().translate(-8.0f, -0.05f, 0.0f))
    val block2Mvp = new Matrix4f(projectionViewMatrix).mul(new Matrix4f().translate(8.0f, -0.05f, 0.0f))
    val block3Mvp = new Matrix4f(projectionViewMatrix).mul(new Matrix4f().translate(-3.0f, 2.95f, 0.0f))
    TextureRenderer.draw(block1Mvp, block)
    TextureRenderer.draw(block2Mvp, block)
    TextureRenderer.draw(block3Mvp, block)
  }
}
