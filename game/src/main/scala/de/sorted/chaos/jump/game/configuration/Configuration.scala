package de.sorted.chaos.jump.game.configuration

import pureconfig.ConfigSource

// Graphic

final case class Graphic(screen: Screen, opengl: Opengl, camera: Camera) {
  private[configuration] def prettyPrint: String =
    s"""+ Graphic
       |  ${screen.prettyPrint}
       |  ${opengl.prettyPrint}
       |  ${camera.prettyPrint}""".stripMargin
}

final case class Screen(title: String, width: Int, height: Int, fullscreen: Boolean, vsync: Boolean) {
  private[configuration] def prettyPrint: String =
    s"""  + Screen
       |      - Title ................. $title
       |      - Resolution ............ ${width}x$height
       |      - Fullscreen ............ $fullscreen
       |      - V-Sync ................ $vsync""".stripMargin
}

final case class Opengl(majorVersion: Int, minorVersion: Int, wireframe: Boolean, backfaceCulling: Boolean) {
  private[configuration] def prettyPrint: String =
    s"""  + OpenGL
       |      - Version ............... $majorVersion.$minorVersion
       |      - Wireframe ............. $wireframe
       |      - Back Face Culling ..... $backfaceCulling""".stripMargin
}

final case class Camera(fieldOfView: Float, nearPlane: Float, farPlane: Float) {
  private[configuration] def prettyPrint: String =
    s"""  + Camera
       |      - Field of View ......... $fieldOfView
       |      - Near Plane ............ $nearPlane
       |      - Far Plane ............. $farPlane""".stripMargin
}

// Game

final case class Game(playerMovement: PlayerMovement) {
  private[configuration] def prettyPrint: String =
    s"""+ Game
       |  ${playerMovement.prettyPrint}""".stripMargin
}

final case class PlayerMovement(deltaX: Float, jumpHeight: Float) {
  private[configuration] def prettyPrint: String =
    s"""  + Player Movement
       |      - delta x ............... $deltaX
       |      - jump height ........... $jumpHeight""".stripMargin
}

final case class Configuration(graphic: Graphic, game: Game) {
  def prettyPrint: String =
    s"""
       |Configuration
       |${graphic.prettyPrint}
       |${game.prettyPrint}""".stripMargin
}

object Configuration {

  def load(): Configuration = {
    import pureconfig.generic.auto._
    ConfigSource.default.loadOrThrow[Configuration]
  }
}
