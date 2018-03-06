package com.billding.libraries

import ammonite.ops.{Path, home, ls, read}

import scala.util.Success
import scala.util.Try

class FileOperations(dir: Path, pathFilters: List[Path=>Boolean]) {

  def filteredFiles(filters: List[Path=>Boolean]) = {
    (ls.rec! dir).filter{ file=> 
      filters.forall( filt=>
        filt(file)
      )
    }
  }

  def filesGroupedByExtension =
    (ls.rec! dir )
      .groupBy { case file => file.ext }

  def readFileAndHandleExceptions(file: Path): Try[Stream[(String, Int)]] =
    Try { read.lines(file).zipWithIndex.toStream }

  def paramaterizedSuccessfullyReadFiles(pathFilters: List[Path=>Boolean]) = 
    filteredFiles(pathFilters)
      .toStream
      .map { file =>
        val readResult: Try[Stream[(String, Int)]] = readFileAndHandleExceptions(file) 
        readResult map { contents => 
          NumberedFileContent(file, contents map { tup => NumberedLine(tup._2,tup._1) } )
        }
      }.collect{case Success(contents) => contents}

  def successlyReadFiles: Stream[NumberedFileContent] =
    paramaterizedSuccessfullyReadFiles(pathFilters)

  def fullContent(bah: String)(implicit wd: Path): Stream[NumberedFileContent] = 
    successlyReadFiles 
      .map{file=>println(file);file}
      .filter (!_.content.isEmpty)

  def contentUniqueLineMap()(implicit wd: Path) = 
    fullContent("blahdeblah")
      .map { x => (x.file, x.content.groupBy { y => TextManipulation.stdSpacing(y.line) }
                             .map { case (key, value) => (key, value.length ) } ) // TODO There's got to be a simpler way.
      }

  def searchForTerm: String=> Stream[NumberedFileContent] = 
    (searchTerm: String) =>
      successlyReadFiles 
        .map { case NumberedFileContent(file, content) => 
          NumberedFileContent(file, content.filter{_.containsIgnoreCase(searchTerm)})
        } filter (!_.content.isEmpty)

}


