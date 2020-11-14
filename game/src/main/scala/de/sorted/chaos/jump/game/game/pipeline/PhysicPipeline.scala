package de.sorted.chaos.jump.game.game.pipeline

import de.sorted.chaos.jump.game.game.{ GameState, LevelState, LoopState }
import de.sorted.chaos.jump.game.input.PlayerMovement

import scala.annotation.tailrec

object PhysicPipeline {

  def updatePhysic(gameState: GameState): GameState = {
    @tailrec
    def helper(currentGameState: GameState): GameState = {
      val windowId               = currentGameState.windowId
      val gameConfig             = currentGameState.configuration.gameConfiguration
      val currentTicks           = currentGameState.loopState.currentTicks
      val nextGameTick           = currentGameState.loopState.currentTicks
      val skipTicks              = currentGameState.configuration.gameConfiguration.gameLoopTiming.getSkipTicks
      val maxFrameSkip           = currentGameState.configuration.gameConfiguration.gameLoopTiming.maxFrameSkip
      val loops                  = currentGameState.loopState.loops
      val currentPlayerAlignment = currentGameState.levelState.playerAlignment
      val nextPlayerAlignment    = PlayerMovement.processInput(windowId, gameConfig, currentPlayerAlignment)
      val newNextGameTick        = nextGameTick + skipTicks
      val newLoops               = loops + 1

      val newGameState = GameState(
        windowId = windowId,
        loopState = LoopState(
          currentTicks = currentTicks,
          nextGameTick = newNextGameTick,
          loops        = newLoops
        ),
        levelState = LevelState(
          playerAlignment = nextPlayerAlignment
        ),
        configuration = currentGameState.configuration
      )

      if (!(currentTicks > nextGameTick && loops < maxFrameSkip)) {
        newGameState
      } else {
        helper(newGameState)
      }
    }

    val initGameState = GameState(
      windowId = gameState.windowId,
      loopState = LoopState(
        currentTicks = System.currentTimeMillis(),
        nextGameTick = gameState.loopState.nextGameTick,
        loops        = 0
      ),
      levelState    = gameState.levelState,
      configuration = gameState.configuration
    )

    helper(initGameState)
  }
}
