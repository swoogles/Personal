package foo
import better.files._
import better.files.Dsl._
object Example{
  def main(args: Array[String]): Unit = {
    println("Hello World")

    val betterFile = cwd /  "WatchHill.txt"

    val fileLines = betterFile.contentAsString.split("\n").toList

    def wordsFrom(line: String) = {
      line.split("\\s+").toList
    }

    def linePrep(line: String) = {
      wordsFrom(line.trim).tail
        .mkString(" ")
    }

    def endsWithPunctuation(word: String) = {
      val punctuation = List(".", "!", "-", "?", ",")
      punctuation.foldLeft(false)(_ || word.endsWith(_))
    }

    def firstLetterOfEachWord(line: String) = {
      val words = wordsFrom(line).tail
      words.map{ word =>
        if (endsWithPunctuation(word)) // Preserve punctuation.
          word.head + word.last.toString
        else
          word.head 
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

    def firstWordOfEachSentence(line: String) = {
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


    def writeNewLines(outFileName: String, newLines: List[String]) = {
      val file = cwd / "out" / (outFileName + ".txt")
      file.overwrite("")
      file.appendLines(newLines:_*)
    }

    def lineConversion(originalLines: List[String], florenceAction: String=>String, sidAction: String=>String, irvAction: String=>String) = {
      originalLines.map { line =>
        // val partialFilter = if line startsWith "FLORENCE."
        // val partial =  { case florenceLine: String if florenceLine startsWith "FLORENCE." => "FLORENCE: " + florenceAction(florenceLine) }

        line match { 
          case florenceLine if florenceLine startsWith "FLORENCE." => "FLORENCE: " + florenceAction(florenceLine)
          case sidLine if sidLine startsWith "SID." => "SID: " + sidAction(sidLine)
          case irvLine if irvLine startsWith "IRV." => "IRV: " + irvAction(irvLine)
          case stageDirection if stageDirection startsWith "(" => "stage direction: " + stageDirection
          case emptyLine if emptyLine isEmpty => emptyLine
          case title if title startsWith "III." => title
          case comment if comment startsWith "//" => ""
          case other => "FAILURE: " + other
        }
      } 
    }

    def totalProgram(outFileName: String, allLines: List[String], florenceAction: String=>String, sidAction: String=>String, irvAction: String=>String) = {
      val convertedLines = lineConversion(allLines, florenceAction, sidAction, irvAction)
      writeNewLines(outFileName, convertedLines)
    }

    totalProgram( "original_script", fileLines, florenceAction=linePrep, sidAction=linePrep, irvAction=linePrep)
    totalProgram( "florence_first_letter_of_each_word", fileLines, florenceAction=firstLetterOfEachWord, sidAction=linePrep, irvAction=linePrep)
    totalProgram( "florence_first_word_of_each_sentence", fileLines, florenceAction=firstWordOfEachSentence, sidAction=linePrep, irvAction=linePrep)
    totalProgram( "sid_first_letter_of_each_word", fileLines, florenceAction=linePrep, sidAction=firstLetterOfEachWord, irvAction=linePrep)
    totalProgram( "sid_first_word_of_each_sentence", fileLines, florenceAction=linePrep, sidAction=firstWordOfEachSentence, irvAction=linePrep)
  }
}
