package de.sorted.chaos.jump.graphic.render.component

import de.sorted.chaos.jump.graphic.CleanUpService
import org.lwjgl.opengl.GL11.GL_FLOAT
import org.lwjgl.opengl.GL15.{GL_ARRAY_BUFFER, glBindBuffer}
import org.lwjgl.opengl.GL20.glVertexAttribPointer
import org.lwjgl.opengl.GL30.{glBindVertexArray, glGenVertexArrays}

final case class InitVao(vboId: Int,
                         sizeOfDataType: Int)

object VertexArrayObject {

  def create(vboIdWithDataSize: Vector[InitVao]): Int = {
    val id = glGenVertexArrays()
    glBindVertexArray(id)
    bindData(vboIdWithDataSize)

    CleanUpService.addVertexArrayObjectId(id)
    id
  }

  private def bindData(vboIdWithDataSize: Vector[InitVao]): Unit =
    vboIdWithDataSize.zipWithIndex.foreach(bindVbo)

  private def bindVbo(tuple: (InitVao, Int)): Unit = {
    val id    = tuple._1.vboId
    val size  = tuple._1.sizeOfDataType
    val index = tuple._2
    glBindBuffer(GL_ARRAY_BUFFER, id)
    glVertexAttribPointer(index, size, GL_FLOAT, false, 0, 0)
  }
}
