package com.billding.clients

import ammonite.ops.Path
import ammonite.ops._

object Kafka {
  object Producer {
    private val client = new ClientBuilder("/usr/local/kafka/bin/kafka-console-producer.sh")
    def c(args: String*): Unit = client.execute(args: _*)

    // def singleTestMessage(message: String)(implicit wd: Path): Unit =
      // (%%echo(message)).out.lines | c("--broker-list", "localhost:9092", "--topic", "test")
  }
}
// Produce a single message
// echo "message 3" | /usr/local/kafka/bin/kafka-console-producer.sh --broker-list localhost:9092 --topic test
