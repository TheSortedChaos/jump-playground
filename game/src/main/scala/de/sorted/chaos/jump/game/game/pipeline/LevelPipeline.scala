package de.sorted.chaos.jump.game.game.pipeline

import de.sorted.chaos.jump.domain.level.Level
import de.sorted.chaos.jump.game.graphic.entity.Entity
import de.sorted.chaos.jump.game.graphic.render.texture.{ TextureRenderer, TexturedEntity }
import de.sorted.chaos.jump.game.utils.TextFileReader
import org.joml.Matrix4f

final case class PipelineItem(entity: TexturedEntity, modelMatrices: Vector[Matrix4f])

object LevelPipeline {

  def loadLevel(filename: String): Vector[PipelineItem] = {
    import io.circe.generic.auto._
    import io.circe.parser._

    val json = TextFileReader.read(filename).mkString
    val level = decode[Level](json) match {
      case Right(level) => level
      case Left(value)  => throw new RuntimeException(value)
    }

    val blockModelMatrices   = level.blocks.map(position  => new Matrix4f().translate(position.x, position.y, 0.0f))
    val floorModelMatrices   = level.floors.map(position  => new Matrix4f().translate(position.x, position.y, 0.0f))
    val pillarsModelMatrices = level.pillars.map(position => new Matrix4f().translate(position.x, position.y, 0.0f))

    Vector(
      PipelineItem(Entity.Block, blockModelMatrices),
      PipelineItem(Entity.Ground, floorModelMatrices),
      PipelineItem(Entity.Pillar, pillarsModelMatrices)
    )
  }

  def drawLevel(projectionViewMatrix: Matrix4f, items: Vector[PipelineItem]): Unit =
    items.foreach(
      pipeline =>
        pipeline.modelMatrices
          .map(m => new Matrix4f(projectionViewMatrix).mul(m))
          .foreach(mvp => TextureRenderer.draw(mvp, pipeline.entity))
    )
}
