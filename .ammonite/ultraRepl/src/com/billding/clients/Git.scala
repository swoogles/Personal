package com.billding.clients

import ammonite.ops._
import com.billding.libraries.{NumberedFileContent, NumberedLine}
import java.time.LocalDate

object Git extends Client {

  private val client = new ClientBuilder("git")
  def execute(args: String*): Unit = client.execute(args: _*)

  def log: Unit =
    execute("log")

  def status: Unit =
    execute("status")

  object checkout {
    private val subClient = client.subclient("checkout")
    def existingBranch(branch: String): Unit = subClient.execute(branch)
    def newBranch(branch: String) = subClient("-b", branch)
  }

  object rebase {
    private val cmd = client.subclient("rebase")
    def apply(branch: String): Unit = cmd.execute(branch)
  }

  object branch {
    private val cmd: Client = client.subclient("branch")
    def execute(args: String*): Unit = cmd.execute(args: _*)
    def delete(branch: String) =
      cmd("-D", branch)
  }

  def blameInteractive(file: Path)(implicit wd: Path) =
    %git('blame, file.toString)

  def blame(file: Path)(implicit wd: Path): Stream[BlameFields] = {
    // Need the -e option to show emails, so that author doesn't contain whitespace
    val commandResult = %%git ('blame, "--date", "short", "-e", file)
    commandResult
      .out
      .lines
      .map { BlameFields.parse }
      .toStream
  }

}

object AdvancedGit {
  // This is so that emails aren't added to the public repo.
  def desiredAuthors() =
    read.lines! (home / "gitCoworkers.txt")

  implicit class SuperString(s: String) {
    def containsAny(other: Seq[String]): Boolean =
      other.exists(s.contains)
  }

  def attachBlameInformation(matchingFiles: NumberedFileContent)(implicit wd: Path): Option[Stream[BlameFields]] = {
    val NumberedFileContent(file, content) = matchingFiles
    val blameResults: Stream[BlameFields] = Git.blame(file) // This fails if a noncommitted file is searched
    // TODO Ensure directory is clean before starting any specific operations.
    val authoredResults =
    content.filter{ case NumberedLine(number,_) =>
      blameResults(number).author.containsAny(desiredAuthors())
    }.map{case NumberedLine(number, _) => blameResults(number)}
    if (authoredResults.isEmpty)
      None
    else
      Some(authoredResults)
  }

  def attachBlameToMultipleFiles(implicit wd: Path) =
    (streamOfFileContents: Stream[NumberedFileContent]) =>
      streamOfFileContents
        .map(attachBlameInformation)
        .flatten
}

case class BlameFields(hash: String, author: String, commitDate: LocalDate, lineNumber: String, lineContent: String) 
object BlameFields {
  def parse(rawLine: String): BlameFields = {
    val pieces = rawLine.split("\\s+")
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
