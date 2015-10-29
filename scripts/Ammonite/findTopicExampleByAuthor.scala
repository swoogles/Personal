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

case class NumberedLine(line: String, number: Int)
val tupledNumberedLine = NumberedLine.apply _

val home = root/'home/'bfrasure
def ammoScript = home/'Repositories/'Personal/"scripts"/'Ammonite/"findTopicExampleByAuthor.scala"
def vimAmmo = %vim ammoScript
def appendScript(newLine: String) = { write.append(ammoScript, "\n" + newLine) }

val extensionsOfInterest = List(".jsp", ".java", ".js")

val badExtensions = List(".swp", ".jar")

def filterExtension(file: Path): Boolean = {extensionsOfInterest.exists(file.last.contains) && !badExtensions.exists(file.last.contains)}

def filterTinyMCE(file: Path): Boolean = {!file.segments.exists(segment => segment == "tiny_mce" || segment == "tinymce")}

def filteredFiles = ls.rec! wd |? {file=> filterExtension(file) && filterTinyMCE(file) }

def filesExcludingBuildDir = filteredFiles |? {!_.segments.contains("build")} toStream


//def allFileContents: Seq[(Path, Vector[NumberedLine])] = filteredFiles map { file => (file, tupledNumberedLine.tupled(read.lines(file) zipWithIndex)) }
def allFileContents: Seq[(Path, Vector[NumberedLine])] = filteredFiles map { file => (file, read.lines(file).zipWithIndex.map(tupledNumberedLine.tupled)) }

// This returns: (fileName, (matchingLine, lineNum)*)
def searchForTerm(searchTerm: String): Seq[(Path, Vector[NumberedLine])] = allFileContents map { case (file: Path,  results: Vector[NumberedLine]) => (file, results filter (_.line.toUpperCase.contains(searchTerm.toUpperCase)))  } filter (!_._2.isEmpty)
