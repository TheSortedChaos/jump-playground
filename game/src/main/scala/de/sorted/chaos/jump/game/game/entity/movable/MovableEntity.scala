package de.sorted.chaos.jump.game.game.entity.movable

import de.sorted.chaos.jump.game.game.entity.{ Alignment, BoundingBox }
import org.joml.Matrix4f

final case class MovableEntity(alignment: Alignment, timings: Timings, boundingBox: BoundingBox) {

  def getModelMatrix: Matrix4f = alignment.getModelMatrix

  def resetTimings: MovableEntity =
    MovableEntity(
      alignment   = this.alignment,
      timings     = this.timings.reset(),
      boundingBox = this.boundingBox
    )

  def startFallTiming: MovableEntity =
    MovableEntity(
      alignment   = this.alignment,
      timings     = this.timings.reset().startFallTimer(this.alignment.position.y),
      boundingBox = this.boundingBox
    )

  def resetDefaultTimer: MovableEntity =
    MovableEntity(
      alignment   = this.alignment,
      timings     = this.timings.updateDefaultTimer(),
      boundingBox = this.boundingBox
    )
}

object MovableEntity {

  def init: MovableEntity = MovableEntity(
    alignment   = Alignment.init,
    timings     = Timings.init,
    boundingBox = BoundingBox.initDefault
  )
}
