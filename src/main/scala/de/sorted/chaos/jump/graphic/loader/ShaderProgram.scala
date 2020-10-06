package de.sorted.chaos.jump.graphic.loader

import de.sorted.chaos.jump.graphic.CleanUpService
import de.sorted.chaos.jump.utils.TextFileReader
import org.lwjgl.opengl.GL20._
import org.lwjgl.opengl.GL11.GL_FALSE

object ShaderProgram {
  def load(vertexShaderFile: String,
           fragmentShaderFile: String): Int = {
    val vertexShaderCode   = TextFileReader.read(vertexShaderFile)
    val fragmentShaderCode = TextFileReader.read(fragmentShaderFile)
    val id               = glCreateProgram()
    val vertexShaderId   = compile(id, vertexShaderCode, GL_VERTEX_SHADER)
    val fragmentShaderId = compile(id, fragmentShaderCode, GL_FRAGMENT_SHADER)

    attachShaders(id, vertexShaderId, fragmentShaderId)
    linkProgram(id)
    validateProgram(id)
    detachShaders(id, vertexShaderId, fragmentShaderId)

    CleanUpService.addShaderIds(vertexShaderId)
    CleanUpService.addShaderIds(fragmentShaderId)
    CleanUpService.addShaderProgramId(id)

    id
  }

  private def detachShaders(programId: Int,
                            vertexShaderId: Int,
                            fragmentShaderId: Int): Unit = {
    glDetachShader(programId, vertexShaderId)
    glDetachShader(programId, fragmentShaderId)
  }

  private def validateProgram(programId: Int): Unit = {
    glValidateProgram(programId)
    if (glGetProgrami(programId, GL_VALIDATE_STATUS) == GL_FALSE) {
      throw new RuntimeException("Validating shader failed: \n" + glGetProgramInfoLog(programId))
    }
  }

  private def linkProgram(programId: Int): Unit = {
    glLinkProgram(programId)
    if (glGetProgrami(programId, GL_LINK_STATUS) == GL_FALSE) {
      throw new RuntimeException("Linking shader failed: \n" + glGetProgramInfoLog(programId))
    }
  }

  private def attachShaders(programId: Int,
                            vertexShaderId: Int,
                            fragmentShaderId: Int): Unit = {
    glAttachShader(programId, vertexShaderId)
    glAttachShader(programId, fragmentShaderId)
  }

  private def compile(programId: Int,
                      shaderCode: Vector[String],
                      shaderType: Int) = {
    val shaderId = glCreateShader(shaderType)
    if (shaderId == 0) {
      throw new RuntimeException("Something went wrong during init shaderId.")
    }

    val sourceCode = shaderCode.filter(item => item != "").mkString("\n")
    glShaderSource(shaderId, sourceCode)
    glCompileShader(shaderId)

    if (glGetShaderi(shaderId, GL_COMPILE_STATUS) == GL_FALSE) {
      throw new RuntimeException("Compiling shader failed: \n" + glGetShaderInfoLog(shaderId))
    } else {
      shaderId
    }
  }
}
