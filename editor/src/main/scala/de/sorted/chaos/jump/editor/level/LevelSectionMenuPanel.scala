package de.sorted.chaos.jump.editor.level

import de.sorted.chaos.jump.editor.level.components.{ExportButton, GridCheckbox}
import javax.swing.JPanel
import javax.swing.JPopupMenu.Separator

class LevelSectionMenuPanel() extends JPanel {
  this.add(new GridCheckbox())
  this.add(new Separator())
  this.add(new ExportButton())
}
