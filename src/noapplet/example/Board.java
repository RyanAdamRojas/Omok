// Authors: Ryan Adam Rojas, Sophia Montenegro
package noapplet.example;

public class Board {
    private String[][] cells; // [col][row]
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
            for (int col = 0; col < this.size; col++) {
                for (int row = 0; row < this.size; row++) {
                    cells[col][row] = null;
                }
            }
        }
    }

    public String validateMove(String playerSymbol, int x, int y) {
        if (isCellAvailable(x, y)){
            placeStone(x, y, playerSymbol);
            if (didWin(playerSymbol)) return "PLAYER_WIN";
            else if (isFull) return "BOARD_FULL";
            else return "STONE_PLACED";
        }
        else return "CELL_UNAVAILABLE";
    }

    private boolean isCellAvailable(int x, int y){
        // Adjust for 1-based indexing (if needed)
        x--; // Assuming x and y are 1-based
        y--;

        // Check if coordinates are within the valid bounds
        if (x >= 0 && x < cells.length && y >= 0 && y < cells[0].length) {
            // Check if the cell is null (available)
            return cells[y][x] == null;
        }
        else {
            // Coordinates are out of bounds
            return false;
        }
        /*
        // If coordinates are out of bounds
        if ((x*y) > maxStoneCount) return false;
        // -1 for coordinate to array index offset, [col][row]
        return cells[y - 1][x - 1] == null;*/
    }

    private void placeStone(int x, int y, String symbol) {
        // -1 for coordinate to array index offset
        this.cells[y-1][x-1] = symbol;
        this.activeStoneCount++;
        if (activeStoneCount == maxStoneCount) isFull = true;
    }

    private boolean didWin(String symbol){ // TODO CHECK IF 5 STONES IN A ROW
        return streakDiagonalRight(symbol) || streakDiagonalLeft(symbol) || streakRows(symbol) || streakColumns(symbol);
    }

    private boolean streakDiagonalRight(String symbol){
        int count = 0;
        // Loop through the 2D array 'cells'
        for (int col = 0; col < this.size; col++) {
            for (int row = 0; row < this.size; row++) {
                count = 0; // Resets count when starting a new right diagonal

                // Check for winning streak in diagonal right and downward  from current cell
                for (int k = 0; k < 5; k++) {  // Winning streak is k = 5
                    if (col + k < this.size && row + k < this.size) { // Stay within bounds
                        String currentCell = cells[col + k][row + k];
                        if (currentCell != null && currentCell.equals(symbol)) {
                            count++;
                            if (count == 5)
                                return true; // Found 5 in a row!!!
                        }
                        else {
                            count = 0; // Resets count anytime a non-matching cell is found
                        }
                    }
                }
            }
        }
        return false;
    }

    private boolean streakDiagonalLeft(String symbol){
        int count = 0;
        // Loop through the 2D array 'cells'
        for (int col = (this. size - 1); col >= 0; col--) {
            for (int row = (this.size -1); row >= 0; row--) {
                count = 0; // Resets count when starting a new left diagonal

                // Check for winning streak in diagonal left and downward from current cell
                for (int k = 0; k < 5; k++) {  // Winning streak is k = 5
                    if (col - k >= 0 && row - k >= 0) { // Stay within bounds
                        String currentCell = cells[col - k][row - k];
                        if (currentCell != null && currentCell.equals(symbol)) {
                            count++;
                            if (count == 5)
                                return true; // Found 5 in a row!!!
                        }
                        else {
                            count = 0; // Resets count anytime a non-matching cell is found
                        }
                    }
                }
            }
        }
        return false;
    }

    private boolean streakRows(String symbol){
        int count = 0;
        for (int col = 0; col < this.size; col++) {
            for (int row = 0; row < this.size; row++) {
                count = 0;  // Resets count when starting a new row

                // Check for winning streak right from current cell
                for (int k = 0; k < 5; k++) {  // Winning streak is k = 5
                    if (row + k < size) { // Stay within bounds in row
                        String currentCell = cells[col][row + k]; // From column, checks down the row
                        if (currentCell != null && currentCell.equals(symbol)) {
                            count++;
                            if (count == 5) return true; // Found 5 in a row!!!
                        }
                        else {
                            count = 0; // Resets count anytime a non-matching cell is found
                        }
                    }
                }
            }
        }
        return false;
    }

    private boolean streakColumns(String symbol){
        int count = 0;
        for (int col = (size - 1); col >= 0; col--) {
            for (int row = (size -1); row >= 0; row--) {
                count = 0; // Resets count when starting a new diagonal

                // Check for winning streak down from current cell
                for (int k = 0; k < 5; k++) {  // Winning streak is k = 5
                    if (col + k < size) { // Stay within bounds in column
                        String currentCell = cells[col + k][row]; // From row, checks down the column
                        if (currentCell != null && currentCell.equals(symbol)) {
                            count++;
                            if (count == 5) return true; // Found 5 in a row!!!
                        }
                        else {
                            count = 0; // Resets count anytime a non-matching cell is found
                        }
                    }
                }
            }
        }
        return false;
    }

    // Boiler Plate Below: Setters and Getters
    public int getSize() {
        return size;
    }
}
