package Core;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class Deck {

    private ArrayList<Card> playDeck;
    private int numDecks;

    public Deck(int numDecks) {
        this.numDecks = numDecks;
        this.playDeck = new ArrayList<>(numDecks * 52);
        generateDeck();
        shuffleDeck();
    }

    private void generateDeck() {
        for (int j = 0; j < numDecks; j++) {
            for (Card.Suit cardSuite : Card.Suit.values()) {
                if (cardSuite == Card.Suit.UNDEF) {
                    continue;
                }
                for (Card.Number cardNumber : Card.Number.values()) {
                    if (cardNumber == Card.Number.UNDEF) {
                        continue;
                    }

                    playDeck.add(new Card(cardNumber, cardSuite));
                }
            }
        }
    }

    private void shuffleDeck() {
        Collections.shuffle(playDeck);
    }

    //  ALTERNATIVE SHUFFLE METHOD -->
    private void shuffleDeckAlt(ArrayList<Card> playDeck) {
        Random randomIndex = ThreadLocalRandom.current();

        for (int i = 0; i < playDeck.size(); i++) {
            int index = randomIndex.nextInt(playDeck.size());
            Card randomCard = playDeck.get(index);
            playDeck.set(index, playDeck.get(i));
            playDeck.set(i, randomCard);
        }
    }

    public Card drawCard() {
        Card drawnCard = playDeck.get(playDeck.size() - 1);
        playDeck.remove(playDeck.size() - 1);
        return drawnCard;
    }
}
