package de.sorted.chaos.jump.domain.level

final case class Level(startPosition: StartPosition, blocks: Vector[Block], floors: Vector[Floor], pillars: Vector[Pillar]) {

  def moveStartPosition(startPosition: StartPosition): Level =
    Level(
      startPosition,
      this.blocks,
      this.floors,
      this.pillars
    )

  def addFloor(floor: Floor): Level =
    Level(
      this.startPosition,
      this.blocks,
      this.floors :+ floor,
      this.pillars
    )

  def addBlock(block: Block): Level =
    Level(
      this.startPosition,
      this.blocks :+ block,
      this.floors,
      this.pillars
    )

  def addPillar(pillar: Pillar): Level =
    Level(
      this.startPosition,
      this.blocks,
      this.floors,
      this.pillars :+ pillar
    )
}

object Level {

  def init: Level = Level(
    startPosition = StartPosition(0, 0),
    blocks        = Vector.empty,
    floors        = Vector.empty,
    pillars       = Vector.empty
  )
}
