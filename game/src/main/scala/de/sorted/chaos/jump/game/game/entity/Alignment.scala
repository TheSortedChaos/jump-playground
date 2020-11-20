package de.sorted.chaos.jump.game.game.entity

import de.sorted.chaos.jump.game.game.entity.Alignment.{ AngleForLeftDirection, AngleForRightDirection }
import org.joml.{ Matrix4f, Vector3f }

final case class Alignment(position: Vector3f, rotation: Vector3f, scale: Vector3f) {

  private[entity] def getModelMatrix =
    new Matrix4f()
      .translation(this.position)
      .rotateX(Math.toRadians(this.rotation.x).toFloat)
      .rotateY(Math.toRadians(this.rotation.y).toFloat)
      .rotateZ(Math.toRadians(this.rotation.z).toFloat)
      .scale(this.scale)

  private[entity] def updatePositionX(newXPosition: Float) =
    Alignment(
      position = new Vector3f(newXPosition, this.position.y, this.position.z),
      rotation = new Vector3f(this.rotation.x, this.rotation.y, this.rotation.z),
      scale    = new Vector3f(this.scale.x, this.scale.y, this.scale.z)
    )

  private[entity] def updatePositionY(newYPosition: Float) =
    Alignment(
      position = new Vector3f(this.position.x, newYPosition, this.position.z),
      rotation = new Vector3f(this.rotation.x, this.rotation.y, this.rotation.z),
      scale    = new Vector3f(this.scale.x, this.scale.y, this.scale.z)
    )

  private[entity] def turnLeft =
    if (this.rotation.y == AngleForLeftDirection) {
      this
    } else {
      Alignment(
        position = new Vector3f(this.position.x, this.position.y, this.position.z),
        rotation = new Vector3f(this.rotation.x, AngleForLeftDirection, this.rotation.z),
        scale    = new Vector3f(this.scale.x, this.scale.y, this.scale.z)
      )
    }

  private[entity] def turnRight =
    if (this.rotation.y == AngleForRightDirection) {
      this
    } else {
      Alignment(
        position = new Vector3f(this.position.x, this.position.y, this.position.z),
        rotation = new Vector3f(this.rotation.x, AngleForRightDirection, this.rotation.z),
        scale    = new Vector3f(this.scale.x, this.scale.y, this.scale.z)
      )
    }

  private[entity] def faceScreen =
    if (this.rotation.y == 0.0f) {
      this
    } else {
      Alignment(
        position = new Vector3f(this.position.x, this.position.y, this.position.z),
        rotation = new Vector3f(this.rotation.x, 0.0f, this.rotation.z),
        scale    = new Vector3f(this.scale.x, this.scale.y, this.scale.z)
      )
    }
}

object Alignment {

  private val AngleForLeftDirection  = -90.0f
  private val AngleForRightDirection = 90.0f

  private[entity] def init: Alignment = Alignment(
    position = new Vector3f(0.0f, 15.0f, 0.0f),
    rotation = new Vector3f(0.0f, 0.0f, 0.0f),
    scale    = new Vector3f(1.0f, 1.0f, 1.0f)
  )
}
