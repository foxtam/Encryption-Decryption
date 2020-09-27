import java.util.Scanner;

class Main {
    static final Scanner in = new Scanner(System.in);

    public static void main(String[] args) {
        int shipped = 0, fixed = 0, rejected = 0;
        int total = in.nextInt();
        for (int i = 0; i < total; i++) {
            switch (in.nextInt()) {
                case 0:
                    shipped++;
                    break;
                case 1:
                    fixed++;
                    break;
                case -1:
                    rejected++;
                    break;
            }
        }
        System.out.printf("%d %d %d", shipped, fixed, rejected);
    }
}