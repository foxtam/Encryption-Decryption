import java.math.BigInteger;
import java.util.Scanner;

class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        BigInteger m = scanner.nextBigInteger();

        for (long n = 0; n < Long.MAX_VALUE; n++) {
            if (factorial(n).compareTo(m) >= 0) {
                System.out.println(n);
                break;
            }
        }
    }

    static BigInteger factorial(long number) {
        BigInteger fact = BigInteger.ONE;
        for (int i = 1; i <= number; i++) {
            fact = fact.multiply(BigInteger.valueOf(i));
        }
        return fact;
    }
}