package ultraRepl.com.billding

import ammonite.ops._
import ammonite.ops.ImplicitWd._

import scala.util._
/*
 * TODOs
 * -Determine if Intellij step can be done prior to jdk setup.
 *    Resolution: Just do it afterwards! Duh.
 *
 * - Figure out npm stuff
 *     Dunno how to do "export" from ammonite.
 *    Possible Resolution: Edit .bash_profile instead
 *    Then execute "source" on the file.
*/

  /* A likely candidate for confusion is:
       () => Try[String]
     The point of this is to have a piece that will execute heavy work, but
     not until the desired time.
  */
object Setup {
  type NAMED_TASK = (String, ()=>Try[String])

  // TODO Use docker-compose from PATH
  val dockerCompose = Path("/usr/local/bin/docker-compose")

  // TODO Reinstate this when I figure out how to make script play nicely in this project
  // @main
  def main(userName: String, repoDir: Path) = {
    val rootSource = repoDir/'dev
    val e2eRoot = repoDir/'e2e

    val bitbucketHost = "bitbucket.collectivemedicaltech.com"
    val repo = "/scm/edie/dev"
    val end2endTestingRepo = "scm/qa/e2e"

    val allTasks: Stream[(Float, NAMED_TASK)] = 
      taskOrganizer(
        List(
          List(
            install("git"), // TODO Configure .gitconfig along with this!)
            install("curl"),
            install("openjdk-8-jdk", "openjdk-8-source", "openjdk-8-doc"),
            ),
          dockerConfig,
          List(
            cloneRepo(repo, bitbucketHost, userName, rootSource),
            cloneRepo(end2endTestingRepo , bitbucketHost, userName, rootSource)
          ),
          dotFileSection(rootSource),
          List(
            ("Flyway Section", flywaySection(rootSource)) // TODO Move name inside? Probably
          ),
          integrationTestConfigSection(rootSource),
          additionalITConfig(rootSource),
          // end2endSection(e2eRoot)
        )
      )

    allTasks
      .map((executeAndRecord _).tupled)
      .takeWhile(_.isSuccess)
      .foreach(println)
  }

  def execute(args: String*): () => Try[String] =
    () => Try {
      val results = %%(args) // Minimizing use of strange %% operator
      if (results.exitCode == 0)
        args.mkString(" ")
      else
        throw new Exception(
          s"Failure in task: ${args.mkString(" ")}" + results.out.lines.mkString("\n")
        )
    }

  // TODO This is the ugliest bit left in this whole endeavor.
  def install(lib: String*) =
    // ("Faux install", () => Success("Wee! fake install because apt is borked"))
    (s"Install ${lib.mkString(", ")}" ,
      () => aptUpdate().flatMap( _ => 
            execute(
              (Seq("sudo", "apt-get", "install") ++ lib): _*
            ).apply()
          )
        )

  val aptUpdate = 
    () => Success("FALSE UPDATE!! DON'T LEAVE THIS!!")
    // execute( "sudo", "apt-get", "update")

  object OSInfo {
    val versionNumber = simpleCommand("uname", "-r")
    val osName = simpleCommand("uname", "-s")
    val processorLine = simpleCommand("uname", "-m")
    val ubuntuVersion = simpleCommand("lsb_release", "-cs")

    private def simpleCommand(args: String*) =
      %%(args).out.lines.head
  }

  val configureDockerJson = {
    val tmpFile = root/'tmp/"daemon.json"
    write.over(tmpFile, """{ "dns": ["192.168.43.11", "192.168.43.6"] }""" )
    delayedTask("Configure Docker Json")( "sudo", "cp", tmpFile.toString, "/etc/docker/daemon.json");
  }

  def dockerConfig(): List[NAMED_TASK] = {
    // sudo usermod -a -G docker $USER
    val keyFile = root/'tmp/"gpg.key"

      List(
        configureDockerJson,
        install(
            "apt-transport-https",
            "software-properties-common",
            s"linux-image-extra-${OSInfo.versionNumber}",
            "linux-image-extra-virtual"
        ),
        executeIfFileDoesNotExist("Download docker gpg key")(keyFile)(
          "curl", "-fsSL", "https://download.docker.com/linux/ubuntu/gpg", "--output", keyFile.toString
          // s"curl -fsSL https://download.docker.com/linux/ubuntu/gpg --output $keyFile"
        ),
        delayedTask("Add docker key to apt")(
          "sudo", "apt-key", "add", keyFile.toString
        ),
        delayedTask("Add docker repo to apt")(
         "sudo", "add-apt-repository", 
         // TODO This is the *one* command that's got spaces in a single argument.
         s"deb [arch=amd64] https://download.docker.com/linux/ubuntu ${OSInfo.ubuntuVersion} stable"
        ),
        executeIfFileDoesNotExist("Download docker compose")(dockerCompose)(
          // This was just sending "Not Found" to the file, rather than crashing/burning. Disappointing...
          "sudo", "curl", "-L", 
          s"https://github.com/docker/compose/releases/download/1.18.0/docker-compose-${OSInfo.osName}-${OSInfo.processorLine}", 
          "-o", dockerCompose.toString 
        ),
        delayedTask("Make docker-compose executable")(
          "sudo", "chmod", "777", dockerCompose.toString
        ),
        install("docker-ce")
      )
  }

