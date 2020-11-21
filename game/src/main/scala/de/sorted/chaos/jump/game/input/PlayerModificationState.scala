package de.sorted.chaos.jump.game.input

import de.sorted.chaos.jump.game.input.Direction.{ Direction, TO_SCREEN }
import de.sorted.chaos.jump.game.input.Velocity.{ STAND, Velocity }

object Direction extends Enumeration {
  type Direction = Value
  val LEFT, RIGHT, TO_SCREEN = Value
}

object Velocity extends Enumeration {
  type Velocity = Value
  val WALK, RUN, STAND = Value
}

final case class PlayerModificationState(direction: Direction, velocity: Velocity, jump: Boolean)

object PlayerModificationState {
  def idle: PlayerModificationState =
    PlayerModificationState(direction = TO_SCREEN, velocity = STAND, jump = false)
}
