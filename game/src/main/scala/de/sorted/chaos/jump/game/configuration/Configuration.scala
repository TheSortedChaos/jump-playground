package de.sorted.chaos.jump.game.configuration

import pureconfig.ConfigSource

final case class Configuration(graphicConfiguration: GraphicConfiguration, gameConfiguration: GameConfiguration) {
  def prettyPrint: String =
    s"""
       |Configuration
       |${graphicConfiguration.prettyPrint}
       |${gameConfiguration.prettyPrint}""".stripMargin
}

object Configuration {

  def load(): Configuration = {
    import pureconfig.generic.auto._
    ConfigSource.default.loadOrThrow[Configuration]
  }
}
