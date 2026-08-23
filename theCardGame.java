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
add modifiers like pickups
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
        IO.clear();
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
            choice = showOptions(sc, pickedUp, playerCards);
            if (choice == 1) {
                playCard(playerCards, compCards, topCard, deck, sc);
            } else if (choice == 2) {
                if (pickedUp == false) {
                    playerCards.add(cards.pickupCard(deck));
                    pickedUp = true;
                } else if (pickedUp == true) {
                    CPUturn(playerCards, compCards, topCard, deck, sc);
                    pickedUp = false;
                }
            } else if (choice == 3) {
                if (chainAvailable(playerCards)) {
                    ArrayList<cards> chainedCards = chainCards(playerCards, sc);
                    placeCard(chainedCards, compCards, topCard, deck, -1, sc, 0);
                } else {
                    IO.print("3 Does nothing. Don't Press it again. I mean it...");
                    IO.enterPause(sc);
                }
            } else if (choice == 0) {
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

    public static int showOptions(Scanner sc, boolean pickedUp, ArrayList<cards> playerCards) {
        IO.print("\n1. Play a card");

        if (pickedUp == true) {
            IO.print("2. End Turn");
        } else {
            IO.print("2. Pick up a card");
        }
        if (chainAvailable(playerCards)) {
            IO.print("3. Chain cards");
        }
        IO.print("0. Quit");
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
         * 5. make the cpu play?
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
            IO.clear();
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

        if (index == -1) {
            // transfer all chained cards to the middle
            cards moved = from.get(from.size() - 1);
            for (int i = 0; i < from.size(); i++) {
                cardSwap(from, middleCards, i);
            }
        } else {
            cards moved = from.get(index);
            // 0 is cpu 1 is user
            if (moved.name.equals("Ace")) {
                from.remove(index);
                if (who == 1) {
                    IO.print("You played a: " + nameFor(moved));
                    String newSuit = IO.StringPut(sc, "Which suit do you want to change it to?");
                    while (!newSuit.equals("Diamonds") && !newSuit.equals("Hearts") && !newSuit.equals("Clubs")
                            && !newSuit.equals("Spades")) {
                        IO.print("Please pick a valid suit");
                        newSuit = IO.StringPut(sc, "Which suit do you want to change it to?");
                    }
                    cards SuitChange = new cards(newSuit, -1);
                    cardSwap(SuitChange, middleCards);

                } else if (who == 0) {
                    IO.print("The CPU played a: " + nameFor(moved));

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
                    IO.print("The CPU changed the suit to: " + suits[ind]);
                    IO.enterPause(sc);

                }
            } else if ((moved.name.equals("Jack")) && ((moved.suit.equals("Spades")) || (moved.suit.equals("Clubs")))) {
                for (int i = 0; i < 5; i++) {
                    to.add(cards.pickupCard(deck));
                }
                cardSwap(from, middleCards, index);
                if (who == 1) {
                    IO.print("You played a: " + nameFor(moved));
                    IO.print("The CPU picked up 5 cards");
                } else if (who == 0) {
                    IO.print("The CPU played a: " + nameFor(moved));
                    IO.print("You picked up 5 cards");
                }
            } else if (moved.name.equals("Two")) {
                for (int i = 0; i < 2; i++) {
                    to.add(cards.pickupCard(deck));
                }
                cardSwap(from, middleCards, index);
                if (who == 1) {
                    IO.print("You played a: " + nameFor(moved));
                    IO.print("The CPU picked up 2 cards");
                } else if (who == 0) {
                    IO.print("The CPU played a: " + nameFor(moved));
                    IO.print("You picked up 2 cards");
                }
            } else {
                cardSwap(from, middleCards, index);
                if (who == 1) {
                    IO.print("You played a: " + nameFor(moved));
                } else if (who == 0) {
                    IO.print("The CPU played a: " + nameFor(moved));
                }
            }
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
            // IO.clear();
            IO.print("The CPU picked up a card");
            compCards.add(cards.pickupCard(deck));
            if (checkPlayable(compCards.get(compCards.size() - 1), currentMiddle)) {
                IO.print("But it can play a: " + nameFor(compCards.get(compCards.size() - 1)));
                placeCard(compCards, playerCards, middleCards, deck, compCards.size() - 1, sc, 0);
            }
            IO.enterPause(sc);
        }
    }

    public static boolean CPUplay(ArrayList<cards> compCards, ArrayList<cards> playerCards,
            ArrayList<cards> middleCards, ArrayList<cards> deck, int i, Scanner sc, boolean played) {
        // cards userplayed = middleCards.get(middleCards.size() - 1);
        // cards cpuplayed = compCards.get(i);
        placeCard(compCards, playerCards, middleCards, deck, i, sc, 0);
        // IO.print("The CPU played: " + nameFor(cpuplayed));
        IO.enterPause(sc);
        played = true;
        return played;
    }

    public static boolean chainAvailable(ArrayList<cards> deck) {
        for (int i = 0; i < deck.size() - 1; i++) {
            for (int j = i + 1; j < deck.size(); j++) {
                cards left = deck.get(i);
                cards right = deck.get(j);
                if (left.name.equals(right.name)) {
                    IO.print(nameFor(right));
                    IO.print(nameFor(left));
                    return true;
                } else if (left.value + 1 == right.value && left.suit.equals(right.suit)) {
                    IO.print(nameFor(right));
                    IO.print(nameFor(left));
                    return true;
                } else if (left.value - 1 == right.value && left.suit.equals(right.suit)) {
                    IO.print(nameFor(right));
                    IO.print(nameFor(left));
                    return true;
                }
            }
        }

        return false;

    }

    public static ArrayList<cards> sortDeck(ArrayList<cards> deck) {
        String[] suits = { "Clubs", "Diamonds", "Hearts", "Spades" };
        String[] names = { "Ace", "Two", "Three", "Four", "Five", "Six", "Seven", "Eight", "Nine", "Ten", "Jack",
                "Queen", "King" };

        ArrayList<cards> sorted = new ArrayList<cards>();

        for (int j = 0; j < suits.length; j++) {
            for (int k = 0; k < names.length; k++) {
                for (int i = 0; i < deck.size(); i++) {
                    cards current = deck.get(i);
                    if (current.name.equals(names[k]) && current.suit.equals(suits[j])) {
                        sorted.add(current);
                    }
                }
            }
        }

        // for (cards c : sorted) {
        // IO.print(nameFor(c));
        // }

        return sorted;
    }

    public static ArrayList<cards> chainCards(ArrayList<cards> deck, Scanner sc) {
        /*
         * if same number
         * cards are ascending
         * cards are decsending
         */

        boolean confirm = false;
        ArrayList<cards> OldMainDeck = new ArrayList<cards>(deck);
        ArrayList<cards> chainedCards = new ArrayList<cards>();

        while (!confirm) {
            refreshDisplay(deck, chainedCards, OldMainDeck);
            int choice = IO.INTput(sc, "Chose a card or input 0 to be done");

            if (choice == 0) {
                String confInput = IO.StringPut(sc, "Confirm [y] or Clear [c]");
                while (!validInput(confInput)) {
                    confInput = IO.StringPut(sc, "Only y or c");
                }
                if (confInput.equalsIgnoreCase("y")) {
                    confirm = true;
                } else if (confInput.equalsIgnoreCase("c")) {
                    chainedCards.clear();
                    deck.clear();
                    deck.addAll(OldMainDeck);
                }
            } else if (chainedCards.size() == 0) {
                cardSwap(deck, chainedCards, choice - 1);
            } else {
                cards toMove = deck.get(choice - 1);
                cards topChain = chainedCards.get(chainedCards.size() - 1);
                if (toMove.name.equals(topChain.name)) {
                    cardSwap(deck, chainedCards, choice - 1);
                } else if ((toMove.value + 1 == topChain.value) && toMove.suit.equals(topChain.suit)) {
                    cardSwap(deck, chainedCards, choice - 1);
                } else if ((toMove.value - 1 == topChain.value) && toMove.suit.equals(topChain.suit)) {
                    cardSwap(deck, chainedCards, choice - 1);
                } else {
                    IO.clear();
                    IO.print("No");
                    IO.enterPause(sc);
                }
            }
        }

        // topCardCheck(chainedCards);
        IO.print("Ok");
        IO.enterPause(sc);

        return chainedCards;

    }

    public static boolean validInput(String input) {
        if (input.equals("y") || input.equals("c")) {
            return true;
        } else {
            return false;
        }
    }

    public static void topCardCheck(ArrayList<cards> chained) {
        cards top = chained.get(chained.size() - 1);

        if (top.name.equals("Ace")) {
            IO.print("Now u change suit");
        } else if ((top.name.equals("Jack")) && ((top.suit.equals("Spades")) || (top.suit.equals("Clubs")))) {
            IO.print("pikcup5");
        } else if (top.name.equals("Two")) {
            IO.print("that's a two now i pickup2 ");
        }

    }

    public static boolean validInput(int input, ArrayList<cards> deck) {
        if (input > deck.size()) {
            return false;
        } else if (input < 0) {
            return false;
        } else {
            return true;
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
