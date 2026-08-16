import java.util.ArrayList;
import java.util.Scanner;

public class theCardGame {
    public static void main(String[] args) {
        ArrayList<cards> playerCards = new ArrayList<cards>();
        ArrayList<cards> compCards = new ArrayList<cards>();
        Scanner scanner = new Scanner(System.in);
        mainMenu(playerCards, compCards, scanner);
    }

    public static void mainMenu(ArrayList<cards> playerCards, ArrayList<cards> compCards, Scanner sc) {

        int choice = 0;
        IO.clear();
        while (choice != 4) {
            printOptions();
            choice = IO.INTput(sc, "Choses one of the options");
            if (choice == 1) {
                theGame(playerCards, compCards, sc);
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

    public static void theGame(ArrayList<cards> playerCards, ArrayList<cards> compCards, Scanner sc) {
        IO.clear();
        // int betAmount = player.setbet(sc);
        IO.clear();
        ArrayList<cards> deck = cards.loadDeckCards();
        ArrayList<cards> topCard = new ArrayList<cards>();
        playerCards.clear();
        compCards.clear();
        theGameSteup(playerCards, compCards, topCard, deck);

        boolean end = false;
        while (!end) {
            refreshDisplay(playerCards, compCards, topCard);
            int choice = 0;
            IO.print("\n1. Play a card");
            IO.print("2. Pick up a card");
            IO.print("3. Quit");
            choice = IO.INTput(sc, "Choose one of the options");
            if (choice == 1) {
                playCard(playerCards, compCards, topCard, deck, sc);
            } else if (choice == 2) {
                playerCards.add(cards.pickupCard(deck));
            } else if (choice == 3) {
                end = true;
            } else {
                IO.print("Please pick a valid option");
            }
        }
    }

    public static void playCard(ArrayList<cards> playerCards, ArrayList<cards> compCards, ArrayList<cards> middleCards,
            ArrayList<cards> deck, Scanner sc) {
        /*
         * 1. Ask which card to play
         * 2. If they can't play it, tell them to pick up a card
         * 3. If they can play it, remove it from their hand and add it to the top card
         * 4. If they played a 2 or blackjack, make the cpu pick up cards
         * 5. make the cpu play??
         * 6.
         */
        boolean playable = checkPlayable(playerCards, middleCards.get(middleCards.size() - 1));

        if (playable) {
            IO.print("\nYour cards: ");
            for (int i = 0; i < playerCards.size(); i++) {
                IO.print((i + 1) + ". " + playerCards.get(i).getName() + " of " + playerCards.get(i).getSuit());
            }
            IO.print("Which card would you like to play?");
            int cardPlace = IO.INTput(sc, "Enter the number of the card you want to play") - 1;
            while (cardPlace >= playerCards.size()) {
                IO.print("Please pick a valid card");
                cardPlace = IO.INTput(sc, "Enter the number of the card you want to play") - 1;
            }
            middleCards.add(playerCards.get(cardPlace));
            playerCards.remove(cardPlace);
            CPUturn(compCards, middleCards, deck, sc);
        } else {
            IO.print("You cannot play any cards, you must pick up a card");
            return;
        }
    }

    public static void cardSwap(ArrayList<cards> from, ArrayList<cards> to, int index) {
        to.add(from.get(index));
        from.remove(index);
        return;
    }

    public static void CPUturn(ArrayList<cards> compCards, ArrayList<cards> middleCards, ArrayList<cards> deck,
            Scanner sc) {
        /*
         * 1. Play a card.
         * a. Use chackplayable.
         * 2. Chose a suit when ace.
         * a. Count each suit, most gets picked.
         * 3. Prioritise pickup cards like 2s and Jacks.
         * a. Search for those first.
         */
        // Searching for pickups
        for (int i = 0; i < compCards.size(); i++) {
            if (compCards.get(i).name.equals("Jack") || compCards.get(i).name.equals("Two")) {
                cards cardToPlay = compCards.get(i);
                cardSwap(compCards, middleCards, i);
            } else if (checkPlayable(compCards, middleCards.get(middleCards.size() - 1))) {
                cards cardToPlay = compCards.get(i);
                cardSwap(compCards, middleCards, i);
            }
            else{

            }
        }
    }

    public static boolean checkPlayable(ArrayList<cards> playerCards, cards middleCard) {
        for (int i = 0; i < playerCards.size(); i++) {
            if (playerCards.get(i).suit.equals(middleCard.suit)) {
                return true;
            } else if (playerCards.get(i).name.equals(middleCard.name)) {
                return true;
            }
        }
        return false;
    }

    public static boolean checkPlayable(cards playerCards, cards middleCard) {
        if (playerCards.suit.equals(middleCard.suit)) {
            return true;
        } else if (playerCards.name.equals(middleCard.name)) {
            return true;
        } else {
            return false;
        }
    }

    public static void refreshDisplay(ArrayList<cards> playerCards, ArrayList<cards> compCards,
            ArrayList<cards> topCard) {
        IO.clear();
        IO.print("==The Card Game==");
        IO.print("\nYour cards: ");
        for (int i = 0; i < playerCards.size(); i++) {
            IO.print(playerCards.get(i).getName() + " of " + playerCards.get(i).getSuit());
        }
        IO.print("\nCPU remaining cards: " + compCards.size());
        IO.print("\nTop card: " + topCard.get(topCard.size() - 1).getName() + " of "
                + topCard.get(topCard.size() - 1).getSuit());
    }

    public static void theGameSteup(ArrayList<cards> playerCards, ArrayList<cards> compCards, ArrayList<cards> topCard,
            ArrayList<cards> deck) {
        for (int i = 0; i < 5; i++) {
            playerCards.add(cards.pickupCard(deck));
            compCards.add(cards.pickupCard(deck));
        }
        topCard.add(cards.pickupCard(deck));
    }
}
