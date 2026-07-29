import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

/*

Fixes or later: 
- Make it so aces can be 1 or 11

*/
public class Blackjack {

    public static void main(String[] args, User player) {
        ArrayList<cards> playerCards = new ArrayList<cards>();
        ArrayList<cards> dealer = new ArrayList<cards>();
        Scanner scanner = new Scanner(System.in);

        mainMenu(playerCards, dealer, scanner, player);
    }

    public static void mainMenu(ArrayList<cards> playerCards, ArrayList<cards> dealer, Scanner sc, User player) {

        int choice = 0;
        IO.clear();
        while (choice != 2) {
            printOptions();
            choice = IO.INTput(sc, "Choses one of the options");
            if (choice == 1) {
                blackjackGame(playerCards, dealer, sc, player);
            } else if (choice == 2) {
                IO.print("Thanks for playing");
            } else {
                IO.print("Please pick a valid option");
            }
        }
    }

    public static void blackjackGame(ArrayList<cards> playerCards, ArrayList<cards> dealer, Scanner sc, User player) {
        int betAmount = setbet(sc, player);
        IO.clear();
        printBlackjackRules(sc);
        ArrayList<cards> deck = cards.loadDeckCards();
        playerCards.clear();
        dealer.clear();
        setupBlackjack(playerCards, dealer, deck);

        boolean end = false;

        while (!end) {
            showYourDeck(playerCards);
            showHouseDeck(dealer);
            int choice = 0;
            IO.print("\n1.Hit\n2.Double\n3.Stand");
            choice = IO.INTput(sc, "\nChoose one of the options\n");
            if (choice == 1) {
                playerCards.add(pickupCard(deck));
                IO.print("You picked up a card");
                IO.clear();

            } else if (choice == 2) {
                playerCards.add(pickupCard(deck));
                end = true;
                IO.print("The CPU will now take their turn");
                sc.nextLine();
                IO.clear();
                cpuTurn(dealer, playerCards, deck, sc);
            } else if (choice == 3) {
                end = true;
                IO.print("The CPU will now take their turn");
                sc.nextLine();
                IO.clear();
                cpuTurn(dealer, playerCards, deck, sc);
            } else {
                IO.print("Please pick a valid option");
            }
            // Go bust
            if (numbersTotal(playerCards) > 21) {
                end = true;
                IO.print("You went bust and automatically lose");
                showYourDeck(playerCards);
                showHouseDeck(dealer);
                IO.print("Press enter to continue");
                sc.nextLine();
                IO.clear();
            }
        }

        whoWon(playerCards, dealer, player, betAmount, sc);

    }

    public static int setbet(Scanner sc, User player) {
        int betAmount = IO.INTput(sc, "How many chips are you betting");

        while (betAmount <= 0 || betAmount > player.getChips()) {

            if (betAmount > player.getChips()) {
                IO.print("You cannot bet more than you have");
            } else if (betAmount <= 0) {
                IO.print("You cannot bet a non-positive amount");
            }
            betAmount = IO.INTput(sc, "How many chips are you betting");
        }
        return betAmount;
    }

    public static void printBlackjackRules(Scanner sc) {
        IO.print("==Blackjack==");
        IO.print("Keep picking up cards until you hit 21 or less");
        IO.print("IF you end turn when under 21 cpu picks up ");
        IO.print("Who ever is the closet wins");
        IO.print("\nPress Enter to contiune");
        sc.nextLine();
        IO.clear();
    }

    public static void setupBlackjack(ArrayList<cards> card, ArrayList<cards> dealer, ArrayList<cards> deck) {
        card.add(pickupCard(deck));
        card.add(pickupCard(deck));
        dealer.add(pickupCard(deck));
        dealer.add(pickupCard(deck));
    }

    public static void showYourDeck(ArrayList<cards> deck) {
        IO.print("\nYour Cards:");
        for (int i = 0; i < deck.size(); i++) {
            cards c = deck.get(i);
            String fullname = c.name + " of " + c.suit;
            IO.print(fullname);
        }
        int total = numbersTotal(deck);
        IO.print("User total: " + total);

    }

    public static void showHouseDeck(ArrayList<cards> deck) {
        IO.print("\nHouse Cards:");
        for (int i = 0; i < deck.size(); i++) {
            cards c = deck.get(i);
            String fullname = c.name + " of " + c.suit;
            IO.print(fullname);
        }
        int total = numbersTotal(deck);
        IO.print("House total: " + total);

    }

    public static void printblackjackOptions() {
        IO.print("\n1.Hit\n2.Double\n3.Stand");
    }

    public static void printOptions() {
        IO.print("==Main Menu==\n");
        IO.print("1. Blackjack");
        IO.print("2. Quit\n");
    }

    public static void whoWon(ArrayList<cards> playerCards, ArrayList<cards> dealer, User player, int betAmount,
            Scanner sc) {
        IO.clear();
        IO.print("==So Who Won==\n");
        if (numbersTotal(playerCards) > 21) {
            IO.print("You bust they win\n");
            player.setChips(player.getChips() - betAmount);
        } else if (numbersTotal(dealer) > 21) {
            IO.print("Dealer bust you win\n");
            player.setChips(player.getChips() + betAmount);
        } else {
            if (numbersTotal(dealer) > numbersTotal(playerCards)) {
                IO.print("They win\n");
                player.setChips(player.getChips() - betAmount);
            } else if (numbersTotal(dealer) == numbersTotal(playerCards)) {
                IO.print("Tie\n");
            } else {
                IO.print("You win\n");
                player.setChips(player.getChips() + betAmount);
            }
        }

        IO.print("Press enter to move to return to the main menu");
        sc.nextLine();
        IO.clear();
    }

    public static void cpuTurn(ArrayList<cards> dealer, ArrayList<cards> playerCards, ArrayList<cards> deck,
            Scanner sc) {
        // IO.print("\n==CPU's Turn==\n");
        boolean cont = true;
        while (numbersTotal(dealer) < 21 && cont) {
            IO.print("Press enter to move to the next turn");
            sc.nextLine();
            IO.clear();
            dealer.add(pickupCard(deck));
            showYourDeck(playerCards);
            showHouseDeck(dealer);

            if (numbersTotal(dealer) == 21) {
                // IO.print("CPU hit 21 so you lose");
            } else if (numbersTotal(dealer) > 21) {
                // IO.print("CPU bust");
            } else if (numbersTotal(dealer) < 21 && numbersTotal(playerCards) <= numbersTotal(dealer)) {
                // IO.print("CPU wins");
                cont = false;
            }

        }

        IO.print("Press enter to see the results");

        sc.nextLine();
        IO.clear();
    }

    public static int numbersTotal(ArrayList<cards> nums) {

        int total = 0;
        for (int i = 0; i < nums.size(); i++) {
            String current = nums.get(i).getName();
            if (current.equals("Jack") || current.equals("Queen") || current.equals("King")) {
                total += 10;
            } else if (current.equals("Ace")) {
                total += 11;
            } else {
                total = total + nums.get(i).getValue();
            }
        }

        if (total > 21) {
            for (int i = 0; i < nums.size(); i++) {
                String current = nums.get(i).getName();
                if (current.equals("Ace")) {
                    total -= 10;
                }

            }
        }
        return total;
    }

    public static void printGameRules() {
        IO.print("\n==Game==\n");
        IO.print("Keep picking up cards until you hit 21 or less");
        IO.print("IF you end turn when under 21 cpu picks up ");
        IO.print("Who ever is the closet wins");
        IO.print("Yes this is just blackjack but with differnet numbers\n");
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
