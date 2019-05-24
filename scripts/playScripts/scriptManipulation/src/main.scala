package foo

import better.files._
import better.files.Dsl._
object Example{
  def wordsFrom(line: String): List[String] = {
    line.split("\\s+").toList
  }

  def linePrep(line: String): String = {
    wordsFrom(line.trim).tail
      .mkString(" ")
  }

  def linePrepWithoutTrimmingBeginning(line: String): String = {
    wordsFrom(line.trim)
      .mkString(" ")
  }

  def endsWithPunctuation(word: String): Boolean = {
    val punctuation = List(".", "!", "-", "?", ",")
    punctuation.foldLeft(false)(_ || word.endsWith(_))
  }

  def firstLetterOfWord(word: String): String = {
    val leadingQuote: String = if ( word.head == '"')  "\"" else ""
    val startIndex = if (leadingQuote.length > 0) 1 else 0

    val trailingQuote: String = if ( word.last == '"')  "\"" else ""
    val endIndex = if (trailingQuote.length > 0) word.length - 1 else word.length
    val quoteStrippedWord = word.substring(startIndex, endIndex)
    val innerWordResult =
      if (quoteStrippedWord == "...") {
        quoteStrippedWord
      } else if (endsWithPunctuation(quoteStrippedWord)) // Preserve punctuation.
        quoteStrippedWord.head + quoteStrippedWord.last.toString
      else
        quoteStrippedWord.head

    leadingQuote + innerWordResult + trailingQuote
  }

  def firstLetterOfEachWord(line: String): String = {
    val words = wordsFrom(line).tail // Ignores character name. Not the right way to handle this!
    words
      .map{ word => firstLetterOfWord(word) }
      .mkString(" ")
  }

  def splitIntoSentences(line: String): List[String] = {
    val punctuation = List('.', '!', '-', '?', ',')
    if (line.size == 0) {
      Nil
    } else {
      val (s1, s2) = line.span{ !punctuation.contains(_) }
      if (!s2.isEmpty) { // mv punctuation to end of previous sentence.
        s1+s2.head :: splitIntoSentences(s2.tail)
      } else {
        List(s1)
      }
    }
  }

  def firstWordOfEachSentence(line: String): String = {
    val preppedLine = linePrep(line)
    val sentences = splitIntoSentences(preppedLine)
    sentences.map { sentence =>
      val (firstWord :: restOfSentence) = wordsFrom(sentence.trim)
      val blackedOutWords = restOfSentence.map { word =>
        word.map{letter=>
          "_"
        }.mkString("") }
        .mkString(" ")

      // TODO rm blacked out punctuation mark, as it increases the masked length by 1 additional underscore
      val result =
        if (!restOfSentence.isEmpty)
          firstWord + " " + blackedOutWords + restOfSentence.last.last
        else
          firstWord + " " + blackedOutWords
      result
    }.mkString(" ")
  }

  private def writeNewLines(outFileName: String, newLines: List[String]) = {
    val file = cwd / "out" / (outFileName + ".txt")
    file.overwrite("")
    file.appendLines(newLines:_*)
  }

  def startsWithAnyCharacter(originalLine: String, characters: List[PlayCharacter]): Boolean = {
    characters.exists(character=> originalLine.startsWith(character.name))
  }

  def convertLinesWithDynamicCharacters(originalLines: List[String], targetCharacter: PlayCharacter, characters: List[PlayCharacter], action: String => String): List[String] = {
    originalLines.map(convertSingleLine(_, targetCharacter, characters, action))
  }

  def convertSingleLine(originalLine: String, targetCharacter: PlayCharacter, characters: List[PlayCharacter], action: String => String): String = {
    println("executing conversion with originalLine: " + originalLine)
    println( "Starts with target character: " + originalLine.startsWith(targetCharacter.name))
    originalLine match {
      case targetCharacterLine
        if targetCharacterLine startsWith (targetCharacter.name + ".") => {
          SpokenLine(targetCharacter, action.apply(targetCharacterLine)).originalScriptFormat
        }
      case otherCharacterLine if startsWithAnyCharacter(otherCharacterLine, characters) => {
        val character: String = otherCharacterLine.takeWhile(_ != '.')
        SpokenLine(PlayCharacter(character), linePrep(otherCharacterLine)).originalScriptFormat
      }
      case togetherLine if togetherLine startsWith "TOGETHER" => CombinedSpokenLine(characters.toSet, togetherLine).toString
      case stageDirection if stageDirection startsWith "(" => StageDirection(stageDirection).toString
      case emptyLine if emptyLine isEmpty => BlankLine().toString
      case comment if comment startsWith "//" => ""
      case other => "FAILURE: " + other
    }
  }

//  def totalProgram(outFileName: String, allLines: List[String], charlieAction: String=>String, randiAction: String=>String, tammyAction: String=>String) = {
//    val convertedLines = convertAllLines(allLines, charlieAction, randiAction, tammyAction)
//    writeNewLines(outFileName, convertedLines)
//  }

  def main(args: Array[String]): Unit = {
    val characters = List(
      PlayCharacter("CHARLIE"),
      PlayCharacter("TAMMY"),
      PlayCharacter("RANDI")
    )

    val actions: List[(String, String=>String)] =
      List(
        ("first_letter_of_each_word", firstLetterOfWord),
        ("first_word_of_each_sentence", firstWordOfEachSentence)
      )

    println("Hello World")

    val betterFile = cwd /  "WatchHill.txt"

    val fileLines = betterFile.contentAsString.split("\n").toList

//    totalProgram( "original_script", fileLines, charlieAction=linePrep, randiAction=linePrep, tammyAction=linePrep)
//    totalProgram( "charlie_first_letter_of_each_word", fileLines, charlieAction=firstLetterOfEachWord, randiAction=linePrep, tammyAction=linePrep)
//    totalProgram( "charlie_first_word_of_each_sentence", fileLines, charlieAction=firstWordOfEachSentence, randiAction=linePrep, tammyAction=linePrep)
//    totalProgram( "randi_first_letter_of_each_word", fileLines, charlieAction=linePrep, randiAction=firstLetterOfEachWord, tammyAction=linePrep)
//    totalProgram( "randi_first_word_of_each_sentence", fileLines, charlieAction=linePrep, randiAction=firstWordOfEachSentence, tammyAction=linePrep)
//    totalProgram( "tammy_first_letter_of_each_word", fileLines, charlieAction=linePrep, randiAction=linePrep, tammyAction=firstLetterOfEachWord)
//    totalProgram( "tammy_first_word_of_each_sentence", fileLines, charlieAction=linePrep, randiAction=linePrep, tammyAction=firstWordOfEachSentence)
  }
}
