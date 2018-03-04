import $file.^.libraries.fileOperations, fileOperations.FileOperations

val targetDir = home/'CmtRepositories/'dev
val fileOperations = new FileOperations(targetDir, pathFilters)

object JavaAnalysis {
  def extractImportedClass(importLine: String): String =
    importLine
      .replace("import","")
      .replace(";","")
      .trim

  def mostImportedClasses = 
    sortedResults
      .filter(_._1.contains("import"))
      .map{case(tup1, tup2) => (extractImportedClass(tup1), tup2)}

  def mostImportedCmtClasses = 
    mostImportedClasses
      .filter(_._1.contains("cmt"))

  def duplicateLines = 
    fileOperations.successlyReadFiles
}


