public class Game {
    private Player players;
    private Deck deck;

    public Game(Player players, Deck deck) {
        this.players = players;
        this.deck = deck;
    }

    public void printInstructions() {
        return;
    }
    public void playGame() {
        return;
    }

    public static void main(String[] args) {
        String[] ranks = {"A", "2", "3"};
        String[] suits = {"Hearts", "Clubs"};
        int[] values = {1,2,3};
        Deck newDeck = new Deck(ranks, suits, values);
        System.out.println(newDeck);
        newDeck.shuffle();
        System.out.println(newDeck);
        Player Chase = new Player("Chase");
        int size = newDeck.getCardsLeft();
        for (int i = 0; i < size; i++) {
            Chase.addCard(newDeck.deal());
        }
        Chase.addPoints(10);
        System.out.println(Chase);
    }
}