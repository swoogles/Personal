package com.billding.clients

import ammonite.ops._
import com.billding.libraries.{FileOperations, TextManipulation}

object UltraRepl{
  def main(args: Array[String]): Unit = {
    println("hi     every     body")
    println(TextManipulation.stdSpacing("hi     every     body"))
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

