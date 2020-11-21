package de.sorted.chaos.jump.editor.level

import java.awt.GridLayout

import de.sorted.chaos.jump.editor.level.LevelSectionPanel.{Columns, Rows}
import de.sorted.chaos.jump.editor.level.components.GridButton
import javax.swing.JPanel

class LevelSectionPanel extends JPanel {
//  this.setBackground(Color.BLACK)
  private val customLayout = new GridLayout(Rows, Columns)
  customLayout.setHgap(0)
  customLayout.setVgap(0)

  this.setLayout(customLayout)

  for (row <- 0 until Rows) {
    for (column <- 0 until Columns) {
      val button = new GridButton()
      this.add(button)
    }
  }
}

object LevelSectionPanel {
  private val Rows    = 20
  private val Columns = 20
}
