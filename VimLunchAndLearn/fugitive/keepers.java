  public void printTest(int num) {
    Stopwatch st = new Stopwatch();
    st.start();
    int actualFactors = numFactors(num);
    st.stop();
    if(actualFactors == expectedFactors)
      System.out.println("PASSED");
    else
      System.out.println("FAILED");
    System.out.println(st.time());
  }

  // pre: num >= 2
  public int numFactors(int num) {
    int result = 0;
    double SQRT = Math.sqrt(num);
    for(int i = 1; i < SQRT; i++) {
      if(num % i == 0) {
        result += 2;
      }
    }
    if(num % SQRT == 0)
      result++;
    return result;
  }
