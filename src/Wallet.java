import java.util.Scanner;

public class Wallet {

    double initialAmount;
    double walletTotal;

    public Wallet(double initialAmount) {
        this.initialAmount = initialAmount;
        this.walletTotal = initialAmount;
    }

    public double getInitialAmount() {
        return initialAmount;
    }

    public void setInitialAmount(double amount) {
        this.initialAmount = amount;
    }

    public double getWalletTotal() {
        return walletTotal;
    }

    public double topUp(double topUpAmount) {
        walletTotal += topUpAmount;
        System.out.println("(Added $" + topUpAmount + ". New wallet balance: $" + BlackjackTable.getDollarString(walletTotal) + ")");
        return walletTotal;
    }

    public boolean isSufficientFunds(double bet) {
        return (bet <= walletTotal);
    }

    public boolean subtractBetAmount(double betAmount) {
        if (isSufficientFunds(betAmount)) {
            walletTotal -= betAmount;
            System.out.println("(Subtracted $" + betAmount + ". New wallet balance: $" + BlackjackTable.getDollarString(walletTotal) + ")");

            return true;
        }
        return false;
    }

    public void placePlayerBet(Player player) {
            double betInput = BlackjackTable.promptInputDouble(BlackjackTable.scanner,"\n" + player.getName() + " - Enter bet: $");
            player.makeBet(betInput);
            player.wallet.handleInsufficientFunds(betInput, player);
            player.wallet.subtractBetAmount(player.hands.get(0).getBet());
    }

    public void handleInsufficientFunds(double betInput, Player player) {
        if (!isSufficientFunds(betInput)) {
            while (!isSufficientFunds(betInput)) {
                String topUpChoiceInput = BlackjackTable.promptInputString(BlackjackTable.scanner, "Insufficient funds. Top up? Y/N");
                switch (topUpChoiceInput.toLowerCase()) {
                    case "y":
                        double topUpAmount = BlackjackTable.promptInputDouble(BlackjackTable.scanner, "Enter amount to top up: $");
                        topUp(topUpAmount);
                        player.makeBet(betInput);
                        if (!player.wallet.isSufficientFunds(betInput)) {
                            System.out.println("Amount still insufficient to cover bet of $" + betInput + ".");
                        }
                        player.makeBet(betInput);
                        break;

                    case "n":
                        betInput = BlackjackTable.promptInputDouble(BlackjackTable.scanner, "Enter new bet: $");
                        player.makeBet(betInput);
                        if (!player.wallet.isSufficientFunds(betInput)) {
                            System.out.println("Insufficient funds. Enter bet smaller than $" + BlackjackTable.getDollarString(player.wallet.getWalletTotal()) + ".");
                        }
                        break;

                    default:
                        System.out.println("Invalid choice. Try again");
                        break;
                }
            }
        }
    }

    public boolean promptTopUpOnly(double betInput, Player player) {
        if (!isSufficientFunds(betInput)) {
            while (!isSufficientFunds(betInput)) {
                String topUpChoiceInput = BlackjackTable.promptInputString(BlackjackTable.scanner, "Funds are insufficient to double bet. Do you wish to top up? Y/N");
                switch (topUpChoiceInput.toLowerCase()) {
                    case "y":
                        double topUpAmount = BlackjackTable.promptInputDouble(BlackjackTable.scanner, "Enter amount to top up: $");
                        topUp(topUpAmount);
                        player.makeBet(betInput);
                        return true;
                    case "n":
                        return false;
                    default:
                        System.out.println("Invalid choice. Try again");
                        break;
                }

            }
        }
        return false;
    }

    public double addWinningsToWallet(double betAmount) {
        double newTotal = walletTotal += (betAmount * 2);
        return walletTotal;
    }

    public void addBlackjackWinnings(double betAmount) {
        double newTotal = walletTotal += (betAmount * 2.5);
    }

    public double returnBetToWallet(double betAmount) {
        return walletTotal += betAmount;
    }

    public void printWalletTotal() {
        System.out.println("Wallet total: $" + BlackjackTable.getDollarString(walletTotal));
    }
}
