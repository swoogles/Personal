import scala.sys.process._
//"ls" #| "grep .scala" #&& Seq("sh", "-c", "scalac *.scala") #|| "echo nothing found" lines

// This uses ! to get the exit code
def fileExists(name: String) = Seq("test", "-f", name).! == 0

// This uses !! to get the whole result as a string
val dirContents = "ls".!!

// This "fire-and-forgets" the method, which can be lazily read through
// a Stream[String]
def sourceFilesAt(baseDir: String): Stream[String] = {
    val cmd = Seq("find", baseDir, "-name", "*.scala", "-type", "f")
      cmd.lines
}

def getWord( line:String, index:Int ):String = {
  line.split("\\s+")(index)
}

def createCommand( command:Seq[String], arguments:Seq[String] ):Seq[String] = {
  command++arguments
}

def runCommand( command:Seq[String] ):String = {
  command.lineStream.last
}

def runFullCommand( command:Seq[String], arguments:Seq[String] ):String = {
  val fullCommand = (command++arguments)
  println("Full Command: " + fullCommand )
  fullCommand.lineStream.last
}


val files = sourceFilesAt(".")
//files.foreach(println)

    //val cmd = Seq("find", baseDir, "-name", "*.scala", "-type", "f")

//case class Activator(command:String

val activator = Seq("./activator") 
val clean = Seq("clean")
val compile = Seq("compile")
val dist = Seq("dist")

val targetDir = "./target/universal/"
val zipFile = "srxsubscriber-1.0.zip"
val unzip = Seq("unzip")
val zipLocation = Seq(targetDir+zipFile)
val unzipLocationFlag = Seq("-d")
val unzipLocation = Seq(targetDir)
val zipParams = zipLocation++unzipLocationFlag++unzipLocation

for ( i <- 0 to 0 ) {
  //var cmdStatus = runFullCommand(activator,clean)
  //var timeInSeconds = getWord(cmdStatus, 3)
  //println("Build time: " + timeInSeconds + " seconds")
  runFullCommand(unzip,zipParams)
}
