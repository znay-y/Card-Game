import java.util.ArrayList;

public class checking {
    public static void main(String[] args) {
        String[] testValues = {
                "Ace", "Two", "Three", "Four", "Five",
                "Six", "Seven", "Eight", "Nine", "Ten"
        };

        String[] testSuits = {
                "Spades", "Hearts", "Diamonds", "Clubs", "Spades",
                "Hearts", "Diamonds", "Clubs", "Spades", "Hearts"
        };

        checkValue(testValues);
        // Ace: 1, Two: 1, Three: 1, Four: 1, Five: 1,
        // Six: 1, Seven: 1, Eight: 1, Nine: 1, Ten: 1

        checkHand(testSuits);
        // Spades: 3, Hearts: 3, Diamonds: 2, Clubs: 2
    }

    public static void checkHand(String[] originalDeck) {
        IO.print("\n== New Deck Suits ==\n");

        /*
         * 1. Count numbers
         * 2. Count Suits
         * 3. Check requirments for hands
         */
        String[] suits = { "Spades", "Hearts", "Diamonds", "Clubs" };
        int[] suitsCount = { 0, 0, 0, 0 };
        IO.print("Here");
        // Numbers/Value check
        if (originalDeck != null) {
            for (int j = 0; j < suits.length; j++) {
                for (int i = 0; i < originalDeck.length; i++) {
                    if (suits[j].equals(originalDeck[i])) {
                        suitsCount[j]++;
                    }
                }
            }
        }

        IO.print(suits[0] + " - " + suitsCount[0]);
        IO.print(suits[1] + " - " + suitsCount[1]);
        IO.print(suits[2] + " - " + suitsCount[2]);
        IO.print(suits[3] + " - " + suitsCount[3]);
    }

    public static void checkValue(String[] originalDeck) {
        IO.print("\n== New Deck Value ==\n");

        String[] values = { "Ace", "Two", "Three", "Four", "Five", "Six", "Seven", "Eight", "Nine", "Ten", "Jack",
                "Queen", "King" };
        int[] valueCount = { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
        // Numbers/Value check
        if (originalDeck != null) {
            for (int j = 0; j < values.length; j++) {
                for (int i = 0; i < originalDeck.length; i++) {
                    if (values[j].equals(originalDeck[i])) {
                        valueCount[j]++;
                    }
                }
            }

        }
        for (int i = 0; i < values.length; i++) {
            if (valueCount[i] > 0) {
                IO.print(values[i] + " - " + valueCount[i]);
            }
        }

    }

}