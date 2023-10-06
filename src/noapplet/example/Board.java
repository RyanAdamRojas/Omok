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
            if (didWin(player.getSymbol())) return "PLAYER_WIN";
            else if (isFull) return "BOARD_FULL";
            else return "STONE_PLACED";
        }
        else return "CELL_UNAVAILABLE";
    }

    private boolean isCellAvailable(int x, int y){
        // If coordinates are out of bounds
        if ((x*y) > maxStoneCount) return false;
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

    private boolean didWin(String symbol){ // TODO CHECK IF 5 STONES IN A ROW

        return streakDiagnalRight(symbol) || streakDiagnalLeft(symbol) || streakRows(symbol) || streakColumns(symbol);
    }

    private boolean streakDiagnalRight(String symbol){
        // TODO TEST
        int count = 0;
        // Loop through the 2D array 'cells'
        for (int i = 0; i < this.size; i++) {
            for (int j = 0; j < this.size; j++) {

                // Reseting count when starting a new diagonal
                count = 0;

                // Check for winning streak in diagnal right and downward  from current (i, j)
                for (int k = 0; k < 5; k++) {  // Winning streak is k = 5
                    if (i + k < this.size && j + k < this.size) { // Stay within bounds
                        if (cells[i + k][j + k].equals(symbol)) {
                            count++;
                            if (count == 5) { // Found 5 in a row
                                return true;
                            }
                        } else {
                            count = 0; // Resets count anytime a non-matching cell is found
                        }
                    }
                }
            }
        }
        return false;
    }

    private boolean streakDiagnalLeft(String symbol){
        // TODO
        return false;
    }

    private boolean streakRows(String symbol){
        // TODO
        return false;
    }

    private boolean streakColumns(String symbol){
        // TODO
        return false;
    }

    // Boiler Plate Below: Setters and Getters
    public int getSize() {
        return size;
    }
}
