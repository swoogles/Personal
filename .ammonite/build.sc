// build.sc
import mill._
import mill.scalalib._

import publish._

// Work on enabling publishing
object ultraRepl extends PublishModule {
  def scalaVersion = "2.12.4"

  def publishVersion = "0.0.1"

  def pomSettings = PomSettings(
    description = "Some libs to power my personal Ammonite Setup.",
    organization = "com.billding",
    url = "https://github.com/swoogles/ammLibs",
    // licenses = Seq(License.MIT),
    licenses = Seq(),
    versionControl = VersionControl.github("swoogles", "ammLibs"),
    developers = Seq(
      Developer("swoogles", "Bill Frasure","https://github.com/swoogles")
        )
      )
  def ivyDeps =
    Agg(
      ivy"org.scala-js::scalajs-tools:0.6.22",
      ivy"com.lihaoyi::ammonite-ops:1.0.3",
      // ivy"com.lihaoyi::ammonite-shell:1.0.5",
      ivy"org.typelevel::cats-core:1.0.1"
      // ivy"org.scalatest::scalatest:3.0.4:test"

    )
}

