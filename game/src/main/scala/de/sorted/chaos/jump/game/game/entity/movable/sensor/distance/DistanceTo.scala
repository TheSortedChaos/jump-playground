package de.sorted.chaos.jump.game.game.entity.movable.sensor.distance

import de.sorted.chaos.jump.game.game.entity.movable.MovableEntity
import de.sorted.chaos.jump.game.game.entity.movable.sensor.distance.DistanceToGroundSensor.{RayLength, createRays, getDistance}
import de.sorted.chaos.jump.game.game.pipeline.Level
import org.joml.{AABBf, Rayf, Vector3f}

trait DistanceTo {

  protected val RayLength = 200.0f
  protected val Delta     = 0.1f

  def getNearestDistance(entity: MovableEntity, level: Level): Float = {
    val rays = createRays(entity)

    level.boundingBoxes
      .flatMap(box => rays.map(ray => (box, ray, box.intersectsRay(ray))))
      .filter(tuple => tuple._3)
      .map(tuple => getDistance(tuple._2, tuple._1))
      .minOption
      .getOrElse(RayLength)
  }

  protected def getDistance(ray: Rayf, boundingBox: AABBf): Float = {
    val start = rayStart(ray)
    val end   = rayEnd(ray, boundingBox)

    start.distance(end)
  }

  protected def rayEnd(ray: Rayf, boundingBox: AABBf): Vector3f

  protected def rayStart(ray: Rayf): Vector3f =
    new Vector3f(
      ray.oX,
      ray.oY,
      ray.oZ
    )

  protected def createRays(entity: MovableEntity): Vector[Rayf]
}
