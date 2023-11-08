import java.awt.desktop.OpenURIEvent;
import java.io.*;
import java.util.Random;
import java.util.Scanner;

// TODO:
// 1. Merge GUI functionality with ConsoleUI, then delete GUI.
// 2. Create new Strategy class to be implemented by Player class for cheat mode
// 3. FIXME: After winning, winning streak isnt printed

public class ConsoleUI {

    private Player player1, player2, currentPlayer;
    private Board board;
    private String gameMode;
    private final String stoneA = "●"; // May be changed
    private final String stoneB = "■"; // May be changed
    private final String starStone = "★"; // May be changed
    private TextGraphics displayBoard;           // DELETE after merging GUI with ConsoleUI
    private final Scanner scanner;
    private final PrintStream printStream;

    ConsoleUI() throws IOException {
        //  No args constructor allows real users to play with terminal
        this(System.in, System.out);
    }

    ConsoleUI(InputStream in, OutputStream out) throws IOException {
        // Args constructor uses test class' hard coded input as user input
        this.scanner = new Scanner(in);             // Wraps variable in inside a scanner  so that it may be read
        this.printStream = new PrintStream(out);    // Wraps variable out inside a printStream so that it may be printed
    }

    public void initConsoleUI() throws IOException {
        printStream.write("""
                       
                       __   __  __        __        __     \s
                 /    /    /|  /         /  | /    /  | /  |
                (    (___ ( | (___      (___|(    (___|(___|
                |   )|      |     )     |    |   )|   )    )
                |__/ |__    |  __/      |    |__/ |  /  __/\s
                                                           \s
                  __        __                    \s
                 /  | /|/| /  | /  |  /    /    / \s
                (   |( / |(   |(___| (    (    (  \s
                |   )|   )|   )|\\    |    |    |  \s
                |__/ |  / |__/ | \\   _    _    _ \s
                
                """.getBytes());
        promptToSetMode();
        promptToSetPlayers();
        promptToSetBoard();
    }

    public void playGame() throws IOException {
        // Game logic switches between players until there's a wins, draw, or a player exits.
        boolean playing = true;
        while (playing) {
            printBoard();; // Will print board
            String result = promptToRequestMove();

            switch (result) { // Game States are based on the players' stone placement.
                case "PLAYER_WIN" -> {
                    player1.setSymbol(starStone);
                    player2.setSymbol(starStone);
                    printBoard();
                    printStream.write((currentPlayer.getName() + " WINS!\n").getBytes());
                    playing = false; //Game ends
                }
                case "BOARD_FULL" -> {
                    printBoard();
                    printStream.write("DRAW!\n".getBytes());
                    playing = false; // Game ends
                }
                case "STONE_PLACED" -> {
                    printBoard();
                    printStream.write(("STONE PLACED FOR " + currentPlayer.getName() + "\n").getBytes());
                    swapCurrentPlayer(); // Next player's turn
                }
                case "CELL_UNAVAILABLE" -> {
                    printStream.write("INVALID. TRY AGAIN\n".getBytes()); // Player must request a different move
                }
                case "EXIT" -> {
                    printStream.write("GAME OVER...\n".getBytes());
                    playing = false; // End of game
                }
            }
        }
    }

    public void promptToSetMode() throws IOException {
        // Sets the game mode from user input, handles exceptions
        printStream.write("""
                SELECT A GAME MODE, "PVP" FOR PLAYER VS PLAYER or "PVC" FOR PLAYER VS COMPUTER:
                >>""".getBytes());

        // User will select the game mode
        String modeChoice;
        boolean selectingMode = true;
        while (selectingMode) {
            modeChoice = scanner.nextLine();
            if (modeChoice.equals("PVP")) {
                printStream.write("YOU SELECTED PLAYER VS PLAYER!\n".getBytes());
                gameMode = "PLAYER_VS_PLAYER";
                selectingMode = false;
            }
            else if (modeChoice.equals("PVC")){
                printStream.write("YOU SELECTED PLAYER VS COMPUTER!\n".getBytes());
                gameMode = "PLAYER_VS_COMPUTER";
                selectingMode = false;
            }
            else {
                printStream.write("INVALID SELECTION!".getBytes());
            }
        }
    }

    public void promptToSetPlayers() throws IOException {
        // Sets player1
        printStream.write("""
                    PLAYER 1, PLEASE ENTER YOUR NAME:
                    >>""".getBytes());
        player1 = new HumanPlayer(scanner.nextLine(), stoneA);
        printStream.write(("HELLO, " + player1.getName() + ".\n").getBytes());

        if (gameMode.equals("PLAYER_VS_PLAYER")) {
            // Sets player2
            printStream.write("""
                    PLAYER 2, PLEASE ENTER YOUR NAME:
                    >>""".getBytes());
            player2 = new HumanPlayer(scanner.nextLine(), stoneB);
            printStream.write(("GREETINGS, " + player2.getName() + ".\n").getBytes());
        }
        else {
            // Exception Handling: Game defaults to mode PLAYER_VS_COMPUTER
            player2 = new ComputerPlayer(null, stoneB);
            printStream.write(("YOUR OPPONENTS' IS " + player2.getName() + ".\n").getBytes());
        }

        // Randomly selects the first player
        Random coinToss = new Random();
        if(coinToss.nextBoolean()) currentPlayer = player1;
        else currentPlayer = player2;

        printStream.write((currentPlayer.getName() + " WILL GO FIRST...\n").getBytes());
    }

    public void promptToSetBoard() throws IOException {
        // Sets board size
        printStream.write("""
                SELECT A THE SIZE OF YOUR BOARD (ENTER A NUMBER BETWEEN 15 and 100):
                >>""".getBytes());

        // Handles non-numeric entry exceptions
        int size = -1;
        String sizeChoice = scanner.nextLine();
        try {
            size = Integer.parseInt(sizeChoice);
        }
        catch(NumberFormatException ignored){
        }

        if (size < 15 || size > 100) {
            printStream.write("INVALID SELECTION. DEFAULTING TO 15*15 BOARD\n".getBytes());
            size = 15;
        }
        board = new Board(size);
        displayBoard = new TextGraphics(size);
        displayBoard.createBoard(); // DELETEME - Unnecessary, add statement to its constructor
    }

    public String promptToRequestMove() throws IOException {
        // This is the currentPlayer requests a move
        printStream.write((
                currentPlayer.getSymbol() + " " +
                currentPlayer.getName() +
                " ENTER \"X Y\" VALUES OR ENTER \"STOP\" TO EXIT THE GAME. (\"2 5\")\n" +
                ">> ").getBytes());

        // Will return boards move evaluation as a string
        return currentPlayer.requestMove(getBoard(), getScanner(), getPrintStream());
    }

    public void printMessage(String message) throws IOException {
        // Not sure why this method exists. It writes to the printStream.
        //  Prof Cheon recommended we add it in his lecture slides
        printStream.write(message.getBytes());
    }

    public void printBoard(){
        // FIXME: GUI should be merged with ConsoleUI.
        displayBoard.drawStones(player1, player2, board);//needs parameters //needs to be fixed
        displayBoard.drawBoard(); //Will print board
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

    public Board getBoard() {
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
        ConsoleUI ui = new ConsoleUI(); // Instantiates UI, prompts to set board
        ui.initConsoleUI();
        ui.playGame();
    }
}
