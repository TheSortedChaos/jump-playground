package de.sorted.chaos.jump.graphic.entity

import de.sorted.chaos.jump.graphic.render.texture.TexturedEntity

object Entity {

  val TexturedCube: TexturedEntity = TexturedEntity.createFrom("/entity/cube/cube.obj", Texture.CubeTexture)

  val TexturedBrix: TexturedEntity = TexturedEntity.createFrom("/entity/brix/brix.obj", Texture.BrixTexture)
}
