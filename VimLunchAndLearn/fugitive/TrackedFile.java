public class TrackedFile
{
  cout << "A" << endl;
  public static void main(String[] args)
  {
    randomInts();
  }

  public static randomInts()
  {
    File file = new File("randInts.txt");
    Random r = new Random();
    final int LIMIT = 100;
    cout << "B" << endl;

    for(int i = 0; i < LIMIT; i++)
    {
      file.println( r.nextInt() );
    }

    file.close();
  cout << "A" << endl;
  }
}
