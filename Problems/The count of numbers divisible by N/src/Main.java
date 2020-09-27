import java.util.Scanner;

class Main {
    static final Scanner in = new Scanner(System.in);

    public static void main(String[] args) {
        int a = in.nextInt();
        int b = in.nextInt();
        int n = in.nextInt();

        int count = 0;
        for (int i = a; i <= b; i++) {
            if (i % n == 0) {
                count++;
            }
        }

        System.out.println(count);
    }
}