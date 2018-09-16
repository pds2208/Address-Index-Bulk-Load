

lazy val bulk = (project in file("."))
  .settings(
    inThisBuild(List(
      organization := "uk.gov.ons",
      scalaVersion := "2.12.6",
      version := "0.1.0-SNAPSHOT"
    )),

    name := "Address Index Bulk Load",
    libraryDependencies ++= Seq(
      "com.google.inject" % "guice" % "4.2.0",
      "org.json4s" %% "json4s-jackson" % "3.6.1",
      "org.json4s" %% "json4s-native" % "3.6.1",
      "com.rabbitmq" % "amqp-client" % "5.4.1",

      "ch.qos.logback" % "logback-classic" % "1.2.3",
      "com.typesafe.scala-logging" %% "scala-logging" % "3.9.0",
    )
  )