  def cloneRepo(repo: String, bitbucketHost: String, userName: String, rootSource: Path) = 
    executeIfFileDoesNotExist(s"Clone repo $repo")(rootSource)(
      "git", "clone", s"https://$userName@$bitbucketHost$repo.git", rootSource.toString
      // s"git clone https://$userName@$bitbucketHost$repo.git $rootSource"
    )

  def flywaySection(rootSource: Path) =
    () => Try {
      val flywayConfDir = rootSource/'DBMANAGE/'conf
      cp.over(flywayConfDir/"flyway.conf.example", flywayConfDir/"flyway.conf")
      "Copied flyway config."
    }

  /*
    I wish this was more specific, and considered the database.url too, but the 
    different spacing makes it a little more obnoxious.

    This is the issue:
      "database.url = jdbc:mysql://localhost:3306/", "database.url = jdbc:mysql://localhost:3307/"
        vs
      "database.url=jdbc:mysql://localhost:3306/", "database.url=jdbc:mysql://localhost:3307/"

  */
  def updateMySqlPort(line: String) =
    line.replace("jdbc:mysql://localhost:3306/", "jdbc:mysql://localhost:3307/")

  def integrationTestConfigSection(rootSource: Path): List[NAMED_TASK] = {
      val javaTestConfig: RelPath = "TestConfig.properties"
      val databaseConfig: RelPath= "database.properties"
      
      val innerPath: RelPath = 'src/"integration-test"/'resources

      def fileTarget(project: RelPath, fileName: RelPath): (Path, Path) =
          (rootSource / project / innerPath / s"$fileName.example",
            rootSource / project / innerPath / fileName)

      val group1 = List(
        "Records",
        // BUG: Tutorial has "test" instead of "integration-test" for Mappers section
        "Mappers",
        ).map{fileTarget(_, databaseConfig)}

      val group2 = List(
        "EDIEEJB",
        "EDIEWEB",
        "EDIEREST"
        ).map{fileTarget(_, javaTestConfig)}

      val edieTestDir = rootSource/'EDIETEST/'src/'main/'resources
      val group3 = 
        (
          edieTestDir/s"$javaTestConfig.example",
          edieTestDir/javaTestConfig
        )

      val allFiles: List[(Path, Path)] =
        group1 ++ group2 :+ group3

      allFiles.map{ case (orig, editedCopy) => 
        (s"copy edited version of $orig to $editedCopy", () => {
          val newContent = 
            read.lines(orig)
              .map{ line=>
                      line
                        .replace("jdbc:mysql://localhost:3306/", "jdbc:mysql://localhost:3307/")
                        .replace("gdb_test_clean", "gdb_test")
                        // DOC BUG: The 2 periods were not mentioned in the docs.
                        .replace("${sys:user.dir}/..", rootSource.toString)
                        // .replace("database.password=", "database.password=password")
              }.mkString("\n")

          write.over(editedCopy, newContent)
          Success(s"copied edited version of $orig to $editedCopy")
      })
    }
  }

  def executeIfFileDoesNotExist(task: String)(file: Path)(args: String*): (String, () => Try[String]) =
    if ( exists ! file)
      (task, () => Success(s"$file exists, so skipping ${args.mkString(" ")}"))
    else
      delayedTask(task)( args: _*)

  object StatusFile {
    private val statusFile = root/'tmp/"cmtSetup.txt"

    def previouslyCompletedStep() = 
      if ( exists ! statusFile )
        read.lines(statusFile).head.toFloat
      else
        -1.0

    def recordCompletedStep(idx: Float) =
      write.over(statusFile, idx.toString)
  }

  def delayedTask(task: String)(args: String*) = 
    (task, execute( args: _*))

  def delayedTaskNew(task: String)(args: String) = 
    delayedTask(task)( args.split("\\s+"): _*)

  // Might be too much abstraction, but it was fun to write/use these next 2 bits
  case class Output(commandResult: CommandResult) {
    def contains(term: String) =
       commandResult.out.lines.exists(_.contains(term))
  }

  def outputFrom(args: String*): Output =
    Output(%%( "sudo", "docker", "network", "ls"))

