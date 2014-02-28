public class TrackedFile
{
  public static void main(String[] args)
  {
    go();
  }

  public static go()
  {
    try {
      PrintStream writer = new PrintStream( new File("randInts.txt"));
      Random r = new Random();
      final int LIMIT = 100;

      for(int i = 0; i < LIMIT; i++)
      {
        writer.println( r.nextInt() );
      }

      writer.close();
    }
    catch(IOException e)
    {
      System.out.println("An error occured while trying to write to the file");
    }
  }
}
