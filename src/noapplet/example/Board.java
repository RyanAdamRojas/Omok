// Authors: Ryan Adam Rojas, Sophia Montenegro
package noapplet.example;

public class Board {
    private String[][] cells;
    private int size = 15;
    private int activeStoneCount = 0;
    private int maxStoneCount = 225;
    private boolean isFull = false;

    Board(int size){
        // Design Choice: size is bound 15 - 100
        if (size < 15 || size > 100) size = 15;

        else {
            // Size accepted
            this.size = size;
            this.maxStoneCount = this.size * this.size;
            this.cells = new String[this.size][this.size];

            // Cells initialized as null
            for (int i = 0; i < this.size; i++) {
                for (int j = 0; j < this.size; j++) {
                    cells[i][j] = null;
                }
            }
        }
    }

    public String validateMove(Player player, int x, int y) {
        if (isCellAvailable(x, y)){
            placeStone(x, y, player.getSymbol());
            if (didWin()) return "PLAYER_WIN";
            else if (isFull) return "BOARD_FULL";
            else return "STONE_PLACED";
        }
        else return "CELL_UNAVAILABLE";
    }

    private boolean isCellAvailable(int x, int y){
        // -1 for coordinate to array index offset
        if (cells[x-1][y-1] == null) return true;
        else return false;
    }

    private void placeStone(int x, int y, String symbol) {
        // -1 for coordinate to array index offset
        this.cells[x-1][y-1] = symbol;
        this.activeStoneCount++;
        if (activeStoneCount == maxStoneCount) isFull = true;
    }

    private boolean didWin(){ // TODO CHECK IF 5 STONES IN A ROW
        return streakDiagnalRight() || streakiagnalLeft() || streakRows() || streakColumns();
    }

    private boolean streakDiagnalRight(){
        // TODO
        int count = 0;
        for (int i = 0; i < this.size; i++) {
            for (int j = 0; j < this.size; j++) {
                //if (cells[i][j] == )
            }
        }
        // DELETME
        return false;
    }

    private boolean streakiagnalLeft(){
        // TODO
        return false;
    }

    private boolean streakRows(){
        // TODO
        return false;
    }

    private boolean streakColumns(){
        // TODO
        return false;
    }
}
