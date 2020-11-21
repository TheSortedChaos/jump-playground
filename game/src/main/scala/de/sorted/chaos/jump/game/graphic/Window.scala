package de.sorted.chaos.jump.game.graphic

import de.sorted.chaos.jump.game.configuration.{ Configuration, GraphicConfiguration }
import org.lwjgl.glfw.GLFW._
import org.lwjgl.glfw.{ GLFW, GLFWErrorCallback }
import org.lwjgl.opengl.GL.createCapabilities
import org.lwjgl.opengl.GL11._
import org.lwjgl.system.MemoryStack
import org.lwjgl.system.MemoryUtil.NULL

object Window {

  def create(configuration: Configuration): Long = {
    val graphicConfiguration = configuration.graphicConfiguration
    initGlfw()
    setProperties(graphicConfiguration)
    val windowId = createOpenGlWindow(graphicConfiguration)
    setKeyCallback(windowId)
    centerWindow(windowId)
    createOpenGlContext(windowId, graphicConfiguration)

    windowId
  }

  private def initGlfw(): Unit = {
    GLFWErrorCallback.createPrint(System.err).set()
    if (!glfwInit()) {
      throw new RuntimeException("GLFW wasn't able to initialized correctly.")
    }
  }

  private def createOpenGlWindow(configuration: GraphicConfiguration): Long = {
    val title    = configuration.screen.title
    val width    = configuration.screen.width
    val height   = configuration.screen.height
    val windowId = glfwCreateWindow(width, height, title, NULL, NULL)
    if (windowId == NULL) {
      throw new RuntimeException("Failed to create the GLFW windowId")
    }

    windowId
  }

  private def setProperties(configuration: GraphicConfiguration): Unit = {
    val majorVersion = configuration.opengl.majorVersion
    val minorVersion = configuration.opengl.minorVersion
    glfwWindowHint(GLFW_VISIBLE, GL_TRUE)
    glfwWindowHint(GLFW_RESIZABLE, GL_TRUE)
    glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, majorVersion)
    glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, minorVersion)
    glfwWindowHint(GLFW_OPENGL_FORWARD_COMPAT, GL_TRUE)
    glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE)
  }

  private def setKeyCallback(windowId: Long): Unit =
    glfwSetKeyCallback(
      windowId,
      (windowId, key, scancode, action, mods) =>
        if (key == GLFW_KEY_ESCAPE && action == GLFW_RELEASE) {
          glfwSetWindowShouldClose(windowId, true)
        }
    )

  private def centerWindow(windowId: Long): Unit = {
    val stack   = MemoryStack.stackPush()
    val pWidth  = stack.mallocInt(1)
    val pHeight = stack.mallocInt(1)
    glfwGetWindowSize(windowId, pWidth, pHeight)
    val vidMode = glfwGetVideoMode(GLFW.glfwGetPrimaryMonitor())
    glfwSetWindowPos(
      windowId,
      (vidMode.width() - pWidth.get(0)) / 2,
      (vidMode.height() - pHeight.get(0)) / 2
    )
  }

  private def createOpenGlContext(windowId: Long, configuration: GraphicConfiguration): Unit = {
    val vsync           = configuration.screen.vsync
    val wireframe       = configuration.opengl.wireframe
    val backfaceCulling = configuration.opengl.backfaceCulling

    glfwMakeContextCurrent(windowId)
    createCapabilities()
    glClearColor(0.3f, 0.3f, 0.3f, 1.0f)
    glfwSwapInterval(if (vsync) 1 else 0)
    glfwShowWindow(windowId)

    glEnable(GL_DEPTH_TEST)
    glEnable(GL_BLEND)
    glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA)

    if (backfaceCulling) {
      glEnable(GL_CULL_FACE)
      glCullFace(GL_BACK)
    }

    if (wireframe) {
      glPolygonMode(GL_FRONT_AND_BACK, GL_LINE) // Set Wireframe Mode
    }
  }

  def destroy(windowId: Long): Unit = {
    glfwDestroyWindow(windowId)
    glfwTerminate()
    glfwSetErrorCallback(null).free()
  }
}
