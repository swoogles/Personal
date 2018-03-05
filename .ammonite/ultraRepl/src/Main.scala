package ultraRepl

import ultraRepl.libraries.Text
import ultraRepl.com.billding.TextManipulation
import ultraRepl.com.billding.FileOperations
import ultraRepl.com.billding.git

import ammonite.ops._

object UltraRepl{
  def main(args: Array[String]): Unit = {
    println("hi     every     body")
    println(Text.stdSpacing("hi     every     body"))
    println(TextManipulation.camelToUnderscores("thisShouldBePrintedInSnakeCase"))
    val fileOps = new FileOperations(home, List(path=>path.ext==("java")))
    /*
    println(
      fileOps.filteredFiles(List()).take(5)
    )
    */
   // git.status
  }
}

