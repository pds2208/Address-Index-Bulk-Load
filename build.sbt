

name := "Address Index Bulk Load"
organization in ThisBuild := "Office for National Statistics"
scalaVersion in ThisBuild := "2.12.6"

routesImport := Seq.empty


lazy val global = project
  .in(file("."))
  .settings(settings)
  .aggregate(
    common,
    csv,
    rest
  )
  
  lazy val common = project
  .settings(
    name := "common",
    settings,
    libraryDependencies ++= commonDependencies ++ Seq(
      dependencies.guice,
      dependencies.rabbitMQ,
      dependencies.typesafeConfig
    )
  )

lazy val csv = project
  .settings(
    name := "csv",
    settings,
    libraryDependencies ++= commonDependencies ++ Seq(
      dependencies.guice,
      dependencies.json4sJackson,
      dependencies.json4sNative,
      dependencies.rabbitMQ,
      dependencies.typesafeConfig
    )
  )
  .dependsOn(
    common
  )

lazy val rest = project
  .settings(
    name := "rest",
    settings,
    libraryDependencies ++= commonDependencies ++ Seq(
      dependencies.guice,
      dependencies.json4sJackson,
      dependencies.json4sNative,
      dependencies.typesafeConfig,
      dependencies.swaggerPlay,
      dependencies.webjarsPlay,
      dependencies.swaggerUI
    )
  )
  .dependsOn(
    common
  )
  .enablePlugins(
    PlayScala
  )

lazy val dependencies =
  new {
    val guiceV            = "4.2.0"
    val json4sV           = "3.6.1"
    val rabbitV           = "5.4.1"
    val typesafeConfigV   = "1.3.2"
    val logbackV          = "1.2.3"
    val scalaLoggingV     = "3.9.0"
    val swaggerPlayV      = "1.6.0"
    val webjarsPlayV       = "2.6.3"
    val swaggerUIV        = "3.18.2"

    val webjarsPlay       = "org.webjars"                %% "webjars-play"    % webjarsPlayV
    val swaggerUI         = "org.webjars"                 % "swagger-ui"      % swaggerUIV
    val guice             = "com.google.inject"           % "guice"           % guiceV
    val json4sJackson     = "org.json4s"                 %% "json4s-jackson"  % json4sV
    val json4sNative      = "org.json4s"                 %% "json4s-native"   % json4sV
    val rabbitMQ          = "com.rabbitmq"                % "amqp-client"     % rabbitV

    val typesafeConfig    = "com.typesafe"                % "config"          % typesafeConfigV
    val logback           = "ch.qos.logback"              % "logback-classic" % logbackV
    val scalaLogging      = "com.typesafe.scala-logging" %% "scala-logging"   % scalaLoggingV

    val swaggerPlay       = "io.swagger"                 %% "swagger-play2"   % swaggerPlayV
  }

lazy val commonDependencies = Seq(
  dependencies.logback,
  dependencies.scalaLogging
)

lazy val settings = commonSettings

lazy val compilerOptions = Seq(
  "-language:experimental.macros",
    "-target:jvm-1.8",
    "-encoding", "UTF-8",
    "-language:reflectiveCalls",
    "-language:experimental.macros",
    "-language:implicitConversions",
    "-language:higherKinds",
    "-language:postfixOps",
    "-deprecation", // warning and location for usages of deprecated APIs
    "-feature", // warning and location for usages of features that should be imported explicitly
    "-unchecked", // additional warnings where generated code depends on assumptions
    "-Xlint", // recommended additional warnings
    "-Xcheckinit", // runtime error when a val is not initialized due to trait hierarchies (instead of NPE somewhere else)
    "-Ywarn-adapted-args", // Warn if an argument list is modified to match the receiver
    //"-Yno-adapted-args", // Do not adapt an argument list (either by inserting () or creating a tuple) to match the receiver
    "-Ywarn-value-discard", // Warn when non-Unit expression results are unused
    "-Ywarn-inaccessible", // Warn about inaccessible types in method signatures
    "-Ywarn-dead-code", // Warn when dead code is identified
    "-Ywarn-unused", // Warn when local and private vals, vars, defs, and types are unused
 //   "-Ywarn-unused-import", //  Warn when imports are unused (don't want IntelliJ to do it automatically)
    "-Ywarn-numeric-widen" // Warn when numerics are widened
)

lazy val commonSettings = Seq(
  scalacOptions ++= compilerOptions,
  resolvers ++= Seq(
    "Local Maven Repository" at "file://" + Path.userHome.absolutePath + "/.m2/repository"
  )
)
