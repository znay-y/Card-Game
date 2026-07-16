import java.util.Scanner;

public class LaunchGame {

    public static void main(String[] args){
Scanner sc = new Scanner(System.in);
        int choice = 0;
        while (choice != 3) {
            printOptions();
            choice = INTput(sc, "Choses one of the options");
            if (choice == 1) {
                Blackjack.main(args);
            } else if (choice == 2) {
                busGame.main(args);
            } else if (choice == 3) {
                print("Thanks for playing");
            } else {
                print("Please pick a valid option");
            }
        }
    }

    public static void printOptions() {
        System.out.println("=== Welcome to the Card Game! ===");
        System.out.println("1. Play Blackjack");
        System.out.println("2. Play Ride the Bus");
        System.out.println("3. Exit");
    }

     public static void print(String output) {
        System.out.println(output);
    }

    public static void print(int value) {
        System.out.println(value);
    }

    public static int INTput(Scanner sc, String message) {
        print(message);
        String input = sc.nextLine();
        int num = Integer.parseInt(input);
        return num;
    }

    public static String StringPut(Scanner sc, String message) {
        print(message);
        String input = sc.nextLine();
        return input;
    }
}
