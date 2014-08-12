import java.io.File

val fileNames = new File(".").listFiles.map(_.getName).toList
//println(fileNames)

val files = new File(".").listFiles
//println(files)

      val narrowFileNames = files.filter(_.getName.contains("scala")).map(_.getName).toList

//println(narrowFileNames)

      val directoryNames = files.filter(_.isDirectory).map(_.getName).toList

//println(directoryNames)

      val subDirectoryNames = files.filter(_.isDirectory).flatMap(_.listFiles).map(_.getName).toList

println(subDirectoryNames)

println("bunk")
