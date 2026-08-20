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
        //System.out.println(chainAvailable(playerCards));
        // checkForChains(playerCards, chained, sc);
        sortDeck(playerCards);
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

    public static void sortDeck(ArrayList<cards> deck) {
        String[] suits = { "Clubs", "Diamonds", "Hearts", "Spades" };
        String[] names = { "Ace", "Two", "Three", "Four", "Five", "Six", "Seven", "Eight", "Nine", "Ten", "Jack",
                "Queen", "King" };

        ArrayList<cards> sorted = new ArrayList<cards>();

        for (int j = 0; j < suits.length; j++) {
            for (int k = 0; k < names.length; k++) {
                for (int i = 0; i < deck.size(); i++) {
                    cards current = deck.get(i);

                    if(current.name.equals(names[k])&&current.suit.equals(suits[j])){
                        sorted.add(current);
                    }

                }
            }
        }

        for(cards c:sorted){
            IO.print(nameFor(c));
        }
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
            int choice = IO.INTput(sc, "Chose");

            if (chainedCards.size() == 0) {
                cardsMove(deck, chainedCards, choice - 1);
            } else {
                cards toMove = deck.get(choice - 1);
                cards topChain = chainedCards.get(chainedCards.size() - 1);
                if (toMove.name.equals(topChain.name)) {
                    cardsMove(deck, chainedCards, choice - 1);
                } else if (toMove.value + 1 == topChain.value) {
                    cardsMove(deck, chainedCards, choice - 1);
                } else if (toMove.value - 1 == topChain.value) {
                    cardsMove(deck, chainedCards, choice - 1);
                } else {
                    IO.clear();
                    IO.print("No");
                    IO.enterPause(sc);
                }
                refresh(deck, chainedCards);
                String done = IO.StringPut(sc, "Are you done [y/n]");
                if (done.equals("y")) {
                    confirm = true;
                } else if (done.equals("n")) {
                    confirm = false;
                }
            }
        }
        IO.print("Ok");
        IO.enterPause(sc);

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
