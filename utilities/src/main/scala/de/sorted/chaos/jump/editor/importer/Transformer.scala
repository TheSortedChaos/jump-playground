package de.sorted.chaos.jump.editor.importer

import de.sorted.chaos.jump.domain.level._

import scala.annotation.tailrec


//noinspection ScalaStyle
object Transformer {

  private val FloorColor         = Color(160, 160, 160)
  private val BlockColor         = Color(140, 140, 140)
  private val PillarColor        = Color(160, 100, 40)
  private val StartPositionColor = Color(255, 255, 0)

  def transform2(tiles: Vector[Tile]): Level = {
    @tailrec
    def helper(remaining: Vector[Tile], level: Level): Level =
      if (remaining.isEmpty) {
        level
      } else {
        val newLevel = remaining.head match {
          case Tile(position, FloorColor)         => level.addFloor(Floor(position.x, position.y))
          case Tile(position, BlockColor)         => level.addBlock(Block(position.x, position.y))
          case Tile(position, PillarColor)        => level.addPillar(Pillar(position.x, position.y))
          case Tile(position, StartPositionColor) => level.moveStartPosition(StartPosition(position.x, position.y))
          case _                                  => level
        }

        helper(remaining.tail, newLevel)
      }

    helper(tiles, Level.init)
  }
}
