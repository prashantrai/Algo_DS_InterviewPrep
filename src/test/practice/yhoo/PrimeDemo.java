package test.practice.yhoo;

class PrimeDemo {

	public void displayPrimes(int n) {

		for (int i = 2; i <= 2345; i++) {

			if (i > 2 && i % 2 == 0)
				continue;

			if (isPrime(i))
				System.out.println(i);
		}
	}

	public boolean isPrime(int i) {

		for (int j = 2; j <= Math.sqrt(i); j++) {

			if (i % j == 0)
				return false;
			
		}
		return true;
	}

}