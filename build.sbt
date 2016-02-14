name := """playpal"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayJava, QueryDSLPlugin)

scalaVersion := "2.11.6"

libraryDependencies ++= Seq(
  javaJdbc,
  cache,
  javaWs,
  javaJpa,
  "org.hibernate" % "hibernate-entitymanager" % "4.3.9.Final",
  "postgresql" % "postgresql" % "9.1-901.jdbc4",
  "mysql" % "mysql-connector-java" % "5.1.18",
  "junit" % "junit" % "4.10"
)
routesGenerator := InjectedRoutesGenerator
