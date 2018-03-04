package ultraRepl

object libraries {
object Text {
  val whiteSpace = 
    "\\s+"

  def stdSpacing(line: String): String =
    line
      .trim
      .replaceAll(whiteSpace, " ")



}
}
