import java.util.NoSuchElementException;
import java.util.Scanner;

public class Game {
    private Player players;
    private Deck deck;
    public final static String[] totalRanks = {"A", "2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K"};
    public final static String[] totalSuits = {"Hearts", "Clubs", "Spades", "Diamonds"};
    public final static int[] totalValues = {1,2,3,4,5,6,7,8,9,10,11,12,13};

    public Game() {}

    // Instructions to be printed before game starts
    public void printInstructions() {
        System.out.println("-----------------------------------------------------");
        System.out.println("              GAME OF PURE STRATEGY");
        System.out.println("-----------------------------------------------------");
        System.out.println("Overview:");
        System.out.println("This is a bidding game played with a standard 52-card deck.");
        System.out.println("Each suit is given to one player:");
        System.out.println("  • Hearts   → You");
        System.out.println("  • Clubs    → Bot 1");
        System.out.println("  • Diamonds → Bot 2 (only in 2-bot mode)");
        System.out.println("  • Spades   → Dealer (prize cards)");
        System.out.println();
        System.out.println("How the game works:");
        System.out.println("1. The dealer (Spades suit) reveals one prize card each round.");
        System.out.println("2. Every player bids a card from their hand.");
        System.out.println("3. The highest card wins the prize card's value in points.");
        System.out.println("4. All bid cards are discarded after the round.");
        System.out.println("5. The game lasts 13 rounds.");
        System.out.println();
        System.out.println("Card Strength:");
        System.out.println("A = 1, 2 = 2, ..., 10 = 10, J = 11, Q = 12, K = 13");
        System.out.println("Highest number wins the round.");
        System.out.println();
        System.out.println("Two-Bot Mode Details:");
        System.out.println("• You bid against Bot 1 and Bot 2.");
        System.out.println("• If two or more bids tie for highest, the prize is discarded.");
        System.out.println();
        System.out.println("At the end of 13 rounds:");
        System.out.println("• The player with the most points wins.");
        System.out.println("-----------------------------------------------------------------");
    }

    //Logic to intiialize the play game sq=equence
    public void playGame() {
        printInstructions();
        gameOne();
    }

    // Main logic for the game!
    public void gameOne() {
        // Initialize the deck for our game against one bot
        Deck newDeck = new Deck(totalRanks, totalSuits, totalValues);
        newDeck.shuffle();

        // Distribute the cards by suit
        Player p1 = new Player("Player 1");
        Player Bot1 = new Player("Bot 1");
        Player dealer = new Player("Dealer"); // holds the prize cards

        // Logic for sorting the cards from the Deck
        while (!newDeck.isEmpty()) {
            Card dealt = newDeck.deal();
            String suit = dealt.getSuit();
            switch (dealt.getSuit()) {
                case "Hearts": p1.addCard(dealt); break;
                case "Clubs": Bot1.addCard(dealt); break;
                case "Spades": dealer.addCard(dealt); break;
                default: break; // Diamonds discarded for single-bot version
            }
        }

        dealer.shuffleHand();

        System.out.println("Your hand: " + p1.getHand());
        System.out.println("The game begins!");

        for (int round = 1; round <= 13; round++) {
            System.out.println("\n ------ Round " + round + " -----");

            // Reveal prize card
            Card prize = dealer.playTopCard();
            System.out.println("Prize card: " + prize + " (worth " + prize.getValue() + " points)");

            // Player bid
            Card playerBid = askForBid(p1);

            // Bot chooses bid randomly
            Card botBid = botBid(Bot1);

            System.out.println("Bot plays: " + botBid);

            //Determine winner
            if (playerBid.getValue() > botBid.getValue()) {
                p1.addPoints(prize.getValue());
                System.out.println("You win the round and earn " + prize.getValue() + " points!");
            } else if (playerBid.getValue() < botBid.getValue()) {
                Bot1.addPoints(prize.getValue());
                System.out.println("Bot wins the round and earns " + prize.getValue() + " points!");
            } else {
                System.out.println("Tie! Prize discarded.");
            }
            System.out.println("Score → You: " + p1.getPoints() + " | Bot: " + Bot1.getPoints());
        }
        // Final result
        System.out.println(" -------- GAME OVER ------- ");
        if(p1.getPoints() > Bot1.getPoints()) {
            System.out.println("🎉 You win with " + p1.getPoints() + " points!");
        }
        else if (p1.getPoints() < Bot1.getPoints())
            System.out.println("🤖 Bot wins with " + Bot1.getPoints() + " points!");
        else
            System.out.println("It's a tie!");
    }

    public void playCard(Player shuffle) {
            int random = (int)(Math.random() * (shuffle.getHand().size()));
            shuffle.playCard(random);
    }

    // PLAYER INPUT BID
    public Card askForBid(Player p) {
        Scanner input = new Scanner(System.in);
        System.out.println("Your hand: " + p.getHand());
        System.out.println("Choose a card to bid (example: A of Hearts): ");

        String response = input.nextLine();
        Card chosen = trimCard(response);

        while (chosen == null || !p.getHand().contains(chosen)) {
            System.out.println("Invalid Card. Try again: ");
            response =input.nextLine();
            chosen = trimCard(response);
        }

        p.getHand().remove(chosen);
        return chosen;
    }

