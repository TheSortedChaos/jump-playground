package de.sorted.chaos.jump.game.game

import de.sorted.chaos.jump.game.configuration.Configuration
import de.sorted.chaos.jump.game.game.entity.MovableEntity
import de.sorted.chaos.jump.game.game.pipeline.{Level, PhysicPipeline, PlayerState, RenderPipeline}
import de.sorted.chaos.jump.game.graphic.{FramesPerSecond, Window}
import org.lwjgl.glfw.GLFW.glfwWindowShouldClose

import scala.annotation.tailrec

final case class LoopState(currentTicks: Long, nextGameTick: Long, loops: Int)

final case class GameState(
  windowId: Long,
  loopState: LoopState,
  playerState: MovableEntity,
  level: Level,
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
      playerState   = MovableEntity.init,
      level         = Level.init,
      configuration = configuration
    )

    executeGameLoop(initGameState)
    Window.destroy(windowId)
  }
}
