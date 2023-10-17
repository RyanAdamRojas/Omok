import java.awt.desktop.OpenURIEvent;
import java.io.*;
import java.util.Random;
import java.util.Scanner;

// From Ryan, Major Changes:
// 0. ConsoleUITest requires that an object of type ByteArrayInputStream be used
//      for testing by passing it into the ConsoleUI constructor. This means that
//      our code has to be refactored to use a ByteStream for IO. Meaning: If the test
//      class or a human is playing the game, all input for moves and such are read from
//      the ByteStream. Think if the ByteStream as the place where real human-user inputs
//      and the test-class inputs are stored so that it can being read by our methods.
//      Text me if you have questions.
// 1. Prof. Cheon wants us to build a board and then pass it through
//      the ConsoleUI constructor. This would require us to move the
//      game control flow to the main method. But this can wait.
// 2. Merge GUI functionality with ConsoleUI, delete GUI.
// 3. Create the simpler "drawBoard()" method. (Note: Add the drawStones() statement inside
//      the drawBoard() method.)
// 4. Fix Computer class stones not being visibly placed when board us drawn
// 5. Create new Strategy class to be implemented by Player class for cheat mode
// 6. Move game control flow out of the constructor!

public class ConsoleUI {

    private Player player1, player2, currentPlayer;
    private Board board;
    private GUI displayBoard; // DELETEME after merging GUI with ConsoleUI
    private Scanner scanner;            // Can use either type ByteArrayInputStream or System.in
    private PrintStream printStream;    // Can use either type ByteArrayprintStream or System.out

    ConsoleUI() throws IOException {
        //  No args constructor allows real users to play with terminal
        this(System.in, System.out);
    }

    ConsoleUI(InputStream in, OutputStream out) throws IOException {
        // Args constructor uses test class' hard coded input as user input
        this.scanner = new Scanner(in);             // Wraps variable in inside a scanner  so that it may be read
        this.printStream = new PrintStream(out);    // Wraps variable out inside a printStream so that it may be printed
    }

    public void playGame() throws IOException {
        // Game logic simply alternates between players (p1, p2, p1, ...) until a player wins, draws, or exits.
        boolean playing = true;
        while (playing) {
            displayBoard.drawBoard(); // Will print board
            String result = promptToRequestMove();

            switch (result) { //The following game states based on stone placements
                case "PLAYER_WIN" -> {
                    player1.setSymbol("★");
                    player2.setSymbol("★");
                    printBoard();
                    printStream.write((currentPlayer.getName() + " WINS!").getBytes());
                    playing = false; //Game ends
                }
                case "BOARD_FULL" -> {
                    printStream.write("DRAW!".getBytes());
                    playing = false; // Game ends
                }
                case "STONE_PLACED" -> {
                    displayBoard.drawStone(player1.getSymbol(), player2.getSymbol(), board); // FIXME by merging GUI.
                    printStream.write(("STONE PLACED FOR " + currentPlayer.getName()).getBytes());
                    swapCurrentPlayer(); //next player's turn
                }
                case "CELL_UNAVAILABLE" -> {
                    printStream.write("INVALID. TRY AGAIN".getBytes()); // Player must request a different move
                }
                case "EXIT" -> {
                    printStream.write("GAME OVER...".getBytes());
                    playing = false; // End of game
                }
            }
        }
    }

    public void initCurrentPlayerRandomly() {
        // Randomly selects the first player
        Random coinToss = new Random();
        if(coinToss.nextBoolean()) currentPlayer = player1;
        else currentPlayer = player2;
    }

    public void initBothPlayers(String mode) {
        player1 = new HumanPlayer("Player1", "●");
        if (mode.equals("P") || mode.equals("p")) {
            player2 = new HumanPlayer("Player2", "■");
        }
        else if (mode.equals("C") || mode.equals("c")){
            player2 = new ComputerPlayer("Computer", "X");
        }
        else {
            // Game defaults to Player versus Computer
            player2 = new ComputerPlayer("Computer", "X");
        }
    }

    public void printMessage(String message) throws IOException {
        // FIXME: Not sure why this method exists. It writes to printStream
        //  Prof Cheon recommended we add it
        printStream.write(message.getBytes());
    }

    public void printBoard(){
        // FIXME: GUI should be merged with ConsoleUI.
        displayBoard.drawStone(player1.getSymbol(), player2.getSymbol(), board);//needs parameters //needs to be fixed
        displayBoard.drawBoard(); //Will print board
    }

    public void promptToSetBoard() throws IOException {
        //  User will select the boards size
        printStream.write("""
                SELECT A THE SIZE OF YOUR BOARD:
                (ENTER A NUMBER BETWEEN 15 and 100)
                >> """.getBytes());

        int size = -1;
        String sizeChoice = scanner.nextLine();  // Reads from either InputStream String or System.in String
        try {                                    // Handling exceptions (i.e. if user inputs non-numeric string)
            size = Integer.parseInt(sizeChoice); // Attempts to cast String to Int
        }
        catch(NumberFormatException ignored){
            // Nothing happens here intentionally
        }

        if (size < 15 || size > 100) {
            printStream.write("INVALID SELECTION. DEFAULTING TO 15X15\n".getBytes());
            size = 15;
        }
        board = new Board(size);
        displayBoard = new GUI(size); // Creates display board
        displayBoard.createBoard(); // DELETEME - Unnecessary, add statement to its constructor
        initCurrentPlayerRandomly();
    }

    public void promptToSetMode() throws IOException {
        // Sets the game mode from user input, handles exceptions
        printStream.write("""
                SELECT A GAME MODE:
                (ENTER "P" FOR PLAYER VS PLAYER or "C" FOR PLAYER VS COMPUTER)
                >> """.getBytes());

        // User will select the game mode
        String modeChoice;
        int modeValue = 0; // 1 means PVP, 2 means  PVC
        boolean selectingMode = true;
        while (selectingMode) {
            modeChoice = scanner.nextLine();
            if (modeChoice.equals("P")) {
                printStream.write("YOU SELECTED PLAYER VS PLAYER! ---------------------------------------\n".getBytes());
                modeValue = 1;
                initBothPlayers(modeChoice);
                selectingMode = false;
            }
            else if (modeChoice.equals("C")){
                printStream.write("YOU SELECTED PLAYER VS COMPUTER! ---------------------------------------\n".getBytes());
                modeValue = 2;
                initBothPlayers(modeChoice);
                selectingMode = false;
            }
            else {
                printStream.write("INVALID SELECTION!".getBytes());
            }
        }
    }

    public String promptToRequestMove() throws IOException {
        // This is how a player requests a move.
        String result = getCurrentPlayer().requestMove(getBoard(), getScanner(), getPrintStream());
        return result;
    }

    public void swapCurrentPlayer(){
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

    public Scanner getScanner() {
        return scanner;
    }

    public PrintStream getPrintStream() {
        return printStream;
    }

    public static void main(String[] args) throws IOException {
        ConsoleUI ui = new ConsoleUI(); // Instantiates UI, prompts to set board
        ui.promptToSetMode();
        ui.promptToSetBoard();
        ui.playGame();
    }
}
