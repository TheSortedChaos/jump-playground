package de.sorted.chaos.jump.graphic.entity

import de.sorted.chaos.jump.graphic.loader.ShaderProgram

object Shader {

  val TextureShader: Int = ShaderProgram.load(
    "/shader/texture-vertex-shader.glsl",
    "/shader/texture-fragment-shader.glsl"
  )
}
