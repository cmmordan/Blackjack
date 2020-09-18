import java.util.ArrayList;
import java.util.Scanner;

public class BlackjackTable {

    ArrayList<Player> playerList;
    private final Dealer dealer;
    Deck deck;
    static Scanner scanner = new Scanner(System.in);

    enum WinCondition {
        WIN, LOSE, TIE
    }

    public BlackjackTable(int numDecks, int numPlayers) {
        this.dealer = new Dealer();
        this.playerList = new ArrayList<>(numPlayers);
        for (int i = 0; i < numPlayers; i++) {
            addPlayer("Player " + (i + 1), 50.0);
        }
        deck = new Deck(numDecks);

    }

    public static void main(String[] args) {

        BlackjackTable table = new BlackjackTable(5, 2);

        table.takePlayerBet();
        table.displayPlayerBets();
        table.dealHands();

        table.playerList.get(0).hands.get(0).setHand(Card.Number.N6, Card.Number.N6);
        //table.dealer.getHand().setHand(Card.Number.N10, Card.Number.N6); //Check for soft 17
        //table.dealer.getHand().setHand(Card.Number.A, Card.Number.N10); //Check for 21

        table.displayDealerHand();
        table.playRound();
    }

    public void addPlayer(String name, double walletAmount) {
        playerList.add(new Player(name, walletAmount));
    }

    public Player findPlayer(String playerName) {
        for(int i = 0; i < playerList.size(); i++) {
            Player player = this.playerList.get(i);
            if(player.getName().equalsIgnoreCase(playerName)) {
                return player;
            }
        }
        return null;
    }

    public void takePlayerBet() {
        Scanner scanner = new Scanner(System.in);
        for (Player player : playerList) {
            player.wallet.placePlayerBet(player);
        }
    }

    private String askForUserInput(Scanner scanner, Player player) {
        System.out.println(player.getName() + " choose action: ");
        String playerChoice = scanner.next();
        scanner.nextLine();
        return playerChoice;
    }

    public static double promptInputDouble(Scanner scanner, String promptUser) {
        double userInput;
        System.out.println(promptUser);
        userInput = scanner.nextDouble();
        scanner.nextLine();
        return userInput;
    }

    public static String promptInputString(Scanner scanner, String promptUser) {
        String userInput;
        System.out.println(promptUser);
        userInput = scanner.nextLine();
        return userInput;
    }

    public char promptInputChar(Scanner scanner, String promptUser) {
        char userInput;
        System.out.println(promptUser);
        userInput = (char)scanner.nextByte();
        scanner.nextLine();
        return userInput;
    }

    private void displayPlayerBets() {
        System.out.println("\nBets for round: ");
        for (Player player : playerList) {
            System.out.println(player.getName() + ": $" + player.hands.get(0).getBet());
        }
    }

    public void dealHands() {
        for (int i = 0; i < 2; i++) {
            for (Player player : playerList) {
                for (Hand hand : player.hands) {
                    hand.addCard(deck.drawCard());
                }
            }
            dealer.getHand().addCard(deck.drawCard());
        }
    }

    public void displayPlayerHandValues() {
        for (Player player : playerList) {
            for (Hand hand : player.hands) {
                System.out.println("\n" + player.getName() + ": ");
                hand.getHandValue();
            }
        }
    }

    public void displayPlayerHands() {
        for(Player player : playerList) {
            for (Hand hand : player.hands) {
                System.out.println(player.getName() + " (Hand " + (player.hands.indexOf(hand) + 1) + "):");
                hand.displayHand();
                System.out.println();
            }
        }
    }

    public void displayDealerHand() {
        System.out.println("\nDealer:");
        dealer.getHand().displayDealerHand();
    }

    private void playRound() {
        Scanner scanner = new Scanner(System.in);

        for (Player player : playerList) {
            for (int i = 0; i < player.hands.size(); i++) {
                Hand hand = player.hands.get(i);

                boolean nextHand = false;

                while (!nextHand) {
                    System.out.println();
                    displayPlayerHands();

                    String userInput = askForUserInput(scanner, player);

                    nextHand = handleUserInput(hand, player, nextHand, userInput); //handleUserInput toggles 'nextHand'
                    //hand.displayHand();
                }
            }
        }

        dealerRound();
        checkWinCondition();
    }

