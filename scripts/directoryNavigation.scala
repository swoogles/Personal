import java.io.File
import java.util.ArrayList

def getSubDirectories(file:File):Option[Array[File]] = {
  if ( file.isDirectory )
    Some(file.listFiles.filter(_.isDirectory))
    //file.listFiles.filter(_.isDirectory).flatMap(getSubDirectories(_))
  else
    None

}

val subdirectories = getSubDirectories( new File(".") )
subdirectories.foreach(println)



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
