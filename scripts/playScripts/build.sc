// build.sc
import mill._, scalalib._

object scriptManipulation extends ScalaModule {
  def scalaVersion = "2.12.4"
  def ivyDeps = Agg(
    ivy"com.github.pathikrit::better-files:3.7.1",
    )
}