    private boolean handleUserInput(Hand hand, Player player, boolean nextHand, String playerChoice) {
        switch (playerChoice.toLowerCase()) {
            case "double down":
            case "dd":
                if (player.wallet.isSufficientFunds(hand.getBet())) {
                    player.wallet.subtractBetAmount(hand.getBet());
                    hand.setBet(hand.getBet() * 2);
                    nextHand = true;
                }
                else {
                    while (!player.wallet.isSufficientFunds(hand.getBet())) {
                        player.wallet.promptTopUpOnly(player.hands.get(0).getBet(), player);
                    }
                    player.wallet.subtractBetAmount(hand.getBet());
                    hand.setBet(hand.getBet() * 2);
                    nextHand = true;
                }
                //fallthrough
            case "hit":
            case "h":
                hand.addCard(deck.drawCard());
                if (hand.isBusted()) {
                    System.out.println("\nBust...");
                    nextHand = true;
                }
                break;

            case "hold":
            case "-":
                nextHand = true;
                break;

            case "split":
            case "s":
                if (hand.isSplittable()) {
                    splitHand(player, hand);
                } else {
                    System.out.println("Hand can't be split.");
                }
                break;
            default:
                System.out.println("Unrecognized command - Please try again.");
                break;
        }
        return nextHand;
    }

    public void splitHand(Player player, Hand hand) {
        if (player.wallet.isSufficientFunds(player.hands.get(0).getBet())) {
            player.hands.add(new Hand());
            Hand newHand = player.hands.get(player.hands.size()-1);
            player.hands.get(1).setBet(player.hands.get(0).getBet());
            player.wallet.subtractBetAmount(player.hands.get(1).getBet());
            newHand.addCard(hand.getCard(1));
            hand.removeCard(1);
            hand.addCard(deck.drawCard());
            newHand.addCard(deck.drawCard());
        }
        else {
            while (!player.wallet.isSufficientFunds(player.hands.get(0).getBet())) {
                player.wallet.promptTopUpOnly(player.hands.get(0).getBet(), player);
            }
            player.hands.add(new Hand());
            Hand newHand = player.hands.get(player.hands.size()-1);
            player.hands.get(1).setBet(player.hands.get(0).getBet());
            player.wallet.subtractBetAmount(player.hands.get(1).getBet());
            newHand.addCard(hand.getCard(1));
            hand.removeCard(1);
            hand.addCard(deck.drawCard());
            newHand.addCard(deck.drawCard());
        }

    }

    private void dealerRound() {
        System.out.println("\nDealer hand:");
        dealer.getHand().displayHand();

        dealer.playHand(deck);

        System.out.println("\nDealer hand final:");
        dealer.getHand().displayHand();

        if (dealer.getHand().getHandValue() > 21) {
            System.out.println("\nDealer busts.");
        }
    }

    public WinCondition checkAgainstDealerHand(Hand hand) {
        int dealerHandValue = dealer.getHand().getHandValue();
        int handValue = hand.getHandValue();

        if (((handValue <= 21) && (handValue > dealerHandValue)) || ((handValue <= 21) && (dealerHandValue > 21))) {
            return WinCondition.WIN;
        }
        else if ((handValue > 21) || ((handValue < dealerHandValue) && dealerHandValue <= 21)) { //ignore this warning (dealerHandValue < 21 condition was wrong - players were drawing ties when less than 21, when dealer was at 21)
            return WinCondition.LOSE;
        }
        else {
            return WinCondition.TIE;
        }
    }

    private void checkWinCondition() {
        for (Player player : playerList) {
            System.out.println("\n" + player.getName() + ":");
            for (Hand hand : player.hands) {
                if (checkAgainstDealerHand(hand) == WinCondition.WIN) {
                    System.out.println("\tHand " + (player.hands.indexOf(hand) + 1) + ": Winning hand!");
                        if (hand.isBlackjack()) {
                            player.wallet.addBlackjackWinnings((hand.getBet()));
                            System.out.println("\n\t\t--> BlackJack!\n\n\t\t--> Won $" + (player.hands.get(0).getBet() + (player.hands.get(0).getBet() * 1.5)));
                        }
                        else {
                            player.wallet.addWinningsToWallet(hand.getBet());
                            System.out.println("\n\t\t--> Won $" + (player.hands.get(0).getBet() * 2) + "!\n\n\t\t");
                        }
                }
                else if (checkAgainstDealerHand(hand) == WinCondition.LOSE) {
                    System.out.println("\t" + "Hand " + (player.hands.indexOf(hand) + 1) + ": Losing hand...");
                    System.out.println("\n\t\t--> $" + (player.hands.get(0).getBet()) + " subtracted from wallet\n\n\t\t");
                }
                else {
                    System.out.println("\t" + "Hand " + (player.hands.indexOf(hand) + 1) + ": Hand is a draw.");
                    player.wallet.returnBetToWallet(player.hands.get(0).getBet());
                }
            }
            System.out.println("\tWallet Total: $" + player.wallet.getWalletTotal() + "/n");
        }
    }

}
