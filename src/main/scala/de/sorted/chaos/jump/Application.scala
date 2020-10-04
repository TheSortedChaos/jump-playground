package de.sorted.chaos.jump

import de.sorted.chaos.jump.configuration.Configuration
import org.slf4j.LoggerFactory

object Application {
  private val Log = LoggerFactory.getLogger("Application")

  def main(args: Array[String]): Unit = {
    val configuration = Configuration.load()
    Log.info("Loading configuration... {}", configuration.prettyPrint)
  }
}
