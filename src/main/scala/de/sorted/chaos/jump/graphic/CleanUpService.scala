package de.sorted.chaos.jump.graphic

import org.lwjgl.opengl.GL11.glDeleteTextures
import org.lwjgl.opengl.GL15.glDeleteBuffers
import org.lwjgl.opengl.GL20.{glDeleteProgram, glDeleteShader}
import org.lwjgl.opengl.GL30.glDeleteVertexArrays
import org.slf4j.LoggerFactory

object CleanUpService {

  private val Log = LoggerFactory.getLogger(this.getClass)

  private var vertexBufferObjectIds = Vector.empty[Int]
  private var vertexArrayObjectIds  = Vector.empty[Int]
  private var shaderIds             = Vector.empty[Int]
  private var shaderProgramIds      = Vector.empty[Int]
  private var textureIds            = Vector.empty[Int]

  def addVertexBufferObjectId(id: Int): Unit = vertexBufferObjectIds = vertexBufferObjectIds :+ id

  def addVertexArrayObjectId(id: Int): Unit = vertexArrayObjectIds = vertexArrayObjectIds :+ id

  def addShaderIds(id: Int): Unit = shaderIds = shaderIds :+ id

  def addShaderProgramId(id: Int): Unit = shaderProgramIds = shaderProgramIds :+ id

  def addTextureId(id: Int): Unit = textureIds = textureIds :+ id

  def cleanUp(): Unit = {
    vertexBufferObjectIds.foreach(id => glDeleteBuffers(id))
    vertexArrayObjectIds.foreach(id => glDeleteVertexArrays(id))
    shaderIds.foreach(id => glDeleteShader(id))
    shaderProgramIds.foreach(id => glDeleteProgram(id))
    textureIds.foreach(id => glDeleteTextures(id))

    vertexBufferObjectIds = Vector.empty[Int]
    vertexArrayObjectIds = Vector.empty[Int]
    shaderIds = Vector.empty[Int]
    shaderProgramIds = Vector.empty[Int]
    textureIds = Vector.empty[Int]

    Log.info("Finish clean up...")
  }
}
