package noapplet.example;

public class Board {
    private String[][] cells;
    private int size = 15;
    private int activeStoneCount = 0;
    private int maxStoneCount = 225;
    private boolean isFull = false;
    private boolean cheatsActive = false;

    Board(int size){
        if(size >= 15 && size <= 100) {
            // Board size chosen by user
            this.size = size;
            this.maxStoneCount = this.size * this.size;
            this.cells = new String[this.size][this.size];

            // All cells are null at first
            for (int i = 0; i < this.size; i++) {
                for (int j = 0; j < this.size; j++) {
                    cells[i][j] = null;
                }
            }
        }
        else{
            //Default: 15 x 15 board
            this.maxStoneCount = this.size * this.size;

            // All cells are null at first
            for (int i = 0; i < this.size; i++) {
                for (int j = 0; j < this.size; j++) {
                    cells[i][j] = null;
                }
            }
        }
    }

    public boolean requestMove(int x, int y, String symbol) {
        if (cells[x-1][y-1] == null) {
            // Offset of -1 for coordinate to array index
            updateBoard(x - 1, y - 1, symbol);
            return true;
        }
        return false; // Cell unavailable
    }

    public void updateBoard(int x, int y, String symbol) {
        this.cells[x][y] = symbol;
    }
}
