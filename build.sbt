ThisBuild / organization := "org.sorted.chaos"
ThisBuild / scalaVersion := "2.13.3"
ThisBuild / version := "0.1.0-SNAPSHOT"

lazy val root = (project in file("."))
  .settings(name := "jump-playground")

libraryDependencies ++= Seq(
  // logger
  "ch.qos.logback" % "logback-core" % "1.2.3",
  "ch.qos.logback" % "logback-classic" % "1.2.3",
  "org.slf4j" % "slf4j-api" % "1.7.30",
  // config
  "com.github.pureconfig" % "pureconfig_2.13" % "0.12.1",
  // math
  "org.joml" % "joml" % "1.9.19",
  // testing
  "org.scalactic" %% "scalactic" % "3.0.8",
  "org.scalatest" %% "scalatest" % "3.0.8" % "test"
)

// https://stackoverflow.com/questions/24222360/cant-get-lwjgl-to-run-using-idea-and-sbt
libraryDependencies ++= {
  val version = "3.2.3"
  val os = "macos" // TODO: Change to "linux" or "windows" if necessary

  Seq("lwjgl", "lwjgl-glfw", "lwjgl-opengl", "lwjgl-stb").flatMap { module => {
    Seq(
      "org.lwjgl" % module % version,
      "org.lwjgl" % module % version classifier s"natives-$os"
    )
  }
  }
}
