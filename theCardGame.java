import java.util.ArrayList;
import java.util.Scanner;

/*Say who wins
show previous card under the top card ✅
When you pick up let them play ✅
Show what the cpu played then enter to continue ✅
make it so it shows a winner
rework ai to clear all card form one suit then use ace
make it so pickup ones and it still ur turn but only for one round ✅
cards that are already played become the original deck ❎
 */
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
        // IO.clear();
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
        ////IO.clear();
        // int betAmount = player.setbet(sc);
        IO.clear();
        ArrayList<cards> deck = cards.loadDeckCards();
        ArrayList<cards> topCard = new ArrayList<cards>();
        playerCards.clear();
        compCards.clear();
        theGameSteup(playerCards, compCards, topCard, deck);

        boolean end = false;
        boolean pickedUp = false;
        while (!end) {
            refreshDisplay(playerCards, compCards, topCard);
            int choice = 0;
            choice = showOptions(sc, pickedUp);
            if (choice == 1) {
                playCard(playerCards, compCards, topCard, deck, sc);
            } else if (choice == 2) {
                if (pickedUp == false) {
                    playerCards.add(cards.pickupCard(deck));
                    pickedUp = true;
                } else if (pickedUp == true) {
                    IO.enterPause(sc);
                    CPUturn(playerCards, compCards, topCard, deck, sc);
                    pickedUp = false;
                }
            } else if (choice == 3) {
                end = true;
            } else {
                IO.print("Please pick a valid option");
            }

            if (playerCards.size() == 0 || compCards.size() == 0) {
                end = true;
            }
        }
        IO.clear();
        IO.print("Did you win...");
        IO.print("I hope so :)");
    }

    public static int showOptions(Scanner sc, boolean pickedUp) {
        IO.print("\n1. Play a card");

        if (pickedUp == true) {
            IO.print("2. End Turn");
        } else {
            IO.print("2. Pick up a card");
        }
        IO.print("3. Quit");
        int choice = IO.INTput(sc, "Choose one of the options");
        return choice;
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
            cards currentMiddle = middleCards.get(middleCards.size() - 1);
            IO.print("The top card is: " + nameFor(currentMiddle));
            IO.print("\nYour cards: ");
            for (int i = 0; i < playerCards.size(); i++) {
                IO.print((i + 1) + ". " + nameFor(playerCards.get(i)));
            }
            int cardPlace = IO.INTput(sc, "Enter the number of the card you want to play") - 1;
            boolean validInput = checkValid(playerCards, currentMiddle, cardPlace, sc);
            while (!validInput) {
                cardPlace = IO.INTput(sc, "Enter the number of the card you want to play") - 1;
                validInput = checkValid(playerCards, currentMiddle, cardPlace, sc);
            }
            // Add letting user chain here
            placeCard(playerCards, compCards, middleCards, deck, cardPlace, sc, 1);
            CPUturn(playerCards, compCards, middleCards, deck, sc);
        } else {
            IO.print("You cannot play any cards, you must pick up a card");
            IO.enterPause(sc);
            return;
        }
    }

    public static boolean checkValid(ArrayList<cards> playerCards, cards currentMiddle, int cardPlace, Scanner sc) {
        if (cardPlace < 0) {
            IO.print("Not a Negative");
            return false;
        } else if (cardPlace >= playerCards.size()) {
            IO.print("Stay in range");
            return false;
        } else if (!checkPlayable(playerCards.get(cardPlace), currentMiddle)) {
            IO.print("NO THERE'S ANOTHER CARD WHAT IS WRONG WITH YOU MY GOSH YOU ARE STUPID HUH YOU LITTLE DUMMY");
            return false;
        } else {
            return true;
        }
    }

    // Takes card from 'from' and checks it for any kinda special stuff like aces or
    // smth
    public static void placeCard(ArrayList<cards> from, ArrayList<cards> to, ArrayList<cards> middleCards,
            ArrayList<cards> deck, int index, Scanner sc, int who) {

        cards moved = from.get(index);
        // 0 is cpu 1 is user
        if (moved.name.equals("Ace")) {
            cardSwap(from, middleCards, index);

            if (who == 1) {
                String newSuit = IO.StringPut(sc, "Which suit do you want to change it to?");
                while (!newSuit.equals("Diamonds") && !newSuit.equals("Hearts") && !newSuit.equals("Clubs")
                        && !newSuit.equals("Spades")) {
                    IO.print("Please pick a valid suit");
                    newSuit = IO.StringPut(sc, "Which suit do you want to change it to?");
                }
                cards SuitChange = new cards(newSuit, -1);
                cardSwap(SuitChange, middleCards);

            } else if (who == 0) {

                cardSwap(from, middleCards, index);

                String[] suits = { "Spades", "Hearts", "Diamonds", "Clubs" };
                int[] suitsCount = { 0, 0, 0, 0 };
                for (int j = 0; j < from.size(); j++) {
                    for (int k = 0; k < suits.length; k++) {
                        if (from.get(j).suit.equals(suits[k])) {
                            suitsCount[k]++;
                        }
                    }
                }
                int highest = 0;
                int ind = 0;
                for (int k = 0; k < suits.length; k++) {
                    if (highest < suitsCount[k]) {
                        highest = suitsCount[k];
                        ind = k;
                    }
                }
                cards SuitChange = new cards(suits[ind], -1);
                cardSwap(SuitChange, middleCards);
            }
        } else if ((moved.name.equals("Jack")) && ((moved.suit.equals("Spades")) || (moved.suit.equals("Clubs")))) {
            for (int i = 0; i < 5; i++) {
                to.add(cards.pickupCard(deck));
            }
            cardSwap(from, middleCards, index);
        } else if (moved.name.equals("Two")) {
            for (int i = 0; i < 2; i++) {
                to.add(cards.pickupCard(deck));
            }
            cardSwap(from, middleCards, index);
        } else {
            cardSwap(from, middleCards, index);
        }
    }

    public static void cardSwap(ArrayList<cards> from, ArrayList<cards> to, int index) {
        to.add(from.get(index));
        from.remove(index);
        return;
    }

    public static void cardSwap(cards from, ArrayList<cards> to) {
        to.add(from);
        return;
    }

    public static void CPUturn(ArrayList<cards> playerCards, ArrayList<cards> compCards, ArrayList<cards> middleCards,
            ArrayList<cards> deck, Scanner sc) {
        /*
         * 1. Play a card.
         * a. Use chackplayable.
         * 2. Chose a suit when ace.
         * a. Count each suit, most gets picked.
         */
        // Searching for pickups
        boolean played = false;
        cards currentMiddle = middleCards.get(middleCards.size() - 1);
        boolean playable = checkPlayable(compCards, currentMiddle);

        if (playable) {

            for (int i = 0; i < compCards.size(); i++) {
                String currentName = compCards.get(i).name;
                String currentSuit = compCards.get(i).suit;
                if (currentName.equals("Jack") && checkPlayable(compCards.get(i), currentMiddle)
                        && ((currentSuit.equals("Spades")) || (currentSuit.equals("Clubs")))) {
                    IO.print(i);
                    played = CPUplay(compCards, playerCards, middleCards, deck, i, sc, played);
                    break;
                } else if (currentName.equals("Two") && checkPlayable(compCards.get(i), currentMiddle)) {
                    played = CPUplay(compCards, playerCards, middleCards, deck, i, sc, played);
                    break;
                } else if (currentName.equals("Ace") && checkPlayable(compCards.get(i), currentMiddle)) {
                    played = CPUplay(compCards, playerCards, middleCards, deck, i, sc, played);
                    break;
                }
            }
            // Checks for next playable card if none of above is found
            if (!played) {
                for (int i = 0; i < compCards.size(); i++) {
                    if (checkPlayable(compCards.get(i), currentMiddle)) {
                        played = CPUplay(compCards, playerCards, middleCards, deck, i, sc, played);
                        break;
                    }
                }
            }
        } else {
            // pickup card if no playable cards
            IO.clear();
            IO.print("The CPU picked up a card");
            compCards.add(cards.pickupCard(deck));
            IO.enterPause(sc);
        }
    }

    public static boolean CPUplay(ArrayList<cards> compCards, ArrayList<cards> playerCards,
            ArrayList<cards> middleCards, ArrayList<cards> deck, int i, Scanner sc, boolean played) {
        cards userplayed = middleCards.get(middleCards.size() - 1);
        cards cpuplayed = compCards.get(i);
        IO.clear();
        placeCard(compCards, playerCards, middleCards, deck, i, sc, 0);
        IO.print("The CPU played: " + nameFor(cpuplayed));
        IO.enterPause(sc);
        played = true;
        return played;
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
        IO.print("\nYour cards(Remaining - " + playerCards.size() + "): ");
        for (int i = 0; i < playerCards.size(); i++) {
            IO.print(nameFor(playerCards.get(i)));
        }
        IO.print("\nCPU remaining cards: " + compCards.size());
        // IO.print("==FOR TESTING ONLY==");
        for (int i = 0; i < compCards.size(); i++) {
            // IO.print(compCards.get(i).getName() + " of " + compCards.get(i).getSuit());
        }
        cards currentMiddle = topCard.get(topCard.size() - 1);
        try {
            cards beforeMiddle = topCard.get(topCard.size() - 2);
            IO.print("\nPrevious card: " + nameFor(beforeMiddle));
        } catch (IndexOutOfBoundsException e) {
            IO.print("\nNothing to see...");
        }
        // Ace changed suit

        if (currentMiddle.getID() == -1) {
            IO.print("The next card played must be a: " + currentMiddle.suit);
        } else {
            IO.print("Top card: " + nameFor(currentMiddle));
        }

    }

    public static void theGameSteup(ArrayList<cards> playerCards, ArrayList<cards> compCards, ArrayList<cards> topCard,
            ArrayList<cards> deck) {
        for (int i = 0; i < 5; i++) {
            playerCards.add(cards.pickupCard(deck));
            compCards.add(cards.pickupCard(deck));
        }
        topCard.add(cards.pickupCard(deck));
    }

    public static String nameFor(cards output) {
        return output.name + " of " + output.suit;
    }
}
