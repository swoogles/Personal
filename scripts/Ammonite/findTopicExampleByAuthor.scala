import ammonite.ops._
import ammonite.repl.Repl._
import ammonite.repl.Repl._
//import ammonite.ops.ImplicitWd._
import sys.process._

import scala.util.Failure
import scala.util.Success
import scala.util.Try
/*
 * Overal goal:
 * Find all mentions of a particular topic in a project by a white list of authors
 * 1. Get all potential files
 *    -JSP, java, js, css, scss
 *    -Special case for struts-config.xml
 * 2. Find/store all files that mention topic of interest
 * 3. Generate/store git blame information for that file
 * 4. Search for topic and approved author on same line
 * 5. Present results in a pleasing way
 */

object Git {
  def blame(file: Path): CommandResult = 
    %%git ('blame, "--date", "short", "-e", file)
}

case class NumberedLine(number: Int, line: String) {
  def containsIgnoreCase(key: String): Boolean = 
    line.toUpperCase.contains(key.toUpperCase)
}
case class NumberedFileContent(file: Path, content: Vector[NumberedLine])

val home = root/'home/'bfrasure
def ammoScript = home/'Repositories/'Personal/"scripts"/'Ammonite/"findTopicExampleByAuthor.scala"
def vimAmmo = %vim ammoScript
val extensionsOfInterest = List(".jsp", ".java", ".js")
val badExtensions = List(".swp", ".jar")
val distDir: Path = 'dist

def appendScript(newLine: String) = 
  { write.append(ammoScript, "\n" + newLine) }


def hasAnApprovedExtension(file: Path): Boolean = 
  extensionsOfInterest.exists(file.last.contains) && !badExtensions.exists(file.last.contains)

def isNotATinyMCEFile(file: Path): Boolean = 
  !file.segments.exists(segment => segment == "tiny_mce" || segment == "tinymce")

def filteredFiles = 
  ls.rec! wd |? { file=> hasAnApprovedExtension(file) && isNotATinyMCEFile(file) && !file.startsWith(distDir)}

def filesExcludingBuildDir = 
  filteredFiles |? {!_.segments.contains("build")} toStream

def readFileAndHandleExceptions(file: Path): Try[Vector[(String, Int)]] = {
  Try {
    read.lines(file).zipWithIndex
  }
  //catch { 
  //  case ex: java.nio.charset.MalformedInputException =>
  //    println("Hit a bad file. Oh well. Just keep moving.")
  //    Failure(ex)
  //}
}
def allFileContents: Seq[NumberedFileContent] =  {
  val successfullyReadFiles = filesExcludingBuildDir 
  .flatMap { file => println("file: " + file); readFileAndHandleExceptions(file) match { 
    case Success(contents) => Some(file, contents map { tup => NumberedLine(tup._2,tup._1) } )
    case Failure(ex) => None
  }
  } 
  successfullyReadFiles map { case (x:Path,y:Vector[NumberedLine]) => NumberedFileContent(x,y) }
}

def searchForTerm(searchTerm: String): Seq[NumberedFileContent] = 
  allFileContents 
  .map { nfc => NumberedFileContent(nfc.file, nfc.content
    .filter (_.containsIgnoreCase(searchTerm)))  
  } filter (!_.content.isEmpty)

val desiredAuthors = List("bill", "tylero", "scott", "jay", "garrett", "brian", "david")

def coalesceBlame(matchingFiles: Seq[NumberedFileContent]) = {

  matchingFiles map { nfc =>
    val blameResults: CommandResult = Git.blame(nfc.file)
    val blameLines: Seq[String] = blameResults.toList

    val matchesDesiredAuthors = 
      nfc.content.filter{ nl => 
        desiredAuthors.exists(blameLines(nl.number).contains) 
      }.map(nl => blameLines(nl.number))

    matchesDesiredAuthors
  } filter { !_.isEmpty }
}
