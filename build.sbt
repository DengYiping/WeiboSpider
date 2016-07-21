name := "WeiboSpider"
organization := "sd.weibo"
version := "0.1"
scalaVersion := "2.11.8"
resolvers += "spray repo" at "http://repo.spray.io"

libraryDependencies += "org.scalatest" % "scalatest_2.11" % "2.2.4" % "test"
//spray stack
libraryDependencies ++= {
  val akkaV = "2.4.8"
  Seq(
    "com.typesafe.akka" %% "akka-actor" % akkaV,
    "com.typesafe.akka" %% "akka-testkit" % akkaV % "test",
    "com.typesafe.akka" %% "akka-http-core" % akkaV,
    "com.typesafe.akka" %% "akka-http-testkit" % akkaV,
    "com.typesafe.akka" %% "akka-http-spray-json-experimental" % akkaV,
    "com.typesafe.akka" %% "akka-http-experimental" % "2.4.8"
  )
}
libraryDependencies += "org.mongodb.scala" %% "mongo-scala-driver" % "1.0.1"
libraryDependencies += "org.jsoup" % "jsoup" % "1.9.2"
libraryDependencies += "com.typesafe" % "config" % "1.3.0"
