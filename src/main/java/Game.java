public class Game {
    public static void main(String[] args) {
        String[] ranks = {"A", "2", "3"};
        String[] suits = {"Hearts", "Clubs"};
        int[] values = {1,2,3};
        Deck newDeck = new Deck(ranks, suits, values);
        System.out.println(newDeck);
        System.out.println(newDeck.isEmpty());
    }
}