package com.billding.clients

import ammonite.ops._

object KafkaPresentation extends Client {
  private val client = new ClientBuilder("sh")
  def execute(args: String*): Unit = client.execute(args: _*)

  private val container = "kafka-connect"

  def startContainers()(implicit wd: Path) = {
    execute("./StartupConfluenceContainers.sh")
    Docker.Kafka.makeFile("kafka-connect")
  }
  
  def removeContainers() = {
    execute("./RemoveConfluenceContainers.sh")
  }

  def writeLinesToSourceFile(numLines: Int)(implicit wd: Path) =
    Docker.Kafka.writeLinesToInputFile(container, numLines)

  def makeConnector(name: String)(implicit wd: Path) =
    Docker.Kafka.makeConnector(container, name: String)
}
