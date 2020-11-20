package de.sorted.chaos.jump.game.game.entity.movable

import de.sorted.chaos.jump.game.game.pipeline.Level
import org.joml.{ AABBf, Rayf, Vector3f }

object DistanceSensor {

  // TODO clean up

  private val RayLength = 200.0f
  private val Delta     = 0.1f

  def shootRayToSky(entity: MovableEntity, level: Level): Float = {
    val ray = createRayToSky(entity)

    getDistanceToNearestObstacleSky(ray, entity, level)
  }

  def shootRayToGround(entity: MovableEntity, level: Level): Float = {
    val ray = createRayToGround(entity)

    getDistanceToNearestObstacleGround(ray, entity, level)
  }

  private def getDistanceToNearestObstacleGround(rays: Vector[Rayf], entity: MovableEntity, level: Level) =
    level.boundingBoxes
      .flatMap(box => rays.map(ray => (box, box.intersectsRay(ray))))
      .filter(tuple => tuple._2)
      .map(tuple => getDistanceForGround(entity, tuple._1))
      .minOption
      .getOrElse(RayLength)

  private def getDistanceToNearestObstacleSky(rays: Vector[Rayf], entity: MovableEntity, level: Level) =
    level.boundingBoxes
      .flatMap(box => rays.map(ray => (box, box.intersectsRay(ray))))
      .filter(tuple => tuple._2)
      .map(tuple => getDistanceForSky(entity, tuple._1))
      .minOption
      .getOrElse(RayLength)

  private def getDistanceForGround(entity: MovableEntity, other: AABBf) = {
    val start = rayStartForGround(entity)
    val end   = rayEndForGround(entity, other)

    start.distance(end)
  }

  private def getDistanceForSky(entity: MovableEntity, other: AABBf) = {
    val start = rayStartForSky(entity)
    val end   = rayEndForSky(entity, other)

    start.distance(end)
  }

  private def rayEndForGround(entity: MovableEntity, boundingBox: AABBf) =
    new Vector3f(
      entity.alignment.position.x,
      boundingBox.maxY,
      entity.alignment.position.z
    )

  private def rayEndForSky(entity: MovableEntity, boundingBox: AABBf) =
    new Vector3f(
      entity.alignment.position.x,
      boundingBox.minY,
      entity.alignment.position.z
    )

  private def rayStartForGround(entity: MovableEntity) =
    new Vector3f(
      entity.alignment.position.x,
      entity.alignment.position.y - entity.boundingBox.yRange,
      entity.alignment.position.z
    )

  private def rayStartForSky(entity: MovableEntity) =
    new Vector3f(
      entity.alignment.position.x,
      entity.alignment.position.y + entity.boundingBox.yRange,
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
