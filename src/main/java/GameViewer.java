import javax.swing.*;
        import java.awt.*;

public class GameViewer extends JFrame {
    private Game window;
    private Image[] cardImages;
    private Image backgroundImage;
    private Image mainBackground;
    private Image Gambling;
    private static int WINDOW_HEIGHT = 1080;
    private static int WINDOW_WIDTH = 1920;
    String[] instructionLines;
    private static final String RESOURCE_PATH = "Resources/";

    public GameViewer(Game window) {
        this.window = window;
        cardImages = new Image[51];
        for (int i = 0; i < 51; i ++) {
            cardImages[i] = new ImageIcon(RESOURCE_PATH + i + ".png").getImage();
        }
        backgroundImage = new ImageIcon(RESOURCE_PATH+"PokerTable.png").getImage();
        Gambling = new ImageIcon(RESOURCE_PATH + "hangover.JPEG").getImage();
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setTitle("Card Game");
        this.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        this.setVisible(true);

    }
    public void paint(Graphics g) {
        // Clear the window.
        super.paint(g);

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
    }

}
