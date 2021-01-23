package de.sorted.chaos.jump.domain.level

sealed trait LevelObject

final case class StartPosition(x: Float, y: Float) extends LevelObject

final case class Block(x: Float, y: Float) extends LevelObject

final case class Floor(x: Float, y: Float) extends LevelObject

final case class Pillar(x: Float, y: Float) extends LevelObject

final case class Empty(x: Float, y: Float) extends LevelObject
