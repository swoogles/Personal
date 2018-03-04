import $file.bash, bash.sudo
import $file.Gradle, Gradle.gradle
import ammonite.ops.{%, %%, Path}

object docker {
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

  object compose {
    def apply(terms: String*)(implicit wd: Path) =
      action(terms)

    private def action(stages: Seq[String])(implicit wd: Path) =
      %( Seq("docker-compose", "--project-directory", wd.toString) ++: stages)

    private def permitFilesForDocker()(implicit wd: Path) =
      sudo("chmod", "o+rx", "-R", "EDIEEAR", "EDIE-DOCKER", "EDIEWEB")

    def refreshImages()(implicit wd: Path) =
      this("build", "--pull")

    def up(containers: String*)(implicit wd: Path) =
      action(List("up", "--no-color") ++: containers)

    def upD(containers: String*)(implicit wd: Path) =
      action(List("up", "--no-color", "-d") ++: containers)

    def monolith()(implicit wd: Path) = {
      permitFilesForDocker()
      up("edie-ear")
    }

    def monolithFresh()(implicit wd: Path) = {
      gradle.monolith()
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

