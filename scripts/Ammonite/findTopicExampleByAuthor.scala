import ammonite.ops._
import ammonite.repl.Repl._
import ammonite.repl.Repl._
//import ammonite.ops.ImplicitWd._
import sys.process._

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

case class NumberedLine(number: Int, line: String) {
  def containsIgnoreCase(key: String): Boolean = 
    line.toUpperCase.contains(key.toUpperCase)
}
case class NumberedFileContent(file: Path, content: Vector[NumberedLine])

val home = 
  root/'home/'bfrasure

def ammoScript = 
  home/'Repositories/'Personal/"scripts"/'Ammonite/"findTopicExampleByAuthor.scala"

def vimAmmo = 
  %vim ammoScript

def appendScript(newLine: String) = 
  { write.append(ammoScript, "\n" + newLine) }

val extensionsOfInterest = 
  List(".jsp", ".java", ".js")

val badExtensions = 
  List(".swp", ".jar")

def filterExtension(file: Path): Boolean = 
  extensionsOfInterest.exists(file.last.contains) && !badExtensions.exists(file.last.contains)

def filterTinyMCE(file: Path): Boolean = 
  !file.segments.exists(segment => segment == "tiny_mce" || segment == "tinymce")

def filteredFiles = 
  ls.rec! wd |? { file=> filterExtension(file) && filterTinyMCE(file) }

def filesExcludingBuildDir = 
  filteredFiles |? {!_.segments.contains("build")} toStream

def allFileContents: Seq[NumberedFileContent] = 
  filesExcludingBuildDir 
  .map { file => (file, read.lines(file).zipWithIndex
    .map{ tup => NumberedLine(tup._2,tup._1)}) 
  } map { case (x:Path,y:Vector[NumberedLine]) => NumberedFileContent(x,y) }

// This returns: (fileName, (matchingLine, lineNum)*)
def searchForTerm(searchTerm: String): Seq[NumberedFileContent] = 
  allFileContents 
  .map { nfc => NumberedFileContent(nfc.file, nfc.content
    .filter (_.containsIgnoreCase(searchTerm)))  
  } filter (!_.content.isEmpty)

val desiredAuthor = "bill"

def coalesceBlame(matchingFiles: Seq[NumberedFileContent]) = {
  matchingFiles map { nfc =>
    val blameResults: CommandResult = 
      %%git ('blame, "--date", "short", "-e", nfc.file)
    val blameLines: Seq[String] = 
      blameResults.toList
    val matchesWithAuthor = 
      matchingFiles
      .map{ nfc => nfc.content.map{ nl => 
      //blameLines(nl.number).contains(desiredAuthor) // actual function desired
      (nl.number, blameLines(nl.number)) // just for debugging at the moment
      } 
    }
    matchesWithAuthor
    //%%git ('blame, nfc.file)
    //blameResults.toString.split("\n")
  }
}

def blameOutput = searchForTerm("jersey") map {nfc=> %%git ('blame, nfc.file) }
