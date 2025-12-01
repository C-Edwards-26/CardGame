import java.util.Scanner;

public class Game {
    private Player players;
    private Deck deck;
    public final static String[] totalRanks = {"A", "2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K"};
    public final static String[] totalSuits = {"Hearts", "Clubs", "Spades", "Diamonds"};
    public final static int[] totalValues = {1,2,3,4,5,6,7,8,9,10,11,12,13};

    public Game(Player players, Deck deck) {
        this.players = players;
        this.deck = deck;
    }

    public Game() {
    }

    public boolean askOpponents(){
        Scanner input = new Scanner(System.in);
        System.out.println("How many opponents would you like to face, one or two?");
        int opponents = input.nextInt();
        while (opponents != 1 && opponents != 2) {
            System.out.println("Incorrect ammount of opponents!");
            System.out.println("How many opponents would you like to face, one or two?");
            opponents = input.nextInt();
        }
        if (opponents == 1) {
            return true;
        }
        return false;
    }

    public void printInstructions() {
        return;
    }
    public void playGame() {
        if (askOpponents()) {
            gameOne();
        }
        else {
            gameTwo();
        }
    }
    public void gameOne() {
        Deck newDeck = newDeck = new Deck(totalRanks, totalSuits, totalValues);
        System.out.println(newDeck);
        newDeck.shuffle();
        Player Chase = new Player("Chase");
        Player Bot1 = new Player("Bot1");
        Player dealing = new Player("Dealing");
        while (!newDeck.isEmpty()) {
            Card dealt = newDeck.deal();
            String suit = dealt.getSuit();

            if (suit.equals("Hearts")) {
                Chase.addCard(dealt);
            }
            if (suit.equals("Clubs")) {
                Bot1.addCard(dealt);
            }
            if (suit.equals("Diamonds")) {
                continue;
            }
            if (suit.equals("Spades")) {
                dealing.addCard(dealt);
            }
        }
        System.out.println(Chase);
        System.out.println(Bot1);
        System.out.println(dealing);
    }

    public void gameTwo() {
        Deck newDeck = new Deck(totalRanks, totalSuits, totalValues);
        newDeck.shuffle();

        Player p1 = new Player("Player 1");
        Player Bot1 = new Player("Bot 1");
        Player Bot2 = new Player("Bot 2");
        Player dealing = new Player("Dealing");

        while (!newDeck.isEmpty()) {
            Card dealt = newDeck.deal();
            String suit = dealt.getSuit();

            if (suit.equals("Hearts")) {
                p1.addCard(dealt);
            }
            if (suit.equals("Clubs")) {
                Bot1.addCard(dealt);
            }
            if (suit.equals("Spades")) {
                dealing.addCard(dealt);
            }
            if (suit.equals("Diamonds")) {
                Bot2.addCard(dealt);
            }
        }
        System.out.println(p1);
        System.out.println(Bot1);
        System.out.println(Bot2);
        System.out.println(dealing);
    }
    public static void main(String[] args) {
        Game g1 = new Game();
        g1.playGame();
    }
}