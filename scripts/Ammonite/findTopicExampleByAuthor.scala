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

def camelToUnderscores(name: String) = "[A-Z\\d]".r.replaceAllIn(name, {m =>
    "_" + m.group(0).toLowerCase()
})
def underscoreToCamel(name: String) = "_([a-z\\d])".r.replaceAllIn(name, {m =>
    m.group(1).toUpperCase()
})

case class BlameFields(hash: String, author: String, commitDate: String, lineNumber: String, lineContent: String) 
object BlameFields {
  def apply(rawLine: String): BlameFields = {
    val pieces = rawLine.split("\\s+")
    pieces.toList match {
      case hash :: author :: commitDate :: lineNumber :: remainingContentWords =>
        val blameLineContent = lineNumber :: remainingContentWords reduce(_+ " " + _)
        BlameFields(hash, author, commitDate, lineNumber, blameLineContent)
      }
  }
  def apply(rawLines: Stream[String]): Stream[BlameFields] = {
    rawLines map { BlameFields(_) }
  }
}

object Git {
  import ammonite.ops.ImplicitWd._
  def blame(file: Path): Stream[BlameFields] = {
    val commandResult = %%git ('blame, "--date", "short", "-e", file)
    commandResult.map { BlameFields(_) } toStream
  }

  def log(): Int = 
    %git ('log)

  def today(): Int = 
    %git ('today)
}

case class NumberedLine(number: Int, line: String) {
  def containsIgnoreCase(key: String): Boolean = 
    //line.toUpperCase.contains(key.toUpperCase)
    underscoreToCamel(line).toUpperCase.contains(underscoreToCamel(key).toUpperCase)
}
case class NumberedFileContent(file: Path, content: Stream[NumberedLine])


val home = root/'home/'bfrasure
def ammoScript = home/'Repositories/'Personal/"scripts"/'Ammonite/"findTopicExampleByAuthor.scala"
val appSubscriberDir: Path = root/'home/'bfrasure/'NetBeansProjects/'smilereminder3/'appSubscriber
val smilereminder3Dir: Path = root/'home/'bfrasure/'NetBeansProjects/'smilereminder3

def vimAmmo = {
  import ammonite.ops.ImplicitWd._
  %vim ammoScript
}
val extensionsOfInterest = List(".jsp", ".java", ".js")
val badExtensions = List(".swp", ".jar")

def appendScript(newLine: String) = 
  write.append(ammoScript, "\n" + newLine)

def hasAnApprovedExtension(file: Path): Boolean = 
  extensionsOfInterest.exists(file.last.contains) && !badExtensions.exists(file.last.contains)

def notATinyMCEFile(file: Path): Boolean = 
  !file.segments.exists(segment => segment == "tiny_mce" || segment == "tinymce")

def pathFilters: List[Path=>Boolean] = 
  List(
    hasAnApprovedExtension, 
    notATinyMCEFile, 
    !_.segments.contains("dist"),
    !_.segments.contains("build")
  )

def filteredFiles = {
  (ls.rec! smilereminder3Dir).filter{ file=> 
  //(ls.rec! appSubscriberDir).filter{ file=> 
    pathFilters.forall( filt=>
      filt(file)
    )
  }
}

def readFileAndHandleExceptions(file: Path): Try[Stream[(String, Int)]] =
  Try { read.lines(file).zipWithIndex.toStream }

def successlyReadFiles: Stream[NumberedFileContent] =
  filteredFiles 
    .map { file =>
      val readResult: Try[Stream[(String, Int)]] = readFileAndHandleExceptions(file) 
      readResult map { contents => 
        NumberedFileContent(file, contents map { tup => NumberedLine(tup._2,tup._1) } )
      }
    }.collect{case Success(contents) => contents}.toStream

def searchForTerm(searchTerm: String): Stream[NumberedFileContent] = 
  successlyReadFiles 
    .map { case NumberedFileContent(file, content) => NumberedFileContent(file, content
      .filter {_.containsIgnoreCase(searchTerm)})
    } filter (!_.content.isEmpty)

def fullContent( bah: String): Stream[NumberedFileContent] = 
  successlyReadFiles 
    .map { case NumberedFileContent(file, content) => NumberedFileContent(file, content)
    } filter (!_.content.isEmpty)

val desiredAuthors = List("bill", "tylero", "scott", "jay", "garrett", "brian", "david")

def attachBlameInformation(matchingFiles: Stream[NumberedFileContent]): Stream[Stream[BlameFields]] = {
  matchingFiles map { case NumberedFileContent(file, content) =>
    val blameResults: Stream[BlameFields] = Git.blame(file) // This fails if a noncommitted file is searched
    content.filter{ case NumberedLine(number,_) => 
      desiredAuthors.exists(blameResults(number).author.contains) 
    }.map{case NumberedLine(number, _) => blameResults(number)}
  } filter { !_.isEmpty }
}

def calculateMostProlificAuthor(blameFields: Stream[Stream[BlameFields]] ) = {
  val results: Map[String, Stream[BlameFields]] = 
  blameFields.flatten groupBy { case line =>
    line.author // returns  Map[String, Stream[BlameFields]]
  }
  (results map { mapByAuthor => (mapByAuthor._1, mapByAuthor._2.length)} toSeq).sortBy{_._2}.reverse
}

def fullSearch = 
  searchForTerm _ andThen attachBlameInformation

def fullBlameContent = 
  fullContent _ andThen attachBlameInformation

def authorRanking = 
  searchForTerm _ andThen attachBlameInformation andThen calculateMostProlificAuthor

//****************** NEW SCRIPT******************
/* Goal: detect duplicate lines in source files
 * 1. Don't get too clever with near matches, but handle obvious ignorables like whitespace lines
 * 2. Find most repetitive author <- Careful with the results of this one...
 * 3. Find term that's used most ofen on these lines
 *    -Filter out language constructs. Too much noise there.
 */

val whiteSpace = 
  "\\s+"

def stdSpacing(line: String): String =
  line.trim.replaceAll(whiteSpace, " ")

def unassociatedContent(nfcs: Seq[NumberedFileContent]): Seq[String] = 
  nfcs || (_.content.map{ x=>stdSpacing(x.line)}) |? (!_.isEmpty)
  
def countedResults(lines: Seq[String]): Map[String, Int] = 
  lines.groupBy{ x => x} | {x=>(x._1, x._2.length)}

def sortedResults = 
  countedResults(unassociatedContent(successlyReadFiles)).toList.sortBy{ - _._2 }

def extractImportedClass(importLine: String) =
  importLine.replace("import","").replace(";","").trim

def mostImportedClasses = 
  sortedResults |? (_._1.contains("import")) | (tup => (extractImportedClass(tup._1), tup._2))

def mostImportedCommunitectClasses = 
  mostImportedClasses |? (_._1.contains("communitect"))

def duplicateLines = 
  successlyReadFiles


/* First forays into scraping the list of engineers off of the intranet site. */
// 
//load.ivy("org.jsoup" %% "jsoup" % "1.8.3")
// After this, I was able to figure out how to connect to a page and download the contents purely through the REPL
//val intranetConn = org.jsoup.Jsoup.connect("http://intranet.communitect.com/")
//val intranetContent = intranetConn.get
// Unfortunately at this point I realized all the content is happening behind some JS that could make it hard to access the data
//Looks like the JavaFX WebView might be the right way to go here. Since JSoup is only meant to parse HTML, you need a more
//full browser to interact with heavy JS sites.
// Scalafx should wrap things up in a pleasing way.
//load.ivy( "org.scalafx" %% "scalafx" % "8.0.60-R9")
//import scalafx.scene.web.WebView
//
def groupedByDate() = 
  (fullBlameContent("bah") || (_ map (_.commitDate.take(4))) groupBy { case year => year }  map { tup => (tup._1, tup._2.length) } toSeq).sortBy(_._1)

// appSubscriber groupedByDate results
//res4: Seq[(String, Int)] = ArrayBuffer(("2007", 6581), ("2008", 806), ("2009", 661), ("2010", 4760), ("2011", 3787), ("2012", 4019), ("2013", 28852), ("2014", 52357), ("2015", 10983))

// full smilereminder3 stats
// Number of lines that were contributed in a given year
//groupedByDate map { x => (x._1, x._2, (x._2.toFloat/res12*100).toInt + "%") }
//res19: Seq[(String, Int, String)] = ArrayBuffer(
//  ("2006", 35111, "8%"),
//  ("2007", 15848, "3%"),
//  ("2008", 4508, "1%"),
//  ("2009", 12779, "3%"),
//  ("2010", 33055, "8%"),
//  ("2011", 29100, "7%"),
//  ("2012", 32700, "8%"),
//  ("2013", 87250, "21%"),
//  ("2014", 84817, "21%"),
//  ("2015", 66555, "16%")
//)
