package de.sorted.chaos.jump.game.input

import org.joml.Vector3f

final case class PlayerModification(velocity: Vector3f, rotation: Vector3f, scale: Vector3f) {

  def setVelocity(newVelocity: Vector3f): PlayerModification =
    PlayerModification(
      velocity = newVelocity,
      rotation = this.rotation,
      scale = this.scale
    )

  def setRotation(newRotation: Vector3f): PlayerModification =
    PlayerModification(
      velocity = this.velocity,
      rotation = newRotation,
      scale = this.scale
    )

  def setScale(newScale: Vector3f): PlayerModification =
    PlayerModification(
      velocity = this.velocity,
      rotation = this.rotation,
      scale = newScale
    )
}

object PlayerModification {
  def init: PlayerModification =
    PlayerModification(
      velocity = new Vector3f(0.0f, 0.0f, 0.0f),
      rotation = new Vector3f(0.0f, 0.0f, 0.0f),
      scale = new Vector3f(1.0f, 1.0f, 1.0f)
    )
}
