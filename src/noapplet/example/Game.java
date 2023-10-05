package noapplet.example;
import java.util.Random;

public class Game {
    private Board board;
    private Player player1;
    private Player player2;
    private Player currentPlayer;
    private String mode;

    public Game(int boardSize, String mode) {
        this.board = new Board(boardSize);
        this.mode = mode;
        Random random = new Random();
        if(random.nextBoolean() == true) currentPlayer = player1;
        else currentPlayer = player2;
    }

    public void addPlayer(String name, boolean isHuman) {
        if (player1 == null) {
            //if(isHuman) player1 = new Player(name, GUI.white);

        }
        else
            //player2 = newPlayer;

        if (player1 != null && player2 != null) {
            System.out.println("Max Players reached: \n"
                    + player1.toString() + "\n"
                    + player2.toString());
        }
    }

    public void switchCurrentPlayer(){
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

    public void setPlayer1(Player newPlayer) {
        this.player1 = newPlayer;
    }

    public Player getPlayer1() {
        return player1;
    }

    public void setPlayer2(Player newPlayer) {
        this.player2 = newPlayer;
    }

    public Player getPlayer2() {
        return player2;
    }

    public void setMode(String newMode){
        // TODO only 2 options
    }
}
