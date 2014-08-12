import java.io.File
import java.util.ArrayList

def getSubDirectories(file:File):Array[File] = {
  if ( file.isDirectory )
    file.listFiles.filter(_.isDirectory)
  else
    new Array[File](0)

}

val subdirectories = getSubDirectories( new File(".") )



//val fileNames = new File(".").listFiles.map(_.getName).toList
////println(fileNames)
//
//val files = new File(".").listFiles
////println(files)
//
//      val narrowFileNames = files.filter(_.getName.contains("scala")).map(_.getName).toList
//
////println(narrowFileNames)
//
//      val directoryNames = files.filter(_.isDirectory).map(_.getName).toList
//
////println(directoryNames)
//
//      val subDirectoryNames = files.filter(_.isDirectory).flatMap(_.listFiles).map(_.getName).toList.sorted
//
//println(subDirectoryNames)
//
//println("bunk")
