package de.sorted.chaos.jump.game.game.level

import org.scalatest.{Matchers, WordSpec}

class LevelLoader2Test extends WordSpec with Matchers {
  "LevelLoader2" should {
    "test" in {
      val input = "/level-01alpha.json"
      LevelLoader2.load(input)
    }
  }
}
