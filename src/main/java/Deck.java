import java.util.ArrayList;

public class Deck {
    private ArrayList<Card> cards;
    private int cardsLeft;
    private int cardLastDealt;

    public Deck(String[] ranks, String[] suits, int[] values) {
        cards = new ArrayList<Card>();
        for (int i = 0; i < suits.length; i++) {
            for (int j = 0; j < ranks.length; j++) {
                Card newCard = new Card(ranks[j], suits[i], values[j]);
                cards.add(newCard);
                cardsLeft ++;
            }
        }
    }

    public boolean isEmpty() {
        if (this.cardsLeft == 0) {
            return true;
        }
        return false;
    }

    public int getCardsLeft() {
        return cardsLeft;
    }

    public Card deal() {
        if (this.cardsLeft == 0) {
            return null;
        }
        cardsLeft --;
        return(cards.get(cardsLeft));
    }

    public void shuffle() {
        for (int i = cards.size() - 1; i > 0; i--) {
            int random = (int)(Math.random() * i);
            Card temp = new Card(cards.get(i));
            cards.set(i, cards.get(random));
            cards.set(random, temp);
        }
    }
    @Override
    public String toString() {
        return "Deck{" +
                "cards=" + cards +
                '}';
    }
}
