  public boolean isPrime(int num) {
    double LIMIT = Math.sqrt(num);
    boolean isPrime = 
      (num == 2) ? true : num % 2 != 0;
    int div = 3;
    while(div <= LIMIT && isPrime) {
      isPrime = num % div != 0;
      div += 2;
    }
    return isPrime;
  }

