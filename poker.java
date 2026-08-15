import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class poker {
    public static void main(String[] args, User player) {
        ArrayList<cards> playerCards = new ArrayList<cards>();
        ArrayList<cards> dealer = new ArrayList<cards>();
        Scanner scanner = new Scanner(System.in);
        mainMenu(playerCards, dealer, scanner, player);
    }

    public static void mainMenu(ArrayList<cards> playerCards, ArrayList<cards> dealer, Scanner sc, User player) {

        int choice = 0;
        IO.clear();
        while (choice != 5) {
            printOptions();
            choice = IO.INTput(sc, "Choses one of the options");
            if (choice == 1) {
                pokerGame(playerCards, dealer, sc, player);
            } else if (choice == 2) {
                player.profile(sc);
            } else if (choice == 3) {
                printPokerRules(sc);
            } else if (choice == 4) {
                handsRanking(sc);
            } else if (choice == 5) {
                IO.print("Thanks for playing");
            } else {
                IO.print("Please pick a valid option");
            }
        }
    }

    public static void printOptions() {
        IO.print("==Poker==");
        IO.print("1. Poker");
        IO.print("2. User profile");
        IO.print("3. Rules");
        IO.print("4. Hands Ranking");
        IO.print("5. Exit\n");
    }

    public static void printPokerRules(Scanner sc) {
        IO.clear();
        IO.print("==Poker==");
        IO.print("Both the user and the house start with 2 cards");
        IO.print("One Card in the middle is shown");
        IO.print("Once a card is shown you can either fold or check or raise");
        IO.print("Who ever has the best hand wins");
        IO.print("Winning doubles the amount of chips you bet with bonuses for your hand");
        IO.print("\nPress Enter to contiune");
        sc.nextLine();
        IO.clear();
    }

    public static void handsRanking(Scanner sc) {
        IO.clear();
        IO.print("==Poker Hands Ranking==");
        IO.print("1. Royal Flush Ace, King, Queen, Jack, 10 of the same suit");
        IO.print("2. Straight Flush Five cards in a sequence of the same suit");
        IO.print("3. Four of a Kind Four cards of the same rank");
        IO.print("4. Full House Three of a kind and a pair");
        IO.print("5. Flush Five cards of the same suit");
        IO.print("6. Straight Five cards in a sequence");
        IO.print("7. Three of a Kind Three cards of the same rank");
        IO.print("8. Two Pair Two different pairs");
        IO.print("9. One Pair Two cards of the same rank");
        IO.print("10. High Card Highest value card");
        IO.print("\nPress Enter to contiune");
        sc.nextLine();
        IO.clear();
    }

    public static void pokerGame(ArrayList<cards> playerCards, ArrayList<cards> dealer, Scanner sc, User player) {
        IO.clear();
        int betAmount = player.setbet(sc);
        IO.clear();
        ArrayList<cards> deck = cards.loadDeckCards();
        playerCards.clear();
        dealer.clear();
        ArrayList<cards> middle = new ArrayList<cards>();
        setupPoker(playerCards, dealer, deck, middle);

        boolean end = false;
        boolean fold = false;

        while (!end) {
            refreshDisplay(playerCards, dealer, middle);
            IO.print("\n1. Fold\n2. Check\n3. Raise");
            int choice = IO.INTput(sc, "Choses one of the options");

            if (choice == 1) {
                IO.print("You folded");
                fold=true;
                end = true;
                continue;
            } else if (choice == 2) {
                IO.print("You checked");
                middle.add(pickupCard(deck));
            } else if (choice == 3) {
                int raiseAmount = IO.INTput(sc, "How much do you want to raise");
                while (raiseAmount <= 0 || raiseAmount > player.getChips()) {
                    if (raiseAmount > player.getChips()) {
                        IO.print("You cannot raise more than you have");
                    } else if (raiseAmount <= 0) {
                        IO.print("You cannot raise a non-positive amount");
                    }
                    raiseAmount = IO.INTput(sc, "How much do you want to raise");
                }
                player.setCurrentBet(player.getCurrentBet() + raiseAmount);
                IO.print("You raised by " + raiseAmount + " chips");
            } else {
                IO.print("Please pick a valid option");
            }
            if (middle.size() == 5) {
                end = true;
            }
        }
        endGame(playerCards, dealer, middle,player,fold);
    }

    public static void endGame(ArrayList<cards> playerCards, ArrayList<cards> dealer, ArrayList<cards> middle, User player,boolean fold) {
        /*
         * ==Things to add ==
         * 1. Changing chips amount
         * 2. Making it so higher hadns wins
         */
                    refreshDisplay(playerCards, dealer, middle);

        if(fold){
            IO.print("You folded and lost "+player.getCurrentBet()+" chips");
        }
        else{

            ArrayList<cards> houseToCheck = joinDeck(dealer, middle);
            ArrayList<cards> userToCheck = joinDeck(playerCards, middle);

            String houseHand = checkHand(houseToCheck);
            String userHand = checkHand(userToCheck);

            int houseRank = handRanking(houseHand);
            int userRank = handRanking(userHand);

            IO.print("\n==End of Game==");
            IO.print("Your hand: " + userHand);
            IO.print("House hand: " + houseHand);

            if (userRank > houseRank) {
                IO.print("You won " + player.getCurrentBet() * 2 + " chips");
                player.setChips(player.getChips() + player.getCurrentBet());
            } else if (userRank < houseRank) {
                IO.print("You lost " + player.getCurrentBet() + " chips");
                player.setChips(player.getChips() - player.getCurrentBet());
            } else {
                IO.print("It's a tie! You get your bet back");
            }
            
        }





    }

    public static void refreshDisplay(ArrayList<cards> playerCards, ArrayList<cards> dealer, ArrayList<cards> middle) {
        IO.clear();
        IO.print("==Poker==");
        IO.print("\nDealer's Cards:");
        for (int i = 0; i < dealer.size(); i++) {
            IO.print(dealer.get(i).getName() + " of " + dealer.get(i).getSuit());
        }
        ArrayList<cards> houseToCheck = joinDeck(dealer, middle);
        IO.print("Current best hand: " + checkHand(houseToCheck));
        IO.print("\nMiddle Cards:");
        for (int i = 0; i < middle.size(); i++) {
            IO.print(middle.get(i).getName() + " of " + middle.get(i).getSuit());

        }

        IO.print("\nYour Cards:");
        for (int i = 0; i < playerCards.size(); i++) {
            IO.print(playerCards.get(i).getName() + " of " + playerCards.get(i).getSuit());
        }
        ArrayList<cards> userToCheck = joinDeck(playerCards, middle);
        IO.print("Current best hand: " + checkHand(userToCheck));
    }

    public static ArrayList<cards> joinDeck(ArrayList<cards> playerCards, ArrayList<cards> middle) {
        ArrayList<cards> newlist = new ArrayList<cards>();

        for (int i = 0; i < playerCards.size(); i++) {
            newlist.add(playerCards.get(i));
        }

        for (int j = 0; j < middle.size(); j++) {
            newlist.add(middle.get(j));
        }

        return newlist;
    }

    public static void setupPoker(ArrayList<cards> playerCards, ArrayList<cards> dealer, ArrayList<cards> deck,
            ArrayList<cards> middle) {
        playerCards.add(pickupCard(deck));
        playerCards.add(pickupCard(deck));
        middle.add(pickupCard(deck));
        middle.add(pickupCard(deck));
        middle.add(pickupCard(deck));
        dealer.add(pickupCard(deck));
        dealer.add(pickupCard(deck));
    }

    public static cards pickupCard(ArrayList<cards> deck) {
        Random cardGen = new Random();

        int val = cardGen.nextInt(51);
        val += 1;
        while (deck.get(val).getPicked() == true) {
            val = cardGen.nextInt(51);
            val += 1;
        }

        // Changes for getting cards
        cards toReturn = deck.get(val);
        // String fullname = toReturn.name + " of " + toReturn.suit;

        // print("\nYou picked up a: " + fullname + "\n");

        deck.get(val).setPicked(true);

        return toReturn;

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

    public static int handRanking(String hand) {
        if (hand.equals("Royal Flush")) {
            return 10;
        } else if (hand.equals("Straight Flush")) {
            return 9;
        } else if (hand.equals("Four of a Kind")) {
            return 8;
        } else if (hand.equals("Full House")) {
            return 7;
        } else if (hand.equals("Flush")) {
            return 6;
        } else if (hand.equals("Straight")) {
            return 5;
        } else if (hand.equals("Three of a Kind")) {
            return 4;
        } else if (hand.equals("Two Pair")) {
            return 3;
        } else if (hand.equals("One Pair")) {
            return 2;
        } else if (hand.equals("High Card")) {
            return 1;
        } else {
            return 0;
        }
    }
}
