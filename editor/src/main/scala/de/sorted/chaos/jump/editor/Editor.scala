package de.sorted.chaos.jump.editor

import java.awt.{Frame, GridLayout}

import de.sorted.chaos.jump.editor.level.{LevelSectionMenuPanel, LevelSectionPanel}
import javax.swing.JFrame

object Editor {

  def main(args: Array[String]): Unit = {

    val mainFrame = new JFrame()
    mainFrame.setTitle("Editor")
    mainFrame.setLayout(new GridLayout(1, 2))
    mainFrame.setExtendedState(Frame.MAXIMIZED_BOTH)
    mainFrame.add(new LevelSectionPanel())
    mainFrame.add(new LevelSectionMenuPanel())
    mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE)
    mainFrame.setVisible(true)
  }
}
