package de.sorted.chaos.jump.game

import de.sorted.chaos.jump.game.configuration.Configuration
import de.sorted.chaos.jump.game.game.MainLoop
import de.sorted.chaos.jump.game.graphic.{ CleanUpService, Window }
import org.slf4j.LoggerFactory

object Game {

  private val Log = LoggerFactory.getLogger(this.getClass)

  def main(args: Array[String]): Unit = {
    val configuration = Configuration.load()
    Log.info(
      "Loaded configuration... {}",
      configuration.prettyPrint
    )

    val windowId = Window.create(configuration)
    Log.info("Create OpenGL window with id = '{}'.", windowId)

    MainLoop.loop(windowId, configuration)

    CleanUpService.cleanUp()
  }
}
