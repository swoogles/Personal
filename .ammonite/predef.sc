val scalaVersion = scala.util.Properties.versionNumberString
interp.load.ivy(
  "com.lihaoyi" %
  s"ammonite-shell_$scalaVersion" %
  ammonite.Constants.version
)
@
val shellSession = ammonite.shell.ShellSession()
import shellSession._
import ammonite.ops._
import ammonite.shell._
ammonite.shell.Configure(interp, repl, wd)

// Maybe this can work if it happens in a separate module that's pre-loaded?
//%mill("ultraRepl.assembly")
// This requires ultraRepl to be "mill assembly"'ed
import $cp.out.ultraRepl.assembly.dest.out

// Reenable once I figure out how to make this co-exist with new amm project
// import $file.scripts.cmtCommandCenter, cmtCommandCenter.Cmt

import com.billding.clients.Git
import com.billding.clients.Gradle
import com.billding.clients.Docker
import com.billding.clients.Cmt
import com.billding.clients.Kafka
// TODO Cmt-specific. Figure out how to completely separate.
// I think it can just be a second project akin to the primary ultraRepl one
import com.billding.clients.Setup


def ll = %ls("-l")

def searchInFileTypes(term: String, extensions: String*) =  {
  val files =
    (ls.rec! wd)
    .filter(file=>(extensions.contains(file.ext)))
    .filter(file=>(stat! file).isDir == false)

  files.filter{ file=> 
    read.lines(file)
      .exists(line=>line.contains(term))
  }
}
