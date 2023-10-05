package noapplet.example;

public class Game {
    private Board board;
    private Player player1;
    private Player player2;
    private Player currentPlayer;
    private String mode;

//    public Game(Board board, Player player1, Player player2, Player currentPlayer, String mode) {
//        this.board = board;
//        this.player1 = player1;
//        this.player2 = player2;
//        this.currentPlayer = currentPlayer;
//        this.mode = mode;
//    }

    public void initBoard(int size) {
        board = new Board(size);
    }

    public void addPlayer(Player player){
        // TODO
    }

    public void updateCurrentPlayer(Player currentPlayer){
        // TODO
    }

    public void setMode(String newMode){
        // TODO only 2 options
    }
}
