ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "2.13.10"

lazy val macroSettings = Seq(
  libraryDependencies ++= Seq(
    scalaOrganization.value % "scala-reflect" % scalaVersion.value,
  ),
)

lazy val macroAnnotationSettings = Seq(
  scalacOptions ++= Seq(
    "-Ymacro-annotations",
  ),
)

lazy val macroExpandSettings = Seq(
  scalacOptions ++= Seq(
    "-Ymacro-debug-lite",
  ),
)

lazy val testSettings = Seq(
  libraryDependencies ++= Seq(
    "org.scalatest" %% "scalatest" % "3.2.14" % Test,
  )
)

lazy val defMacroMacros = (project in file("def-macros/macro-macros"))
  .settings(
    macroSettings,
  )

lazy val defMacros = (project in file("def-macros/macros"))
  .settings(
    macroSettings,
    macroExpandSettings,
  )
  .dependsOn(defMacroMacros)

lazy val defCore = (project in file("def-macros/core"))
  .settings(
    macroExpandSettings,
    testSettings,
  )
  .dependsOn(defMacros)

lazy val annotationMacroMacros = (project in file("macro-annotations/macro-macros"))
  .settings(
    macroSettings,
    macroAnnotationSettings,
  )

lazy val annotationMacros = (project in file("macro-annotations/macros"))
  .settings(
    macroSettings,
    macroAnnotationSettings,
    macroExpandSettings,
  )
  .dependsOn(annotationMacroMacros)

lazy val annotationCore = (project in file("macro-annotations/core"))
  .settings(
    macroAnnotationSettings,
    macroExpandSettings,
    testSettings,
  )
  .dependsOn(annotationMacros)