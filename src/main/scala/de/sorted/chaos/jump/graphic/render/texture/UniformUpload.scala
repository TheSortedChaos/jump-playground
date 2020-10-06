package de.sorted.chaos.jump.graphic.render.texture

import de.sorted.chaos.jump.graphic.render.Uniform
import org.joml.Matrix4f

object UniformUpload extends Uniform {

  def upload(locations: Map[String, Int],
             modelViewPerspectiveMatrix: Matrix4f,
             textureId: Int): Unit = {
    uploadModelViewPerspectiveMatrix(locations, modelViewPerspectiveMatrix)
    activateTexture(locations, textureId)
  }

  def init(shaderProgramId: Int): Map[String, Int] =
    Set(
      createLocationFor(shaderProgramId, ModelViewPerspectiveMatrix),
      createLocationFor(shaderProgramId, TextureSampler)
    ).toMap
}
