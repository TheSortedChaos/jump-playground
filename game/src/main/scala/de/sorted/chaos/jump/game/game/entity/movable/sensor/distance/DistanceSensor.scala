package de.sorted.chaos.jump.game.game.entity.movable.sensor.distance

import de.sorted.chaos.jump.game.game.entity.movable.MovableEntity
import de.sorted.chaos.jump.game.game.pipeline.Level

object DistanceSensor {

  def getDistanceToTop(entity: MovableEntity, level: Level): Float = DistanceToSkySensor.getNearestDistance(entity, level)

  def getDistanceToBottom(entity: MovableEntity, level: Level): Float = DistanceToGroundSensor.getNearestDistance(entity, level)
}
