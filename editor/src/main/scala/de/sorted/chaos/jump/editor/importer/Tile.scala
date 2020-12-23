package de.sorted.chaos.jump.editor.importer

import java.awt.image.BufferedImage

final case class Position(x: Float, y: Float)

final case class Color(r: Int, g: Int, b: Int)

/**
  * a tile is 10 pixels x 10 pixels
  */
final case class Tile(position: Position, color: Color)

object Tile {

  private val TileSize = 10

  def getTiles(image: BufferedImage): Vector[Tile] = {
    val width  = image.getWidth / TileSize
    val height = image.getHeight / TileSize
    val result =
      for (y <- 0 until height;
           x <- 0 until width) yield {
        val color = image.getRGB(x * 10 + 5, y * 10 + 5)
        val rgb   = getColor(color)

        Tile(Position(x, height - y), rgb)
      }

    result.toVector
  }

  private def getColor(color: Int) = {
    val a = (color >> 24) & 0xff
    val r = (color >> 16) & 0xff
    val g = (color >> 8) & 0xff
    val b = color & 0xff

    Color(r, g, b)
  }

}
