package de.sorted.chaos.jump.game.graphic.entity

import de.sorted.chaos.jump.game.graphic.render.animate.AnimatedEntity
import de.sorted.chaos.jump.game.graphic.render.texture.TexturedEntity

object Entity {

  val GroundStoneBase: TexturedEntity = TexturedEntity.createFrom(
    "/entity/ground/stone-base.obj",
    Texture.LowPolyTexture
  )

  val TexturedCube: TexturedEntity = TexturedEntity.createFrom("/entity/cube/cube.obj", Texture.CubeTexture)

  val TexturedBrix: TexturedEntity = TexturedEntity.createFrom("/entity/brix/brix.obj", Texture.BrixTexture)

  val TexturedBlock: TexturedEntity = TexturedEntity.createFrom("/entity/cube/cube.obj", Texture.LowPolyTexture)

  val walkingHero: AnimatedEntity = AnimatedEntity.createFrom(
    List(
      "/entity/hero/hero3_000001.obj",
      "/entity/hero/hero3_000002.obj",
      "/entity/hero/hero3_000003.obj",
      "/entity/hero/hero3_000004.obj",
      "/entity/hero/hero3_000005.obj",
      "/entity/hero/hero3_000006.obj",
      "/entity/hero/hero3_000007.obj",
      "/entity/hero/hero3_000008.obj",
      "/entity/hero/hero3_000009.obj",
      "/entity/hero/hero3_000010.obj",
      "/entity/hero/hero3_000011.obj",
      "/entity/hero/hero3_000012.obj",
      "/entity/hero/hero3_000013.obj",
      "/entity/hero/hero3_000014.obj",
      "/entity/hero/hero3_000015.obj",
      "/entity/hero/hero3_000016.obj",
      "/entity/hero/hero3_000017.obj",
      "/entity/hero/hero3_000018.obj",
      "/entity/hero/hero3_000019.obj",
      "/entity/hero/hero3_000020.obj",
      "/entity/hero/hero3_000021.obj",
      "/entity/hero/hero3_000022.obj",
      "/entity/hero/hero3_000023.obj",
      "/entity/hero/hero3_000024.obj"
    ),
    Texture.LowPolyTexture
  )
}
