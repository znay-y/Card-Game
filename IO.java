import java.util.Scanner;

public class IO {

    public static void print(String output) {
        System.out.println(output);
    }

    public static void print(int value) {
        System.out.println(value);
    }

    public static int INTput(Scanner sc, String message) {
        print(message);
        String input = sc.nextLine();
        try {
            Integer.parseInt(input);
        } catch (NumberFormatException e) {
            print("Please enter a valid integer");
            return INTput(sc, message);
        }
        return Integer.parseInt(input);
    }

    public static String StringPut(Scanner sc, String message) {
        print(message);
        String input = sc.nextLine();
        return input;
    }

    public static void clear() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

}
