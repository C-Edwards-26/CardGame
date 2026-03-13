import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/*
 * GameViewer
 * -----------
 * This class is responsible for all GUI rendering.
 * It displays:
 * - Welcome screen
 * - Game board
 * - Cards
 * - Scores
 * - Win/Lose screen
 *
 * It listens for the ENTER key to switch screens.
 */

public class GameViewer extends JFrame implements KeyListener {

    // Reference to the Game logic class
    private Game window;

    private JButton restartButton;

    // Arrays storing images for each suit
    private Image[] heartsCardImages;
    private Image[] diamondsCardImages;
    private Image[] clubsCardImages;
    private Image[] spadesCardImages;

    // Background and extra images
    private Image wonBackgroundImage;
    private Image backgroundImage;
    private Image mainBackground;
    private Image Gambling;
    private Image cardBack;

    // Window dimensions
    private static int WINDOW_HEIGHT = 1080;
    private static int WINDOW_WIDTH = 1920;

    // Instruction text lines
    String[] instructionLines;

    // Folder path for image resources
    private static final String RESOURCE_PATH = "Resources/";

    // Controls whether welcome screen or game screen is shown
    private boolean showSecondScreen = false;

    /*
     * Constructor
     * Loads all images and sets up the JFrame.
     */
    public GameViewer(Game window) {
        this.window = window;

        // Create arrays for 13 cards per suit
        spadesCardImages = new Image[13];
        heartsCardImages = new Image[13];
        diamondsCardImages = new Image[13];
        clubsCardImages = new Image[13];

        // Call our new method to load the images
        loadCardImages();

        // Load background images
        wonBackgroundImage = new ImageIcon(RESOURCE_PATH+ "Confeti.jpg").getImage();
        backgroundImage = new ImageIcon(RESOURCE_PATH+"PokerTable.png").getImage();
        Gambling = new ImageIcon(RESOURCE_PATH + "hangover.JPEG").getImage();
        cardBack = new ImageIcon(RESOURCE_PATH + "back.png").getImage();

        // JFrame setup
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setTitle("Card Game");
        this.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        this.setLayout(null); // Allows us to use absolute coordinates for the button

        // Initialize Restart Button
        // --- NEW RESTART BUTTON SETUP ---
        restartButton = new JButton("Restart Game");

        // You may want to move the button to the top-left corner so it doesn't
        // block your cards during gameplay! (x=20, y=20)
        restartButton.setBounds(20, 20, 200, 50);

        restartButton.setFont(new Font("Arial", Font.BOLD, 20));

        // 1. CHANGE THIS TO TRUE so it is always visible
        restartButton.setVisible(true);
        restartButton.setFocusable(false);

        restartButton.addActionListener(e -> {
            // 2. WE REMOVED the setVisible(false) line from here!
            window.reset();
            loadCardImages();
            repaint();
            new Thread(() -> window.gameOne()).start();
        });

        this.add(restartButton);
        // --------------------------------
        this.setVisible(true);
        this.addKeyListener(this);
    }
    public void paint(Graphics g) {
        // Clear the window.
        super.paint(g);

        if (showSecondScreen) {
            paintSecondScreen(g);
        } else {
            paintWelcomeScreen(g);
        }

        // CRITICAL FIX: Use paintComponents(g) to safely draw the button on top!
        // Do NOT use restartButton.repaint() here, or it will create an infinite loop.
        if (restartButton != null && restartButton.isVisible()) {
            super.paintComponents(g);
        }
    }

    // Draws the main game screen.
    public void paintSecondScreen(Graphics g) {
        g.drawImage(backgroundImage, 0, 0, WINDOW_WIDTH, WINDOW_HEIGHT, this);
        drawPlayersCards(g, 325, 600);
        drawBotsCards(g, 325, 100);
        drawDealingCards(g, 575, 350);
        drawScoring(g, 180, 420);
        checkWinLooseScreen(g, 400, 450);
    }

    // Draws the welcome/instructions screen.
    public void paintWelcomeScreen(Graphics g) {
        g.setColor(new Color(18, 181, 86));
        g.fillRect(0, 0, WINDOW_WIDTH, WINDOW_HEIGHT);

        // Instruction box
        g.setColor(Color.black);
        g.fillRect(100, 100, WINDOW_WIDTH - 600, WINDOW_HEIGHT - 400);
        g.setColor(new Color(18, 181, 86));
        g.fillRect(110, 110, WINDOW_WIDTH - 620, WINDOW_HEIGHT - 420);
        g.setColor(Color.black);
        g.setFont(new Font("Arial", Font.BOLD, 70));
        g.drawString("Welcome to the Game of Pure Skill!", 180, 200);

        // Split instruction text into lines and draw
        g.setFont(new Font("Arial", Font.BOLD, 20));
        instructionLines = window.INSTRUCTIONS.split("\\n");
        g.drawRect(465, 230, 585, 535);
        for (int i = 0; i < instructionLines.length; i++) {
            g.drawString(instructionLines[i], 470, 250 + (25 * i));
        }
        g.setColor(Color.black);

        // Decorative Images
        g.drawImage(Gambling, 125, 250, 325, 500, this);
        g.drawImage(Gambling, 1065, 250, 325, 500, this);
        g.setFont(new Font("Arial", Font.BOLD, 28));
        g.drawString("*HIT ENTER TO CONTINUE*", 575, 835);
    }

