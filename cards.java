import java.util.Random;
import java.util.Scanner;
import java.util.Arrays;

public class cards {
    public static void main(String[] args) {
        print("Cards Games\n");
        Scanner scanner = new Scanner(System.in);

        int[] hand = genCards(scanner);

        print("\nBefore going to method\n ");
        System.out.println(Arrays.toString(hand));

        for(int i =0;i<hand.length;i++){
            print("Card " + (i+1) + ": " + identifyCard(hand[i]));
        }
    }

    public static String identifyCard(int cardID){
        String suit="";

        if (cardID <= 54 && cardID >= 41) {
                suit = "Clubs";
            } else if (cardID <= 40 && cardID >= 27) {
                suit = "Spades";
            } else if (cardID <= 26 && cardID >= 13) {
                suit = "Diamonds";
            } else if (cardID <= 12 && cardID >= 0) {
                suit = "Hearts";
            }
        
        int newCardID = cardID % 13;
        if(newCardID == 0){
            return "Ace of " + suit;
        } else if(newCardID == 11){
            return "Jack of " + suit;
        } else if(newCardID == 12){
            return "Queen of " + suit;
        } else if(newCardID == 13){
            return "King of " + suit;
        } else {
            return newCardID + " of " + suit;
        }
    }

    public static int[] genCards(Scanner sc) {

        int cardsAmount = INTput(sc, "How many cards do you want?");

        print("\nYou want " + cardsAmount + " cards\n");

        int[] cards = new int[0];
        System.out.println(Arrays.toString(cards));

        for (int i = 0; i < cardsAmount; i++) {
            Random r = new Random();
            int cardID = r.nextInt(54);

            print("Current value: " + cardID);

            // 54-41 then 40-27 then 26-13 then 12-0
            if (cardID <= 54 && cardID >= 41) {
                cards = intArrayAppend(cards, cardID);
            } else if (cardID <= 40 && cardID >= 27) {
                cards = intArrayAppend(cards, cardID);
            } else if (cardID <= 26 && cardID >= 13) {
                cards = intArrayAppend(cards, cardID);
            } else if (cardID <= 12 && cardID >= 0) {
                cards = intArrayAppend(cards, cardID);
            }
        }

        return cards;
    }

    public static void print(String output) {
        System.out.println(output);
    }

    public static int INTput(Scanner sc, String message) {
        print(message);
        String input = sc.nextLine();
        int num = Integer.parseInt(input);
        return num;
    }

    public static int[] intArrayAppend(int[] arr, int value) {
        int[] newArr = new int[arr.length + 1];
        for (int i = 0; i < arr.length; i++) {
            newArr[i] = arr[i];
        }
        newArr[arr.length] = value;
        return newArr;
    }
}
