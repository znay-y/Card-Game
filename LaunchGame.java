import java.util.Scanner;

public class LaunchGame {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int choice = 0;
        IO.clear();
        User player = initUser(sc);
        IO.clear();
        while (choice != 4) {
            IO.clear();
            printOptions();
            choice = IO.INTput(sc, "Choses one of the options");
            if (choice == 1) {
                Blackjack.main(args, player);
            } else if (choice == 2) {
                busGame.main(args, player);
            } else if (choice == 3) {
                player.profile(sc);
            } else if (choice == 4) {
                IO.print("Thanks for playing");
                IO.clear();
            } else {
                IO.print("Please pick a valid option");
            }
        }
    }

    public static User initUser(Scanner sc) {

        String name = IO.StringPut(sc, "Enter your name: ");
        User player = new User();
        player.setName(name);
        player.setChips(1000);
        return player;
    }

    public static void printOptions() {
        IO.print("=== Welcome to the Card Game! ===");
        IO.print("1. Play Blackjack");
        IO.print("2. Play Ride the Bus");
        IO.print("3. User Profile");
        IO.print("4. Exit");
    }

}
