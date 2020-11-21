package de.sorted.chaos.jump.game.graphic.render.animate

import de.sorted.chaos.jump.game.graphic.entity.Shader
import de.sorted.chaos.jump.game.graphic.render.texture.UniformUpload
import org.joml.Matrix4f
import org.lwjgl.opengl.GL11.{ glDrawArrays, GL_TRIANGLES }
import org.lwjgl.opengl.GL20
import org.lwjgl.opengl.GL20.{ glEnableVertexAttribArray, glUseProgram }
import org.lwjgl.opengl.GL30.glBindVertexArray

object AnimationRenderer {
  private val ShaderProgramId = Shader.TextureShader
  private val UploadLocations = UniformUpload.init(ShaderProgramId)

  def draw(mvp: Matrix4f, animatedEntity: AnimatedEntity): Unit = {

    val step                = animatedEntity.currentStep
    val vertexArrayObjectId = animatedEntity.vertexArrayObjectIds(step)
    val size                = animatedEntity.size(step)
    // TODO only one vertexArrayObjectId with data from .. to for animation step

    glBindVertexArray(vertexArrayObjectId)
    glUseProgram(ShaderProgramId)
    enableAttribArray()

    UniformUpload.upload(UploadLocations, mvp, animatedEntity.textureId)

    glDrawArrays(GL_TRIANGLES, 0, size)
    disableAttribArray()
    glBindVertexArray(0)
    glUseProgram(0)
  }

  private def enableAttribArray(): Unit = {
    glEnableVertexAttribArray(0)
    glEnableVertexAttribArray(1)
  }

  private def disableAttribArray(): Unit = {
    GL20.glDisableVertexAttribArray(0)
    GL20.glDisableVertexAttribArray(1)
  }
}
