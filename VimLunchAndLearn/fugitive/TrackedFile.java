public class TrackedFile
{
  public static void main(String[] args)
  {
    go();
  }

  public static go()
  {
    File file = new File("randInts.txt");
    Random r = new Random();
    final int LIMIT = 100;

    for(int i = 0; i < LIMIT; i++)
    {
      file.println( r.nextInt() );
    }

    file.close();
  }
}
