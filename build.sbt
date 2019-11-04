name := """taxi_order"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.13.0"

libraryDependencies ++= Seq(
  guice,
  jdbc,
  "org.scalatestplus.play" %% "scalatestplus-play" % "4.0.3" % Test,
  "org.xerial" % "sqlite-jdbc" % "3.28.0",
  "org.scalikejdbc" %% "scalikejdbc" % "3.3.5",
  "org.scalikejdbc" %% "scalikejdbc-config" % "3.3.5",
  "org.scalikejdbc" % "scalikejdbc-play-initializer_2.12" % "2.6.0",
)
