package de.sorted.chaos.jump.game.game.level

import de.sorted.chaos.jump.game.graphic.render.texture.TexturedEntity
import de.sorted.chaos.jump.game.utils.TextFileReader
import de.sorted.chaos.jump.game.utils.datastructure.quadtree.{Position, QuadTree}
import org.joml.{AABBf, Matrix4f}

final case class StaticLevelEntity(modelMatrix: Matrix4f, objectType: TexturedEntity, boundingBox: AABBf)

object LevelLoader2 {

  def load(filename: String) = {
    val level = read(filename)
    val quadTree = QuadTree.init[StaticLevelEntity](2000, 2000) // todo get Size from json?

    // TodoMap wich ID == which TecturedEntity
    // level.foreachEntry((entry, x) => entry)

    println("x)")
  }

  private def read(filename: String) = {
    import io.circe.generic.auto._
    import io.circe.parser._

    val json = TextFileReader.read(filename).mkString
    decode[Map[String, List[Position]]](json) match {
      case Right(level) => level
      case Left(value)  => throw new RuntimeException(value)
    }
  }
}