    // BOT LOGIC
    public Card botBid(Player bot) {
        int index = (int)(Math.random() * bot.getHand().size());
        return bot.getHand().remove(index);
    }
    // TRIM CARD LOGIC FOR CHECKING IF A CARD IS A CARD.
    public Card trimCard(String input) {
        String[] parts = input.split(" of ");
        if (parts.length != 2) return null;

        String r = parts[0].trim();
        String s = parts[1].trim();

        // Validate rank
        boolean rankValid = false;
        for (String T : totalRanks) {
            if (T.equals(r)) rankValid = true;
        }

        // Validate suit
        boolean suitValid = false;
        for (String U : totalSuits) {
            if (U.equals(s)) suitValid = true;
        }
        if (!rankValid || !suitValid) return null;

        int val = 0;
        for (int i = 0; i < totalRanks.length; i++) {
            if (totalRanks[i].equals(r)) val = totalValues[i];
        }
        return new Card(r, s, val);
    }

    // My ATTEMPT for two bot game (got too complciated)
//    public void gameTwo() {
//        Deck newDeck = new Deck(totalRanks, totalSuits, totalValues);
//        newDeck.shuffle();
//
//        Player p1 = new Player("Player 1");
//        Player bot1 = new Player("Bot 1");
//        Player bot2 = new Player("Bot 2");
//        Player dealer = new Player("Dealer");
//
//        while (!newDeck.isEmpty()) {
//            Card dealt = newDeck.deal();
//            switch (dealt.getSuit()) {
//                case "Hearts":   p1.addCard(dealt);  break;
//                case "Clubs":    bot1.addCard(dealt); break;
//                case "Diamonds": bot2.addCard(dealt); break;
//                case "Spades":   dealer.addCard(dealt); break;
//            }
//        }
//        dealer.shuffleHand();
//
//        // Display initial hands
//        System.out.println("Your hand: " + p1.getHand());
//        System.out.println("Bot 1 hand: " + bot1.getHand());
//        System.out.println("Bot 2 hand: " + bot2.getHand());
//        System.out.println("\nThe 3-player Game of Pure Strategy begins!");
//
//        for (int round = 1; round <= 13; round++) {
//            System.out.println("\n ------ Round " + round + " -----");
//
//            // Reveal prize card
//            Card prize = dealer.playTopCard();
//            System.out.println("Prize card: " + prize + " (worth " + prize.getValue() + " points)");
//
//            // Bids
//            Card p1Bid = askForBid(p1);
//            Card b1Bid = botBid(bot1);
//            Card b2Bid = botBid(bot2);
//
//            System.out.println("Bot 1 plays: " + b1Bid);
//            System.out.println("Bot 2 plays: " + b2Bid);
//
//            //Determine winner
//            Card highest = p1Bid;
//            Player winner = p1;
//
//            if (b1Bid.getValue() > highest.getValue()) {
//                highest = b1Bid;
//                winner = bot1;
//            }
//            if (b1Bid.getValue() > highest.getValue()) {
//                highest = b2Bid;
//                winner = bot2;
//            }
//
//            // Check for tie
//            boolean tie =
//                    (p1Bid.getValue() == b1Bid.getValue()) ||
//                            (p1Bid.getValue() == b2Bid.getValue()) ||
//                            (b1Bid.getValue() == b2Bid.getValue());
//
//            if (tie && highest.getValue() == p1Bid.getValue() &&
//                    highest.getValue() == b1Bid.getValue() &&
//                    highest.getValue() == b2Bid.getValue()) {
//                System.out.println("Triple tie! Prize discarded.");
//            }
//                System.out.println("Tie for highest bid — prize discarded.");
//            }
//        }
//    }

    // All the functions below were going to be for two bot game, but ended up being scrapped b/c I never made it.

//    public boolean askOpponents(){
//        Scanner input = new Scanner(System.in);
//        int opponents = -1;
//        while (opponents != 1 && opponents != 2) {
//            System.out.println("How many opponents would you like to face, one or two?");
//            try {
//                opponents = input.nextInt();
//            }
//            catch(NoSuchElementException e) {
//                input.nextLine();
//                System.out.println("Incorrect type of opponents!");
//                opponents = -1;
//            }
//        }
//        if (opponents == 1) {
//            return true;
//        }
//        return false;
//    }

//    public void askCard(Player p) {
//        Scanner input = new Scanner(System.in);
//        System.out.println("What card would you like to Bid?");
//        String response = input.nextLine();
//        while (!checkAnswer(p, response)) {
//            System.out.println("That isn't a correct Card! Try Again. (The correct syntax is (rank + of + suit) ex: " +
//                    "( A of Spades)");
//            response = input.nextLine();
//        }
//        System.out.print("Your Card: " + response);
//        p.getHand().remove((returnIndex(p, response)));
//    }

//public boolean checkAnswer(Player p, String response){
//    Card c = trimCard(response);
//    if (c == null) {
//        return false;
//    }
//    return p.getHand().contains(c);
//}

//    public int returnIndex(Player p, String response){
//        Card c = trimCard(response);
//        return p.getHand().indexOf(c);
//    }

    // Main method to start up everything and get the game going!
    public static void main(String[] args) {
        Game g1 = new Game();
        g1.playGame();
    }
}