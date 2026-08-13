import java.util.Scanner;

class User {
    String name;
    int chips;
    int currentBet;
    int saveID;

    public User(String name, String chips, String saveID) {
        this.name = name;
        this.chips = Integer.parseInt(chips);
        this.saveID = Integer.parseInt(saveID);
    }

     public User(String name, String chips) {
        this.name = name;
        this.chips = Integer.parseInt(chips);
    }

    public String getName() {
        return name;
    }

    public int getChips() {
        return chips;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setChips(int chips) {
        this.chips = chips;
    }

    public int getCurrentBet() {
        return currentBet;
    }

    public void setCurrentBet(int currentBet) {
        this.currentBet = currentBet;
        IO.print("\nThe current bet is " + currentBet+"\n");
    }

    public void profile(Scanner sc){
        IO.clear();
        IO.print("User Profile");
        IO.print("Name: " + this.getName());
        IO.print("Chips: " + this.getChips());
        IO.print("Press enter to continue");

        sc.nextLine();
        IO.clear();
    }
//Differs from setcurrentbet cause it is for initial bets so has validation
    public int setbet(Scanner sc) {
        int betAmount = IO.INTput(sc, "How many chips are you betting");

        while (betAmount <= 0 || betAmount > this.getChips()) {

            if (betAmount > this.getChips()) {
                IO.print("You cannot bet more than you have");
            } else if (betAmount <= 0) {
                IO.print("You cannot bet a non-positive amount");
            }
            betAmount = IO.INTput(sc, "How many chips are you betting");
        }
        setCurrentBet(betAmount);
        return betAmount;
    }

}