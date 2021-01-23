package de.sorted.chaos.jump.editor.importer

import java.awt.image.BufferedImage

import javax.imageio.ImageIO

object Image {

  def read(filename: String): BufferedImage = {
    val stream = this.getClass.getResourceAsStream(filename)
    ImageIO.read(stream)
  }
}
