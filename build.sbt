import org.fusesource.scalate.ScalatePlugin.ScalateKeys._
import org.scalatra.sbt._

val ScalatraVersion = "2.3.0"


ScalatraPlugin.scalatraSettings

scalateSettings

organization := "com.tookitaki"

name := "SlickScalatraApp"

version := "0.1.0-SNAPSHOT"

scalaVersion := "2.10.4"

resolvers += Resolver.typesafeRepo("releases")

val mysqlDependencies = Seq(
  "mysql" % "mysql-connector-java" % "5.1.34",
  "com.typesafe.slick" %% "slick" % "2.1.0",
  "c3p0" % "c3p0" % "0.9.1.2"
)

libraryDependencies ++= Seq(
  "org.scalatra" %% "scalatra" % ScalatraVersion,
  "org.scalatra" %% "scalatra-scalate" % ScalatraVersion,
  "org.scalatra" %% "scalatra-specs2" % ScalatraVersion % "test",
  "org.scalatra" %% "scalatra-json" % ScalatraVersion,
  "org.json4s"   %% "json4s-jackson" % "3.2.11",
  "ch.qos.logback" % "logback-classic" % "1.1.5" % "runtime",
  "org.eclipse.jetty" % "jetty-webapp" % "9.2.15.v20160210" % "container",
  "javax.servlet" % "javax.servlet-api" % "3.1.0" % "provided"
)

libraryDependencies ++= mysqlDependencies


scalateTemplateConfig in Compile := {
  val base = (sourceDirectory in Compile).value
  Seq(
    TemplateConfig(
      base / "webapp" / "WEB-INF" / "templates",
      Seq.empty, /* default imports should be added here */
      Seq(
        Binding("context", "_root_.org.scalatra.scalate.ScalatraRenderContext", importMembers = true, isImplicit = true)
      ), /* add extra bindings here */
      Some("templates")
    )
  )
}

enablePlugins(JettyPlugin)
