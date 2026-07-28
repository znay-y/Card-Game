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

    public static void rideTheBus(Scanner sc, ArrayList<cards> deck) {
        /*
         * 1. Give cpu a card each round
         * 2. Ask red or black
         * 3. get new card then ask higher or lower
         * 4. get a new card and ask inside or outside
         * 5. get a new card and ask what suit
         */

        ArrayList<cards> house = new ArrayList<>();
        int round = 1;
        while (round != -1) {
            if (round == 1) {
                round = round1(deck,house, round, sc);
            } else if (round == 2) {
                round = round2(deck,house, round, sc);
            } else if (round == 3) {
                round = round3(deck,house, round, sc);
            } else if (round == 4) {
                round = round4(deck,house, round, sc);
            }

        }

    }

    public static int round1(ArrayList<cards> deck, ArrayList<cards> house, int round, Scanner sc) {
        IO.print("Round 1");
        house.add(pickupCard(deck));

        String colChoice = IO.StringPut(sc, "Red or Black?");

        cards current = house.get(0);
        if (colChoice.equalsIgnoreCase("RED")) {
            if (current.suit.equals("Diamonds") || current.suit.equals("Hearts")) {
                IO.print("good");
                IO.print("The card picked was " + CardID(current));
                return 2;
            } else {
                IO.print("bad");
                IO.print("The card picked was " + CardID(current));

                return -1;
            }
        } else if (colChoice.equalsIgnoreCase("Black")) {
            if (current.suit.equals("Spades") || current.suit.equals("Clubs")) {
                IO.print("good");
                IO.print("The card picked was " + CardID(current));

                return 2;

            } else {
                IO.print("bad");
                IO.print("The card picked was " + CardID(current));

                return -1;
            }
        } else {
            IO.print(
                    "Something went wrong please try contacting the support helpdesk for an official report on your issue");
            return -1;
        }

    }

    public static int round2(ArrayList<cards> deck, ArrayList<cards> house, int round, Scanner sc) {
        IO.print("Round 2");
        house.add(pickupCard(deck));

        IO.print("The last card was: " + CardID(house.get(0)));

        String highLowChoice = IO.StringPut(sc, "Is the new card higher or lower");

        cards first = house.get(0);
        cards second = house.get(1);

        if (highLowChoice.equalsIgnoreCase("higher")) {
            if (first.getValue() < second.getValue()) {
                IO.print("ok");
                return 3;
            } else if (first.getValue() > second.getValue()) {
                IO.print("what are you doing?");
                return -1;
            } else {
                IO.print("It's a tie");
                return -1;
            }
        } else if (highLowChoice.equalsIgnoreCase("lower")) {
            if (first.getValue() < second.getValue()) {
                IO.print("what?");
                return -1;
            } else if (first.getValue() > second.getValue()) {
                IO.print("ok");
                return 3;
            } else {
                IO.print("It's a tie");
                return -1;
            }
        } else {
            IO.print(
                    "Something went wrong please try contacting the support helpdesk for an official report on your issue");
            return -1;
        }
    }

    public static int round3(ArrayList<cards> deck, ArrayList<cards> house, int round, Scanner sc) {
        IO.print("Round 3");
        house.add(pickupCard(deck));
        return 4;
    }

    public static int round4(ArrayList<cards> deck, ArrayList<cards> house, int round, Scanner sc) {
        IO.print("Round 4");
        house.add(pickupCard(deck));
        return -1;
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

    public static String CardID(cards c) {
        String fullname = c.name + " of " + c.suit;
        return fullname;
    }

}
