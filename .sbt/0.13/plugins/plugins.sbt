addSbtPlugin("com.dscleaver.sbt" % "sbt-quickfix" % "0.4.1")

resolvers ++= Seq(
    "Sonatype OSS Releases" at "https://oss.sonatype.org/content/repositories/releases/",
      "Sonatype OSS Snapshots" at "https://oss.sonatype.org/content/repositories/snapshots/"
    )

addSbtPlugin("net.ceedubs" %% "sbt-ctags" % "0.1.0")

addSbtPlugin("org.wartremover" % "sbt-wartremover" % "1.2.0")

