import javax.print.attribute.standard.Media;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionAdapter;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;
import java.net.URL;
import java.util.Random;

/**Graphically represents the Board class*/
public class BoardPanel extends JPanel {
    private final Color[] boardPalette = {
            new Color(255, 205, 145),
            new Color(166, 121, 55)
    };
    private final Color[] blueStonePalette = {
            new Color(  0,  10, 212 ),
            new Color( 63, 163, 255),
            new Color(255, 255, 255)
    };
    private final Color[] redStonePalette = {
            new Color(150, 0, 0),
            new Color(255, 100, 100),
            new Color(255, 255, 255)
    };
    private final Color[] greenStonePalette = {
            new Color(0, 114, 0),
            new Color(84, 208, 84),
            new Color(218, 255, 218)
    };
    private final Color[] whiteStonePalette = {
            new Color(162, 162, 162),
            new Color(232, 232, 232),
            new Color(255, 255, 255)
    };
    private static JFrame frame; // For debugging
    private final int BOARD_PIXEL_LENGTH = 512;
    private final int PIXELS_BTWN_LINES = 32;
    private Player[] players = new Player[3]; // Size three to avoid using index 0;
    private Player currentPlayer;
    private Board board;
    private Graphics brush;
    private GUI gui;
    private JavaClient javaClient;
    private String gameID;
    private int hoverRow = -1;
    private int hoverCol = -1;
    private boolean gameActive;
    private boolean isOnlineGame;

    BoardPanel() {
        this(null, null, null, new HumanPlayer(), new ComputerPlayer());
    }

    BoardPanel(GUI gui, JavaClient javaClient, String gameID, Player playerOne, Player playerTwo) {
        // Setting Up
        setPreferredSize(new Dimension(BOARD_PIXEL_LENGTH, BOARD_PIXEL_LENGTH));
        initMouseHoverEffect();
        initMouseClickFunctionality();
        this.board = new Board();
        this.gui = gui;
        this.javaClient = javaClient;
        this.gameID = gameID;
        this.gameActive = true;
        players[1] = playerOne.getName().equals("Name not set") ? new HumanPlayer("Player One", StoneColor.BLUE) : playerOne;
        players[2] = playerTwo;
        if (gameID != null)
            this.isOnlineGame = true;

        // Setting up the first move
        if (isOnlineGame)
            setCurrentPlayer(players[1]);   // In online game, the server requires human to move first
        else
            setFirstPlayerRandomly();       // Human or offline computer may go first
        setGUIHeaderLabel(currentPlayer.getName() + " goes first!");
        if (currentPlayer.isComputer())     //  If computer must make first move
            executeComputerMove();
    }

