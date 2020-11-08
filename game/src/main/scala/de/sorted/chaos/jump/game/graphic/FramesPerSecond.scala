package de.sorted.chaos.jump.game.graphic

class FramesPerSecond {
  private var lastTime = System.currentTimeMillis()
  private var frames   = 0

  def process(): Unit = {
    val currentTime = System.currentTimeMillis()
    val diff        = currentTime - lastTime
    if (diff >= 1000) {
      println(
        s"""|
            |frames per second: ${1000 * frames / diff.toDouble}
            |current: ${currentTime} last: ${lastTime} diff: ${currentTime - lastTime}
            |frames: $frames
            |""".stripMargin
      )
      frames   = 0
      lastTime = System.currentTimeMillis()
    }
    frames = frames + 1
  }
}

object FramesPerSecond {
  def apply(): FramesPerSecond = new FramesPerSecond()
}
