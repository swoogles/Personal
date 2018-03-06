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

// This requires ultraRepl to be "mill assembly"'ed
import $cp.out.ultraRepl.assembly.dest.out

// Reenable once I figure out how to make this co-exist with new amm project
// import $file.scripts.cmtCommandCenter, cmtCommandCenter.Cmt

import ultraRepl.com.billding.git
import ultraRepl.com.billding.Gradle
import ultraRepl.com.billding.Docker
import ultraRepl.com.billding.Setup

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
