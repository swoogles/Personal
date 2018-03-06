package ultraRepl.com.billding
import ammonite.ops.Path

object TextManipulation {
  def camelToUnderscores(name: String) = "[A-Z\\d]".r.replaceAllIn(name, {m =>
    "_" + m.group(0).toLowerCase()
  })
  def underscoreToCamel(name: String) = "_([a-z\\d])".r.replaceAllIn(name, {m =>
    m.group(1).toUpperCase()
  })

	val whiteSpace =
		"\\s+"

	def stdSpacing(line: String): String =
		line
			.trim
			.replaceAll(whiteSpace, " ")

}

case class NumberedLine(number: Int, line: String) {
  def containsIgnoreCase(key: String): Boolean = 
    //line.toUpperCase.contains(key.toUpperCase)
    TextManipulation.underscoreToCamel(line)
      .toUpperCase
      .contains(TextManipulation.underscoreToCamel(key).toUpperCase)
}

case class NumberedFileContent(file: Path, content: Stream[NumberedLine])

