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
        IO.print("==Main Menu==\n");
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

        while (!end) {
            refreshDisplay(playerCards, dealer,middle);
            IO.print("\n1. Fold\n2. Check\n3. Raise");
            int choice = IO.INTput(sc, "Choses one of the options");

            if (choice == 1) {
                IO.print("You folded");
                end = true;
                continue;
            } else if (choice == 2) {
                IO.print("You checked");

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
        }
        //endGame();
    }

    public static void refreshDisplay(ArrayList<cards> playerCards, ArrayList<cards> dealer,ArrayList<cards> middle) {
        IO.clear();
        IO.print("==Poker==");
        IO.print("\nDealer's Cards:");
        for (int i = 0; i < dealer.size(); i++) {
            IO.print(dealer.get(i).getName() + " of " + dealer.get(i).getSuit());
        }
        IO.print("\nMiddle Cards:");
        for (int i = 0; i < middle.size(); i++) {
            IO.print(middle.get(i).getName() + " of " + middle.get(i).getSuit());
        }

        IO.print("Your Cards:");
        for (int i = 0; i < playerCards.size(); i++) {
            IO.print(playerCards.get(i).getName() + " of " + playerCards.get(i).getSuit());
        }
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

}
