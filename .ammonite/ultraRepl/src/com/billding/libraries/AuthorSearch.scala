package com.billding.libraries

import ammonite.ops.{Path, home}
import com.billding.clients.{AdvancedGit, BlameFields, Git}

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
// TODO How about we make content a def that's called as needed? Right now we have to read
// from the disk to make an NFC instance.

object AuthorSearch {
  val targetDir = home/'CmtRepositories/'dev

  val extensionsOfInterest = List("jsp", "java", "js")
  val badExtensions = List(".swp", ".jar", ".json", ".min.js")

  def hasAnApprovedExtension(file: Path): Boolean = 
    extensionsOfInterest.exists(file.ext.equals) && !badExtensions.exists(file.last.contains)

  implicit class SuperCollection[A](seq: Seq[A]) {
    def containsAny(other: Seq[A]) = 
      other.exists(seq.contains)
  }

  def pathFilters: List[Path=>Boolean] = 
    List(
      hasAnApprovedExtension, 
      !_.segments.containsAny( List(
        "dist", 
        "build", 
        "node_modules",
        "ckeditor"
        ) )
    )



  val fileOperations = new FileOperations(targetDir, pathFilters)

  //Map[String, Stream[Int]]
  def sortMapByNumberOfValueElements[A](map: Map[String, Int]): Stream[(String, Int)] =
   map.toStream.sortBy{-_._2}

  def isLineSignificant(lineOfCode: String) =
    lineOfCode.count{ Character.isLetter(_) } > 5

  def hasSignificantDuplicates( tup: (Path, Stream[(String, Int)])): Boolean =
    (!tup._2.isEmpty)

  def fileContentRankedByMostDuplicatedLine(implicit wd: Path) = 
    // Must be duplicated at least three times to show up in results
    fileOperations.contentUniqueLineMap
      .map{x => (x._1, sortMapByNumberOfValueElements(x._2)
                         .filter{x=>isLineSignificant(x._1) && x._2 > 2}) 
      }.filter(hasSignificantDuplicates)

  def numberOfMostDuplicatedLineOccurances(implicit wd: Path) = 
    fileContentRankedByMostDuplicatedLine
      .map{ x => (x._1, x._2.take(1).map {_._2 }.head )}

  def calculateMostProlificAuthor(blameFields: Stream[Stream[BlameFields]] ): Seq[(String, Int)] = {
    val results: Map[String, Stream[BlameFields]] = 
    blameFields.flatten groupBy { case line =>
      line.author // returns  Map[String, Stream[BlameFields]]
    }
    results
      .map { mapByAuthor => (mapByAuthor._1, mapByAuthor._2.length)}
      .toSeq
      .sortBy{_._2}
      .reverse
  }

  def fullBlameContent(implicit wd: Path): (String) => Stream[Stream[BlameFields]] = 
    (fileOperations.fullContent _)
      .andThen(AdvancedGit.attachBlameToMultipleFiles)

  def fullSearch(implicit wd: Path): String => Stream[Stream[BlameFields]] =
    fileOperations.searchForTerm
      .andThen(AdvancedGit.attachBlameToMultipleFiles)

  def authorRanking(implicit wd: Path): String => Seq[(String, Int)] =
    fullSearch
      .andThen(calculateMostProlificAuthor)

  //****************** NEW SCRIPT******************
  /* Goal: detect duplicate lines in source files
   * 1. Don't get too clever with near matches, but handle obvious ignorables like whitespace lines
   * 2. Find most repetitive author <- Careful with the results of this one...
   * 3. Find term that's used most ofen on these lines
   *    -Filter out language constructs. Too much noise there.
   */

  def unassociatedContent(nfcs: Seq[NumberedFileContent]): Seq[String] = 
    nfcs
      .flatMap(_.content.map{ x=>TextManipulation.stdSpacing(x.line)})
      .filter(!_.isEmpty)
    
  def countedResults(lines: Seq[String]): Map[String, Int] = 
    lines
      .groupBy{ x => x}
      .map{x=>(x._1, x._2.length)}

      // TODO Move this into .ammonite/ultraRepl project
  def sortedResults: Seq[(String, Int)] = 
    countedResults(unassociatedContent(fileOperations.successlyReadFiles))
      .toSeq
      .sortBy{ - _._2 }

  //res4: Seq[(String, Int)] = ArrayBuffer(("2007", 6581), ("2008", 806), ("2009", 661), ("2010", 4760), ("2011", 3787), ("2012", 4019), ("2013", 28852), ("2014", 52357), ("2015", 10983))
  def groupedByDate()(implicit wd: Path): Seq[(Int, Int)] = 
    fullBlameContent(wd)("bah")
      .flatMap{_ map (blameFields => blameFields.commitDate.getYear)}
      .groupBy { identity }
      .map { tup => (tup._1, tup._2.length) }
      .toSeq
      .sortBy(_._1)


  /*
  // Number of lines that were contributed in a given year
  def fullRepoStats() =
    groupedByDate map { x => (x._1, x._2, (x._2.toFloat/res12*100).toInt + "%") }
  */

  /*
  res19: Seq[(String, Int, String)] = ArrayBuffer(
    ("2006", 35111, "8%"),
    ("2007", 15848, "3%"),
    ("2008", 4508, "1%"),
    ("2009", 12779, "3%"),
    ("2010", 33055, "8%"),
    ("2011", 29100, "7%"),
    ("2012", 32700, "8%"),
    ("2013", 87250, "21%"),
    ("2014", 84817, "21%"),
    ("2015", 66555, "16%")
  )
  */

  // authorRanking("Client")
  // searchForTerm("PatientDescriptionLogic").foreach(println)

  // authorRanking("PatientDescriptionLogic").foreach(println)

  // fullSearch("PatientDescriptionLogic").foreach(println)

  // List all authors and number of commits.
  // git shortlog --summary --numbered


}
