import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

class cards {
    int ID;
    int value;
    String suit;
    String name;
    boolean picked;

    public int getID() {
        return ID;
    }

    public String getName() {
        return name;
    }

    public String getSuit() {
        return suit;
    }

    public int getValue() {
        return value;
    }

    public boolean getPicked() {
        return picked;
    }

    public void setID(int iD) {
        ID = iD;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSuit(String suit) {
        this.suit = suit;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public void setPicked(boolean picked) {
        this.picked = picked;
    }
}

public class TheCardGames {

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

    public static void main(String[] args) {
        ArrayList<cards> cards = new ArrayList<cards>();
        ArrayList<cards> dealer = new ArrayList<cards>();
        Scanner scanner = new Scanner(System.in);

        mainMenu(cards, dealer, scanner);
    }

    public static void mainMenu(ArrayList<cards> cards, ArrayList<cards> dealer, Scanner sc) {

        int choice = 0;
        while (choice != 2) {
            printOptions();
            choice = INTput(sc, "Choses one of the options");
            if (choice == 1) {
                blackjackGame(cards, dealer, sc);
            } else if (choice == 2) {
            print("Thanks for playing");
            } else {
                print("Please pick a valid option");
            }
        }
    }

    public static void blackjackGame(ArrayList<cards> cards, ArrayList<cards> dealer, Scanner sc) {

        printBlackjackRules();
        ArrayList<cards> deck = loadDeckCards();
        cards.clear();
        dealer.clear();
        setupBlackjack(cards, dealer, deck);

        boolean end = false;

        while (!end) {
            int choice = 0;
            printblackjackOptions();

            /*
             * 1.hit
             * 2.double
             * 3.Stand
             */
            choice = INTput(sc, "\nChoose one of the options\n");
            if (choice == 1) {
                cards.add(pickupCard(deck));
                showYourDeck(cards);
            } else if (choice == 2) {
                cards.add(pickupCard(deck));
                end = true;
            } else if (choice == 3) {
                end = true;
            } else {
            }

            if (numbersTotal(cards) > 21) {
                end = true;
            } else if (numbersTotal(cards) == 21) {
                print("Ye ok u win");
                end = true;
            }
        }

        cpuTurn(dealer, cards, deck);
        whoWon(cards, dealer);

    }

    public static void printBlackjackRules() {
        print("\n==Blackjack==");
        print("Keep picking up cards until you hit 21 or less");
        print("IF you end turn when under 21 cpu picks up ");
        print("Who ever is the closet wins");
    }

    public static void setupBlackjack(ArrayList<cards> card, ArrayList<cards> dealer, ArrayList<cards> deck) {
        card.add(pickupCard(deck));
        card.add(pickupCard(deck));
        dealer.add(pickupCard(deck));
        dealer.add(pickupCard(deck));

        showYourDeck(card);
        showHouseDeck(dealer);
    }

    public static void showYourDeck(ArrayList<cards> deck) {
        print("\nYour Cards:");
        for (int i = 0; i < deck.size(); i++) {
            cards c = deck.get(i);
            String fullname = c.name + " of " + c.suit;
            print(fullname);
        }

    }

    public static void showHouseDeck(ArrayList<cards> deck) {
        print("\nHouse Cards:");
        for (int i = 0; i < deck.size(); i++) {
            cards c = deck.get(i);
            String fullname = c.name + " of " + c.suit;
            print(fullname);
        }

    }

    public static void printblackjackOptions() {
        print("\n1.Hit\n2.Double\n3.Stand");
    }

    public static void printOptions() {
        print("\n==Main Menu==\n");
        print("1. Game (Server's Down)");
        print("2. Blackjack");
        print("3. Quit\n");
    }    

    public static void whoWon(ArrayList<cards> cards, ArrayList<cards> dealer) {
        print("==So Who Won==");
        if (numbersTotal(cards) > 21) {
            print("You bust they win");
        } else if (numbersTotal(dealer) > 21) {
            print("Dealer bust you win");
        } else {
            if (numbersTotal(dealer) > numbersTotal(cards)) {
                print("\nThey win\n");
            } else if (numbersTotal(dealer) == numbersTotal(cards)) {
                print("\nTie\n");
            } else {
                print("\nYour win\n");
            }
        }

    }

    public static void cpuTurn(ArrayList<cards> dealer, ArrayList<cards> cards, ArrayList<cards> deck) {
        print("\n==CPU's Turn==\n");
        boolean cont = true;
        while (numbersTotal(dealer) < 21 && cont) {
            dealer.add(pickupCard(deck));
            showHouseDeck(dealer);

            if (numbersTotal(dealer) == 21) {
                //print("CPU hit 21 so you lose");
            } else if (numbersTotal(dealer) > 21) {
                //print("CPU bust");
            } else if (numbersTotal(dealer) < 21 && numbersTotal(cards) <= numbersTotal(dealer)) {
                //print("CPU wins");
                cont = false;
            }
        }
    }

    public static int numbersTotal(ArrayList<cards> nums) {

        int total = 0;
        for (int i = 0; i < nums.size(); i++) {
            total = total + nums.get(i).getValue();
        }
        return total;
    }

    public static void printGameRules() {
        print("\n==Game==\n");
        print("Keep picking up cards until you hit 21 or less");
        print("IF you end turn when under 21 cpu picks up ");
        print("Who ever is the closet wins");
        print("Yes this is just blackjack but with differnet numbers\n");
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
        //String fullname = toReturn.name + " of " + toReturn.suit;

        //print("\nYou picked up a: " + fullname + "\n");

        deck.get(val).setPicked(true);

        return toReturn;

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
