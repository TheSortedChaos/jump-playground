package de.sorted.chaos.jump.game.graphic.entity

import de.sorted.chaos.jump.game.graphic.loader.ShaderProgram

object Shader {

  val TextureShader: Int = ShaderProgram.load(
    "/shader/texture-vertex-shader.glsl",
    "/shader/texture-fragment-shader.glsl"
  )
}
