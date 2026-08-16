import java.util.ArrayList;
import java.util.Scanner;

public class theCardGame {
    public static void main(String[] args) {
        ArrayList<cards> playerCards = new ArrayList<cards>();
        ArrayList<cards> dealer = new ArrayList<cards>();
        Scanner scanner = new Scanner(System.in);

        mainMenu(playerCards, dealer, scanner);
    }

    public static void mainMenu(ArrayList<cards> playerCards, ArrayList<cards> dealer, Scanner sc) {

        int choice = 0;
        IO.clear();
        while (choice != 4) {
            printOptions();
            choice = IO.INTput(sc, "Choses one of the options");
            if (choice == 1) {
                theGame(playerCards, dealer, sc);
            } else if (choice == 2) {
                // player.profile(sc);
            } else if (choice == 3) {
                printRules(sc);
            } else if (choice == 4) {
                IO.print("Thanks for playing");
            } else {
                IO.print("Please pick a valid option");
            }
        }
    }

    public static void printOptions() {
        IO.print("==Main Menu==\n");
        IO.print("1. The Card Game");
        IO.print("2. User profile");
        IO.print("3. Rules");
        IO.print("4. Exit\n");
    }

    public static void printRules(Scanner sc) {
        IO.clear();
        IO.print("==The Card Game==");
        IO.print("Both the player and the cpu will start with 5 cards");
        IO.print("There will be a starting cards in the middle");
        IO.print("Cards can only be played if the suit or number matches");
        IO.print("Cards can be chained if the number matches or in a row of the same suit");
        IO.print("The first player to get rid of all their cards wins");
        IO.print("If the player cannot play a card they must pick up a card");
        IO.print("If the player picks up a card they can play it if it matches");
        IO.print("Playing a blackjack (Spades or clubs) results in picking up 5 cards");
        IO.print("Playing a 2 results in the other player picking up 2 cards");
        IO.print("Jacks and 2s can be chained resulting in a pick up of more cards");
        IO.enterPause(sc);
    }

    public static void theGame(ArrayList<cards> playerCards, ArrayList<cards> dealer, Scanner sc) {
        IO.clear();
        // int betAmount = player.setbet(sc);
        IO.clear();
        ArrayList<cards> deck = cards.loadDeckCards();
        ArrayList<cards> topCard = new ArrayList<cards>();
        playerCards.clear();
        dealer.clear();
        theGameSteup(playerCards, dealer, topCard, deck);

        boolean end = false;
        while (!end) {
            refreshDisplay(playerCards, dealer, topCard);
            int choice = 0;
            IO.print("1. Play a card");
            IO.print("2. Pick up a card");
            IO.print("3. Quit");
            choice = IO.INTput(sc, "Choses one of the options");
            if (choice == 1) {
                // playCard(playerCards, dealer, topCard, deck, sc);
            } else if (choice == 2) {
                // pickUpCard(playerCards, deck);
            } else if (choice == 3) {
                end = true;
            } else {
                IO.print("Please pick a valid option");
            }
        }
    }

    public static void refreshDisplay(ArrayList<cards> playerCards, ArrayList<cards> dealer, ArrayList<cards> topCard) {
        IO.clear();
        IO.print("==The Card Game==");
        IO.print("Your cards: " + playerCards);
        IO.print("CPU cards: " + dealer.size());
        IO.print("Top card: " + topCard.get(topCard.size() - 1));
    }
    public static void theGameSteup(ArrayList<cards> playerCards, ArrayList<cards> dealer, ArrayList<cards> topCard,
            ArrayList<cards> deck) {
        for (int i = 0; i < 5; i++) {
            playerCards.add(cards.pickupCard(deck));
            dealer.add(cards.pickupCard(deck));
        }
        topCard.add(cards.pickupCard(deck));
    }
}