    // raws player's remaining cards.
    public void drawPlayersCards(Graphics g, int x, int y) {
        int currentPlayerBid = window.getCurrentPlayerBid();

        // Remove card image once played
        if (currentPlayerBid > 0) {
            heartsCardImages[currentPlayerBid - 1] = null;
        }
        for (int i = 0; i < heartsCardImages.length; i++) {
            g.drawImage(heartsCardImages[i], x + (60* i), y, 100, 200, this);
        }
    }
    // Draws Bots cards
    public void drawBotsCards(Graphics g, int x, int y) {
        int currentBotBid = window.getCurrentBotBid();
        if (currentBotBid > 0) {
            clubsCardImages[currentBotBid - 1] = null;
        }
        for (int i = 0; i < clubsCardImages.length; i++) {
            g.drawImage(clubsCardImages[i], x + (60* i), y, 100, 200, this);
        }
    }

    // Draws prize card and card back.
    public void drawDealingCards(Graphics g, int x, int y) {
        int currentPrizeCard = window.getCurrentPrizeCard();
        g.drawImage(cardBack, x, y, 100, 200, this);

        if (currentPrizeCard > 0) {
            g.drawImage(spadesCardImages[currentPrizeCard - 1], x + 200, y, 100, 200, this);
        }
        g.drawRect(x-5,y-5,110,210);
        g.drawRect(x+195,y-5,110,210);
    }

    // Draws current score.
    public void drawScoring(Graphics g, int x, int y) {
        g.setFont(new Font("Intel", Font.BOLD, 38));
        g.drawString("YOUR SCORE: " + window.getCurrentPlayersPoints(), x, y);
        g.drawString("BOT SCORE: " + window.getCurrentBotPoints(), x + 802, y);
    }

    // Checks wins or losses and prints said screen
    public void checkWinLooseScreen(Graphics g, int x, int y) {
        if (window.isGameOver()) {
            // ---> We REMOVED the setVisible line from here <---

            g.setColor(new Color(18, 181, 86));
            g.drawImage(wonBackgroundImage, 0, 0, WINDOW_WIDTH, WINDOW_HEIGHT, this);
            g.setColor(new Color(184, 164, 15));
            g.setFont(new Font("Intel", Font.BOLD, 120));
            if (window.isTie()) {
                g.drawString("AWWW..." , x, y - 125);
                g.drawString("IT'S A TIE!!! ", x, y);
                g.setColor(Color.black);
                g.setFont(new Font("Intel", Font.BOLD, 38));
                g.drawString("YOUR SCORE: " + window.getCurrentPlayersPoints(), x+ 100, y+ 100);
                g.drawString("BOT SCORE: " + window.getCurrentBotPoints(), x + 100, y+ 150);
            }
            else if (window.isWon()) {
                g.drawString("CONGRATS!!" , x, y - 125);
                g.drawString("YOU WON!!! ", x, y);
                g.setColor(Color.black);
                g.setFont(new Font("Intel", Font.BOLD, 38));
                g.drawString("YOUR SCORE: " + window.getCurrentPlayersPoints(), x+ 100, y+ 100);
                g.drawString("BOT SCORE: " + window.getCurrentBotPoints(), x + 100, y+ 150);
            }
            else {
                g.drawString("AWWW..." , x, y - 125);
                g.drawString("YOU LOST!!! " , x, y);
                g.setColor(Color.black);
                g.setFont(new Font("Intel", Font.BOLD, 38));
                g.drawString("YOUR SCORE: " + window.getCurrentPlayersPoints(), x+ 100, y+ 100);
                g.drawString("BOT SCORE: " + window.getCurrentBotPoints(), x + 100, y+ 150);
            }
        }
    }

    public void loadCardImages() {
        for (int i = 0; i < 13; i++) {
            spadesCardImages[i]   = new ImageIcon(RESOURCE_PATH + (i * 4 + 1) + ".png").getImage();
            heartsCardImages[i]   = new ImageIcon(RESOURCE_PATH + (i * 4 + 2) + ".png").getImage();
            diamondsCardImages[i] = new ImageIcon(RESOURCE_PATH + (i * 4 + 3) + ".png").getImage();
            clubsCardImages[i]    = new ImageIcon(RESOURCE_PATH + (i * 4 + 4) + ".png").getImage();
        }
    }

    public void showRestartButton() {
        restartButton.setVisible(true);
        repaint();
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    public void keyPressed(KeyEvent e){
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            showSecondScreen = !showSecondScreen;
            repaint();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
