package de.sorted.chaos.jump.graphic.render.animate

import de.sorted.chaos.jump.graphic.render.texture.TexturedEntity
import de.sorted.chaos.wavefront.WavefrontReader

final case class AnimatedEntity(currentStep: Int,
                                vertexArrayObjectIds: List[Int],
                                textureId           : Int,
                                size                : List[Int]) {

  def nextStep: AnimatedEntity = {
    val newNextStep = if (this.currentStep == vertexArrayObjectIds.size - 1) 0 else this.currentStep + 1

    AnimatedEntity(
      newNextStep,
      this.vertexArrayObjectIds,
      this.textureId,
      this.size)
  }
}

object AnimatedEntity {

  def createFrom(filenames: List[String],
                 textureId: Int): AnimatedEntity = {
    val tuple                         = filenames
      .map(filename => WavefrontReader.from(filename))
      .map(mesh => (TexturedEntity.createVertexArrayObject(mesh), mesh.vertices.length / 3))
    val (vertexArrayObjectIds, sizes) = tuple.unzip

    AnimatedEntity(
      0,
      vertexArrayObjectIds,
      textureId,
      sizes)
  }
}
