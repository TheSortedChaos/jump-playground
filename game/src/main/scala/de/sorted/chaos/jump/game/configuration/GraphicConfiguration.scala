package de.sorted.chaos.jump.game.configuration

final case class GraphicConfiguration(screen: Screen, opengl: Opengl, camera: Camera) {
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
