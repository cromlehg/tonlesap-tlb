name := "tonlesap-tlb"

organization := "com.blockwit"

version := "0.1"

scalaVersion := "2.12.10"

libraryDependencies ++= Seq(
  "org.json" % "json" % "20190722",
  "ch.qos.logback" % "logback-classic" % "1.2.3" % Test,
  "ch.qos.logback" % "logback-core" % "1.2.3",
  "org.slf4j" % "slf4j-api" % "1.7.28",
  "commons-io" % "commons-io" % "2.6"
)
