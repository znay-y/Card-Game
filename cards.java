import java.util.ArrayList;

class cards {
    
    int ID;
    int value;
    String suit;
    String name;
    boolean picked;

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
                cards toMake = new cards();
                toMake.setSuit(suits[i]);
                toMake.setName(names[j]);
                toMake.setPicked(false);
                toMake.setValue(j + 1);
                toMake.setID(idcount);
                idcount++;
                deck.add(toMake);
            }
        }
        return deck;
    }
}