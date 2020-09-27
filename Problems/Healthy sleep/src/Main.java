import java.util.Scanner;

class Main {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int A = in.nextInt();
        int B = in.nextInt();
        int H = in.nextInt();
        if (H < A) {
            System.out.println("Deficiency");
        } else if (H > B) {
            System.out.println("Excess");
        } else {
            System.out.println("Normal");
        }
    }
}