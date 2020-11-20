ThisBuild / organization := "org.sorted.chaos"
ThisBuild / scalaVersion := "2.13.3"
ThisBuild / version := "0.1.0-SNAPSHOT"

// ------------ [ MODULES ] ----------------

lazy val global = project
  .in(file("."))
  .settings(name := "jump-playground", settings)
  .aggregate(
    game,
    editor
  )

lazy val shared = project
  .settings(
    name := "shared",
    settings,
    libraryDependencies ++=
      loggingDependencies ++
      testingDependencies
  )

lazy val game = project
  .settings(
    name := "game",
    settings,
    libraryDependencies ++=
      loggingDependencies ++
      testingDependencies ++
      lwjglDependencies ++
      Seq(
        dependencies.wavefrontReader,
        dependencies.pureConfig
      )
  )
  .dependsOn(shared)

lazy val editor = project
  .settings(
    name := "editor",
    settings,
    libraryDependencies ++=
      loggingDependencies ++
      testingDependencies :+
      dependencies.pureConfig
  )
  .dependsOn(shared)

// ------------ [ DEPENDENCIES ] ----------------

lazy val dependencies = new {

  val osClassifier = "macos" // TODO: Change to "linux" or "windows" if necessary

  val jomlVersion            = "1.9.19"
  val logbackVersion         = "1.2.3"
  val lwjglVersion           = "3.2.3"
  val pureConfigVersion      = "0.12.1"
  val scalaTestVersion       = "3.0.8"
  val slf4jVersion           = "1.7.30"
  val wavefrontReaderVersion = "1.0.0"

  val joml              = "org.joml"                  % "joml"              % jomlVersion
  val logback           = "ch.qos.logback"            % "logback-core"      % logbackVersion
  val logbackClassic    = "ch.qos.logback"            % "logback-classic"   % logbackVersion
  val lwjgl             = "org.lwjgl"                 % "lwjgl"             % lwjglVersion
  val lwjglNative       = "org.lwjgl"                 % "lwjgl"             % lwjglVersion classifier s"natives-$osClassifier"
  val lwjglGlfw         = "org.lwjgl"                 % "lwjgl-glfw"        % lwjglVersion
  val lwjglGlfwNative   = "org.lwjgl"                 % "lwjgl-glfw"        % lwjglVersion classifier s"natives-$osClassifier"
  val lwjglOpenGl       = "org.lwjgl"                 % "lwjgl-opengl"      % lwjglVersion
  val lwjglOpenGlNative = "org.lwjgl"                 % "lwjgl-opengl"      % lwjglVersion classifier s"natives-$osClassifier"
  val lwjglStb          = "org.lwjgl"                 % "lwjgl-stb"         % lwjglVersion
  val lwjglStbNative    = "org.lwjgl"                 % "lwjgl-stb"         % lwjglVersion classifier s"natives-$osClassifier"
  val pureConfig        = "com.github.pureconfig"     % "pureconfig_2.13"   % pureConfigVersion
  val scalactic         = "org.scalactic"             %% "scalactic"        % scalaTestVersion
  val scalatest         = "org.scalatest"             %% "scalatest"        % scalaTestVersion % "test"
  val slf4j             = "org.slf4j"                 % "slf4j-api"         % slf4jVersion
  val wavefrontReader   = "com.github.thesortedchaos" %% "wavefront-reader" % wavefrontReaderVersion
}

lazy val loggingDependencies = Seq(
  dependencies.logback,
  dependencies.logbackClassic,
  dependencies.slf4j
)

lazy val lwjglDependencies = Seq(
  dependencies.lwjgl,
  dependencies.lwjglNative,
  dependencies.lwjglGlfw,
  dependencies.lwjglGlfwNative,
  dependencies.lwjglOpenGl,
  dependencies.lwjglOpenGlNative,
  dependencies.lwjglStb,
  dependencies.lwjglStbNative,
  dependencies.joml
)

lazy val testingDependencies = Seq(
  dependencies.scalactic,
  dependencies.scalatest
)

// ------------ [ SETTINGS ] ----------------

lazy val settings =
scalafmtSettings ++
wartRemoverSettings

lazy val scalafmtSettings = Seq(
  scalafmtOnCompile := true,
  scalafmtTestOnCompile := true,
  scalafmtVersion := "1.2.0"
)

lazy val wartRemoverSettings = Seq(
  wartremoverWarnings in (Compile, compile) ++= Warts.allBut(Wart.Throw)
)
