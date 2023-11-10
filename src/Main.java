import java.io.*;
import java.util.Scanner;

// TODO:
// 1. Merge GUI functionality with ConsoleUI, then delete GUI.
// 2. Create new Strategy class to be implemented by Player class for cheat mode
// 3. FIXME: After winning, winning streak isnt printed

public class Main {

    private Player player1, player2, currentPlayer;
    private static Board board;
    private String gameMode;
    private final String stoneA = "●";      // May be changed
    private final String stoneB = "■";      // May be changed
    private final String starStone = "★";   // May be changed
    private TextGraphics displayBoard;           // DELETE after merging GUI with ConsoleUI
    private GUI gui;
    private final Scanner scanner;
    private final PrintStream printStream;

    Main() throws IOException {
        //  No args constructor allows real users to play with terminal
        this(System.in, System.out);
    }

    Main(InputStream in, OutputStream out) throws IOException {
        // Args constructor uses test class' hard coded input as user input
        this.scanner = new Scanner(in);             // Wraps variable in inside a scanner  so that it may be read
        this.printStream = new PrintStream(out);    // Wraps variable out inside a printStream so that it may be printed
    }

    public void init() throws IOException {
        board = new Board(15);
        gui = new GUI();
    }

    public void playGame() throws IOException {
        // Game logic switches between players until there's a wins, draw, or a player exits.
        boolean playing = true;
        while (playing) {
//            printBoard();; // Will print board
//            String result = promptToRequestMove(); // FIXME
//
//            switch (result) { // Game States are based on the players' stone placement.
//                case "PLAYER_WIN" -> {
//                    player1.setSymbol(starStone);
//                    player2.setSymbol(starStone);5
//                    printBoard();
//                    printStream.write((currentPlayer.getName() + " WINS!\n").getBytes());
//                    playing = false; //Game ends
//                }
//                case "BOARD_FULL" -> {
//                    printBoard();
//                    printStream.write("DRAW!\n".getBytes());
//                    playing = false; // Game ends
//                }
//                case "STONE_PLACED" -> {
//                    printBoard();
//                    printStream.write(("STONE PLACED FOR " + currentPlayer.getName() + "\n").getBytes());
//                    swapCurrentPlayer(); // Next player's turn
//                }
//                case "CELL_UNAVAILABLE" -> {
//                    printStream.write("INVALID. TRY AGAIN\n".getBytes()); // Player must request a different move
//                }
//                case "EXIT" -> {
//                    printStream.write("GAME OVER...\n".getBytes());
//                    playing = false; // End of game
//                }
//            }
        }
    }

    private void swapCurrentPlayer() {
        if (currentPlayer.equals(player1))
            currentPlayer = player2;
        else currentPlayer = player1;
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

    public String getGameMode() {
        return gameMode;
    }

    public Scanner getScanner() {
        return scanner;
    }

    public PrintStream getPrintStream() {
        return printStream;
    }

    public static void main(String[] args) throws IOException {
        new Main().init(); // Instantiates UI, prompts to set board
    }
}
