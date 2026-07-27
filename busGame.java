import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class busGame {
    public static void main(String[] args, User player) {
        Scanner sc = new Scanner(System.in);
        ArrayList<cards> deck = cards.loadDeckCards();

        mainMenu(deck, sc);
    }

    public static void mainMenu(ArrayList<cards> cards, Scanner sc) {

        int choice = 0;
        while (choice != 2) {
            printOptions();
            choice = IO.INTput(sc, "Choses one of the options");
            if (choice == 1) {
                rideTheBus(sc, cards);
            } else if (choice == 2) {
            IO.print("Thanks for playing");
            } else {
                IO.print("Please pick a valid option");
            }
        }
    }

    public static void rideTheBus(Scanner sc, ArrayList<cards> cards) {
        /*
         * 1. Give user a card
         * 2. Ask red or black
         * 3. get new card then ask higher or lower
         * 4. get a new card and ask inside or outside
         * 5. get a new card and ask what suit
         */

        ArrayList<cards> house = new ArrayList<cards>();
        boolean gameOver = false;

        while (!gameOver) {
            IO.print("Welcome to Ride the Bus!");
            IO.print("How many chips are you betting");

            house.add(pickupCard(cards));

            // round 1
            IO.print("The house pikced a card");
            IO.print("Guess if the card is red or black");

            String colorGuess = IO.StringPut(sc, "Enter your guess (red or black): ");

            if (colorGuess.equalsIgnoreCase("red")) {
                IO.print("Correct! The card was " + house.get(0).getName() + " of " + house.get(0).getSuit());
            } else if (colorGuess.equalsIgnoreCase("black")) {
                IO.print("Correct! The card was " + house.get(0).getName() + " of " + house.get(0).getSuit());
            } else {
                IO.print("Incorrect! The card was " + house.get(0).getName() + " of " + house.get(0).getSuit());
                gameOver = true;
            }

            IO.print("Before round 2 begins. You can choose to continue or quit. If you quit, you will take your chips");
            String continueGame = IO.StringPut(sc, "Enter 'continue' to play on or 'quit' to quit: ");
            if (continueGame.equalsIgnoreCase("quit")) {
                gameOver = true;
            }
            // round 2 - Pick a new card and ask higher or lower
            house.add(pickupCard(cards));
            IO.print("The house picked a new card");
            IO.print("Guess if the new card is higher or lower than the previous card");
            String hlGuess = IO.StringPut(sc, "Enter your guess (higher or lower): ");

            if (hlGuess.equalsIgnoreCase("higher")) {
                if (house.get(1).getValue() > house.get(0).getValue()) {
                    IO.print("Correct! The new card was " + house.get(1).getName() + " of " + house.get(1).getSuit());
                } else {
                    IO.print("Incorrect! The new card was " + house.get(1).getName() + " of " + house.get(1).getSuit());
                    gameOver = true;
                }
            } else if (hlGuess.equalsIgnoreCase("lower")) {
                if (house.get(1).getValue() < house.get(0).getValue()) {
                    IO.print("Correct! The new card was " + house.get(1).getName() + " of " + house.get(1).getSuit());
                } else {
                    IO.print("Incorrect! The new card was " + house.get(1).getName() + " of " + house.get(1).getSuit());
                    gameOver = true;
                }
            } else {
                IO.print("Invalid guess. Please enter 'higher' or 'lower'.");
            }

            // round 3 - Pick a new card and ask inside or outside
            house.add(pickupCard(cards));
            IO.print("The house picked a new card");
            IO.print("Guess if the new card is inside or outside the range of the previous two cards");
            String ioGuess = IO.StringPut(sc, "Enter your guess (inside or outside): ");

            if (ioGuess.equalsIgnoreCase("inside")) {
                if ((house.get(1).getValue() > house.get(0).getValue()
                        && house.get(2).getValue() < house.get(1).getValue()
                        && house.get(2).getValue() > house.get(0).getValue()) ||
                        (house.get(1).getValue() < house.get(0).getValue()
                                && house.get(2).getValue() > house.get(1).getValue()
                                && house.get(2).getValue() < house.get(0).getValue())) {
                    IO.print("Correct! The new card was " + house.get(2).getName() + " of " + house.get(2).getSuit());
                } else {
                    IO.print("Incorrect! The new card was " + house.get(2).getName() + " of " + house.get(2).getSuit());
                    gameOver = true;
                }
            } else if (ioGuess.equalsIgnoreCase("outside")) {
                if ((house.get(1).getValue() > house.get(0).getValue()
                        && (house.get(2).getValue() > house.get(1).getValue()
                                || house.get(2).getValue() < house.get(0).getValue()))
                        ||
                        (house.get(1).getValue() < house.get(0).getValue()
                                && (house.get(2).getValue() < house.get(1).getValue()
                                        || house.get(2).getValue() > house.get(0).getValue()))) {
                    IO.print("Correct! The new card was " + house.get(2).getName() + " of " + house.get(2).getSuit());
                } else {
                    IO.print("Incorrect! The new card was " + house.get(2).getName() + " of " + house.get(2).getSuit());
                    gameOver = true;
                }
            } else {
                IO.print("Invalid guess. Please enter 'inside' or 'outside'.");
            }

            // round 4 - Pick a new card and ask what suit
            house.add(pickupCard(cards));
            IO.print("The house picked a new card");
            IO.print("Guess the suit of the new card");
            String suitGuess = IO.StringPut(sc, "Enter your guess (Spades, Hearts, Diamonds, Clubs): ");

            if (suitGuess.equalsIgnoreCase(house.get(3).getSuit())) {
                IO.print("Correct! The new card was " + house.get(3).getName() + " of " + house.get(3).getSuit());
            } else {
                IO.print("Incorrect! The new card was " + house.get(3).getName() + " of " + house.get(3).getSuit());
                gameOver = true;
            }
        }

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

    public static void printOptions() {
        IO.print("=== Welcome to Ride the Bus! ===");
        IO.print("1. Play Ride the Bus");
        IO.print("2. Exit");
    }


}
