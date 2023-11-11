import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionAdapter;

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
            new Color(86, 86, 86, 200),
            new Color(200, 200, 200, 200),
            new Color(255, 255, 255)
    };
    private final int BOARD_PIXEL_LENGTH = 512;
    private final int PIXELS_BTWN_LINES = 32;
    private Board board = Main.getBoard();
    private Graphics brush;
    private int hoverRow = -1;
    private int hoverCol = -1;

    BoardPanel(){
        addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                updateHoverPosition(e);
            }
        });
    }

    private void updateHoverPosition(MouseEvent e) {
        hoverCol = Math.round((float) (e.getX() / PIXELS_BTWN_LINES));
        hoverRow = Math.round((float) (e.getY() / PIXELS_BTWN_LINES));
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // Initializes attributes
        initBoardDimensions(g);
        paintBoardBackground();
        paintBoardLines();
        paintStones();
        if (hoverRow >= 0 && hoverCol >= 0) {
            paintStone(StoneColor.WHITE, hoverCol, hoverRow);

        }
    }

    private void initBoardDimensions(Graphics g) {
        // Pixel Perfect: Length = 2 (PIXELS_BTWN_LINES * NUM_SPACES) -> 512 = 2(16 * 16)
        setSize(new Dimension(BOARD_PIXEL_LENGTH, BOARD_PIXEL_LENGTH));
        this.board = Main.getBoard();
        this.brush = g;
    }

    private void paintBoardBackground() {
        brush.setColor(boardColor);
        brush.fillRect(0, 0, BOARD_PIXEL_LENGTH, BOARD_PIXEL_LENGTH);
    }

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

    private void paintStones() {
        // Traverses Board.cells and paints the players stones
        for (int col = 0; col < board.size(); col++){
            for (int row = 0; row < board.size(); row++) {
                Player player = board.getCells()[col][row];
                if (player != null)
                    paintStone(player.getStoneColor(), col, row);
            }
        }
    }

    private void paintStone(StoneColor color, int col, int row) {
        // Artfully paints stones using 3-layers of colored ovals.
        // Chooses which pallet to use
        Color[] pallet = whiteStonePalette;
        switch (color) {
            case BLUE -> pallet = blueStonePalette;
            case RED -> pallet = redStonePalette;
        }

        // Artfully paints the dark bottom layer of stone
        int stoneDiameter = 24; // Three-quarters the width of an intersection
        int stoneRadius = 12;
        int xOffset = computeRowX(col) - stoneRadius;
        int yOffset = computeRowY(row) - stoneRadius;
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

    private int computeRowX(int col) {
        return col * PIXELS_BTWN_LINES;
    }

    private int computeRowY(int row) {
        return row * PIXELS_BTWN_LINES;
    }

    public static void main(String[] args) {
    }
}
