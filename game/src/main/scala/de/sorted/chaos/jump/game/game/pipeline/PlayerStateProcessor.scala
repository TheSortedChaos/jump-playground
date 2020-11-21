package de.sorted.chaos.jump.game.game.pipeline

import de.sorted.chaos.jump.game.configuration.Configuration
import de.sorted.chaos.jump.game.game.entity.movable.{
  HorizontalMovementProcessor,
  IdleMovementProcesor,
  MovableEntity,
  VerticalMovementProcessor
}
import de.sorted.chaos.jump.game.input.{ Direction, PlayerModificationState, Velocity }

object PlayerStateProcessor {

  def nextPlayerSate(
      previousPlayerState: MovableEntity,
      level: Level,
      playerModifiers: PlayerModificationState,
      configuration: Configuration
  ): MovableEntity = {

    val velocityX = configuration.gameConfiguration.playerMovement.velocityX
    val velocityY = configuration.gameConfiguration.playerMovement.velocityY

    val horizontal = (playerModifiers.direction, playerModifiers.velocity) match {
      case (Direction.LEFT, Velocity.WALK)  => HorizontalMovementProcessor.next(previousPlayerState, level, -velocityX)
      case (Direction.RIGHT, Velocity.WALK) => HorizontalMovementProcessor.next(previousPlayerState, level, velocityX)
      case _                                => IdleMovementProcesor.next(previousPlayerState)
    }

    val vertical = VerticalMovementProcessor.next(
      horizontal,
      level,
      velocityY,
      playerModifiers.jump
    )

    vertical
  }
}
