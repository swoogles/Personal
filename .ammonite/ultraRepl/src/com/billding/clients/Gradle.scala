package ultraRepl.com.billding

import ammonite.ops.Path

object Gradle extends Client {
  val client = new ClientBuilder("./gradlew")
  def execute(args: String*): Unit = client.execute(args: _*)

  private def findBugsStages = List("findbugsMain", "findbugsTest")
  private def testStages = List("integrationTest", "test")

  // Can we get rid of implicit wd: Path on each of these? Should we?
  def build()(implicit wd: Path) =
    c("build")

  def cleanBuild()(implicit wd: Path) =
    c("clean", "build")

  def findbugs()(implicit wd: Path) =
    c(findBugsStages)

  def test()(implicit wd: Path) =
    c(testStages)

  def fullPrProcess()(implicit wd: Path) =
    c(findBugsStages ++: testStages)

  def monolith()(implicit wd: Path) =
    c("ear")
}
