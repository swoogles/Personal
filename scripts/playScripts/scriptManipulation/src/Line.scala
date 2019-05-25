package foo
sealed trait Line

case class SpokenLine(character: PlayCharacter, content: String) extends Line {
  val originalScriptFormat: String = character.name + ". " + content
}

case class StageDirection(content: String) extends Line

case class BlankLine() extends Line

case class CombinedSpokenLine(characters: Set[PlayCharacter], content: String) extends Line

case class EveryoneLine(characters: Set[PlayCharacter], content: String) extends Line

case object PageBreak extends Line {
  val originalScriptFormat: String = "------------------"
}

case class PageNumber(number: Int)

case object StartLine extends Line {
  val originalScriptFormat: String = "Start"
}

case object EndLine extends Line {
  val originalScriptFormat: String = "End"
}
