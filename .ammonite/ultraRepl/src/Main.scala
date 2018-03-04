package ultraRepl

import ultraRepl.libraries.Text
import ultraRepl.com.billding.TextManipulation

object Example{
  def main(args: Array[String]): Unit = {
    println("hi     every     body")
    println(Text.stdSpacing("hi     every     body"))
    println(TextManipulation.camelToUnderscores("thisShouldBePrintedInSnakeCase"))
  }
}

