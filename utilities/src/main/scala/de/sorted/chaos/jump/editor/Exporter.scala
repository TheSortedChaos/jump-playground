package de.sorted.chaos.jump.editor

import de.sorted.chaos.jump.domain.level.Level
import de.sorted.chaos.jump.editor.importer.{Image, SaveFile, Tile, Transformer}

object Exporter {

  final val Filename = "/Users/mwittig/development/repositories/jump-playground/game/src/main/resources/level/level-01.json"

  import io.circe._, io.circe.generic.auto._, io.circe.parser._, io.circe.syntax._

  def main(args: Array[String]): Unit = {
    val image = Image.read("/level01/level01.png")
    val tiles = Tile.getTiles(image)
    val level = Transformer.transform2(tiles)
    val json = level.asJson.spaces2
    SaveFile.to(Filename, json)

    println(json)

    val x = decode[Level](json)
    println(x)
  }
}
