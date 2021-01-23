package de.sorted.chaos.jump.game.game.level

import de.sorted.chaos.jump.domain.level.Level
import de.sorted.chaos.jump.game.graphic.entity.Entity
import de.sorted.chaos.jump.game.graphic.render.texture.{ TextureRenderer, TexturedEntity }
import de.sorted.chaos.jump.game.utils.TextFileReader
import de.sorted.chaos.jump.game.utils.datastructure.quadtree.{ Boundary, Data, Position, QuadTree }
import org.joml.{ AABBf, Matrix4f }

final case class LevelDataObject(modelMatrix: Matrix4f, objectType: TexturedEntity, boundingBox: AABBf)

//noinspection ScalaStyle
object LevelLoader {


  // TODO think about BoundingBox in LevelDataObject, because a Pillar shouldn't have a bounding box
  // also think about Naming LevelDataObject ?!?!
  // clean up all the stuff !!

  def load(filename: String): QuadTree[LevelDataObject] = {
    import io.circe.generic.auto._
    import io.circe.parser._
    val json = TextFileReader.read(filename).mkString
    val levelJson = decode[Level](json) match {
      case Right(level) => level
      case Left(value)  => throw new RuntimeException(value)
    }

    val quadTree = QuadTree.init[LevelDataObject](2000, 2000)

    levelJson.pillars.foreach(
      pillar =>
        quadTree.insert(
          Data(
            Position(pillar.x, pillar.y),
            LevelDataObject(
              new Matrix4f().translate(pillar.x, pillar.y, 0.0f),
              Entity.Pillar,
              new AABBf()
            )
          )
        )
    )

    levelJson.floors.foreach(
      floor =>
        quadTree.insert(
          Data(
            Position(floor.x, floor.y),
            LevelDataObject(
              new Matrix4f().translate(floor.x, floor.y, 0.0f),
              Entity.Ground,
              new AABBf()
            )
          )
        )
    )

    levelJson.blocks.foreach(
      blocks =>
        quadTree.insert(
          Data(
            Position(blocks.x, blocks.y),
            LevelDataObject(
              new Matrix4f().translate(blocks.x, blocks.y, 0.0f),
              Entity.Block,
              new AABBf()
            )
          )
        )
    )

    quadTree
  }

  def drawLevel(quadTree: QuadTree[LevelDataObject], projectionViewMatrix: Matrix4f): Unit = {
    val boundingBox = Boundary(Position(0.0f, 0.0f), Position(48.0f, 25.0f))
    val levelItems  = quadTree.query(boundingBox)

    levelItems
      .map(levelItem => (levelItem.modelMatrix, levelItem.objectType))
      .map(tuple => (new Matrix4f(projectionViewMatrix).mul(tuple._1), tuple._2))
      .foreach(tuple => TextureRenderer.draw(tuple._1, tuple._2))
  }
}
