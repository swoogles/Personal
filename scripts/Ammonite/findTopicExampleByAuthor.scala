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

case class BlameFields(hash: String, author: String, commitDate: String, lineNumber: String, lineContent: String) 
object BlameFields {
  def apply(rawLine: String): BlameFields = {
    val pieces = rawLine.split("\\s+")
    val hash = pieces(0)
    val author = pieces(1)
    val commitDate = pieces(2)
    val lineNumber = pieces(3)
    val blameLineContent = pieces.drop(3).reduce(_+ " " + _)
    BlameFields(hash, author, commitDate, lineNumber, blameLineContent)
  }
  def apply(rawLines: Vector[String]): Vector[BlameFields] = {
    rawLines map { BlameFields(_) }
  }
}

object Git {
  def blame(file: Path): Vector[BlameFields] = {
    val commandResult = %%git ('blame, "--date", "short", "-e", file)
    commandResult.toVector.map { BlameFields(_) }
  }

  def log(): Int = 
    %git ('log)

  def today(): Int = 
    %git ('today)
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
  write.append(ammoScript, "\n" + newLine)

def hasAnApprovedExtension(file: Path): Boolean = 
  extensionsOfInterest.exists(file.last.contains) && !badExtensions.exists(file.last.contains)

def isNotATinyMCEFile(file: Path): Boolean = 
  !file.segments.exists(segment => segment == "tiny_mce" || segment == "tinymce")

def filt: Path=>Boolean = isNotATinyMCEFile

def pathFilters: List[Path=>Boolean] = 
  List(
    hasAnApprovedExtension, 
    isNotATinyMCEFile, 
    !_.startsWith(distDir),
    !_.segments.contains("build")
  )

def filteredFiles = 
  ls.rec! wd |? { file=> 
    pathFilters.forall( filt=>
      filt(file)
    )
  }

def readFileAndHandleExceptions(file: Path): Try[Vector[(String, Int)]] =
  Try {
    read.lines(file).zipWithIndex
  }

def allFileContentsAttempts: Seq[Try[NumberedFileContent]] =
  filteredFiles 
  .map { file =>
    val readResult: Try[Vector[(String, Int)]] = readFileAndHandleExceptions(file) 
    readResult map { contents => 
      NumberedFileContent(file, contents map { tup => NumberedLine(tup._2,tup._1) } )
    }
  }

val allFileContents: Seq[NumberedFileContent] = allFileContentsAttempts.collect{case Success(contents) => contents}

def searchForTerm(searchTerm: String): Seq[NumberedFileContent] = 
  allFileContents 
  .map { nfc => NumberedFileContent(nfc.file, nfc.content
    .filter (_.containsIgnoreCase(searchTerm)))
  } filter (!_.content.isEmpty)

val desiredAuthors = List("bill", "tylero", "scott", "jay", "garrett", "brian", "david")

def attachBlameInformation(matchingFiles: Seq[NumberedFileContent]): Seq[Vector[BlameFields]] = {

  matchingFiles map { nfc =>
    val blameResults: Seq[BlameFields] = Git.blame(nfc.file) // This fails if a noncommitted file is searched

    val matchesDesiredAuthors = 
      nfc.content.filter{ nl => 
        desiredAuthors.exists(blameResults(nl.number).author.contains) 
      }.map(nl => blameResults(nl.number))

    matchesDesiredAuthors
  } filter { !_.isEmpty }
}

def fullSearch = 
  searchForTerm _ andThen attachBlameInformation
