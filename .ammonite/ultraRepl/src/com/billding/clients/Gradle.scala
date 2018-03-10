package com.billding.clients

import ammonite.ops.Path

trait GradleOps {
  def build()(implicit wd: Path): Unit
  def cleanBuild()(implicit wd: Path): Unit
  def findbugs()(implicit wd: Path): Unit
  def test()(implicit wd: Path): Unit
  def fullPrProcess()(implicit wd: Path): Unit
}

object Gradle extends Client with GradleOps {
  private val client = new ClientBuilder("./gradlew")
  def execute(args: String*): Unit = client.execute(args: _*)

  private def findBugsStages = List("findbugsMain", "findbugsTest")
  private def testStages = List("integrationTest", "test")

  // Can we get rid of implicit wd: Path on each of these? Should we?
  def build()(implicit wd: Path): Unit =
    c("build")

  def cleanBuild()(implicit wd: Path): Unit =
    c("clean", "build")

  def findbugs()(implicit wd: Path): Unit =
    c(findBugsStages)

  def test()(implicit wd: Path): Unit =
    c(testStages)

  def fullPrProcess()(implicit wd: Path): Unit =
    c(findBugsStages ++: testStages)

  def monolith()(implicit wd: Path): Unit =
    c("ear")

  object Projects {
    val EDIECOMMONS = Project("EDIECOMMONS")
    val EDIEEAR = Project("EDIEEAR")
    val EDIEEJB = Project("EDIEEJB")
    val EDIEHAR = Project("EDIEHAR")
    val EDIEREST = Project("EDIEREST")
    val EDIEWEB = Project("EDIEWEB")
    val HL7 = Project("HL7")
    val Mappers = Project("Mappers")
    val MLLPEJB = Project("MLLPEJB")
    val Records = Project("Records")
  }

  sealed case class Project(name: String) extends GradleOps {
    private def findBugsStages = List("findbugsMain", "findbugsTest")
    private def testStages = List("integrationTest", "test")
    def build()(implicit wd: Path) =
      c("build")

    def cleanBuild()(implicit wd: Path) =
      complexTask(List("clean", "build"))

    def test()(implicit wd: Path) =
      Gradle.c(s":$name:test")

    def testIndividual(testClass: String)(implicit wd: Path) =
      Gradle.c(s":$name:test --tests *$testClass*")

    def integrationTest()(implicit wd: Path) =
      Gradle.c(s":$name:integrationTest")

    def complexTask(stages: List[String]) =
      Gradle.c(
        stages.map(stage => s":$name:$stage")
      )

    def findbugs()(implicit wd: Path) =
      complexTask(Gradle.findBugsStages)

    def fullPrProcess()(implicit wd: Path) =
      complexTask(findBugsStages ++: testStages)

    // Implement this
    // ./gradlew --info :EDIEEJB:test --tests *CarePlanContentTest*

  }
}

