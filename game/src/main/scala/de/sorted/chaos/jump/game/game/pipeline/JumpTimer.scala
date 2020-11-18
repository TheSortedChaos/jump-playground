package de.sorted.chaos.jump.game.game.pipeline

final case class JumpTimer(timestamp: Long, jumpStartTimestamp: Long, jumpStartY: Float, jumpAvailable: Boolean)

object JumpTimer {

  def init: JumpTimer = JumpTimer(
    timestamp          = 0L,
    jumpStartTimestamp = 0L,
    jumpStartY         = 0.0f,
    jumpAvailable      = true
  )
}
