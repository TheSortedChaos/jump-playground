package de.sorted.chaos.jump.game.game.pipeline

import de.sorted.chaos.jump.game.game.GameState
import de.sorted.chaos.jump.game.game.entity.movable.MovableEntity
import de.sorted.chaos.jump.game.game.level.{ LevelDataObject, LevelLoader }
import de.sorted.chaos.jump.game.graphic.entity.Entity
import de.sorted.chaos.jump.game.graphic.matrix.MatrixStack
import de.sorted.chaos.jump.game.graphic.render.texture.{ TextureRenderer, TexturedEntity }
import de.sorted.chaos.jump.game.utils.datastructure.quadtree.QuadTree
import org.joml.Matrix4f
import org.lwjgl.glfw.GLFW.{ glfwPollEvents, glfwSwapBuffers }
import org.lwjgl.opengl.GL11.{ glClear, GL_COLOR_BUFFER_BIT, GL_DEPTH_BUFFER_BIT }

object RenderPipeline {

  private val Hero         = Entity.Block
  private val Ground       = Entity.Ground
  private val Pillar       = Entity.Pillar
  private val Level01      = LevelPipeline.loadLevel("/level/level-01.json")
  private val Level01Alpha = LevelLoader.load("/level/level-01.json")

  def draw(gameState: GameState): Unit = {
    val windowId             = gameState.windowId
    val interpolation        = getInterpolation(gameState) // interpolation between two render states
    val configuration        = gameState.configuration
    val playerState          = gameState.playerState
    val projectionViewMatrix = MatrixStack.getProjectionViewMatrix(configuration, playerState)

    glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT)
    //   drawHero(projectionViewMatrix, playerState, Hero)
    // drawLevel(projectionViewMatrix, Ground, Pillar)
    drawLevelAlpha(Level01Alpha, projectionViewMatrix)
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

  private def drawLevel(projectionViewMatrix: Matrix4f, ground: TexturedEntity, block: TexturedEntity): Unit =
    LevelPipeline.drawLevel(projectionViewMatrix, Level01)

  private def drawLevelAlpha(quadTree: QuadTree[LevelDataObject], projectionViewMatrix: Matrix4f): Unit =
    LevelLoader.drawLevel(quadTree, projectionViewMatrix)
}
