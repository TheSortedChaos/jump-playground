package de.sorted.chaos.jump.game.game.entity.movable.sensor.distance

import de.sorted.chaos.jump.game.game.entity.movable.MovableEntity
import org.joml.{ AABBf, Rayf, Vector3f }

object DistanceToSkySensor extends DistanceTo {

  override protected def rayEnd(ray: Rayf, boundingBox: AABBf) =
    new Vector3f(
      ray.dX,
      boundingBox.minY,
      ray.dZ
    )

  override protected def createRays(entity: MovableEntity) =
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
}
