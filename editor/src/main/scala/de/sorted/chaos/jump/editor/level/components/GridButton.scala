package de.sorted.chaos.jump.editor.level.components

import java.awt.{Color, Dimension}

import javax.swing.JButton
import javax.swing.border.LineBorder

//noinspection ScalaStyle
class GridButton extends JButton {

  private val customBorder = new LineBorder(Color.BLUE, 1)
  this.setBorder(customBorder)
  this.setBackground(Color.BLACK)
  this.setOpaque(true)
  this.addActionListener(event => this.setBackground(Color.GRAY))

  @Override
  override def getPreferredSize: Dimension = new Dimension(10, 10)
}
