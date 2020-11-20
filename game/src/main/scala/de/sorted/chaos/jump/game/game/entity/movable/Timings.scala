package de.sorted.chaos.jump.game.game.entity.movable

final case class AirTimer(startTimestamp: Long, startY: Float)

final case class Timings(now: Long, fallTimer: Option[AirTimer], jumpTimer: Option[AirTimer]) {

  private[entity] def updateDefaultTimer() =
    Timings(
      now       = System.currentTimeMillis(),
      fallTimer = this.fallTimer,
      jumpTimer = this.jumpTimer
    )

  private[entity] def getDeltaTime = {
    val now       = System.currentTimeMillis()
    val deltaTime = now - this.now
    val newTiming = Timings(now, this.fallTimer, this.jumpTimer)

    (deltaTime, newTiming)
  }

  private[entity] def getFallDeltaTime = {
    val now           = System.currentTimeMillis()
    val fallDeltaTIme = if (this.fallTimer.isEmpty) 0L else now - this.fallTimer.get.startTimestamp
    val newTiming = Timings(
      now       = this.now,
      fallTimer = this.fallTimer,
      jumpTimer = this.jumpTimer
    )

    (fallDeltaTIme, newTiming)
  }

  private[entity] def getJumpDeltaTime = {
    val now           = System.currentTimeMillis()
    val jumpDeltaTime = if (this.jumpTimer.isEmpty) 0L else now - this.jumpTimer.get.startTimestamp
    val newTiming = Timings(
      now       = this.now,
      fallTimer = this.fallTimer,
      jumpTimer = this.jumpTimer
    )

    (jumpDeltaTime, newTiming)
  }

  private[entity] def startFallTimer(startY: Float) =
    Timings(
      now = this.now,
      fallTimer = Some(
        AirTimer(
          startTimestamp = System.currentTimeMillis(),
          startY         = startY
        )
      ),
      jumpTimer = None
    )

  def startJumpTimer(startY: Float): Timings =
    Timings(
      now       = this.now,
      fallTimer = None,
      jumpTimer = Some(
        AirTimer(
          startTimestamp = System.currentTimeMillis(),
          startY         = startY
        )
      )
    )

  private[entity] def reset() =
    Timings(
      now       = this.now,
      fallTimer = None,
      jumpTimer = None
    )
}

object Timings {

  private[entity] def init: Timings = Timings(
    now       = System.currentTimeMillis(),
    fallTimer = None,
    jumpTimer = None
  )
}
