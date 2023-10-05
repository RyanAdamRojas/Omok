package noapplet.example;

public class Board {
    private String[][] board;
    private int size = 15;
    private int activeStoneCount = 0;
    private int maxStoneCount = -1;
    private boolean isFull = false;
    private boolean cheatsActive = false;

    Board(){
        this.maxStoneCount = this.size * this.size;
    }

    Board(int size){
        if(size >= 15 && size <= 100) {
            // Board size chosen by user
            this.size = size;
            this.maxStoneCount = this.size * this.size;
            this.board = new String[this.size][this.size];

            // All cells are null at first
            for (int i = 0; i < this.size; i++) {
                for (int j = 0; j < this.size; j++) {
                    board[i][j] = null;
                }
            }
        }
        else{
            //Default: 15 x 15 board
            this.maxStoneCount = this.size * this.size;

            // All cells are null at first
            for (int i = 0; i < this.size; i++) {
                for (int j = 0; j < this.size; j++) {
                    board[i][j] = null;
                }
            }
        }
    }

    public boolean requestMove(int x, int y, String symbol) {
        if (board[x-1][y-1] == null) {
            updateBoard(x - 1, y - 1, symbol);
            return true;
        }
        return false;
    }

    public void updateBoard(int x, int y, String symbol) {
        this.board[x][y] = symbol;
    }
}
