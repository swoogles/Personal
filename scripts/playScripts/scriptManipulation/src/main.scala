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

  def endsWithPunctuation(word: String): Boolean = {
    val punctuation = List(".", "!", "-", "?", ",")
    punctuation.foldLeft(false)(_ || word.endsWith(_))
  }

  def firstLetterOfEachWord(line: String): String = {
    val words = wordsFrom(line).tail
    words.map{ word =>
      println(word)
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

  def lineConversion(originalLines: List[String], charlieAction: String=>String, randiAction: String=>String, tammyAction: String=>String): List[String] = {
    originalLines.map { line =>
      line match {
        case charlieLine if charlieLine startsWith "CHARLIE:" => "CHARLIE: " + charlieAction(charlieLine)
        case randiLine if randiLine startsWith "RANDI:" => "RANDI: " + randiAction(randiLine)
        case tammyLine if tammyLine startsWith "TAMMY:" => "TAMMY: " + tammyAction(tammyLine)
        case togetherLine if togetherLine startsWith "TOGETHER" => togetherLine
        case stageDirection if stageDirection startsWith "(" => stageDirection
        case emptyLine if emptyLine isEmpty => emptyLine
        case title if title startsWith "WATCH HILL" => title
        case comment if comment startsWith "//" => ""
        case other => "FAILURE: " + other
      }
    }
  }

  def totalProgram(outFileName: String, allLines: List[String], charlieAction: String=>String, randiAction: String=>String, tammyAction: String=>String) = {
    val convertedLines = lineConversion(allLines, charlieAction, randiAction, tammyAction)
    writeNewLines(outFileName, convertedLines)
  }

  def main(args: Array[String]): Unit = {
    println("Hello World")

    val betterFile = cwd /  "WatchHill.txt"

    val fileLines = betterFile.contentAsString.split("\n").toList

    totalProgram( "original_script", fileLines, charlieAction=linePrep, randiAction=linePrep, tammyAction=linePrep)
    totalProgram( "charlie_first_letter_of_each_word", fileLines, charlieAction=firstLetterOfEachWord, randiAction=linePrep, tammyAction=linePrep)
    totalProgram( "charlie_first_word_of_each_sentence", fileLines, charlieAction=firstWordOfEachSentence, randiAction=linePrep, tammyAction=linePrep)
    totalProgram( "randi_first_letter_of_each_word", fileLines, charlieAction=linePrep, randiAction=firstLetterOfEachWord, tammyAction=linePrep)
    totalProgram( "randi_first_word_of_each_sentence", fileLines, charlieAction=linePrep, randiAction=firstWordOfEachSentence, tammyAction=linePrep)
    totalProgram( "tammy_first_letter_of_each_word", fileLines, charlieAction=linePrep, randiAction=linePrep, tammyAction=firstLetterOfEachWord)
    totalProgram( "tammy_first_word_of_each_sentence", fileLines, charlieAction=linePrep, randiAction=linePrep, tammyAction=firstWordOfEachSentence)
  }
}
