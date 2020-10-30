package de.sorted.chaos.jump.graphic.render.texture

import de.sorted.chaos.jump.graphic.render.component.{InitVao, VertexArrayObject, VertexBufferObject}
import org.lwjgl.opengl.GL15.GL_STATIC_DRAW
import de.sorted.chaos.wavefront.WavefrontReader
import de.sorted.chaos.wavefront.mesh.Mesh

final case class TexturedEntity(vertexArrayObjectId: Int,
                                textureId         : Int,
                                size              : Int)

object TexturedEntity {

  def createFrom(filename: String,
                 textureId: Int): TexturedEntity = {
    val mesh                = WavefrontReader.from(filename)
    val vertexArrayObjectId = createVertexArrayObject(mesh)
    val size                = mesh.vertices.length / 3

    TexturedEntity(
      vertexArrayObjectId,
      textureId,
      size
    )
  }

  def createVertexArrayObject(mesh: Mesh): Int = {
    val vertexVboId  = VertexBufferObject.create(mesh.vertices, GL_STATIC_DRAW)
    val vertexTuple  = InitVao(vertexVboId, 3)
    val textureVboId = VertexBufferObject.create(mesh.textures, GL_STATIC_DRAW)
    val textureTuple = InitVao(textureVboId, 2)
    val tuples       = Vector(vertexTuple, textureTuple)

    VertexArrayObject.create(tuples)
  }
}