  def additionalITConfig(rootSource: Path): List[NAMED_TASK] = {
    val dir = rootSource/'EDIEEAR/'deployments
    val composeFile = rootSource/"docker-compose.yml"
    List(
      delayedTask(s"Change permission of $dir for Docker Compose")(
        "sudo", "chmod", "777", dir.toString
        // Alternative: sudo chown 1000:1000 rootSource/'EDIEEAR/'deployments
      ),
      ("Create Cmt docker network",
          if ( outputFrom( "sudo", "docker", "network", "ls").contains("cmtdev"))
          // if ( %%( "sudo", "docker", "network", "ls").out.lines.exists(_.contains("cmtdev")))
            () => Success("Cmt Network already exists.")
          else 
            execute( "sudo", "docker", "network", "create", "cmtdev")
      ),
      delayedTask("Initialize DB via flyway")(
        dockerCompose.toString , "-f", composeFile.toString, "--project-directory", rootSource.toString, "up", "-d", "edie-flyway"
      ),
      // TODO Does this actually need to be done in this setup script?
      delayedTask("Follow Flyway container logs")( 
        dockerCompose.toString , "-f", composeFile.toString, "--project-directory", rootSource.toString, "logs"
      ),
      // TODO Ensure that this is solid. Docker's not really working via this script right now.
      // Command to start all but edie-ear, which has to be restarted often during development
      // docker-compose up -d edie-rabbitmq edie-auth edie-httpd onboarding-services edie-mysql && docker-compose logs -f
      delayedTaskNew("Start all docker containers")(
        s"$dockerCompose -f $composeFile --project-directory $rootSource up -d"
      )
    )
  }


  def dotFileSection(rootSource: Path) = {
    val proFile = home / ".profile"

    def addToFileIfNotPresent(file: Path, varName: String, value: String): NAMED_TASK = 
      ( "Set $varName", () => 
                          if ( read.lines(file)
                                 .exists(_.contains(s"$varName=")))
                            Success(s"$varName already set.")
                          else {
                            write.append(proFile, s"$varName=$value\n")
                            Success(s"$varName newly set.")
                          }
      )

    List(
      executeIfFileDoesNotExist("Create .profile")(proFile)(
        "touch", proFile.toString
      ),
      addToFileIfNotPresent(proFile, "JAVA_HOME", "/usr/lib/jvm/java-8-openjdk-amd64/jre"),
      addToFileIfNotPresent(proFile, "npm_config_registry", "'http://nexus.collectivemedicaltech.com:8081/repository/npm/'"),
      addToFileIfNotPresent(proFile, "REPO_DEV", rootSource.toString),
    )
  }

  def end2endSection(e2eRoot: Path): List[NAMED_TASK] = {
    val driverFile = root/'tmp/"chromedriver_linux64.zip"
          val newContent = 
            read.lines(e2eRoot / 'src / 'test / 'resources / "test.properties.example")
              .map{ line=>
                      line
                        // .replace("database.username = nakker", "database.username = root")
                        // .replace("database.password = homer2", "database.password = password")
                        // .replace("e2e.url=https://localhost:443", "*real ip address here*")
              }.mkString("\n")

          write.over(e2eRoot / 'src / 'test / 'resources / "test.properties", newContent)
          Success(s"copied edited version of e2e config")

    List(
        executeIfFileDoesNotExist("Download Chrome Driver for Selenium tests")(driverFile)(
          "curl", "-fsSL", "https://chromedriver.storage.googleapis.com/2.35/chromedriver_linux64.zip", "--output", driverFile.toString
        ),
      // TODO This drops it in the cur directory. Where should it go?
        delayedTask("Unzip Chrome Driver")( 
          "sudo", "unzip" , driverFile.toString
        ),
    )
  }

  def taskOrganizer(tasks: List[List[NAMED_TASK]]): Stream[(Float, NAMED_TASK)] =
    tasks
      .zipWithIndex
      .map{case (sectionTasks, sectionIdx) =>
        sectionTasks
          .zipWithIndex
          .map{ case (task, taskIdx) => (s"$sectionIdx.$taskIdx".toFloat, task) }
      }.flatten
      .toStream

  def executeAndRecord(stepNum: Float, task: NAMED_TASK) = {
    val (taskName, taskFunc) = task

    val bigRes = 
      if (StatusFile.previouslyCompletedStep() >= stepNum) {
        Success(s"Step $stepNum completed on a previous run.")
      } else {
        // println(s"Starting Step $stepNum: $taskName.")
        val res = taskFunc.apply()
        if (res.isSuccess) {
          StatusFile.recordCompletedStep(stepNum)
        }
        res
      }
    println(s"Step $stepNum: $taskName. Result: $bigRes")
    // println(s"Step $stepNum: Finish $taskName.")
    bigRes
  }
}
