package de.sorted.chaos.jump.game.game.entity.movable

object IdleMovementProcesor {

  def next(movableEntity: MovableEntity): MovableEntity =
    MovableEntity(
      alignment   = movableEntity.alignment.faceScreen,
      timings     = movableEntity.timings.updateDefaultTimer(),
      boundingBox = movableEntity.boundingBox
    )
}
