package de.sorted.chaos.jump.game.configuration

final case class GameConfiguration(playerMovement: PlayerMovement, gameLoopTiming: GameLoopTiming) {
  private[configuration] def prettyPrint: String =
    s"""+ Game
       |  ${playerMovement.prettyPrint}
       |  ${gameLoopTiming.prettyPrint}""".stripMargin
}

final case class PlayerMovement(velocityX: Float, jumpHeight: Float, turnRotationStep: Float) {
  private[configuration] def prettyPrint: String =
    s"""  + Player Movement
       |      - Velocity X ............ $velocityX
       |      - Jump Height ........... $jumpHeight
       |      - Turn Rotation Step .... $turnRotationStep""".stripMargin
}

final case class GameLoopTiming(ticksPerSecond: Int, maxFrameSkip: Int) {

  private[configuration] def prettyPrint: String =
    s"""  + Game Loop Timing
       |      - Ticks per Second....... $ticksPerSecond
       |      - Skip Ticks ............ $getSkipTicks
       |      - Max Frame Skip......... $maxFrameSkip""".stripMargin

  def getSkipTicks: Int = 1000 / ticksPerSecond
}
