package com.billding.libraries

import ammonite.ops.home

object JavaAnalysis {
  val targetDir = home/'CmtRepositories/'dev
  val fileOperations = new FileOperations(targetDir, List())
  def extractImportedClass(importLine: String): String =
    importLine
      .replace("import","")
      .replace(";","")
      .trim

  def mostImportedClasses = 
    AuthorSearch.sortedResults
      .filter(_._1.contains("import"))
      .map{case(tup1, tup2) => (extractImportedClass(tup1), tup2)}

  def mostImportedCmtClasses = 
    mostImportedClasses
      .filter(_._1.contains("cmt"))

  def duplicateLines = 
    fileOperations.successlyReadFiles
}

