import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionAdapter;
import java.awt.image.BufferedImage;

/**Graphically represents the Board class*/
public class BoardPanel extends JPanel {
    private final Color boardColor = new Color(255, 205, 145);
    private final Color boardLineColor = new Color(166, 121, 55);
    private final Color[] blueStonePalette = {
            new Color(0, 10, 212, 200),
            new Color(63, 163, 255, 200),
            new Color(255, 255, 255)
    };
    private final Color[] redStonePalette = {
            new Color(150, 0, 0, 200),
            new Color(255, 100, 100, 200),
            new Color(255, 255, 255)
    };
    private final Color[] whiteStonePalette = {
            new Color(173, 173, 173, 200),
            new Color(234, 234, 234, 200),
            new Color(255, 255, 255)
    };
    private StoneColor stoneColor;
    private Player currentPlayer;
    private final int BOARD_PIXEL_LENGTH = 512;
    private final int PIXELS_BTWN_LINES = 32;
    private Board board = Main.getBoard();
    private Graphics brush;
    private int hoverRow = -1;
    private int hoverCol = -1;

    BoardPanel() {
        initMouseHoverEffect();
        initMouseClickFunctionality();
        setPreferredSize(new Dimension(BOARD_PIXEL_LENGTH, BOARD_PIXEL_LENGTH));
        if (Main.getBoard() == null)
            this.board = new Board();
        else
            this.board = Main.getBoard();
    }

    /**Paints the BoardPanel*/
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        brush = g;
        paintBoardBackground();
        paintBoardLines();
        paintStones();
        paintStoneUnderMouse();
    }

    /**Paints precise pixel-based rectangle*/
    private void paintBoardBackground() {
        brush.setColor(boardColor);
        brush.fillRect(0, 0, BOARD_PIXEL_LENGTH, BOARD_PIXEL_LENGTH);
    }

    /**Paints precise pixel-based lines*/
    private void paintBoardLines() {
        brush.setColor(boardLineColor);

        // Draws horizontal grid lines
        for(int line = 0; line < board.size() + 2; line++){
            int y = line * PIXELS_BTWN_LINES;
            brush.drawLine(0, y, BOARD_PIXEL_LENGTH, y);
        }

        // Draws vertical grid
        for(int line = 0; line < board.size() + 2; line++){
            int x = line * PIXELS_BTWN_LINES;
            brush.drawLine(x, 0, x, BOARD_PIXEL_LENGTH);
        }
    }

    /**Traverses paints stones given by board*/
    private void paintStones() {
        // Traverses Board.cells and paints the players stones
        for (int col = 0; col < board.size(); col++){
            for (int row = 0; row < board.size(); row++) {
                Player currentPlayer = board.getCells()[col][row];
                if (currentPlayer != null) {
                    // Zero-based conversion from Cell[][]
                    paintStone(currentPlayer.getStoneColor(), col + 1, row + 1);
                }
            }
        }
    }

    /**Artfully paints stones using 3-layers of colored ovals. */
    private void paintStone(StoneColor color, int col, int row) {
        // Won't print stone if coordinates are out of bounds
        if ((row < 1 || col < 1) || (row > 15 || col > 15))
            return;

        // Chooses which pallet to use
        Color[] pallet = whiteStonePalette;
        switch (color) {
            case BLUE -> pallet = blueStonePalette;
            case RED -> pallet = redStonePalette;
        }

        // Artfully paints the dark bottom layer of stone
        int stoneDiameter = 28; // 7/8-ths the width of an intersection
        int stoneRadius = 12;
        int xOffset = (col * PIXELS_BTWN_LINES) - stoneRadius;
        int yOffset = (row * PIXELS_BTWN_LINES) - stoneRadius;
        brush.setColor(pallet[0]);
        brush.fillOval(xOffset, yOffset, stoneDiameter, stoneDiameter);

        // Artfully paints the colorful middle layer of stone
        int middleLayerOffset = stoneDiameter / 10;
        int middleLayerDiameter = stoneDiameter - middleLayerOffset;
        brush.setColor(pallet[1]);
        brush.fillOval(xOffset + middleLayerOffset, yOffset, middleLayerDiameter, middleLayerDiameter);

        // Artfully paints the 'shinny' top layer of stone
        int topLayerWidth = stoneDiameter / 12;         // Top layer is thin oval 1/12th the width of stone
        int topLayerHeight = stoneDiameter / 6;         // Top layer is tall oval 1/6th the height of stone
        int topLayerXOffset = (4 * stoneDiameter) / 5;  // Top layer is to the right 4/5th from left of stone
        int topLayerYOffset = stoneDiameter / 5;        // Top layer is above midline 1/5th from top of stone
        brush.setColor(pallet[2]);
        brush.fillOval(xOffset + topLayerXOffset, yOffset + topLayerYOffset, topLayerWidth, topLayerHeight);
    }

    /**Paints a stone on an intersection nearest to the mouse*/
    private void paintStoneUnderMouse() {
        if (hoverRow >= 0 && hoverCol >= 0)
            paintStone(StoneColor.WHITE, hoverCol, hoverRow);
        else {
            paintStones();
            hoverRow = -1;
            hoverCol = -1;
        }
    }

    /**Converts mouse coordinates to nearest row and column index */
    private void updateHoverPosition(MouseEvent e) {
        hoverCol = Math.round((float) ((e.getX() + 16) / PIXELS_BTWN_LINES)); // Pixel(32, 32) / Pixels(32, 32) = Intersection(1,1)
        hoverRow = Math.round((float) ((e.getY() + 16) / PIXELS_BTWN_LINES)); // Pixel(32, 32) / Pixels(32, 32) = Intersection(1,1)
        repaint();
    }

    /**Adds mouse hover placement effect to BoardPanel*/
    private void initMouseHoverEffect() {
        addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                updateHoverPosition(e);
            }
        });
    }

    /**Adds mouse click functionality to place stone */
    private void initMouseClickFunctionality() {
        addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                currentPlayer = new HumanPlayer("Bruh", StoneColor.BLUE); // FIXME Delete this line

                // Zero-based conversion into cell[][] in parameters
                board.evaluateMove(currentPlayer, hoverCol - 1, hoverRow - 1);
                System.out.println("DEBUG: Clicked:" + hoverRow + " " + hoverCol); //
                repaint();
            }

            @Override
            public void mousePressed(MouseEvent e) {}

            @Override
            public void mouseReleased(MouseEvent e) {}

            @Override
            public void mouseEntered(MouseEvent e) {}

            @Override
            public void mouseExited(MouseEvent e) {}
        });
    }

    public void setStoneColor(StoneColor stoneColor) {
        this.stoneColor = stoneColor;
    }

    public void setCurrentPlayer(Player currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Ensure the application closes when the frame is closed
        frame.setSize(new Dimension(512,512));
        frame.add(new BoardPanel()); // Adds the BoardPanel before calling pack
        frame.setResizable(false);  // Call setResizable before pack()
        frame.pack();               // Pack the frame after adding components
        frame.setVisible(true);     // Make the frame visible after packing
    }

}
