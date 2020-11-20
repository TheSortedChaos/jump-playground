package de.sorted.chaos.jump.game.game.entity.movable

import de.sorted.chaos.jump.game.game.pipeline.Level
import org.joml.{ AABBf, Rayf, Vector3f }

object DistanceSensor {

  private val RayLength = 200.0f
  private val Delta     = 0.1f

  def shootRayToSky(entity: MovableEntity, level: Level): Float = {
    val ray = createRayToSky(entity)

    getDistanceToNearestObstacle(ray, entity, level)
  }

  def shootRayToGround(entity: MovableEntity, level: Level): Float = {
    val ray = createRayToGround(entity)

    getDistanceToNearestObstacle(ray, entity, level)
  }

  private def getDistanceToNearestObstacle(rays: Vector[Rayf], entity: MovableEntity, level: Level) =
    level.boundingBoxes
      .flatMap(box => rays.map(ray => (box, box.intersectsRay(ray))))
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
    Vector(
      new Rayf(
        entity.alignment.position.x - entity.boundingBox.xRange + Delta,
        entity.alignment.position.y + entity.boundingBox.yRange,
        entity.alignment.position.z - entity.boundingBox.zRange + Delta,
        entity.alignment.position.x - entity.boundingBox.xRange + Delta,
        entity.alignment.position.y + RayLength,
        entity.alignment.position.z - entity.boundingBox.zRange + Delta
      ),
      new Rayf(
        entity.alignment.position.x,
        entity.alignment.position.y + entity.boundingBox.yRange,
        entity.alignment.position.z,
        entity.alignment.position.x,
        entity.alignment.position.y + RayLength,
        entity.alignment.position.z
      ),
      new Rayf(
        entity.alignment.position.x + entity.boundingBox.xRange - Delta,
        entity.alignment.position.y + entity.boundingBox.yRange,
        entity.alignment.position.z + entity.boundingBox.zRange - Delta,
        entity.alignment.position.x + entity.boundingBox.xRange - Delta,
        entity.alignment.position.y + RayLength,
        entity.alignment.position.z + entity.boundingBox.zRange - Delta
      )
    )

  private def createRayToGround(entity: MovableEntity) =
    Vector(
      new Rayf(
        entity.alignment.position.x - entity.boundingBox.xRange + Delta,
        entity.alignment.position.y - entity.boundingBox.yRange,
        entity.alignment.position.z - entity.boundingBox.zRange + Delta,
        entity.alignment.position.x - entity.boundingBox.xRange + Delta,
        entity.alignment.position.y - RayLength,
        entity.alignment.position.z - entity.boundingBox.zRange + Delta
      ),
      new Rayf(
        entity.alignment.position.x,
        entity.alignment.position.y - entity.boundingBox.yRange,
        entity.alignment.position.z,
        entity.alignment.position.x,
        entity.alignment.position.y - RayLength,
        entity.alignment.position.z
      ),
      new Rayf(
        entity.alignment.position.x + entity.boundingBox.xRange - Delta,
        entity.alignment.position.y - entity.boundingBox.yRange,
        entity.alignment.position.z + entity.boundingBox.zRange - Delta,
        entity.alignment.position.x + entity.boundingBox.xRange - Delta,
        entity.alignment.position.y - RayLength,
        entity.alignment.position.z + entity.boundingBox.zRange - Delta
      )
    )
}
