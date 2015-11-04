load.ivy("com.lihaoyi" %% "ammonite-shell" % ammonite.Constants.version)
@
val sess = ammonite.shell.ShellSession()
import sess._
import ammonite.ops._
ammonite.shell.Configure(repl, wd)

val ammoScriptDir = home/'Repositories/'Personal/"scripts"/'Ammonite

//val ammoScript = ammoScriptDir/"findTopicExampleByAuthor.scala"
val ammoScript = ammoScriptDir/"duplicateLines.scala"

load.exec(ammoScriptDir/"findTopicExampleByAuthor.scala")
load.exec(ammoScriptDir/"duplicateLines.scala")
