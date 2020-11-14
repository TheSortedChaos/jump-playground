package de.sorted.chaos.jump.game.game.pipeline

import de.sorted.chaos.jump.game.game.{ GameState, LoopState }
import de.sorted.chaos.jump.game.input.MovementInput

import scala.annotation.tailrec

object PhysicPipeline {

  def updatePhysic(gameState: GameState): GameState = {
    @tailrec
    def helper(currentGameState: GameState): GameState = {
      val windowId        = currentGameState.windowId
      val currentTicks    = currentGameState.loopState.currentTicks
      val nextGameTick    = currentGameState.loopState.nextGameTick
      val skipTicks       = currentGameState.configuration.gameConfiguration.gameLoopTiming.getSkipTicks
      val maxFrameSkip    = currentGameState.configuration.gameConfiguration.gameLoopTiming.maxFrameSkip
      val loops           = currentGameState.loopState.loops
      val newNextGameTick = nextGameTick + skipTicks
      val newLoops        = loops + 1

      val newGameState = GameState(
        windowId = windowId,
        loopState = LoopState(
          currentTicks = currentTicks,
          nextGameTick = newNextGameTick,
          loops        = newLoops
        ),
        playerState = PlayerState.modify(
          previousPlayerState = currentGameState.playerState,
          playerModifiers = MovementInput.processInput(
            windowId,
            currentGameState.configuration.gameConfiguration
          ),
          configuration = currentGameState.configuration
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
      playerState   = gameState.playerState,
      configuration = gameState.configuration
    )

    helper(initGameState)
  }
}
