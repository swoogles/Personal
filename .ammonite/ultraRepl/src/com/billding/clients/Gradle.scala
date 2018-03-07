package com.billding.clients

import ammonite.ops.Path

object Gradle extends Client {
  val client = new ClientBuilder("./gradlew")
  def execute(args: String*): Unit = client.execute(args: _*)

  def findBugsStages = List("findbugsMain", "findbugsTest")
  def testStages = List("integrationTest", "test")

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
  sealed case class Project(name: String) {
    def test()(implicit wd: Path) =
      Gradle.c(s":$name:test")

    def integrationTest()(implicit wd: Path) =
      Gradle.c(s":$name:integrationTest")

    def complexTask(stages: List[String]) =
      Gradle.c(
        stages.map(stage => s":$name:$stage")
      )

    def findbugs()(implicit wd: Path) =
      complexTask(Gradle.findBugsStages)

  }
}

