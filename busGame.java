import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class busGame {
    public static void main(String[] args, User player) {
        Scanner sc = new Scanner(System.in);
        ArrayList<cards> deck = cards.loadDeckCards();
        mainMenu(deck, player, sc);
    }

    public static void mainMenu(ArrayList<cards> cards, User player, Scanner sc) {

        int choice = 0;
        while (choice != 4) {
            printOptions();
            choice = IO.INTput(sc, "Choses one of the options");
            if (choice == 1) {
                rideTheBus(sc, cards, player);
            } else if (choice == 2) {
                player.profile(sc);
            } else if (choice == 3) {
                printRules(sc);
            } else if (choice == 4) {
                IO.print("Returning to the main menu...");
            } else {
                IO.print("Please pick a valid option");
            }
        }
    }

    public static void printRules(Scanner sc) {
        IO.clear();

        IO.print("=== Rules ===");
        IO.print("There are 4 rounds to the game");
        IO.print("1. Guess if the card is red or black");
        IO.print("2. Guess if the next card is higher or lower");
        IO.print("3. Guess if next cards is inside or outside the range of the previous two");
        IO.print("4. Guess the suit of the last card");
        IO.print("After each round your bet multiplies by two and can be taken out if you quit");
        IO.print("\nPress enter to continue");
        sc.nextLine();
    }

    public static void printOptions() {
        IO.clear();
        IO.print("=== Welcome to Ride the Bus! ===");
        IO.print("1. Play Ride the Bus");
        IO.print("2. User profile");
        IO.print("3. Rules");
        IO.print("4. Exit");
    }

    public static void rideTheBus(Scanner sc, ArrayList<cards> deck, User player) {
        IO.clear();

        int betAmount = player.setbet(sc);

        ArrayList<cards> house = new ArrayList<>();
        int round = 1;
        while (round != -1 && round != 5 && round != 6) {
            if (round == 1) {
                round = round1(deck, house, round, sc, betAmount, player);
            } else if (round == 2) {
                round = round2(deck, house, round, sc, betAmount, player);
            } else if (round == 3) {
                round = round3(deck, house, round, sc, betAmount, player);
            } else if (round == 4) {
                round = round4(deck, house, round, sc, betAmount, player);
            }
        }

        // IO.print("it IS working");
        gameEnd(round, sc, player);

    }

    public static int round1(ArrayList<cards> deck, ArrayList<cards> house, int round, Scanner sc, int betAmount,
            User player) {
        IO.clear();
        int toReturn = 0;

        IO.print("Round 1");
        house.add(pickupCard(deck));

        String colChoice = IO.StringPut(sc, "Red or Black?");

        cards current = house.get(0);
        if (colChoice.equalsIgnoreCase("RED")) {
            if (current.suit.equals("Diamonds") || current.suit.equals("Hearts")) {
                IO.clear();
                IO.print("\nCorrect!\n");
                IO.print("The card picked was " + CardID(current));
                player.setCurrentBet(betAmount * 2);
                toReturn = 2;
            } else {
                IO.clear();
                IO.print("\nIncorrect!\n");
                IO.print("The card picked was " + CardID(current));
                player.setCurrentBet(-player.getCurrentBet());
                IO.print("Press enter to continue");
                sc.nextLine();
                return -1;
            }
        } else if (colChoice.equalsIgnoreCase("Black")) {
            if (current.suit.equals("Spades") || current.suit.equals("Clubs")) {
                IO.clear();
                IO.print("\nCorrect!\n");
                IO.print("The card picked was " + CardID(current));
                player.setCurrentBet(betAmount * 2);
                toReturn = 2;

            } else {
                IO.clear();
                IO.print("\nIncorrect!\n");
                IO.print("The card picked was " + CardID(current));
                player.setCurrentBet(-player.getCurrentBet());
                IO.print("Press enter to continue");
                sc.nextLine();
                return -1;
            }
        } else {
            IO.print(
                    "Something went wrong please try contacting the support helpdesk for an official report on your issue");
            return -1;
        }

        String takeOut = IO.StringPut(sc, "\nDo you want to take out your bet and end the game? (yes/no)");
        if (takeOut.equalsIgnoreCase("yes")) {
            toReturn = 6;
        } else {

            toReturn = 2;
        }

        return toReturn;
    }

    public static int round2(ArrayList<cards> deck, ArrayList<cards> house, int round, Scanner sc, int betAmount,
            User player) {
        IO.clear();

        IO.print("Round 2");
        house.add(pickupCard(deck));
        int toReturn = 0;

        IO.print("The last card was: " + CardID(house.get(0)));

        String highLowChoice = IO.StringPut(sc, "Is the new card higher or lower");

        cards first = house.get(0);
        cards second = house.get(1);

        IO.print("The new card is: " + CardID(second));

        if (highLowChoice.equalsIgnoreCase("higher")) {
            if (first.getValue() < second.getValue()) {
                IO.clear();
                IO.print("\nCorrect!\n");
                IO.print(CardID(second) + " was higher than " + CardID(first));
                player.setCurrentBet(betAmount * 3);
                toReturn = 3;
            } else if (first.getValue() > second.getValue()) {
                IO.clear();
                IO.print("\nIncorrect!\n");
                IO.print(CardID(second) + " was not higher than " + CardID(first));
                player.setCurrentBet(-player.getCurrentBet());
                IO.print("Press enter to continue");
                sc.nextLine();
                return -1;
            } else {
                IO.print("It's a tie");
                return -1;
            }
        } else if (highLowChoice.equalsIgnoreCase("lower")) {
            if (first.getValue() < second.getValue()) {
                IO.clear();
                IO.print("\nCorrect!\n");
                IO.print(CardID(second) + " was lower than " + CardID(first));
                player.setCurrentBet(betAmount * 3);
                toReturn = 3;
            } else if (first.getValue() > second.getValue()) {
                IO.clear();
                IO.print("\nIncorrect!\n");
                IO.print(CardID(second) + " was not lower than " + CardID(first));
                player.setCurrentBet(-player.getCurrentBet());
                IO.print("Press enter to continue");
                sc.nextLine();
                return -1;
            } else {
                IO.print("It's a tie");
                return -1;
            }
        } else {
            IO.print(
                    "Something went wrong please try contacting the support helpdesk for an official report on your issue");
            return -1;
        }

        String takeOut = IO.StringPut(sc, "\nDo you want to take out your bet and end the game? (yes/no)");
        if (takeOut.equalsIgnoreCase("yes")) {
            toReturn = 6;
        } else {

            toReturn = 3;
        }

        return toReturn;
    }

    public static int round3(ArrayList<cards> deck, ArrayList<cards> house, int round, Scanner sc, int betAmount,
            User player) {
        IO.clear();

        IO.print("Round 3");
        house.add(pickupCard(deck));

        cards first = house.get(0);
        cards second = house.get(1);
        cards third = house.get(2);

        IO.print("First: " + CardID(first));
        IO.print("Second: " + CardID(second));

        // inside or out
        int upper;
        int lower;

        if (first.getValue() > second.getValue()) {
            upper = first.getValue();
            lower = second.getValue();
        } else {
            upper = second.getValue();
            lower = first.getValue();
        }

        ArrayList<Integer> inside = new ArrayList<Integer>();

        for (int i = lower; i < upper; i++) {
            inside.add(i);
        }

        String inOutChoice = IO.StringPut(sc, "Is the new card inside or outside the range of the previous two");

        int newValue = third.getValue();
        int toReturn = 0;
        if (inOutChoice.equalsIgnoreCase("inside")) {
            if (inside.contains(newValue)) {
                IO.clear();
                IO.print("\nCorrect!\n");
                player.setCurrentBet(betAmount * 4);
                IO.print("The card was: " + CardID(third));
                toReturn = 4;
            } else {
                IO.clear();
                IO.print("\nIncorrect!\n");
                player.setCurrentBet(-player.getCurrentBet() * 3);
                IO.print("The card was: " + CardID(third));
                IO.print("Press enter to continue");
                sc.nextLine();
                return -1;
            }
        } else if (inOutChoice.equalsIgnoreCase("outside")) {
            if (!inside.contains(newValue)) {
                IO.clear();
                IO.print("\nCorrect!\n");
                player.setCurrentBet(betAmount * 4);
                IO.print("The card was: " + CardID(third));
                toReturn = 4;
            } else {
                IO.clear();
                IO.print("\nIncorrect!\n");
                player.setCurrentBet(-player.getCurrentBet());
                IO.print("The card was: " + CardID(third));
                IO.print("Press enter to continue");
                sc.nextLine();
                return -1;
            }
        } else {
            IO.print(
                    "Something went wrong please try contacting the support helpdesk for an official report on your issue");
            return -1;
        }

        String takeOut = IO.StringPut(sc, "\nDo you want to take out your bet and end the game? (yes/no)");
        if (takeOut.equalsIgnoreCase("yes")) {
            toReturn = 6;
        } else {

            toReturn = 4;
        }

        return toReturn;
    }

    public static int round4(ArrayList<cards> deck, ArrayList<cards> house, int round, Scanner sc, int betAmount,
            User player) {
        IO.clear();

        IO.print("Round 4");
        house.add(pickupCard(deck));
        cards last = house.get(3);
        String suitGuess = IO.StringPut(sc, "Guess the suit of the final card");
        String currentSuit = last.getSuit();

        if (currentSuit.equalsIgnoreCase(suitGuess)) {
            IO.print("\nCorrect!\n");
            player.setCurrentBet(betAmount * 5);
            IO.print("The card was: " + CardID(last));
            return 5;
        } else {
            IO.print("\nIncorrect!\n");
            player.setCurrentBet(-player.getCurrentBet());
            IO.print("The card was: " + CardID(last));
            IO.print("Press enter to continue");
            sc.nextLine();
            return -1;
        }

    }

    public static void gameEnd(int win, Scanner sc, User player) {
        IO.clear();

        if (win == 5) {
            IO.print("You win the game!");
            IO.print("\n You won a total of " + player.getCurrentBet() + " chips!");
            player.setChips(player.getChips() + player.getCurrentBet());
        } else if (win == -1) {
            IO.print("You lost hahah");
            int userChips = player.getChips();
            if (player.getCurrentBet() > 0) {
                player.setCurrentBet(-player.getCurrentBet());
            }
            int newChips = userChips + player.getCurrentBet();
            if (newChips < 0) {
                newChips = 0;
            }
            player.setChips(newChips);
            IO.print("You now have this many chips: " + player.getChips());
        } else if (win == 6) {
            IO.print("You took out your bet and ended the game");
            IO.print("This many chips:" + player.getCurrentBet());
            player.setChips(player.getChips() + player.getCurrentBet());
            IO.print("You now have this many chips: " + player.getChips());
        } else {
            IO.print(
                    "Something went wrong please try contacting the support helpdesk for an official report on your issue");
        }
        IO.print("Press enter to continue");
        sc.nextLine();
    }

    public static cards pickupCard(ArrayList<cards> deck) {
        Random cardGen = new Random();

        int val = cardGen.nextInt(51);
        val += 1;
        while (deck.get(val).getPicked() == true) {
            val = cardGen.nextInt(51);
            val += 1;
        }

        // Changes for getting cards
        cards toReturn = deck.get(val);
        // String fullname = toReturn.name + " of " + toReturn.suit;

        // print("\nYou picked up a: " + fullname + "\n");

        deck.get(val).setPicked(true);

        return toReturn;

    }

    public static String CardID(cards c) {
        String fullname = c.name + " of " + c.suit;
        return fullname;
    }

}
