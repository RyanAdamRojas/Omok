import java.io.*;
import java.util.Random;

// TODO:
// 1. Merge GUI functionality with ConsoleUI, then delete GUI.
// 2. Create new Strategy class to be implemented by Player class for cheat mode
// 3. FIXME: After winning, winning streak isnt printed

public class Main {
    public static Main theInstance = new Main();
    private Player player1, player2, currentPlayer;
    private static Board board;
    private GUI gui;
    private final String stoneA = "●";      // May be changed
    private final String stoneB = "■";      // May be changed
    private final String starStone = "★";   // May be changed
    private TextGraphics displayBoard;      // DELETE after merging GUI with ConsoleUI


    public void init() {
    }

    public void run() {
        board = new Board(15);
        gui = new GUI(theInstance);
    }

    public void setCurrentPlayer() {
        Random random = new Random();
        boolean heads = random.nextBoolean();
        if (heads) currentPlayer = player1;
        else currentPlayer = player2;
    }

    public void setCurrentPlayer(Player player) {
        this.currentPlayer = player;
    }

    public void swapCurrentPlayer() {
        if (currentPlayer.equals(player1))
            currentPlayer = player2;
        else currentPlayer = player1;
    }

    public void setPlayer1(Player player) {
        this.player1 = player;
    }

    public void setPlayer2(Player player) {
        this.player2 = player;
    }

    public static Main getInstance() {
        return theInstance;
    }

    public Player getPlayer1() {
        return player1;
    }

    public Player getPlayer2() {
        return player2;
    }

    public Player getCurrentPlayer() {
        return this.currentPlayer;
    }

    public static Board getBoard() {
        return board;
    }

    public GUI getGui() {
        return gui;
    }

    public static void main(String[] args) throws IOException {
        theInstance.init();
        theInstance.run();
    }
}
