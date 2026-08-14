import java.util.ArrayList;

public class checking {
    public static void main(String[] args) {
        ArrayList<cards> deck = cards.loadDeckCards();

        ArrayList<cards> royalFlush = new ArrayList<>();

        royalFlush.add(deck.get(9)); // Ten of Spades
        royalFlush.add(deck.get(10)); // Jack of Spades
        royalFlush.add(deck.get(11)); // Queen of Spades
        royalFlush.add(deck.get(12)); // King of Spades
        royalFlush.add(deck.get(0)); // Ace of Spades

        IO.print("Hand found: " + checkHand(royalFlush));

    }

    public static int[] checkSuits(ArrayList<cards> originalDeck) {
        // IO.print("\n== New Deck Suits ==\n");
        String[] suits = { "Spades", "Hearts", "Diamonds", "Clubs" };
        int[] suitsCount = { 0, 0, 0, 0 };
        // IO.print("Here");
        // Numbers/Value check
        if (originalDeck != null) {
            for (int j = 0; j < suits.length; j++) {
                for (int i = 0; i < originalDeck.size(); i++) {
                    if (suits[j].equals(originalDeck.get(i).getSuit())) {
                        suitsCount[j]++;
                    }
                }
            }
        }

        for (int i = 0; i < suits.length; i++) {
            if (suitsCount[i] > 0) {
                // IO.print(suits[i] + " - " + suitsCount[i]);
            }
        }

        return suitsCount;
    }

    public static int[] checkValue(ArrayList<cards> originalDeck) {
        // IO.print("\n== New Deck Value ==\n");

        String[] values = { "Ace", "Two", "Three", "Four", "Five", "Six", "Seven", "Eight", "Nine", "Ten", "Jack",
                "Queen", "King" };
        int[] valueCount = { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
        // Numbers/Value check
        if (originalDeck != null) {
            for (int j = 0; j < values.length; j++) {
                for (int i = 0; i < originalDeck.size(); i++) {
                    if (values[j].equals(originalDeck.get(i).getName())) {
                        valueCount[j]++;
                    }
                }
            }

        }
        for (int i = 0; i < values.length; i++) {
            if (valueCount[i] > 0) {
                // IO.print(values[i] + " - " + valueCount[i]);
            }
        }

        return valueCount;

    }

    public static String checkHand(ArrayList<cards> givenDeck) {
        boolean pair = false;
        int[] suitsCount = checkSuits(givenDeck);
        int[] valueCount = checkValue(givenDeck);

        int count = 0;

        boolean flush = false;
        boolean four = false;
        boolean three = false;
        boolean twoPair = false;
        boolean straight = false;
        boolean rf = false;

        // Check for flush
        for (int i = 0; i < suitsCount.length; i++) {
            if (suitsCount[i] == 5) {
                flush = true;
                break;
            }
        }
        for (int i = 0; i < valueCount.length; i++) {
            if (valueCount[i] == 4) {
                four = true;
            } else if (valueCount[i] == 3) {

                three = true;
            } else if (valueCount[i] == 2) {
                if (pair == true) {
                    twoPair = true;
                }
                pair = true;
            }
            if (valueCount[i] == 1) {
                count++;
                if (count == 5) {
                    straight = true;
                }
            } else {
                count = 0;
            }

        }

        // high straight
        if ((valueCount[0] == 1) && (count == 4)) {
            rf = true;
        }

        return stringHand(flush, four, three, twoPair, pair, straight, rf);
    }

    public static String stringHand(boolean flush, boolean four, boolean three, boolean twoPair, boolean pair,
            boolean straight, boolean rf) {
        if (rf == true) {
            return "Royal Flush";
        } else if (flush && straight) {
            return "Straight Flush";
        } else if (four) {
            return "Four of a Kind";
        } else if (three && pair) {
            return "Full House";
        } else if (flush) {
            return "Flush";
        } else if (straight) {
            return "Straight";
        } else if (three) {
            return "Three of a Kind";
        } else if (twoPair) {
            return "Two Pair";
        } else if (pair) {
            return "One Pair";
        } else {
            return "High Card";
        }
    }

}