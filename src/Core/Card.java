package Core;

public class Card {

    public Card(Number num, Suit suit) {
        setNum(num);
        setSuit(suit);
    }

    enum Number {
        UNDEF, N2, N3, N4, N5, N6, N7, N8, N9, N10, J, Q, K, A;
    }
    enum Suit {
        UNDEF, DIAMOND, HEART, CLUB, SPADE;
    }

    private Number num = Number.UNDEF;
    private Suit suit = Suit.UNDEF;
    private int aceValue = 11;

    public void toggleAceValue() {
        if (aceValue == 11) {
            aceValue = 1;
        }
        else {
            aceValue = 11;
        }
    }

    public Number getNum() {
        return num;
    }

    public void setNum(Number num) {
        this.num = num;
    }

    public Suit getSuit() {
        return suit;
    }

    public void setSuit(Suit suit) {
        this.suit = suit;
    }

    public int getBlackjackValue() {
        switch (num) {
            case N2:
                return 2;
            case N3:
                return 3;
            case N4:
                return 4;
            case N5:
                return 5;
            case N6:
                return 6;
            case N7:
                return 7;
            case N8:
                return 8;
            case N9:
                return 9;
            case N10:
            case J:
            case Q:
            case K:
                return 10;
            case A:
                return aceValue;
            default:
                return 0;
        }
    }

    public char getCardNumber() {
        switch (num) {
            case N2:
                return '2';
            case N3:
                return '3';
            case N4:
                return '4';
            case N5:
                return '5';
            case N6:
                return '6';
            case N7:
                return '7';
            case N8:
                return '8';
            case N9:
                return '9';
            case N10:
                return '⑩';
            case J:
                return 'J';
            case Q:
                return 'Q';
            case K:
                return 'K';
            case A:
                return 'A';
            default:
                return ' ';
        }
    }

    public char getCardSuit() {
        switch (suit) {
            case DIAMOND:
                return '♦';
            case HEART:
                return '♥';
            case CLUB:
                return '♣';
            case SPADE:
                return '♠';
            default:
                return ' ';
        }
    }

    public String getDisplayedCard() {
        return "[" + getCardNumber() + getCardSuit() + "]";
    }

}
