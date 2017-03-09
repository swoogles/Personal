addSbtPlugin("com.dscleaver.sbt" % "sbt-quickfix" % "0.4.1")

resolvers ++= Seq(
    "Sonatype OSS Releases" at "https://oss.sonatype.org/content/repositories/releases/",
      "Sonatype OSS Snapshots" at "https://oss.sonatype.org/content/repositories/snapshots/"
    )

addSbtPlugin("net.ceedubs" %% "sbt-ctags" % "0.1.0")

addSbtPlugin("org.wartremover" % "sbt-wartremover" % "1.2.0")

// addSbtPlugin("ch.epfl.scala" % "sbt-scalafix" % "0.3.0")
// addSbtPlugin("ch.epfl.scala" % "sbt-scalafix" % "0.3.2-SNAPSHOT")


addSbtPlugin("org.ensime" % "sbt-ensime" % "1.12.5")

addSbtPlugin("io.get-coursier" % "sbt-coursier" % "1.0.0-M15")

