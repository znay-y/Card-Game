import java.util.Scanner;

public class userProfile {
    public static void main(String[] args, User player,Scanner sc) {

        IO.clear();
        IO.print("User Profile");
        IO.print("Name: " + player.getName());
        IO.print("Chips: " + player.getChips());
        IO.print("Press enter to continue");

        sc.nextLine();
        IO.clear();

    }

}
