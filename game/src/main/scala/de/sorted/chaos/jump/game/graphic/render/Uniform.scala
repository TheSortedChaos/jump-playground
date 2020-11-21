package de.sorted.chaos.jump.game.graphic.render

import org.joml.Matrix4f
import org.lwjgl.BufferUtils
import org.lwjgl.opengl.GL11.{ glBindTexture, GL_TEXTURE_2D }
import org.lwjgl.opengl.GL13.{ glActiveTexture, GL_TEXTURE0 }
import org.lwjgl.opengl.GL20.{ glGetUniformLocation, glUniform1i, glUniformMatrix4fv }

trait Uniform {

  protected val ModelViewPerspectiveMatrix = "modelViewPerspectiveMatrix"
  protected val TextureSampler             = "textureSampler"
  private val capacity                     = 16
  private val MatrixBuffer                 = BufferUtils.createFloatBuffer(capacity)

  protected def uploadModelViewPerspectiveMatrix(locations: Map[String, Int], modelViewPerspectiveMatrix: Matrix4f): Unit =
    glUniformMatrix4fv(
      locations(ModelViewPerspectiveMatrix),
      false,
      modelViewPerspectiveMatrix.get(MatrixBuffer)
    )

  protected def activateTexture(locations: Map[String, Int], textureId: Int): Unit = {
    glUniform1i(locations(TextureSampler), 0)
    glActiveTexture(GL_TEXTURE0)
    glBindTexture(GL_TEXTURE_2D, textureId)
  }

  protected def createLocationFor(shaderProgramId: Int, name: String): (String, Int) = {
    val locationId = glGetUniformLocation(shaderProgramId, name)
    (name, locationId)
  }
}
