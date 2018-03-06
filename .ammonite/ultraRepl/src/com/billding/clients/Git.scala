package ultraRepl.com.billding

import ammonite.ops._
// import ammonite.repl.Repl._
import ammonite.ops.ImplicitWd._
import java.time.LocalDate

object git extends Client {
  implicit class SuperString(s: String) {
    def containsAny(other: Seq[String]) = 
      other.exists(s.contains)
  }

// This is so that emails aren't added to the public repo.
  val desiredAuthors =
    read.lines! home / "gitCoworkers.txt"

  val client = new ClientBuilder("git")
  def execute(args: String*): Unit = client.execute(args: _*)

  def log =
    c("log")

  def status =
    c("status")

  object checkout extends Client {
    val cmd = client.subclient("checkout")
    def execute(args: String*): Unit = cmd.execute(args: _*)
    def existingBranch(branch: String) = cmd(branch)
    def newBranch(branch: String) = cmd("-b", branch)
  }

  def checkoutBase(args: String*) =
    %git("checkout" +: args)

  object branch extends Client {
    val cmd = client.subclient("branch")
    def execute(args: String*): Unit = cmd.execute(args: _*)
    def delete(branch: String) =
      cmd("-D", branch)
  }

  def blameInteractive(file: Path) =
    %git('blame, file.toString)

  def blame(file: Path): Stream[BlameFields] = {
    // Need the -e option to show emails, so that author doesn't contain whitespace
    val commandResult = %%git ('blame, "--date", "short", "-e", file)
    commandResult
      .out
      .lines
      .map { BlameFields.parse }
      .toStream
  }

  def attachBlameInformation(matchingFiles: NumberedFileContent): Option[Stream[BlameFields]] = {
    val NumberedFileContent(file, content) = matchingFiles
      val blameResults: Stream[BlameFields] = git.blame(file) // This fails if a noncommitted file is searched
      // TODO Ensure directory is clean before starting any specific operations.
      val authoredResults = 
        content.filter{ case NumberedLine(number,_) => 
          blameResults(number).author.containsAny(desiredAuthors) 
        }.map{case NumberedLine(number, _) => blameResults(number)}
      if (authoredResults.isEmpty)
        None
      else
        Some(authoredResults)
  }

  def attachBlameToMultipleFiles = 
    (streamOfFileContents: Stream[NumberedFileContent]) => 
      streamOfFileContents
        .map(attachBlameInformation)
        .flatten


}

case class BlameFields(hash: String, author: String, commitDate: LocalDate, lineNumber: String, lineContent: String) 
object BlameFields {
  def parse(rawLine: String): BlameFields = {
    println("fullLine: " + rawLine)
    val pieces = rawLine.split("\\s+")
    println("numPieces: " + pieces.length)
    pieces.toList match {
      case hash :: author :: lineNumber :: commitDateString :: remainingContentWords =>
        val commitDate = java.time.LocalDate.parse(commitDateString)
        val blameLineContent = lineNumber :: remainingContentWords reduce(_+ " " + _)
        BlameFields(hash, author, commitDate, lineNumber, blameLineContent)
      }
  }
  def apply(rawLines: Stream[String]): Stream[BlameFields] = {
    rawLines map { BlameFields.parse }
  }
}