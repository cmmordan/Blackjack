package Core;

import java.util.ArrayList;

public class Player {

    String name;
    Wallet wallet;
    ArrayList<Hand> hands = new ArrayList<>(1);

    public Player (String name, double startingAmount) {
        this.name = name;
        this.wallet = new Wallet(startingAmount);
        this.hands.add(new Hand());
        if (!name.equalsIgnoreCase("dealer")) {
            System.out.println("Welcome " + name + "! Wallet balance: $" + startingAmount);
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void makeBet(double bet) {
        if (wallet.isSufficientFunds(bet)) {
            hands.get(0).setBet(bet);
        }

    }
}
