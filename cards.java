import java.util.ArrayList;
import java.util.Random;

class cards {

    int ID;
    int value;
    String suit;
    String name;
    boolean picked;

    public cards(String newSuit, int id) {
        this.suit = newSuit;
        this.name = "Suit Changer";
        this.ID = id;
    }

    public cards(int id, int value, String suit, String name, boolean picked) {
        this.ID = id;
        this.value = value;
        this.suit = suit;
        this.name = name;
        this.picked = picked;
    }

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

    public static ArrayList<cards> loadDeckCards() {
        ArrayList<cards> deck = new ArrayList<cards>();
        int idcount = 1;

        String[] suits = { "Spades", "Hearts", "Diamonds", "Clubs" };
        String[] names = { "Ace", "Two", "Three", "Four", "Five", "Six", "Seven", "Eight", "Nine", "Ten", "Jack",
                "Queen", "King" };

        for (int i = 0; i <= 3; i++) {
            for (int j = 0; j < 13; j++) {
                cards toMake = new cards(idcount, j + 1, suits[i], names[j], false);
                idcount++;
                deck.add(toMake);
            }
        }
        return deck;
    }

    public static cards pickupCard(ArrayList<cards> deck) {
        Random cardGen = new Random();
        int val = cardGen.nextInt(deck.size());
        while (deck.get(val).getPicked() == true) {
            val = cardGen.nextInt(deck.size());
        }
        cards toReturn = deck.get(val);
        deck.get(val).setPicked(true);
        return toReturn;
    }
}