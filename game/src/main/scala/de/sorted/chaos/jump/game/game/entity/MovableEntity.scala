package de.sorted.chaos.jump.game.game.entity

import de.sorted.chaos.jump.game.game.pipeline.Level
import org.joml.{ AABBf, Matrix4f, Rayf, Vector3f }

final case class MovableEntity(alignment: Alignment, timings: Timings, boundingBox: BoundingBox) {

  def getModelMatrix: Matrix4f = alignment.getModelMatrix

//  def getBoundingBox: AABBf =
//    new AABBf(
//      this.alignment.position.x - this.boundingBox.xRange,
//      this.alignment.position.y - this.boundingBox.yRange,
//      this.alignment.position.z - this.boundingBox.zRange,
//      this.alignment.position.x + this.boundingBox.xRange,
//      this.alignment.position.y + this.boundingBox.yRange,
//      this.alignment.position.z + this.boundingBox.zRange
//    )

  def idle: MovableEntity =
    MovableEntity(
      alignment   = this.alignment.faceScreen,
      timings     = timings.updateDefaultTimer(),
      boundingBox = this.boundingBox
    )

  def moveHorizontal(level: Level, horizontalVelocity: Float): MovableEntity = {
    val lastPosition           = this.alignment.position.x
    val (deltaTime, newTiming) = this.timings.getDeltaTime
    val newPosition            = lastPosition + (horizontalVelocity / 1000.0f * deltaTime)
    val newAlignment = if (horizontalVelocity < 0.0f) {
      this.alignment.turnLeft.updatePositionX(newPosition)
    } else {
      this.alignment.turnRight.updatePositionX(newPosition)
    }
    val boundingBox = MovableEntity.getBoundingBox(newAlignment, this.boundingBox)

    if (level.entityIntersectWithBoundingBox(boundingBox)) {
      MovableEntity(
        alignment   = this.alignment,
        timings     = newTiming,
        boundingBox = this.boundingBox
      )
    } else {
      MovableEntity(
        alignment   = newAlignment,
        timings     = newTiming,
        boundingBox = this.boundingBox
      )
    }
  }

  def jump(level: Level, gravity: Float, velocityY: Float): MovableEntity = {
    val availableHeight = MovableEntity.sendRayToSky(level, this)

    println(s"availableHeight = $availableHeight")

    if (availableHeight <= 0.1f) {
      MovableEntity(
        alignment   = this.alignment,
        timings     = this.timings.reset(),
        boundingBox = this.boundingBox
      )
    } else {
      println(s"jumpTimer empty? ${this.timings.jumpTimer.isEmpty}")
      val (deltaJumpTime, newTiming) = if (this.timings.jumpTimer.isEmpty) {
        (0L, this.timings.startJumpTimer(this.alignment.position.y))
      } else {
        this.timings.getJumpDeltaTime
      }
      val jumpStartY   = newTiming.jumpTimer.get.startY
      val newPosition  = jumpStartY + velocityY / 1000.0f * deltaJumpTime - (gravity / 1000000.0f / 2.0f * deltaJumpTime * deltaJumpTime)
      val newAlignment = this.alignment.updatePositionY(newPosition)

      val timeToMaxHeight = velocityY / gravity / 2.0f * 1000000

      println(s"timeToMaxHeight = $timeToMaxHeight deltaJumpTime = ${deltaJumpTime}")

      if (deltaJumpTime > timeToMaxHeight) {
        MovableEntity(
          alignment   = newAlignment,
          timings     = newTiming.reset().startFallTimer(newAlignment.position.y),
          boundingBox = this.boundingBox
        )
      } else {
        MovableEntity(
          alignment   = newAlignment,
          timings     = newTiming,
          boundingBox = this.boundingBox
        )
      }
    }
  }

  def fall(level: Level, gravity: Float): MovableEntity = {
    val fallHeight = MovableEntity.sendRayToGround(level, this)

    if (fallHeight.getOrElse(0.1f) <= 0.1f) {
      MovableEntity(
        alignment   = this.alignment,
        timings     = this.timings.reset(),
        boundingBox = this.boundingBox
      )
    } else {
      val (fallDeltaTime, newTiming) = if (this.timings.fallTimer.isEmpty) {
        (0L, this.timings.startFallTimer(this.alignment.position.y))
      } else {
        this.timings.getFallDeltaTime
      }
      val fallStartY   = newTiming.fallTimer.get.startY
      val newPosition  = fallStartY - (gravity / 1000000.0f / 2.0f * fallDeltaTime * fallDeltaTime)
      val newAlignment = this.alignment.updatePositionY(newPosition)

      MovableEntity(
        alignment   = newAlignment,
        timings     = newTiming,
        boundingBox = this.boundingBox
      )
    }
  }
}

object MovableEntity {

  def init: MovableEntity = MovableEntity(
    alignment   = Alignment.init,
    timings     = Timings.init,
    boundingBox = BoundingBox.initDefault
  )

  private def getBoundingBox(alignment: Alignment, boundingBox: BoundingBox) =
    new AABBf(
      alignment.position.x - boundingBox.xRange,
      alignment.position.y - boundingBox.yRange,
      alignment.position.z - boundingBox.zRange,
      alignment.position.x + boundingBox.xRange,
      alignment.position.y + boundingBox.yRange,
      alignment.position.z + boundingBox.zRange
    )

  private def sendRayToGround(level: Level, movableEntity: MovableEntity) = {
    val ray = new Rayf(
      movableEntity.alignment.position.x,
      movableEntity.alignment.position.y - movableEntity.boundingBox.yRange,
      movableEntity.alignment.position.z,
      movableEntity.alignment.position.x,
      movableEntity.alignment.position.y - 500.0f,
      movableEntity.alignment.position.z
    )

    level.boundingBoxes
      .map(box => (box, box.intersectsRay(ray)))
      .filter(tuple => tuple._2)
      .map(
        tuple =>
          new Vector3f(
            movableEntity.alignment.position.x,
            movableEntity.alignment.position.y - movableEntity.boundingBox.yRange,
            movableEntity.alignment.position.z
          ).distance(
            movableEntity.alignment.position.x,
            tuple._1.maxY,
            movableEntity.alignment.position.z
          )
      )
      .minOption
  }

  private def sendRayToSky(level: Level, movableEntity: MovableEntity) = {
    val ray = new Rayf(
      movableEntity.alignment.position.x,
      movableEntity.alignment.position.y + movableEntity.boundingBox.yRange,
      movableEntity.alignment.position.z,
      movableEntity.alignment.position.x,
      movableEntity.alignment.position.y + 500.0f,
      movableEntity.alignment.position.z
    )

    level.boundingBoxes
      .map(box => (box, box.intersectsRay(ray)))
      .filter(tuple => tuple._2)
      .map(
        tuple =>
          new Vector3f(
            movableEntity.alignment.position.x,
            movableEntity.alignment.position.y + movableEntity.boundingBox.yRange,
            movableEntity.alignment.position.z
          ).distance(
            movableEntity.alignment.position.x,
            tuple._1.maxY,
            movableEntity.alignment.position.z
          )
      )
      .minOption
      .getOrElse(500.0f)
  }
}
