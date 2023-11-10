import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class BoardPanel extends JPanel {
    private Board board = Main.getBoard();
    private Graphics brush;
    private Color boardColor = new Color(255, 205, 145);
    private Color boardLineColor = new Color(166, 121, 55);
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
            new Color(86, 86, 86, 200),
            new Color(200, 200, 200, 200),
            new Color(255, 255, 255)
    };
    private int numLinesOnBoard;
    private int pixelsFromPanelWall;
    private int boardPixelLength;
    private int pixelsBtwnLines;
    private int boardXOrigin;
    private int boardYOrigin;

    BoardPanel(){

    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // Initializes attributes
        initBoardDimensions(g);
        paintBoardBackground();
        paintBoardLines();
        paintStones();
    }

    private void initBoardDimensions(Graphics g) {
        this.board = Main.getBoard();
        this.numLinesOnBoard = board.size() + 2;
        this.pixelsFromPanelWall = 18;      // Distance from frame (DO NOT CHANGE, lossy pixel integer division)
        this.boardPixelLength = 512;        // Board visual size (DO NOT CHANGE, , lossy pixel integer division)
        this.pixelsBtwnLines = boardPixelLength / (board.size() + 1);
        this.boardXOrigin = pixelsFromPanelWall;
        this.boardYOrigin = boardXOrigin;
        this.brush = g;
    }

    private void paintBoardBackground() {
        brush.setColor(boardColor);
        brush.fillRect(boardXOrigin, boardYOrigin, boardPixelLength, boardPixelLength);
    }

    private void paintBoardLines() {
        brush.setColor(boardLineColor);

        // Draws horizontal numLinesOnBoard
        for(int line = 0; line < numLinesOnBoard; line++){
            int x1 = pixelsFromPanelWall;                               // Start of line
            int y1 = pixelsFromPanelWall + (line * pixelsBtwnLines);    // Y-position
            int x2 = boardPixelLength + pixelsFromPanelWall;            // End of line
            int y2 = y1;
            brush.drawLine(x1, y1, x2 , y2);
        }

        // Draws vertical numLinesOnBoard
        for(int line = 0; line < numLinesOnBoard; line++){
            int x1 = pixelsFromPanelWall + (line * pixelsBtwnLines);    // X-position
            int y1 = pixelsFromPanelWall;                               // Top of line
            int x2 = x1;
            int y2 = boardPixelLength + pixelsFromPanelWall;      // Bottom of line
            brush.drawLine(x1, y1, x2, y2);
        }
    }

    private void paintStones() {
        // Traverses Board.cells and paints the players stones
        for (int col = 0; col < board.size(); col++){
            for (int row = 0; row < board.size(); row++) {
                Player player = board.getCells()[col][row];
                if (player != null)
                    paintStone(player.getStoneColor(), col, row);
                else
                    addButtonToOverlayAt(col, row); // Adds button where there isn't already a stone
            }
        }
    }

    private void add(MouseListener[] mouseListeners) {
    }

    private void paintStone(StoneColor color, int col, int row) {
        int x = computeEffectiveFrameX(col);
        int y = computeEffectiveFrameY(row);
        // Artfully paints stones using 3-layers of colored ovals.
        // Chooses which pallet to use
        Color[] pallet = whiteStonePalette;
        switch (color) {
            case BLUE -> pallet = blueStonePalette;
            case RED -> pallet = redStonePalette;
        }

        // Paints the dark bottom layer of stone
        int stoneDiameter = (3 * pixelsBtwnLines) / 4; // Three-quarters the width of an intersection
        brush.setColor(pallet[0]);
        brush.fillOval(x, y, stoneDiameter, stoneDiameter);

        // Paints the colorful middle layer of stone
        int middleLayerOffset = stoneDiameter / 10;
        int middleLayerDiameter = stoneDiameter - middleLayerOffset;
        brush.setColor(pallet[1]);
        brush.fillOval(x + middleLayerOffset, y, middleLayerDiameter, middleLayerDiameter);

        // Paints the 'shinny' top layer of stone
        int topLayerWidth = stoneDiameter / 12;         // Top layer is thin oval 1/12th the width of stone
        int topLayerHeight = stoneDiameter / 6;         // Top layer is tall oval 1/6th the height of stone
        int topLayerXOffset = (4 * stoneDiameter) / 5;  // Top layer is to the right 4/5th from left of stone
        int topLayerYOffset = stoneDiameter / 5;        // Top layer is above midline 1/5th from top of stone
        brush.setColor(pallet[2]);
        brush.fillOval(x + topLayerXOffset, y + topLayerYOffset, topLayerWidth, topLayerHeight);
    }

    private void addButtonToOverlayAt(int col, int row) {
        new GridLayout(board.size(), board.size());
        JRadioButton button = new JRadioButton();
        button.setMinimumSize(new Dimension(pixelsBtwnLines, pixelsBtwnLines));
        button.setVisible(true);
        add(button);
    }

    private int computeEffectiveFrameX(int col) {
        return (col * pixelsBtwnLines) + pixelsFromPanelWall;
    }

    private int computeEffectiveFrameY(int row) {
        return (row * pixelsBtwnLines) + pixelsFromPanelWall;
    }

    private void drawMouseHover() {
        MouseListener mouseListener = new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {

            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        };
    }

    public static void main(String[] args) {
    }
}
