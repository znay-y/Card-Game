import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;


public class busGame {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        ArrayList<cards> cards = loadDeckCards();

        rideTheBus(sc, cards);
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
            print("Welcome to Ride the Bus!");
print("How many chips are you betting");

        

        house.add(pickupCard(cards));

        // round 1
        print("The house pikced a card");
        print("Guess if the card is red or black");

        String colorGuess = StringPut(sc, "Enter your guess (red or black): ");

        if (colorGuess.equalsIgnoreCase("red")) {
            print("Correct! The card was " + house.get(0).getName() + " of " + house.get(0).getSuit());
        } else if (colorGuess.equalsIgnoreCase("black")) {
            print("Correct! The card was " + house.get(0).getName() + " of " + house.get(0).getSuit());
        } else {
            print("Incorrect! The card was " + house.get(0).getName() + " of " + house.get(0).getSuit());
            gameOver = true;
        }

        print("Before round 2 begins. You can choose to continue or quit. If you quit, you will take your chips");
        String continueGame = StringPut(sc, "Enter 'continue' to play on or 'quit' to quit: ");
        if (continueGame.equalsIgnoreCase("quit")) {
            gameOver = true;
        }
        //round 2 - Pick a new card and ask higher or lower
        house.add(pickupCard(cards));
        print("The house picked a new card");
        print("Guess if the new card is higher or lower than the previous card");
        String hlGuess = StringPut(sc, "Enter your guess (higher or lower): ");

        if(hlGuess.equalsIgnoreCase("higher")) {
            if(house.get(1).getValue() > house.get(0).getValue()) {
                print("Correct! The new card was " + house.get(1).getName() + " of " + house.get(1).getSuit());
            } else {
                print("Incorrect! The new card was " + house.get(1).getName() + " of " + house.get(1).getSuit());
                gameOver = true;
            }
        } else if(hlGuess.equalsIgnoreCase("lower")) {
            if(house.get(1).getValue() < house.get(0).getValue()) {
                print("Correct! The new card was " + house.get(1).getName() + " of " + house.get(1).getSuit());
            } else {
                print("Incorrect! The new card was " + house.get(1).getName() + " of " + house.get(1).getSuit());
                gameOver = true;
            }
        } else {
            print("Invalid guess. Please enter 'higher' or 'lower'.");
        }

        //round 3 - Pick a new card and ask inside or outside
        house.add(pickupCard(cards));
        print("The house picked a new card");
        print("Guess if the new card is inside or outside the range of the previous two cards");
        String ioGuess = StringPut(sc, "Enter your guess (inside or outside): ");

        if(ioGuess.equalsIgnoreCase("inside")) {
            if((house.get(1).getValue() > house.get(0).getValue() && house.get(2).getValue() < house.get(1).getValue() && house.get(2).getValue() > house.get(0).getValue()) || 
               (house.get(1).getValue() < house.get(0).getValue() && house.get(2).getValue() > house.get(1).getValue() && house.get(2).getValue() < house.get(0).getValue())) {
                print("Correct! The new card was " + house.get(2).getName() + " of " + house.get(2).getSuit());
            } else {
                print("Incorrect! The new card was " + house.get(2).getName() + " of " + house.get(2).getSuit());
                gameOver = true;
            }
        } else if(ioGuess.equalsIgnoreCase("outside")) {
            if((house.get(1).getValue() > house.get(0).getValue() && (house.get(2).getValue() > house.get(1).getValue() || house.get(2).getValue() < house.get(0).getValue())) || 
               (house.get(1).getValue() < house.get(0).getValue() && (house.get(2).getValue() < house.get(1).getValue() || house.get(2).getValue() > house.get(0).getValue()))) {
                print("Correct! The new card was " + house.get(2).getName() + " of " + house.get(2).getSuit());
            } else {
                print("Incorrect! The new card was " + house.get(2).getName() + " of " + house.get(2).getSuit());
                gameOver = true;
            }
        } else {
            print("Invalid guess. Please enter 'inside' or 'outside'.");
        }

        //round 4 - Pick a new card and ask what suit
        house.add(pickupCard(cards));
        print("The house picked a new card");
        print("Guess the suit of the new card");
        String suitGuess = StringPut(sc, "Enter your guess (Spades, Hearts, Diamonds, Clubs): ");

        if(suitGuess.equalsIgnoreCase(house.get(3).getSuit())) {
            print("Correct! The new card was " + house.get(3).getName() + " of " + house.get(3).getSuit());
        } else {
            print("Incorrect! The new card was " + house.get(3).getName() + " of " + house.get(3).getSuit());
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

    public static ArrayList<cards> loadDeckCards() {
        ArrayList<cards> deck = new ArrayList<cards>();
        int idcount = 1;

        String[] suits = { "Spades", "Hearts", "Diamonds", "Clubs" };
        String[] names = { "Ace", "Two", "Three", "Four", "Five", "Six", "Seven", "Eight", "Nine", "Ten", "Jack",
                "Queen", "King" };

        for (int i = 0; i <= 3; i++) {
            for (int j = 0; j < 13; j++) {
                cards toMake = new cards();
                toMake.setSuit(suits[i]);
                toMake.setName(names[j]);
                toMake.setPicked(false);
                toMake.setValue(j + 1);
                toMake.setID(idcount);
                idcount++;
                deck.add(toMake);
            }
        }
        return deck;
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
