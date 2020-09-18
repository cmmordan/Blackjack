import java.util.ArrayList;

public class Hand {

    private ArrayList<Card> hand;
    private double bet;

    public Hand() {
        this.hand =  new ArrayList<>();
        this.bet = 0;
    }

    public void addCard(Card card) {
        hand.add(card);
        if (getHandValue() > 21) {
            for (Card c : hand) {
                if (c.getBlackjackValue() == 11) {
                    c.toggleAceValue();
                    break;
                }
            }
        }
    }

    public Card getCard(int i) {
        return this.hand.get(i);
    }

    public void setHand(Card.Number card1, Card.Number card2) {
        hand.get(0).setNum(card1);
        hand.get(1).setNum(card2);
    }

    public void setBet(double bet) {
        this.bet = bet;
    }

    public double getBet() {
        return this.bet;
    }

    public void removeCard(int i) {
        this.hand.remove(i);
    }

    public int getHandValue() {
        int handValue = 0;
        for (Card card : hand) {
            handValue += card.getBlackjackValue();
        }
        return handValue;
    }

    public void setHandValue(int handValue) {
        if(getHandValue() > 21) {
            handValue = 0;
        }
    }

    public boolean isSplittable() {
        return (hand.size() == 2) && (hand.get(0).getNum() == hand.get(1).getNum());
    }

    public boolean isSoft() {
        for (Card card : hand) {
            if (card.getBlackjackValue() == 11) {
                return true;
            }
        }
        return false;
    }

    public void displayHand() {
        System.out.print("\n\t");
        for (Card card : hand) {
            System.out.print(card.getDisplayedCard());
        }
        System.out.println("\n");
    }

    public void displayDealerHand() {
        System.out.print("\n\t[??]");
        for (int i = 1; i < hand.size(); i++) {
            Card card = hand.get(i);
            System.out.print(card.getDisplayedCard());
        }
        System.out.println("\n\n----------------\n----------------\n");
    }

    public boolean isBusted() {
        return getHandValue() > 21;
    }

    public boolean isBlackjack() {
        return (getHandValue() == 21) && (hand.size() == 2);
    }

}
