import sbt.Keys.libraryDependencies
import sbtassembly.AssemblyPlugin.defaultShellScript

ThisBuild / version := "0.1.0-SNAPSHOT"
ThisBuild / scalaVersion := "2.13.15"
ThisBuild / assemblyPrependShellScript := Some(defaultShellScript)

val mainClassName = "org.ntic.flights.FlightsLoaderApp"

lazy val root = (project in file("."))
  .settings(
    name := "Flight_data_manager",
    Compile / run / mainClass := Some("com.ntic"),
    Compile / packageBin / mainClass := Some("com.ntic"),
    assembly / mainClass := Some("MiPlanner"),
    assembly / assemblyJarName := "flights_loader.jar",
    // TODO: define la clase principal del proyecto para la etapa `run` de `Compile`
    // TODO: define la clase principal del proyecto para la etapa `packageBin` de `Compile`
    // TODO: define la clase principal del proyecto para el ensamblado de `assembly`
    // TODO: define `flights_loader.jar` como el nombre del jar que se genera en la etapa assembly

    libraryDependencies ++= Seq(
      "com.typesafe" % "config" % "1.4.3",
      "com.typesafe.akka" %% "akka-http-spray-json" % "10.5.2",
      "org.scalatest" %% "scalatest" % "3.2.17" % Test,
      "org.scala-lang" %% "toolkit-test" % "0.1.7" % Test
    )
  )
