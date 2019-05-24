package foo
sealed trait Line

case class SpokenLine(character: PlayCharacter, content: String) extends Line {
  val originalScriptFormat: String = character.name + ". " + content
}

case class StageDirection(content: String) extends Line

case class BlankLine() extends Line

case class CombinedSpokenLine(characters: Set[PlayCharacter], content: String) extends Line
