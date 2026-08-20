import java.util.ArrayList;
import java.util.Scanner;

public class chaining {
    public static void main(String[] args) {
        IO.clear();
        ArrayList<cards> playerCards = new ArrayList<cards>();
        ArrayList<cards> entireDeck = cards.loadDeckCards();
        ArrayList<cards> chained = new ArrayList<cards>();
        Scanner sc = new Scanner(System.in);
        /*
         * ArrayList<cards> entireDeck = new ArrayList<cards>();
         * 
         * // Test cards
         * entireDeck.add(new cards(1, 1, "Spades", "Ace", false));
         * entireDeck.add(new cards(2, 2, "Hearts", "Two", false));
         * entireDeck.add(new cards(3, 3, "Diamonds", "Three", false));
         * entireDeck.add(new cards(4, 4, "Clubs", "Four", false));
         * entireDeck.add(new cards(5, 4, "Spades", "Four", false));
         * entireDeck.add(new cards(6, 5, "Hearts", "Five", false));
         * entireDeck.add(new cards(7, 7, "Diamonds", "Seven", false));
         * entireDeck.add(new cards(8, 9, "Clubs", "Nine", false));
         * entireDeck.add(new cards(9, 10, "Spades", "Ten", false));
         * entireDeck.add(new cards(10, 13, "Hearts", "King", false));
         */

        for (int i = 0; i < 20; i++) {
            playerCards.add(cards.pickupCard(entireDeck));
        }
        // System.out.println(chainAvailable(playerCards));
        // checkForChains(playerCards, chained, sc);
        playerCards = sortDeck(playerCards);
        if (chainAvailable(playerCards)) {
            checkForChains(playerCards, chained, sc);
        }

    }

    public static void refresh(ArrayList<cards> deck, ArrayList<cards> chained) {
        IO.clear();
        IO.print("==Remaining cards==\n");
        for (int i = 0; i < deck.size(); i++) {
            IO.print((i + 1) + ". " + nameFor(deck.get(i)));
        }
        IO.print("\n== Chained cards==\n");
        for (int i = 0; i < chained.size(); i++) {
            IO.print((i + 1) + ". " + nameFor(chained.get(i)));
        }
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

        for (cards c : sorted) {
            IO.print(nameFor(c));
        }

        return sorted;
    }

    public static void checkForChains(ArrayList<cards> deck, ArrayList<cards> chainedCards, Scanner sc) {
        /*
         * if same number
         * cards are ascending
         * cards are decsending
         */

        boolean confirm = false;
        ArrayList<cards> OldMainDeck = new ArrayList<cards>(deck);

        while (!confirm) {
            refresh(deck, chainedCards);
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
            }
             else if (chainedCards.size() == 0) {
                cardsMove(deck, chainedCards, choice - 1);
            }  else {
                cards toMove = deck.get(choice - 1);
                cards topChain = chainedCards.get(chainedCards.size() - 1);
                if (toMove.name.equals(topChain.name)) {
                    cardsMove(deck, chainedCards, choice - 1);
                } else if ((toMove.value + 1 == topChain.value)&& toMove.suit.equals(topChain.suit)) {
                    cardsMove(deck, chainedCards, choice - 1);
                } else if ((toMove.value - 1 == topChain.value)&& toMove.suit.equals(topChain.suit)) {
                    cardsMove(deck, chainedCards, choice - 1);
                } else {
                    IO.clear();
                    IO.print("No");
                    IO.enterPause(sc);
                }
            }
        } 

        topCardCheck(chainedCards);
        IO.print("Ok");
        IO.enterPause(sc);

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

    public static void cardsMove(ArrayList<cards> from, ArrayList<cards> to, int index) {
        to.add(from.get(index));
        from.remove(index);
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

    public static String nameFor(cards output) {
        return output.name + " of " + output.suit;
    }
}
