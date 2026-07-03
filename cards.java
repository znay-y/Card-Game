import java.util.Random;
import java.util.Scanner;
import java.util.Arrays;

public class cards {
    public static void main(String[] args) {
        print("Cards Games\n");
        Scanner scanner = new Scanner(System.in);

        int [] hand = genCards(scanner);

        print("\nBefore going to method\n ");
        System.out.println(Arrays.toString(hand));

        showDeck(hand);
    }

    public static void showDeck(int[] hand) {
        print("\nYour hand is: ");
        for (int i = 0; i < hand.length; i++) {
            int cardID = hand[i];

            if (cardID <= 54 && cardID >= 41) {
                if(cardID>=41&&cardID<=51){
                    print((cardID - 41)+ " of Clubs");
                }
                else if(cardID==52){
                    print("Jack of Clubs");
                }
                else if(cardID==53){
                    print("Queen of Clubs");
                }
                else if(cardID==54){
                    print("King of Clubs");
                }
                else if(cardID==55){
                    print("Ace of Clubs");
                }
            } else if (cardID <= 40 && cardID >= 27) {
                if(cardID<=37&&cardID>=27){
                    print((cardID - 27)+ " of Spades");
                }
                else if(cardID==38){
                    print("Jack of Spades");
                }
                else if(cardID==39){
                    print("Queen of Spades");
                }
                else if(cardID==40){
                    print("King of Spades");
                }
                else if(cardID==41){
                    print("Ace of Spades");
                }
            } else if (cardID <= 26 && cardID >= 13) {
                if(cardID<=23&&cardID>=13){
                    print((cardID - 13)+ " of Diamonds");
                }
                else if(cardID==24){
                    print("Jack of Diamonds");
                }
                else if(cardID==25){
                    print("Queen of Diamonds");
                }
                else if(cardID==26){
                    print("King of Diamonds");
                }
                else if(cardID==27){
                    print("Ace of Diamonds");
                }
            } else if (cardID <= 12 && cardID >= 0) {
                if(cardID<=10&&cardID>=0){
                    print((cardID - 0)+ " of Hearts");
                }
                else if(cardID==11){
                    print("Jack of Hearts");
                }
                else if(cardID==12){
                    print("Queen of Hearts");
                }
                else if(cardID==13){
                    print("King of Hearts");
                }
                else if(cardID==14){
                    print("Ace of Hearts");
                }
            }
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
        for(int i = 0; i < arr.length; i++) {
            newArr[i] = arr[i];
        }
        newArr[arr.length] = value;
        return newArr;
    }
}
