package de.sorted.chaos.jump.game.game

import de.sorted.chaos.jump.game.configuration.Configuration
import de.sorted.chaos.jump.game.game.pipeline.{ PhysicPipeline, RenderPipeline }
import de.sorted.chaos.jump.game.graphic.{ FramesPerSecond, Window }
import de.sorted.chaos.jump.game.input.PlayerAlignment
import org.joml.Vector3f
import org.lwjgl.glfw.GLFW.glfwWindowShouldClose

import scala.annotation.tailrec

final case class LoopState(currentTicks: Long, nextGameTick: Long, loops: Int)

final case class LevelState(playerAlignment: PlayerAlignment)

final case class GameState(
    windowId: Long,
    loopState: LoopState,
    levelState: LevelState,
    configuration: Configuration
)

object MainLoop {

  val Fps = new FramesPerSecond()

  /*
    In case of wondering. This idea of a game loop is taken from:
    https://dewitters.com/dewitters-gameloop/ - "Constant Game Speed
    independent of Variable FPS", but instead of using loops it's
    implemented recursively. Because it's tail recursive, the compiler
    makes a loop out of it. The 'advantage' of having immutable
    objects remains.
   */
  def loop(windowId: Long, configuration: Configuration): Unit = {
    @tailrec
    def executeGameLoop(currentGameState: GameState): GameState = {
      val windowId      = currentGameState.windowId
      val nextGameState = PhysicPipeline.updatePhysic(currentGameState)
      RenderPipeline.draw(nextGameState)

      if (glfwWindowShouldClose(windowId)) {
        nextGameState
      } else {
        Fps.process()
        executeGameLoop(nextGameState)
      }
    }

    val initGameState = GameState(
      windowId = windowId,
      loopState = LoopState(
        currentTicks = 0,
        nextGameTick = System.currentTimeMillis(),
        loops        = 0
      ),
      levelState = LevelState(
        playerAlignment = PlayerAlignment(
          offset   = new Vector3f(),
          rotation = new Vector3f(),
          scale    = new Vector3f(1.0f, 1.0f, 1.0f)
        )
      ),
      configuration = configuration
    )

    executeGameLoop(initGameState)
    Window.destroy(windowId)
  }
}
