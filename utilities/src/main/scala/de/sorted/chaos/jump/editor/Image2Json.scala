package de.sorted.chaos.jump.editor

import java.awt.image.BufferedImage
import java.io.{ File, PrintWriter }
import javax.imageio.ImageIO

final case class Color(r: Int, g: Int, b: Int)

final case class Position(x: Float, y: Float)

object Image2Json {

  private val TileSize = 5
  private val BackgroundColor = Color(255, 255, 255)

  def main(args: Array[String]): Unit = {
    val input = "/level01/level01.png"
    val output = "/Users/mwittig/development/repositories/jump-playground/game/src/main/resources/level/level-01alpha.json"
//    val input = args(0)
//    val output = args(1)

    exec(input, output)
  }

  def exec(inputFile: String, outputFile: String): Unit = {
    import io.circe.generic.auto._
    import io.circe.syntax._

    val image              = read(inputFile)
    val positionsByColorId = transform(image)
    val asJson             = positionsByColorId.asJson.spaces2
    write(outputFile, asJson)
  }

  private def transform(image: BufferedImage) = {
    val width  = image.getWidth / TileSize
    val height = image.getHeight / TileSize

    (for (y <- 0 until height;
          x <- 0 until width) yield {
      val color = image.getRGB(x * TileSize + 2, y * TileSize + 2)
      val rgb   = getColor(color)

      if (rgb == BackgroundColor) {
        None
      } else {
        val key   = f"${rgb.r}%03d${rgb.g}%03d${rgb.b}%03d"
        val value = Position(x, height - y)

        Some((key, value))
      }
    }).flatten  // remove background (None)
      .groupMap(mapEntry => mapEntry._1)(x => x._2)
  }

  private def getColor(color: Int) = {
    val r = (color >> 16) & 0xff
    val g = (color >> 8) & 0xff
    val b = color & 0xff

    Color(r, g, b)
  }

  private def read(filename: String): BufferedImage = {
    val stream = this.getClass.getResourceAsStream(filename)
    ImageIO.read(stream)
  }

  private def write(filename: String, content: String): Unit = {
    val writer = new PrintWriter(new File(filename))
    writer.write(content)
    writer.close()
  }
}