    /**Paints the BoardPanel*/
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        brush = g;
        paintBoardBackground();
        paintBoardLines();
        paintStonesFromBoard();
        paintStoneUnderMouse();
        paintWinningStreak();
    }

    /**Paints precise pixel-based rectangle*/
    private void paintBoardBackground() {
        brush.setColor(boardPalette[0]);
        brush.fillRect(0, 0, BOARD_PIXEL_LENGTH, BOARD_PIXEL_LENGTH);
    }

    /**Paints precise pixel-based lines*/
    private void paintBoardLines() {
        brush.setColor(boardPalette[1]);

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
    private void paintStonesFromBoard() {
        // Traverses Board.cells and paints the players stones
        for (int col = 0; col < board.size(); col++){
            for (int row = 0; row < board.size(); row++) {
                Player currentPlayer = board.getCells()[col][row];
                if (currentPlayer != null) {
                    // +1 because board objects coordinates are zero based where boardPanel are not
                    paintStone(currentPlayer.getStoneColor(), col + 1, row + 1);
                }
            }
        }
    }

    /**Artfully paint a stone using 3-layers of colored ovals. */
    private void paintStone(StoneColor color, int col, int row) {
        // If coordinates are out of bounds or stone is present return
        if (outsideBounds(col, row))
            return;

        // Chooses which pallet to use
        Color[] pallet = whiteStonePalette;
        switch (color) {
            case BLUE -> pallet = blueStonePalette;
            case RED -> pallet = redStonePalette;
            case GREEN -> pallet = greenStonePalette;
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
        if (gameActive &&
            !currentPlayer.isComputer() &&
            !outsideBounds(hoverCol, hoverRow) &&
            !board.isCellOccupied(hoverCol - 1, hoverRow - 1))
        {
            paintStone(currentPlayer.getStoneColor(), hoverCol, hoverRow); // Then, paint the hover stone
        }
    }

    private void paintWinningStreak() {
        if (!gameActive){
            for (Place place: board.getWinningRow()) {
                if (place != null) {
                    // +1 because board objects coordinates are zero based where boardPanel are not
                    paintStone(StoneColor.GREEN, place.x + 1, place.y + 1);
                }
            }
        }
    }

    /**Adds mouse hover placement effect to BoardPanel*/
    private void initMouseHoverEffect() {
        addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                if (!currentPlayer.isComputer() && gameActive)
                    updateHoverPosition(e);
            }
        });
    }

    /** Converts mouse coordinates to the nearest row and column index on the board*/
    private void updateHoverPosition(MouseEvent e) {
        int nearestCol = Math.round((float) ((e.getX() + 16) / PIXELS_BTWN_LINES));
        int nearestRow = Math.round((float) ((e.getY() + 16) / PIXELS_BTWN_LINES));
        if (outsideBounds(nearestCol, nearestRow)) {
            nearestCol = Math.min(Math.max(nearestCol, 1), 15); // Keeps within bounds
            nearestRow = Math.min(Math.max(nearestRow, 1), 15); // Keeps within bounds
        }
        hoverCol = nearestCol;
        hoverRow = nearestRow;
        repaint();
    }

    /**Adds mouse click functionality to place stone */
    private void initMouseClickFunctionality() {
        addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent me) {
                if (!currentPlayer.isComputer() && gameActive) {
                    // Zero-based step-down conversion from display grid to array[][]
                    evaluateGameState(board.evaluateMove(currentPlayer, hoverCol - 1, hoverRow - 1));
                    // Sends url request to server
                    if (isOnlineGame) {
                        handleServerResponse();
                    }
                }
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

    private void handleServerResponse() {
        // Builds urls path based on mouse position when clicked
        // +1 because board objects coordinates are zero based where boardPanel are not
        String query = String.format("play/?pid=%s&x=%d&y=%d", gameID, (hoverCol - 1), (hoverRow - 1));

        // Gets, tokenizes, and parses the result from query
        String result = GUI.javaClient.sendGet(query);
        String[] tokens = result.split("\"");

        int responseX = -1;
        int responseY = -1;
        if (tokens.length > 20) {
            responseX = parseTokenForInt(tokens[18]); // My helper method
            responseY = parseTokenForInt(tokens[20]); // My helper method
        }

        // Log the server's response to the console
        // +1 because board objects coordinates are zero based where boardPanel are not
        logServerResponse(result, hoverRow-1, hoverCol-1 , responseX, responseY);

        // Execute the server's response
        executeComputerMove(responseX, responseY);
    }

    private int parseTokenForInt(String token) {
        try {
            return Integer.parseInt(token.replace(":", "").replace(",", "").trim());
        } catch (NumberFormatException e) {
            System.err.println("Error parsing integer from token: " + token + " - " + e.getMessage());
            return -1;
        }
    }

    private void logServerResponse(String result, int x1, int y1, int x2, int y2) {
        if (x2 == -1 || y2 == -1) {
            System.out.println("Error parsing tokens: " + result);
        } else {
            System.out.println("        Player moves: " + x1 + " " + y1);
            System.out.println("     Server responds: " + x2 + " " + y2);
            System.out.println(result); // DEBUG
        }
    }

    private void evaluateGameState(State state) {
        switch (state) {
            case STONE_PLACED -> {
                playSound("stoneClick.wav");
                board.print();
                swapCurrentPlayer();
                setGUIHeaderLabel("It's " + currentPlayer.getName() + "'s turn");
                if (currentPlayer.isComputer() && !isOnlineGame)
                    executeComputerMove();
            }
            case BOARD_FULL -> {
                gameActive = false;
                setGUIHeaderLabel("Game Over: Board is full");
            }
            case PLAYER_WIN -> {
                playSound("bruh.wav");
                gameActive = false;
                setGUIHeaderLabel("Game Over: " + currentPlayer.getName() + " wins!");
            }
        }
        repaint();
    }

    private void setGUIHeaderLabel(String message) {
        if (gui != null)
            gui.setHeaderLabel(message);
        else
            frame.setTitle(message);
    }

    private void executeComputerMove() {
        // Default values for offline moves
        executeComputerMove(-1, -1);
    }

    private void executeComputerMove(final int x, final int y) {
        int COMPUTER_THINK_DELAY = 1500;
        new Timer(COMPUTER_THINK_DELAY, ae -> {
            if (currentPlayer.isComputer()) { // Checks again in case of error
                ComputerPlayer computerPlayer = (ComputerPlayer) currentPlayer;
                if (isOnlineGame)
                    evaluateGameState(board.evaluateMove(computerPlayer, x, y));  // Lambda requires final variables
                else
                    evaluateGameState(computerPlayer.placeRandomEmptyCell(board));
                repaint();
            }
        }).start();
    }

    public void playSound(String fileName) {
        // Using getResource to get the sound file within the resources folder
        // "/res/Sounds/stoneClick.wav"
        String directory = "/Sounds/";
        URL soundURL = getClass().getResource(directory + fileName);

        // Check if the URL is not null
        if (soundURL != null) {
            try (AudioInputStream audioStream = AudioSystem.getAudioInputStream(soundURL)) {
                // Get a sound clip resource
                Clip clip = AudioSystem.getClip();

                // Open audio clip and load samples from the audio input stream
                clip.open(audioStream);

                // Play the audio clip
                clip.start();
            } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
                e.printStackTrace();
            }
        } else {
            System.err.println("Sound file not found: /Sounds/stoneClick.wav");
        }
    }

    private boolean outsideBounds(int col, int row) {
        return (row < 1 || row > 15) || (col < 1 || col > 15);
    }

    private void swapCurrentPlayer() {
        currentPlayer = currentPlayer.equals(players[1]) ? players[2] : players[1];
    }

    private void setFirstPlayerRandomly() {
        Random random = new Random();
        currentPlayer = random.nextBoolean() ? players[1] : players[2];
    }

    private void setCurrentPlayer(Player player) {
        this.currentPlayer = player;
    }

    public static void main(String[] args) {
        // For testing
        frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Ensure the application closes when the frame is closed
        frame.setSize(new Dimension(512,512));
        frame.add(new BoardPanel());    // Adds the BoardPanel before calling pack
        frame.setResizable(false);      // Call setResizable before pack()
        frame.pack();                   // Pack the frame after adding components
        frame.setVisible(true);         // Make the frame visible after packing
    }
}
