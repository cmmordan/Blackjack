package Core;

public class Dealer extends Player {

    public Dealer() {
        super("Dealer", 0.0);
    }

    public Hand getHand() {
        return hands.get(0);
    }

    public void playHand(Deck deck) {
        while (shouldDrawCard()) {
            getHand().addCard(deck.drawCard());
        }

        /*if (!shouldDrawCard()) {
        }*/
    }

    private boolean shouldDrawCard() {
        return (getHand().getHandValue() < 17) // less than 17
                || ((getHand().getHandValue() == 17) && (getHand().isSoft())); // soft 17
    }
}
