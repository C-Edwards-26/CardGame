import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class GameViewer extends JFrame implements KeyListener {
    private Game window;
    private Image[] heartsCardImages;
    private Image[] diamondsCardImages;
    private Image[] clubsCardImages;
    private Image[] spadesCardImages;
    private Image backgroundImage;
    private Image mainBackground;
    private Image Gambling;
    private Image cardBack;
    private static int WINDOW_HEIGHT = 1080;
    private static int WINDOW_WIDTH = 1920;
    String[] instructionLines;
    private static final String RESOURCE_PATH = "Resources/";
    private boolean showSecondScreen = false;

    public GameViewer(Game window) {
        this.window = window;
        spadesCardImages = new Image[14];
        clubsCardImages = new Image[14];
        heartsCardImages = new Image[14];
        diamondsCardImages = new Image[14];
        for (int i = 0; i < 56; i += 4) {
            spadesCardImages[(i/4)] = new ImageIcon(RESOURCE_PATH + i + ".png").getImage();
            heartsCardImages[(i/4)] = new ImageIcon(RESOURCE_PATH + (i + 1) + ".png").getImage();
            diamondsCardImages[(i/4)] = new ImageIcon(RESOURCE_PATH + (i + 2) + ".png").getImage();
            clubsCardImages[(i/4)] = new ImageIcon(RESOURCE_PATH + (i + 3) + ".png").getImage();
        }
        backgroundImage = new ImageIcon(RESOURCE_PATH+"PokerTable.png").getImage();
        Gambling = new ImageIcon(RESOURCE_PATH + "hangover.JPEG").getImage();
        cardBack = new ImageIcon(RESOURCE_PATH + "back.png").getImage();
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setTitle("Card Game");
        this.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
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
    }

    public void paintSecondScreen(Graphics g) {
        g.drawImage(backgroundImage, 0, 0, WINDOW_WIDTH, WINDOW_HEIGHT, this);
        drawPlayersCards(g, 300, 600);
        drawBotsCards(g, 300, 100);
        drawDealingCards(g, 300, 300);
    }

    public void paintWelcomeScreen(Graphics g) {
        g.setColor(new Color(18, 181, 86));
        g.fillRect(0, 0, WINDOW_WIDTH, WINDOW_HEIGHT);
        g.setColor(Color.black);
        g.fillRect(100, 100, WINDOW_WIDTH - 600, WINDOW_HEIGHT - 400);
        g.setColor(new Color(18, 181, 86));
        g.fillRect(110, 110, WINDOW_WIDTH - 620, WINDOW_HEIGHT - 420);
        g.setColor(Color.black);
        g.setFont(new Font("Arial", Font.BOLD, 70));
        g.drawString("Welcome to the Game of Pure Skill!", 180, 200);
        g.setFont(new Font("Arial", Font.BOLD, 20));
        instructionLines = window.INSTRUCTIONS.split("\\n");
        g.drawRect(465, 230, 585, 535);
        for (int i = 0; i < instructionLines.length; i++) {
            g.drawString(instructionLines[i], 470, 250 + (25 * i));
        }
        g.setColor(Color.black);
        g.drawImage(Gambling, 125, 250, 325, 500, this);
        g.drawImage(Gambling, 1065, 250, 325, 500, this);
        g.setFont(new Font("Arial", Font.BOLD, 28));
        g.drawString("*HIT ENTER TO CONTINUE*", 575, 835);
    }

    public void drawPlayersCards(Graphics g, int x, int y) {
        int currenPlayerBid = window.getCurrentPlayerBid();
        heartsCardImages[currenPlayerBid - 1] = null;
        for (int i = 0; i < heartsCardImages.length; i++) {
            g.drawImage(heartsCardImages[i], x + (60* i), y, 100, 200, this);
        }
    }
    public void drawBotsCards(Graphics g, int x, int y) {
        for (int i = 0; i < clubsCardImages.length -1; i++) {
            g.drawImage(cardBack, x + (60* i), y, 100, 200, this);
        }
    }
    public void drawDealingCards(Graphics g, int x, int y) {
        int currentPrizeCard = window.getCurrentPrizeCard();
        g.drawImage(cardBack, x, y, 100, 200, this);
        g.drawImage(spadesCardImages[currentPrizeCard], x + 200, y, 100, 200, this);

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
