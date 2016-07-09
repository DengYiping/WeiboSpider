name := "WeiboSpider"
organization := "sd.weibo"
version := "0.1"
scalaVersion := "2.11.8"
resolvers += "spray repo" at "http://repo.spray.io"

libraryDependencies += "org.scalatest" % "scalatest_2.11" % "2.2.4" % "test"
//spray stack
libraryDependencies ++= {
  val akkaV = "2.4.1"
  val sprayV = "1.3.3"
  Seq(
    "io.spray" %% "spray-can" % sprayV,
    "io.spray" %% "spray-http" % sprayV,
    "io.spray" %% "spray-httpx" % sprayV,
    "io.spray" %% "spray-routing" % sprayV,
    "io.spray" %% "spray-servlet" % sprayV,
    "io.spray" %% "spray-util" % sprayV,
    "io.spray" %% "spray-io" % sprayV,
    "io.spray" %% "spray-client" % sprayV,
    "io.spray" %% "spray-caching" % sprayV,
    "io.spray" %% "spray-testkit" % sprayV % "test",
    "io.spray" %% "spray-json" % "1.3.2",
    "com.typesafe.akka" %% "akka-actor" % akkaV,
    "com.typesafe.akka" %% "akka-testkit" % akkaV % "test"
  )
}
libraryDependencies += "org.mongodb.scala" %% "mongo-scala-driver" % "1.0.1"
