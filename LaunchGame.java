import java.util.*;
import java.io.*;

public class LaunchGame {

    public static void main(String[] args) {
        // IO.clear();
        ArrayList<User> extractedSaves = new ArrayList<User>();
        Scanner sc = new Scanner(System.in);
        int choice = 0;

        User player = introScreen(sc, extractedSaves);
        IO.clear();
        while (choice != 6) {
            IO.clear();
            printOptions();
            choice = IO.INTput(sc, "Choses one of the options");
            if (choice == 1) {
                Blackjack.main(args, player);
            } else if (choice == 2) {
                busGame.main(args, player);
            } else if (choice == 3) {
                //theCardGame.main(args, player);
            } else if (choice == 4) {
                poker.main(args, player);
            } else if (choice == 5) {
                player.profile(sc);
            } else if (choice == 6) {
                saveGame(player);
                IO.print("Thanks for playing");
                IO.clear();
            } else {
                IO.print("Please pick a valid option");
            }
        }
    }

    public static User introScreen(Scanner sc, ArrayList<User> extractedSaves) {
        IO.clear();
        File localSave = new File("saves/profiles.csv");
        int choice = IO.INTput(sc, "Chose one of the following options:\n1. Load a save\n2. Create a new save");
        User player = null;

        while (choice != 1 && choice != 2) {
            IO.print("Please pick a valid option");
            choice = IO.INTput(sc, "Chose one of the following options:\n1. Load a save\n2. Create a new save");
        }

        if (choice == 1) {
            try {
                Scanner myReader = new Scanner(localSave);
                IO.clear();
                IO.print("\nChose one of the following save\n");
                while (myReader.hasNextLine()) {
                    String data = myReader.nextLine();
                    String[] people = data.split(",");
                    // String saveID = people[0];
                    String name = people[0];
                    String chips = people[1];
                    // IO.print("Save ID: " + saveID + " - Name: " + name + " - Chips: " + chips);
                    extractedSaves.add(new User(name, chips));
                }
                myReader.close();
                player = loadSave(sc, extractedSaves);
            } catch (FileNotFoundException e) {
                System.out.println("An error occurred.");
            }
        } else if (choice == 2) {
            String name = IO.StringPut(sc, "Enter your name: ");
            player = new User(name, "1000");
        }

        IO.enterPause(sc);
        return player;

    }

    public static User loadSave(Scanner sc, ArrayList<User> extractedSaves) {
        for (int i = 0; i < extractedSaves.size(); i++) {
            IO.print((i + 1) + ". " + extractedSaves.get(i).getName() + " - " + extractedSaves.get(i).getChips()
                    + " chips");
        }

        int saveChoice = IO.INTput(sc, "Chose one of the saves");
        while (saveChoice <= 0 || saveChoice > extractedSaves.size()) {
            IO.print("Please pick a valid option");
            saveChoice = IO.INTput(sc, "Chose one of the saves");
        }
        IO.lineRemover("saves/profiles.csv", saveChoice);
        return extractedSaves.get(saveChoice - 1);
    }

    public static void saveGame(User player) {
        try {
            FileWriter myWriter = new FileWriter("saves/profiles.csv", true);
            myWriter.write(player.getName() + "," + player.getChips() + "\n");
            myWriter.close();
            IO.print("Successfully saved game.");
        } catch (IOException e) {
            IO.print("An error occurred.");
        }
    }

    public static void printOptions() {
        IO.print("=== Welcome to the Card Game! ===");
        IO.print("1. Play Blackjack");
        IO.print("2. Play Ride the Bus");
        IO.print("3. The Card Game");
        IO.print("4. Poker");
        IO.print("5. User Profile");
        IO.print("6. Save and Exit");
    }
}
