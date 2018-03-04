// build.sc
import mill._
import mill.scalalib._

object ultraRepl extends ScalaModule {
  def scalaVersion = "2.12.4"
  def ivyDeps =
    Agg(
      ivy"org.scala-js::scalajs-tools:0.6.22",
      ivy"com.lihaoyi::ammonite-ops:1.0.3",
      ivy"org.typelevel::cats-core:1.0.1"
      // ivy"org.scalatest::scalatest:3.0.4:test"

    )
}

