// Authors: Ryan Adam Rojas, Sophia Montenegro

package noapplet.example;
import java.util.Random;

public class Game {
    private Board board;
    private Player player1;
    private Player player2;
    private Player currentPlayer;
   // private boolean cheatsActive; // not used

    public Game(String newMode, int boardSize) {
        this.board = new Board(boardSize);

        // Design Decision: Starting player is randomized for max fun
        Random coinToss = new Random();
        if(coinToss.nextBoolean()) currentPlayer = player1;
        else currentPlayer = player2;
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
        if (isHuman) player1 = new HumanPlayer(name, "●");
        else player1 = new ComputerPlayer("●");
    }

    public void setPlayer2(String name, boolean isHuman) {
        if (isHuman) player2 = new HumanPlayer(name,  "○");
        else player2 = new ComputerPlayer( "○");
    }

    // Boilerplate Below: Setter and Getters
    public Player getPlayer2() {
        return player2;
    }
    public Player getPlayer1() {
        return player1;
    }
}
