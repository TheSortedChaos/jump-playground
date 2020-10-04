package de.sorted.chaos.jump

import de.sorted.chaos.jump.configuration.Configuration
import de.sorted.chaos.jump.game.MainLoop
import de.sorted.chaos.jump.graphic.Window
import org.slf4j.LoggerFactory

object Application {
  private val Log = LoggerFactory.getLogger("Application")

  def main(args: Array[String]): Unit = {
    val configuration = Configuration.load()
    Log.info("Loaded configuration... {}", configuration.prettyPrint)

    val windowId = Window.create(configuration)
    Log.info("Create OpenGL window with id = '{}'.", windowId)

    MainLoop.start(windowId)
  }
}
