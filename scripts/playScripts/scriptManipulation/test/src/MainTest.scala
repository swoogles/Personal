package foo

import utest._


object HelloTests extends TestSuite{
  val tests = Tests{
    /*
    'WordSurroundedWithQuotesAndPunctuation - {
      val word = "\"Yes,\""
      assert(
        Example.firstLetterOfWord(word) == "\"Y,\""
      )
    }

    'WithQuotesAndEllipse - {
      val lineWithQuotesAndEllipses = """
      "Yes," Wolf said "but give me a chance to change man.
      I’ll make sure he goes outside every day to play, I’ll comfort him when he’s sick or sad,
      I’ll be his guide when he cannot see, protect him when he’s in danger, I’ll round up his sheep,
      I’ll make him laugh just by being me ... I’ll love man unconditionally and remind man who he’s supposed to be."
      """
      val expectedResult = "\"Y,\" W s \"b g m a c t c m. I m s h g o e d t p, I c h w h s o s, I b h g w h c s, p h w h i d, I r u h s, I m h l j b b m ... I l m u a r m w h s t b.\""

      val actualResultss = Example.firstLetterOfEachWord(lineWithQuotesAndEllipses)
      assert(actualResultss.contains(expectedResult))
    }
    */


    val characters = List(
      "CHARLIE",
      "TAMMY",
      "RANDI"
    )

    val actions: List[(String, String=>String)] =
      List(
        ("first_letter_of_each_word", Example.firstLetterOfWord),
        ("first_word_of_each_sentence", Example.firstWordOfEachSentence)
      )


    'DynamicCharacterList - {
      val line = "RANDI: hi there. I'm Randi."
      val result = Example.convertSingleLine(line, "RANDI", characters, Example.firstLetterOfEachWord _)
      println(result)

    }
  }


}