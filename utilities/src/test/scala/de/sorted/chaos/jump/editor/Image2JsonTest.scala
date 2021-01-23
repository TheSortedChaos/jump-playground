package de.sorted.chaos.jump.editor

import org.scalatest.{Matchers, WordSpec}

class Image2JsonTest extends WordSpec with Matchers {
  "Image2Json" should {
    "test" in {
      val input = "/image1.png"
      val output = "/Users/mwittig/development/repositories/jump-playground/editor/src/test/resources/output.json"
      Image2Json.exec(input, output)
    }
  }
}
