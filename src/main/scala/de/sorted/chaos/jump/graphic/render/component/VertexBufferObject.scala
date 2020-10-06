package de.sorted.chaos.jump.graphic.render.component

import de.sorted.chaos.jump.graphic.CleanUpService
import org.lwjgl.BufferUtils
import org.lwjgl.opengl.GL15.{GL_ARRAY_BUFFER, glBindBuffer, glBufferData, glGenBuffers}

object VertexBufferObject {

  def create(data: Array[Float],
             drawType: Int): Int = {
    val buffer = BufferUtils.createFloatBuffer(data.length)
    buffer.put(data)
    buffer.flip()

    val id = glGenBuffers()
    glBindBuffer(GL_ARRAY_BUFFER, id)
    glBufferData(GL_ARRAY_BUFFER, buffer, drawType)

    CleanUpService.addVertexBufferObjectId(id)
    id
  }
}
