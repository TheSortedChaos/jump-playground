package de.sorted.chaos.jump.game.graphic.loader

import java.io.File

import de.sorted.chaos.jump.game.graphic.CleanUpService
import org.lwjgl.opengl.GL11._
import org.lwjgl.opengl.GL30.glGenerateMipmap
import org.lwjgl.stb.STBImage
import org.lwjgl.system.MemoryStack

object TextureLoader {
  def load(filename: String): Int = {
    val stack         = MemoryStack.stackPush()
    val widthBuffer   = stack.mallocInt(1)
    val heightBuffer  = stack.mallocInt(1)
    val channelBuffer = stack.mallocInt(1)

    val file        = getClass.getResource(filename).getFile
    val textureFile = new File(file).getAbsolutePath
    STBImage.stbi_set_flip_vertically_on_load(true)
    val buffer = STBImage.stbi_load(
      textureFile,
      widthBuffer,
      heightBuffer,
      channelBuffer,
      4
    )
    if (buffer == null) {
      throw new IllegalArgumentException(s"Image file '$textureFile' not loaded: ${STBImage.stbi_failure_reason()}")
    }

    val width  = widthBuffer.get()
    val height = heightBuffer.get()

    val textureId = glGenTextures()
    glBindTexture(GL_TEXTURE_2D, textureId)
    glPixelStorei(GL_UNPACK_ALIGNMENT, 1)
    glTexImage2D(
      GL_TEXTURE_2D,
      0,
      GL_RGBA,
      width,
      height,
      0,
      GL_RGBA,
      GL_UNSIGNED_BYTE,
      buffer
    )

    glGenerateMipmap(GL_TEXTURE_2D)

    STBImage.stbi_image_free(buffer)

    CleanUpService.addTextureId(textureId)
    textureId
  }
}
