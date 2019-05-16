// build.sc
import mill._, scalalib._

object scriptManipulation extends ScalaModule {
  def scalaVersion = "2.12.4"
  override def ivyDeps = Agg(
    ivy"com.github.pathikrit::better-files:3.7.1",
    )
  object test extends Tests {
    def ivyDeps = Agg(ivy"com.lihaoyi::utest:0.6.0")
    def testFrameworks = Seq("utest.runner.Framework")
  }
}
