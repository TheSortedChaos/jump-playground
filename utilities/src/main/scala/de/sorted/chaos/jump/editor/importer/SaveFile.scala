package de.sorted.chaos.jump.editor.importer

import java.io.{ File, PrintWriter }

object SaveFile {

  def to(filename: String, content: String): Unit = {
    val writer = new PrintWriter(new File(filename))
    writer.write(content)
    writer.close()
  }
}
