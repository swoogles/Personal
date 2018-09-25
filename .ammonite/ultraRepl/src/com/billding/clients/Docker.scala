package com.billding.clients

import ammonite.ops.{%, %%, Path}

object Docker {
	def ps(implicit wd: Path) =
		%docker("ps")

	def psMin(implicit wd: Path) =
		// %docker("ps", "--format", "{{.ID}}: {{.Image}}: {{.Status}}")
		// (%%docker("ps", "--format", "{{.Image}}: {{.Status}}"))
		(%%docker("ps", "--format", "{{.Image}}"))
			.out
			.lines
			.map(line=>line.dropWhile(_!='/').drop(1))
      .map{container=>println(container); container}

	// Note: I can only attach to the containers prepended with "cmt/"
	def attach(container: String)(implicit wd: Path) = 
		%docker("exec", "-it", container, "bash")

  object Kafka {
    def innerCommand(container: String)(args: String*)(implicit wd: Path) = {
      // %docker( Seq("exec", container, "sh", "-c")  ++: args)
      %docker( Seq("exec", container, "sh", "-c", args.mkString(" ")))
    }

    def makeFile(container: String)(implicit wd: Path) = {
      innerCommand(container)("mkdir", "-p", "/tmp/quickstart/file")
    }

    def writeLinesToInputFile(container: String, numLines: Int)(implicit wd: Path) =
      %docker("exec", container, "sh", "-c", s"seq 0 ${numLines}  >> /tmp/quickstart/file/input.txt")

    def makeConnector(container: String, name: String)(implicit wd: Path) = {
      import ammonite.ops._
      val fileContents = (read.lines!(wd / "connectors" / (name + ".json"))).mkString("\n")
      println("Deleting existing connector, if necessary")
      %docker("exec", container, "curl", "-X", "DELETE", s"http://kafka-connect:8082/connectors/$name")
      println(s"Creating new connector: $name")
      %docker("exec", container, "curl", "-X", "POST", "-H", "Content-Type: application/json", "--data", fileContents, "http://kafka-connect:8082/connectors")
    }

  }

  object compose {
    def apply(terms: String*)(implicit wd: Path) =
      action(terms)

    private def action(stages: Seq[String])(implicit wd: Path) =
      %( Seq("docker-compose", "--project-directory", wd.toString) ++: stages)

    private def permitFilesForDocker()(implicit wd: Path) =
      Bash.sudo("chmod", "o+rx", "-R", "EDIEEAR", "EDIE-DOCKER", "EDIEWEB")

    def refreshImages()(implicit wd: Path) =
      this("build", "--pull")

    private def up(containers: String*)(implicit wd: Path) =
      action(List("up", "--no-color") ++: containers)

    private def upD(containers: String*)(implicit wd: Path) =
      action(List("up", "--no-color", "-d") ++: containers)

    def monolith()(implicit wd: Path) = {
      permitFilesForDocker()
      up("edie-ear")
    }

    def monolithFresh()(implicit wd: Path) = {
      Gradle.monolith()
      permitFilesForDocker()
      up("edie-ear")
    }

    def nonCore()(implicit wd: Path) = {
      permitFilesForDocker()
      upD("edie-flyway", "edie-rabbitmq", "edie-auth", "edie-httpd", "onboarding-services", "edie-mysql" )
    }

    def fullSetup()(implicit wd: Path) = {
      nonCore()
      monolith()
    }
    def down(implicit wd: Path) = 
      action(List("down"))
  }
}


/*
kafka-connect control-center kafka-rest schema-registry kafka zookeeper patient-activity edie-mysql cp-all-in-one_control-center_1 cp-all-in-one_connect_1 cp-all-in-one_schema_registry_1 cp-all-in-one_broker_1 cp-all-in-one_zookeeper_1

mysql_test_0021891d-fdf4-4af0-badf-fe2fe07e8555
mysql_test_1622ee84-8717-4014-9f4b-28b4c88d92d8
*/
