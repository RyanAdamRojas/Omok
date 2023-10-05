package noapplet.example;
import java.util.Random;

public class Game {
    private Board board;
    private Player player1;
    private Player player2;
    private Player currentPlayer;
    private String mode;
    private boolean cheatsActive;

    Game(String newMode, int boardSize) {
        this.board = new Board(boardSize);
        this.setMode(newMode); // "P" or "C". Defaults to "C".

        // Starting player is randomized
        Random coinToss = new Random();
        if(coinToss.nextBoolean()) currentPlayer = player1;
        else currentPlayer = player2;
    }

    public void addPlayer(String name, boolean isHuman) {
        // Sets Player 1
        if (player1 == null) setPlayer1(name, isHuman);
        else if (player2 == null) setPlayer2(name, isHuman);
        else { System.out.println("Max Players reached: \n"
                    + player1.toString() + "\n"
                    + player2.toString());
        }
    }

    public void swapCurrentPlayer(){
        if (player1 != null || player2 != null){
            System.out.println("Cannot switch players: ");
            System.out.println("Player 1: " + player1.toString());
            System.out.println("Player 2: " + player2.toString());
        }
        else {
            // Swaps players
            if (currentPlayer == player1)
                currentPlayer = player2;
            else currentPlayer = player1;
        }
    }

    public void setPlayer1(String name, boolean isHuman) {
        if (isHuman) player1 = new HumanPlayer(name, "X" /*GUI.whiteStone*/); // FIXME
        else player1 = new ComputerPlayer( "X" /*GUI.whiteStone*/);
    }

    public void setPlayer2(String name, boolean isHuman) {
        if (isHuman) player2 = new HumanPlayer(name,  "O" /*GUI.blackStone*/);
        else player2 = new ComputerPlayer( "X" /*GUI.blackStone*/);
    }

    public void setMode(String newMode){
        // Exception Handling: Game will default to Player vs Computer
        if(newMode.equals("P")) this.mode = "P";
        else if (newMode.equals("C")) this.mode = "C";
        else {
            mode = "C";
            System.out.println("Gamemode defaulted to Player vs Computer");
        }
    }

    public void setCheatsActive(boolean cheatsActive) {
        this.cheatsActive = cheatsActive;
    }

    // Boilerplate Below: Setter and Getters
    public Player getPlayer2() {
        return player2;
    }
    public Player getPlayer1() {
        return player1;
    }
    public String getMode(){
        return mode;
    }
}
