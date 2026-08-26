import java.util.ArrayList;
import java.util.Scanner;

/*
🔴 Not tested
✅ Done

Say who wins
show previous card under the top card ✅
When you pick up let them play ✅
Show what the cpu played then enter to continue ✅
make it so it shows a winner ✅
rework ai to clear all card form one suit then use ace ✅
make it so pickup ones and it still ur turn but only for one round ✅
cards that are already played become the original deck ❎
add modifiers like pickups ✅
let the CPU chain cards 
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
        IO.clear();
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
            choice = showOptions(sc, pickedUp, playerCards, topCard);
            if (choice == 1) {
                if (checkPlayable(playerCards, topCard.get(topCard.size() - 1))) {
                    playCard(playerCards, compCards, topCard, deck, sc);
                } else {
                    IO.print("You cannot play\n Press 2 to pick up a card");
                    IO.enterPause(sc);
                }
            } else if (choice == 2) {
                if (pickedUp == false) {
                    playerCards.add(cards.pickupCard(deck));
                    pickedUp = true;
                } else if (pickedUp == true) {
                    CPUturn(playerCards, compCards, topCard, deck, sc);
                    pickedUp = false;
                }
            } else if (choice == 3) {
                if (isChainPlayable(playerCards, topCard)) {
                    ArrayList<cards> chainedCards = chainCards(playerCards, topCard, sc);
                    moveChain(chainedCards, playerCards, compCards, deck, sc, topCard, 1);
                    CPUturn(playerCards, compCards, topCard, deck, sc);
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
                IO.clear();
                if (playerCards.size() == 0) {
                    IO.print("You won!");
                } else if (compCards.size() == 0) {
                    IO.print("The CPU won!");
                }
            }
        }
        IO.enterPause(sc);
    }

    // Current problem: needs to check for chain then check if cards in chain are
    // playable not just if teh entire deck is playable
    public static boolean isChainPlayable(ArrayList<cards> playerCards, ArrayList<cards> middleCards) {
        if (chainAvailable(playerCards, middleCards)) {
            return true;
        }
        return false;
    }

    public static boolean validRangeInput(ArrayList<cards> playerCards, int input) {
        if (input < 0 || input >= playerCards.size()) {
            return false;
        } else {
            return true;
        }
    }

    public static int showOptions(Scanner sc, boolean pickedUp, ArrayList<cards> playerCards,
            ArrayList<cards> middleCards) {
        if (checkPlayable(playerCards, middleCards.get(middleCards.size() - 1))) {
            IO.print("\n1. Play a card");
        } else {
            IO.print("1. Unavailable");
        }
        if (pickedUp == true) {
            IO.print("2. End Turn");
        } else {
            IO.print("2. Pick up a card");
        }
        if (isChainPlayable(playerCards, middleCards)) {
            IO.print("3. Chain cards");
        } else {
            IO.print("3. Unavailable");
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
            while (!checkValid(playerCards, currentMiddle, cardPlace)) {
                cardPlace = IO.INTput(sc, "Enter the number of the card you want to play") - 1;
            }
            // Add letting user chain here
            IO.clear();
            cards toPlace = playerCards.get(cardPlace);
            placeCard(toPlace, playerCards, compCards, middleCards, deck, sc, false);
            CPUturn(playerCards, compCards, middleCards, deck, sc);
        } else {
            IO.print("You cannot play any cards, you must pick up a card");
            IO.enterPause(sc);
            return;
        }
    }

    public static boolean checkValid(ArrayList<cards> playerCards, cards currentMiddle, int cardPlace) {
        if (!(validRangeInput(playerCards, cardPlace))) {
            IO.print("Input is outside of range");
            return false;
        } else if (!checkPlayable(playerCards.get(cardPlace), currentMiddle)) {
            IO.print("NO THERE'S ANOTHER CARD WHAT IS WRONG WITH YOU MY GOSH YOU ARE STUPID HUH YOU LITTLE DUMMY");
            return false;
        } else {
            return true;
        }
    }

    public static void moveChain(ArrayList<cards> chain, ArrayList<cards> playerCards, ArrayList<cards> CPUcards,
            ArrayList<cards> deck, Scanner sc, ArrayList<cards> middleCards, int who) {
        // transfer all chained cards to the middle
        int count = 0;
        while (chain.size() > 0) {

            cards moved = chain.get(0);

            if (who == 0) {
                IO.print("The CPU played " + nameFor(moved));
                placeCard(moved, playerCards, CPUcards, middleCards, deck, sc, true);
            } else if (who == 1) {
                IO.print("You played " + nameFor(moved));
                placeCard(moved, playerCards, CPUcards, middleCards, deck, sc, false);
            }
            count++;
        }
        if (who == 0) {
            IO.print("The CPU played " + count + " cards");
        } else if (who == 1) {
            IO.print("You played " + count + " cards");
        }
    }
    /*
     * Placecard in:
     * One card (the one the user picked)
     * int for who
     * user,cpu and deck of cards
     * 
     * Place card does:
     * check card for specials and redirects it
     * 
     * Redirects:
     * applies change to relevant deck
     * only one deck sent and whos deck it is getting added to
     */

    public static void placeCard(cards toPlace, ArrayList<cards> playerCards, ArrayList<cards> CPUcards,
            ArrayList<cards> middleCards, ArrayList<cards> fullDeck, Scanner sc, boolean CPU) {
        if (toPlace.name.equals("Ace")) {
            if (CPU) {
                cardPlacer(toPlace, CPUcards, middleCards);
                cards suitChange = applyAce(CPUcards);
                IO.print("The CPU changed the suit to: " + suitChange.suit);
                middleCards.add(suitChange);
            } else {
                cardPlacer(toPlace, playerCards, middleCards);
                cards suitChange = applyAce(playerCards, sc);
                IO.print("You changed the suit to: " + suitChange.suit);
                middleCards.add(suitChange);
            }
        } else if (isBlackjack(toPlace)) {
            if (CPU) {
                cardPlacer(toPlace, CPUcards, middleCards);
                blackJackApply(playerCards, fullDeck);
                IO.print("The CPU played a: " + nameFor(toPlace));
                IO.print("You picked up 5 cards");
            } else {
                cardPlacer(toPlace, playerCards, middleCards);
                blackJackApply(CPUcards, fullDeck);
                IO.print("You played a: " + nameFor(toPlace));
                IO.print("The CPU picked up 5 cards");
            }
        } else if (toPlace.name.equals("Two")) {
            if (CPU) {
                cardPlacer(toPlace, CPUcards, middleCards);
                twoApply(playerCards, fullDeck);
                IO.print("The CPU played a: " + nameFor(toPlace));
                IO.print("You picked up 2 cards");
            } else {
                cardPlacer(toPlace, playerCards, middleCards);
                twoApply(CPUcards, fullDeck);
                IO.print("You played a: " + nameFor(toPlace));
                IO.print("The CPU picked up 2 cards");
            }
        } else {
            if (CPU) {
                cardPlacer(toPlace, CPUcards, middleCards);
                IO.print("The CPU played a: " + nameFor(toPlace));
            } else {
                cardPlacer(toPlace, playerCards, middleCards);
                IO.print("You played a: " + nameFor(toPlace));
            }
        }
    }

    public static void cardPlacer(cards find, ArrayList<cards> cardsIn, ArrayList<cards> middleCards) {
        for (int i = 0; i < cardsIn.size(); i++) {
            cards c = cardsIn.get(i);
            if (c.equals(find)) {
                cardsIn.remove(i);
                middleCards.add(c);
            }
        }
    }

    public static void blackJackApply(ArrayList<cards> deck, ArrayList<cards> entireDeck) {
        for (int i = 0; i < 5; i++) {
            deck.add(cards.pickupCard(entireDeck));
        }
    }

    public static void twoApply(ArrayList<cards> deck, ArrayList<cards> entireDeck) {
        for (int i = 0; i < 2; i++) {
            deck.add(cards.pickupCard(entireDeck));
        }
    }

    public static cards applyAce(ArrayList<cards> deck, Scanner sc) {
        String newSuit = IO.StringPut(sc, "Which suit do you want to change it to?");
        while (!validSuitCheck(newSuit)) {
            IO.print("Please pick a valid suit");
            newSuit = IO.StringPut(sc, "Which suit do you want to change it to?");
        }
        cards SuitChange = new cards(newSuit, -1);
        return SuitChange;
    }

    public static cards applyAce(ArrayList<cards> deck) {
        String[] suits = { "Spades", "Hearts", "Diamonds", "Clubs" };
        int[] suitsCount = { 0, 0, 0, 0 };
        for (int j = 0; j < deck.size(); j++) {
            for (int k = 0; k < suits.length; k++) {
                if (deck.get(j).suit.equals(suits[k])) {
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
        return SuitChange;
    }

    public static boolean isBlackjack(cards check) {
        if (check.name.equals("Jack") && ((check.suit.equals("Spades")) || (check.suit.equals("Clubs")))) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean isBlackjack(ArrayList<cards> check) {
        for (cards c : check) {
            if (c.name.equals("Jack") && ((c.suit.equals("Spades")) || (c.suit.equals("Clubs")))) {
                return true;
            }
        }
        return false;
    }

    public static boolean validSuitCheck(String suit) {
        if (suit.equalsIgnoreCase("Spades") || suit.equalsIgnoreCase("Hearts") || suit.equalsIgnoreCase("Diamonds")
                || suit.equalsIgnoreCase("Clubs")) {
            return true;
        } else {
            return false;
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
         * Thought process for ai
         * see if chain
         * check for the current suit and play that first
         * within suit priorisise order 2,jack....,ace
         * ace changes as normal
         */

        cards currentMiddle = middleCards.get(middleCards.size() - 1);
        boolean playable = checkPlayable(compCards, currentMiddle);
        // check for chains

        if (isChainPlayable(compCards, middleCards)) {
            // compCards = sortDeck(compCards, 0);
            ArrayList<cards> chainedCards = CPUchain(compCards, middleCards);
            moveChain(chainedCards, playerCards, compCards, deck, sc, middleCards, 0);
        } else if (playable) {
            cards exctractedCard = CPUHandCheck(compCards, currentMiddle, deck);
            IO.enterPause(sc);
            placeCard(exctractedCard, playerCards, compCards, middleCards, deck, sc, true);
        } else {
            // pickup card if no playable cards
            // //IO.clear();
            IO.print("The CPU picked up a card");
            compCards.add(cards.pickupCard(deck));
            for (cards c : compCards) {
                if (checkPlayable(c, currentMiddle)) {
                    IO.print("But it can play a: " + nameFor(c));

                }
            }
        }
    }

    public static cards CPUHandCheck(ArrayList<cards> compCards, cards currentMiddle,
            ArrayList<cards> deck) {
        /*
         * Check for bj
         * then for twos
         * then clear all suit
         * funish with ace
         * if not then pickup (done)
         */
        if (blackJackFound(compCards)) {
            for (cards c : compCards) {
                if (isBlackjack(c)) {
                    return c;
                }
            }
        } else if (twoFound(compCards)) {
            for (cards c : compCards) {
                if (c.name.equals("Two")) {
                    return c;
                }
            }
        } else if (!suitClear(compCards, currentMiddle)) {
            for (cards c : compCards) {
                if (c.suit.equals(currentMiddle.suit)) {
                    return c;
                }
            }
        } else if (aceFound(compCards)) {
            for (cards c : compCards) {
                if (c.name.equals("Ace")) {
                    return c;
                }
            }
        }
        return null;
    }

    public static boolean blackJackFound(ArrayList<cards> CPUcards) {
        for (cards c : CPUcards) {
            if (isBlackjack(c)) {
                return true;
            }
        }
        return false;
    }

    public static boolean twoFound(ArrayList<cards> CPUcards) {
        for (cards c : CPUcards) {
            if (c.name.equals("Two")) {
                return true;
            }
        }
        return false;
    }

    public static boolean suitClear(ArrayList<cards> CPUcards, cards currentMiddle) {
        String topSuit = currentMiddle.suit;
        for (cards c : CPUcards) {
            if (c.suit.equals(topSuit)) {
                return false;
            }
        }
        return true;
    }

    public static boolean aceFound(ArrayList<cards> CPUcards) {
        for (cards c : CPUcards) {
            if (c.name.equals("Ace")) {
                return true;
            }
        }
        return false;
    }

    public static boolean chainAvailable(ArrayList<cards> deck, ArrayList<cards> middleCards) {
        ArrayList<cards> chainableCards = new ArrayList<cards>();
        for (int i = 0; i < deck.size() - 1; i++) {
            for (int j = i + 1; j < deck.size(); j++) {
                cards left = deck.get(i);
                cards right = deck.get(j);

                if (left.name.equals(right.name)) {
                    chainableCards.add(left);

                } else if (left.value + 1 == right.value && left.suit.equals(right.suit)) {
                    chainableCards.add(left);

                } else if (left.value - 1 == right.value && left.suit.equals(right.suit)) {
                    chainableCards.add(left);

                }
            }
        }
        if (checkPlayable(chainableCards, middleCards.get(middleCards.size() - 1))) {
            return true;
        }
        return false;
    }

    public static ArrayList<cards> CPUchain(ArrayList<cards> CPUcards, ArrayList<cards> middleCards) {
        ArrayList<cards> chainedCards = new ArrayList<cards>();

        /*
         * First card needs to be playable with the middle
         * next only has to be chainable with the playable card
         */
        IO.clear();
        IO.print("\nCardssorted liek this");
        for (cards c : CPUcards) {
            IO.print(nameFor(c));
        }

        IO.print("\nTHE CPU IS CHAINING");
        if (chainedCards.size() == 0) {
            for (int i = 0; i < CPUcards.size(); i++) {
                IO.print("THE CPU IS CHECKING " + nameFor(CPUcards.get(i)));
                if (checkPlayable(CPUcards.get(i), middleCards.get(middleCards.size() - 1))) {
                    IO.print("THE CPU PLAYEED A " + nameFor(CPUcards.get(i)));
                    cardSwap(CPUcards, chainedCards, i);
                    break;
                }
            }
        }

        while (canChainAdd(CPUcards, chainedCards.get(chainedCards.size() - 1))) {
            for (int i = 0; i < CPUcards.size(); i++) {
                cards toMove = CPUcards.get(i);
                cards topChain = chainedCards.get(chainedCards.size() - 1);
                IO.print("THE CPU IS CHECKING " + nameFor(toMove) + " WITH " + nameFor(topChain));
                if (toMove.name.equals(topChain.name)) {
                    cardSwap(CPUcards, chainedCards, i);
                    IO.print("MATCH FOUND\n");
                    i = 0;
                    break;
                } else if ((toMove.value + 1 == topChain.value) && toMove.suit.equals(topChain.suit)) {
                    cardSwap(CPUcards, chainedCards, i);
                    IO.print("MATCH FOUND\n");
                    i = 0;
                    break;
                } else if ((toMove.value - 1 == topChain.value) && toMove.suit.equals(topChain.suit)) {
                    cardSwap(CPUcards, chainedCards, i);
                    IO.print("MATCH FOUND\n");
                    i = 0;
                    break;
                } else {
                    IO.print("THEY WERE NOT A MATCH");
                }
            }
        }
        Scanner sc = new Scanner(System.in);
        for (cards c : chainedCards) {
            IO.print(nameFor(c));
        }

        IO.print("\nCPU CARDS AFTER CHAINING\n");
        for (cards c : CPUcards) {

            IO.print(nameFor(c));
        }
        IO.enterPause(sc);
        return chainedCards;
    }

    public static boolean canChainAdd(ArrayList<cards> checking, cards topInChain) {
        IO.print("RIght: " + nameFor(topInChain));
        for (int i = 0; i < checking.size(); i++) {
            IO.print("Checking: " + nameFor(checking.get(i)));
            IO.print("Left: " + nameFor(checking.get(i)));
            if (checking.get(i).name.equals(topInChain.name)) {
                IO.print(nameFor(topInChain));
                IO.print(nameFor(checking.get(i)));
                return true;
            } else if (checking.get(i).value + 1 == topInChain.value && checking.get(i).suit.equals(topInChain.suit)) {
                IO.print(nameFor(topInChain));
                IO.print(nameFor(checking.get(i)));
                return true;
            } else if (checking.get(i).value - 1 == topInChain.value && checking.get(i).suit.equals(topInChain.suit)) {
                IO.print(nameFor(topInChain));
                IO.print(nameFor(checking.get(i)));
                return true;
            }
        }
        return false;
    }

    public static ArrayList<cards> sortDeck(ArrayList<cards> deck, int user) {
        String[] suits = { "Clubs", "Diamonds", "Hearts", "Spades" };

        String[] namesCPU = { "Two", "Jack", "Three", "Four", "Five", "Six", "Seven", "Eight", "Nine", "Ten",
                "Queen", "King", "Ace" };
        String[] names = { "Ace", "Two", "Three", "Four", "Five", "Six", "Seven", "Eight", "Nine", "Ten", "Jack",
                "Queen", "King" };

        ArrayList<cards> sorted = new ArrayList<cards>();

        for (int j = 0; j < suits.length; j++) {
            for (int k = 0; k < names.length; k++) {
                for (int i = 0; i < deck.size(); i++) {
                    cards current = deck.get(i);
                    if (user == 1) {
                        if (current.name.equals(namesCPU[k]) && current.suit.equals(suits[j])) {
                            sorted.add(current);
                        }
                    } else if (user == 0) {
                        if (current.name.equals(names[k]) && current.suit.equals(suits[j])) {
                            sorted.add(current);
                        }
                    }
                }
            }
        }
        return sorted;
    }

    public static ArrayList<cards> chainCards(ArrayList<cards> deck, ArrayList<cards> middleCards, Scanner sc) {
        /*
         * if same number
         * cards are ascending
         * cards are decsending
         */

        boolean confirm = false;
        ArrayList<cards> OldMainDeck = new ArrayList<cards>(deck);
        ArrayList<cards> chainedCards = new ArrayList<cards>();
        cards topMiddleCard = middleCards.get(middleCards.size() - 1);
        while (!confirm) {
            refreshChainDisplay(deck, chainedCards, topMiddleCard);
            int choice = IO.INTput(sc, "Chose a card or input 0 to be done");
            while (!validInput(choice, deck)) {
                choice = IO.INTput(sc, "Please pick a valid option");
            }
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
                if (checkPlayable(deck.get(choice - 1), topMiddleCard)) {
                    cardSwap(deck, chainedCards, choice - 1);
                } else {
                    IO.print("Selection is Unavailable.\nIf you press 0 then c to clear your selections");
                    IO.enterPause(sc);
                }

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
                    IO.print("Selection is Unavailable.\nIf you press 0 then c to clear your selections");
                    IO.enterPause(sc);
                }
            }
        }

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
        /*
         * IO.print("==FOR TESTING ONLY==");
         * for (int i = 0; i < compCards.size(); i++) {
         * IO.print(compCards.get(i).getName() + " of " + compCards.get(i).getSuit());
         * }
         */

        IO.print("\nThere are currently " + topCard.size() + " cards in the middle");
        cards currentMiddle = topCard.get(topCard.size() - 1);

        /*
         * for (int i = 0; i < topCard.size(); i++) {
         * IO.print("Middle card " + (i + 1) + ": " + nameFor(topCard.get(i)));
         * }
         */

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

    public static void refreshChainDisplay(ArrayList<cards> playerCards, ArrayList<cards> chainDeck,
            cards topMiddleCard) {
        IO.clear();
        // playerCards = sortDeck(playerCards, 0);
        // chainDeck = sortDeck(chainDeck, 0);

        IO.print("==Chain Mode Activated==");

        IO.print("\nYour cards(Remaining - " + playerCards.size() + "): ");
        for (int i = 0; i < playerCards.size(); i++) {
            IO.print((i + 1) + ": " + nameFor(playerCards.get(i)));
        }
        IO.print("\nChain deck: " + chainDeck.size());

        for (int i = 0; i < chainDeck.size(); i++) {
            IO.print(chainDeck.get(i).getName() + " of " + chainDeck.get(i).getSuit());
        }
        if (chainDeck.size() == 0) {
            IO.print("Your chain is empty and must be able to be oplaced on top of: " + nameFor(topMiddleCard));

        }
    }

    public static void theGameSteup(ArrayList<cards> playerCards, ArrayList<cards> compCards, ArrayList<cards> topCard,
            ArrayList<cards> deck) {
        for (int i = 0; i < 5; i++) {
            compCards.add(cards.pickupCard(deck));
            playerCards.add(cards.pickupCard(deck));
        }
        topCard.add(cards.pickupCard(deck));
    }

    public static String nameFor(cards output) {
        return output.name + " of " + output.suit;
    }
}
