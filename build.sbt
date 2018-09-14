import Dependencies._

lazy val root = (project in file(".")).
  settings(
    inThisBuild(List(
      organization := "seyed",
      scalaVersion := "2.11.8",
      version      := "0.1.0-SNAPSHOT"
    )),
    name := "location-entropy-calculator",
    libraryDependencies ++= Seq(scalaTest, scalaCheck, apacheSpark, sparkSql, guice)

  )

coverageEnabled.in(Test, test) := true
