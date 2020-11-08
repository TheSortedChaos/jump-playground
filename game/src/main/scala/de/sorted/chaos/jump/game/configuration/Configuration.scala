package de.sorted.chaos.jump.game.configuration

import pureconfig.ConfigSource

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

final case class Configuration(screen: Screen, opengl: Opengl, camera: Camera) {
  def prettyPrint: String =
    s"""
       |Configuration
       |${screen.prettyPrint}
       |${opengl.prettyPrint}
       |${camera.prettyPrint}""".stripMargin
}

object Configuration {

  def load(): Configuration = {
    import pureconfig.generic.auto._
    ConfigSource.default.loadOrThrow[Configuration]
  }
}
