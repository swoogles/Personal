package ultraRepl.com.billding

import Bash.{backgroundCommand, backgroundSudoCommand}

import ammonite.ops.{%, %%, home, Path, RelPath}

import scala.util._
object Cmt {
  def vpnPid()(implicit wd: Path): Option[String] =
    Try {
      (%%pidof("openfortigui"))
        .out
        .lines
        .head
    } match {
      case Failure(ex) => None
      case Success(x) => Some(x)
    }

    def killVpn()(implicit wd: Path) =
      vpnPid()
        .foreach(pid=>backgroundSudoCommand(s"kill -9 $pid"))

  def startVpn()(implicit wd: Path) =
    backgroundSudoCommand("openfortigui")

  private def startWavebox()(implicit wd: Path) =
    backgroundCommand("wavebox")

  private def startIntellij()(implicit wd: Path) = {
    val toolBoxDir = home/ RelPath(".local/share/JetBrains/Toolbox")
    val toolBoxBinDir = toolBoxDir / RelPath("apps/IDEA-U/ch-0/173.4127.27/bin")
    backgroundCommand(s"$toolBoxBinDir/idea.sh")
  }

  def start()(implicit wd: Path) = {
    startVpn()
    startWavebox()
    startIntellij()
  }

  val browser = new ClientBuilder("firefox")
  object splunk {
    def searchPage() = browser("https://logging.int.collectivemedicaltech.com/en-US/app/search/search")
  }

  object confluence {
    def gettingStarted() = browser("https://confluence.collectivemedicaltech.com/display/~doug.erickson/Getting+Started")
  }

  object bitbucket {
    def repos() = browser("https://bitbucket.collectivemedicaltech.com/projects")
  }
}

