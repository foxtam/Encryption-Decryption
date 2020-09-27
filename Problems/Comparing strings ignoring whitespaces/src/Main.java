import java.util.Scanner;

class Main {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        String firstLine = in.nextLine();
        String secondLine = in.nextLine();
        String firstWithoutSpaces = firstLine.replaceAll("\\s+", "");
        String secondWithoutSpaces = secondLine.replaceAll("\\s+", "");
        System.out.println(firstWithoutSpaces.equals(secondWithoutSpaces));
    }
}