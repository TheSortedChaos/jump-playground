package de.sorted.chaos.jump.game.configuration

final case class GameConfiguration(playerMovement: PlayerMovement, gameLoopTiming: GameLoopTiming) {
  private[configuration] def prettyPrint: String =
    s"""+ Game
       |  ${playerMovement.prettyPrint}
       |  ${gameLoopTiming.prettyPrint}""".stripMargin
}

final case class PlayerMovement(velocityX: Float, velocityY: Float, turnRotationStep: Float, gravity: Float) {
  private[configuration] def prettyPrint: String =
    s"""  + Player Movement
       |      - Velocity X ............ $velocityX
       |      - Velocity Y ............ $velocityY
       |      - Turn Rotation Step .... $turnRotationStep
       |      - Gravity ............... $gravity""".stripMargin
}

final case class GameLoopTiming(ticksPerSecond: Int, maxFrameSkip: Int) {

  private[configuration] def prettyPrint: String =
    s"""  + Game Loop Timing
       |      - Ticks per Second....... $ticksPerSecond
       |      - Skip Ticks ............ $getSkipTicks
       |      - Max Frame Skip......... $maxFrameSkip""".stripMargin

  def getSkipTicks: Int = 1000 / ticksPerSecond
}
