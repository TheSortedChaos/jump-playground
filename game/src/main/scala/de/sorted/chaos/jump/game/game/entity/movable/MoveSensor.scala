package de.sorted.chaos.jump.game.game.entity.movable

import de.sorted.chaos.jump.game.game.pipeline.Level
import org.joml.{AABBf, Rayf, Vector3f}

object MoveSensor {

  private val RayLength = 200.0f

  def shootRayToSky(entity: MovableEntity, level: Level): Float = {
    val ray = createRayToSky(entity)

    getDistanceToNearestObstacle(ray, entity, level)
  }

  def shootRayToGround(entity: MovableEntity, level: Level): Float = {
    val ray = createRayToGround(entity)

    getDistanceToNearestObstacle(ray, entity, level)
  }

  private def getDistanceToNearestObstacle(ray: Rayf, entity: MovableEntity, level: Level) =
    level.boundingBoxes
      .map(box => (box, box.intersectsRay(ray)))
      .filter(tuple => tuple._2)
      .map(tuple => getDistance(entity, tuple._1))
      .minOption
      .getOrElse(RayLength)

  private def getDistance(entity: MovableEntity, other: AABBf) = {
    val start = rayStart(entity)
    val end   = rayEnd(entity, other)

    start.distance(end)
  }

  private def rayEnd(entity: MovableEntity, boundingBox: AABBf) =
    new Vector3f(
      entity.alignment.position.x,
      boundingBox.maxY,
      entity.alignment.position.z
    )

  private def rayStart(entity: MovableEntity) =
    new Vector3f(
      entity.alignment.position.x,
      entity.alignment.position.y - entity.boundingBox.yRange,
      entity.alignment.position.z
    )

  private def createRayToSky(entity: MovableEntity) =
    new Rayf(
      entity.alignment.position.x,
      entity.alignment.position.y + entity.boundingBox.yRange,
      entity.alignment.position.z,
      entity.alignment.position.x,
      entity.alignment.position.y + RayLength,
      entity.alignment.position.z
    )

  private def createRayToGround(entity: MovableEntity) =
    new Rayf(
      entity.alignment.position.x,
      entity.alignment.position.y - entity.boundingBox.yRange,
      entity.alignment.position.z,
      entity.alignment.position.x,
      entity.alignment.position.y - RayLength,
      entity.alignment.position.z
    )
}
