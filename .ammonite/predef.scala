load.ivy("com.lihaoyi" %% "ammonite-shell" % ammonite.Constants.version)
@
import ammonite.ops._
import ammonite.ops.ImplicitWd._

val ammoScriptDir = home/'Repositories/'Personal/"scripts"/'Ammonite

//val ammoScript = ammoScriptDir/"findTopicExampleByAuthor.scala"
/*val ammoScript = ammoScriptDir/"duplicateLines.scala"*/

/*load.exec(ammoScriptDir/"findTopicExampleByAuthor.scala")*/
/*load.exec(ammoScriptDir/"duplicateLines.scala")*/


def filesOnly(file: Path): Boolean = (file.isFile)
def javaProjectFileFilter(file: Path): Boolean = (file.isFile && (file.ext == "java" || file.ext == "properties" ))
def scalaProjectFileFilter(file: Path): Boolean = (file.isFile && (file.ext == "scala" || file.ext == "conf" || file.name == "routes" || (file.name == "build" && file.ext == "sbt") ) )

def searchJavaFiles(searchTerm: String) =
  searchFiles(searchTerm, javaProjectFileFilter)

def searchScalaFiles(searchTerm: String) =
  searchFiles(searchTerm, scalaProjectFileFilter)


def searchFiles(searchTerm: String, fileFilter: Path=>Boolean) = { 
  ls.rec! cwd |? 
    fileFilter | 
    (file => (file, read.lines(file).filter(_.contains(searchTerm)) )) |? 
    (_._2.nonEmpty) 
}


def searchJavaFilesNameOnly(searchTerm: String) = searchJavaFiles(searchTerm) | (_._1)

case class DockerPsLine(
  id: String, 
  image: String, 
  command: String, 
  created: String, 
  status: String, 
  ports: String, 
  names: String
)

  object DockerPsLine {
    def apply(rawLine: String): Option[DockerPsLine] = {
      /*val fields = rawLine.split("\\w+")*/
      val fields = rawLine.split(":::")

        fields match {
          case Array(id, image, command, created, status, ports, names) => Some(DockerPsLine(id, image, command, created, status, ports, names))
          case other => None
        }
    }

  }


def dockerStopExceptNetbeans() = {
  val psOutputFormat = "{{.ID}}:::{{.Image}}:::{{.Command}}:::{{.CreatedAt}}:::{{.Status}}:::ports={{.Ports}}:::{{.Names}}"
  val psLines = (%%docker("ps", "-a", s"--format=$psOutputFormat")).out.lines
  val containers = psLines.flatMap { rawLine => DockerPsLine(rawLine) }
  val nonNetbeansContainers = containers.filterNot(_.names=="netbeans")
  nonNetbeansContainers foreach { container => 
    %docker("stop", container.id) 
    // println("nonnetbeans id: " + container.id)
  }
  
}

def dockerStartExceptNetbeans() = {
  val psOutputFormat = "{{.ID}}:::{{.Image}}:::{{.Command}}:::{{.CreatedAt}}:::{{.Status}}:::ports={{.Ports}}:::{{.Names}}"
  val psLines = (%%docker("ps", "-a", s"--format=$psOutputFormat")).out.lines
  val containers = psLines.flatMap { rawLine => DockerPsLine(rawLine) }
  val nonNetbeansContainers = containers.filterNot(_.names=="netbeans")
  nonNetbeansContainers foreach { container => 
    %docker("start", container.id) 
    // println("nonnetbeans id: " + container.id)
  }
  
}

