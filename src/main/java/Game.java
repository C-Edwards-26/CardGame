import java.util.Scanner;

public class Game {
    private Player players;
    private Deck deck;
    public final static String[] totalRanks = {"A", "2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K"};
    public final static String[] totalSuits = {"Hearts", "Clubs", "Spades", "Diamonds"};
    public final static int[] totalValues = {1,2,3,4,5,6,7,8,9,10,10,10,10};

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
        Deck newDeck = new Deck(totalRanks, totalSuits, totalValues);
        askOpponents();
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

    public static void main(String[] args) {
        Game g1 = new Game();
        g1.playGame();
    }
}