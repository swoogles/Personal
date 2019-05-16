package foo

import utest._

object HelloTests extends TestSuite{
  val tests = Tests{
    'test1 - {
      throw new Exception("test1")
    }
    'test2 - {
      1
    }
    'test3 - {
      val a = List[Byte](1, 2)
      assert()
      val lineWithQuotesAndEllipses = """
      RANDI: "Yes," Wolf said – but give me a chance to change man. I’ll make sure he goes outside every day to play, I’ll comfort him when he’s sick or sad, I’ll be his guide when he cannot see, protect him when he’s in danger, I’ll round up his sheep, I’ll make him laugh just by being me ... I’ll love man unconditionally and remind man who he’s supposed to be.
      """
      a(10)
    }
  }
}