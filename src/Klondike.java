import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.io.*;
import javax.imageio.*;
import javax.swing.*;

/** Graphic user interface for the Klondike solitaire game. */
public class Klondike {

    /** java.awt.Color for the background. */
    public static final java.awt.Color DARK_GREEN = new java.awt.Color(2, 58, 230);

    public static final int GAME_WIDTH = 700;
    public static final int GAME_HEIGHT = 600;
    public static final int CARD_WIDTH = 72;
    public static final int CARD_HEIGHT = 96;
    public static final int SPLAY_OFFSET = 25;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    Klondike game = new Klondike();
                }
            });
    }

    private class GamePanel extends javax.swing.JPanel {

        /** Game model associated with this GUI. */
        private KlondikeModel model;    
        
        /** null when waiting for a first mouse click. */
        private boolean waitingForSource;
        private Deck source = null;

        public GamePanel() {
            setBackground(DARK_GREEN);
            setPreferredSize( new Dimension(GAME_WIDTH, GAME_HEIGHT) );
            
            model = new KlondikeModel();
            waitingForSource = true;

            addMouseListener( new MouseAdapter() {
                    @Override
                    public void mouseClicked( MouseEvent e ) {
                        doMouseClick(e.getX(), e.getY());
                    }
                });
        }

        private void doMouseClick(int x, int y) {
            int column = x*7/getWidth() + 1;
            if(column < 1) column = 1;
            else if (column > 7) column = 7;

            //System.out.println("column = " + column);
            if(waitingForSource) {
                if(y < 0.3*getHeight()) {
                    if(column == 1) {
                        model.drawNextCard();
                    } else if(column == 2 && model.getDrawPile().size() > 0) {
                        source = model.getDrawPile();
                        waitingForSource = false;
                    }
                } else {
                    source = model.getTableau(column - 1);
                    if(source.size() > 0) {
                        waitingForSource = false;
                    }
                }
            } else {
                waitingForSource = true;
                if(y < 0.3*getHeight()) {
                    model.moveToFoundation(source, column - 4);
                } else {
                    model.moveToTableau(source, column - 1);
                }
            }
            repaint();
        }

        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            
            int xUnit = getWidth() / 7 ;
            int xOffset = (xUnit - CARD_WIDTH) / 2;
            int yPos = 20;
            draw(g, model.getDeck(), xOffset, yPos, false);
            draw(g, model.getDrawPile(), xUnit + xOffset, yPos, false);
            for (int i = 0; i < 4; i++) {
                draw(g, model.getFoundation(i), (i + 3)*xUnit + xOffset, yPos, false);
            }
            for (int i = 0; i < 7; i++) {
                draw(g, model.getTableau(i), i*xUnit + xOffset, (int)(0.3*getHeight()), true);
            }
            // Draw labels for foundations
            g.setColor(Color.WHITE);
            g.drawString("Clubs", 3*xUnit + xOffset, yPos - 5);
            g.drawString("Spades", 4*xUnit + xOffset, yPos - 5);
            g.drawString("Hearts", 5*xUnit + xOffset, yPos - 5);
            g.drawString("Diamonds", 6*xUnit + xOffset, yPos - 5);
            // Draw instructions
            g.drawString(waitingForSource ?
                         "Click on deck, draw pile, or tableau." :
                         "Click on destination, or on background to abort move.",
                         30, getHeight() - 40);
        }

        /**
         * Draws this deck at x, y. If splayed is false, only the top card is drawn.
         * Otherwise, the cards appear splayed, with cards closer to the top of the
         * deck lower on the screen but in front of other cards.
         */
        public void draw(Graphics g, Deck deck, int x, int y, boolean splayed) {
            if (deck.size() == 0) {
                g.setColor(Color.GRAY);
                g.fillRect(x, y, CARD_WIDTH, CARD_HEIGHT);
            } else if (splayed) {
                for (int i = 0; i < deck.size(); i++) {                    
                    BufferedImage image = getImage(deck.get(i));
                    g.drawImage(image, x, y, CARD_WIDTH, CARD_HEIGHT, null);
                    y += SPLAY_OFFSET;
                }
            } else {
                BufferedImage image = getImage(deck.get(deck.size() - 1));
                g.drawImage(image, x, y, CARD_WIDTH, CARD_HEIGHT, null);
            }
        }

        public BufferedImage getImage(Card card) {
            BufferedImage image = null;
            try {
                String filename = imageFilename(card);
                image = ImageIO.read(new File(filename));
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            return image;
        }

    }

    /**
     * Returns the filename of the image for this card. All of the files (from
     * http://www.jfitz.com/cards/) should be in a directory "card-images".
     */
    public static String imageFilename(Card card) {
        if (!card.isFaceUp()) {
            return "card-images" + java.io.File.separator + "b2fv.png";
        }
        int result = 1 + card.getSuit().ordinal();
        if(card.getRank().ordinal() > 0) {
            result += 4 * (13 - card.getRank().ordinal());
        }
        return "card-images" + java.io.File.separator + result + ".png";
    }


    public Klondike() {
        
        JFrame frame = new JFrame("Klondike Solitaire");
        JPanel gamePanel = new GamePanel();

        frame.add(gamePanel, BorderLayout.CENTER);

        frame.pack();
        frame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
        frame.setLocationRelativeTo ( null );
        frame.setResizable ( false );
        frame.setVisible ( true ); 
    }
}
