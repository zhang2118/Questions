name := "Facebook-getAlbums"

version := "1.0-SNAPSHOT"

libraryDependencies ++= Seq(
  javaJdbc,
  javaEbean,
  cache
)

libraryDependencies += "org.facebook4j" % "facebook4j-core" % "2.3.0"

play.Project.playJavaSettings
