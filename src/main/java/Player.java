import java.util.ArrayList;

public class Player {
    private ArrayList<Card> hand;
    private int points;
    private String name;

    public Player(String name, ArrayList<Card> newHand) {
        this.points = 0;
        this.name = name;
        hand = newHand;
    }

    public Player(String name) {
        this.name = name;
        this.points = 0;
        hand = new ArrayList<>();
    }

    public ArrayList<Card> getHand() {
        return hand;
    }

    public int getPoints() {
        return points;
    }

    public String getName() {
        return name;
    }

    public void addPoints(int addPoints) {
        points += addPoints;
    }

    public void addCard(Card newCard) {
        hand.add(newCard);
    }

    public void playCard(int i) {
        System.out.println("The card dealt is the " + hand.get(i) + "!");
        hand.remove(i);
    }

    public void shuffleHand() {
        for (int i = hand.size() - 1; i > 0; i--) {
            int r = (int)(Math.random() * (i+1));
            Card temp = hand.get(i);
            hand.set(i, hand.get(r));
            hand.set(r, temp);
        }
    }

    public Card playTopCard(){
        return hand.remove(0);
    }

    @Override
    public String toString() {
        return name + " has " + points + " points. " +
                name + "s cards: " + hand;
    }
}
