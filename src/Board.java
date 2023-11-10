// Authors: Ryan Adam Rojas, Sophia Montenegro

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

/**
 * Abstraction of an Omok board, which consists of n x n
 intersections
 * or places where players can place their stones. The board can be
 * accessed using a pair of 0-based indices (x, y), where x and y
 * denote the column and row number, respectively. The top-left
 * intersection is represented by the indices (0, 0), and the
 * bottom-right intersection is represented by the indices (n-1, n-1).
 */
public class Board {
    private Player[][] cells; // [col][row]
    private int size;
    private int occupiedCellCount;
    private int maxOccupiedCellCount;
    private boolean isFull = false;
    private List<Place> winningRow = new LinkedList<Place>();

    /** Create a new board of the default size. */
    Board() {
        this(15);
    }

    /** Create a new board of the specified size.
     * @param size */
    Board(int inSize) {
        // Design Choice: size is bound 15 - 100
        if (inSize < 15 || inSize > 100)
            inSize = 15;

        this.size = inSize;
        this.maxOccupiedCellCount = this.size * this.size;
        this.cells = new Player[size][size];

        // Cells initialized to null
        for (int col = 0; col < this.size; col++) {
            for (int row = 0; row < this.size; row++) {
                cells[col][row] = null;
            }
        }
    }

    /**Returns the size of this board
     * @return int size*/
    public int size() {
        return size;
    }

    /** Removes all the stones placed on the board, effectively
     * resetting the board to its original state.
     */
    public void clear() {
        for (int col = 0; col < size; col++){
            for (int row = 0; row < size; row++) {
                cells[col][row] = null;
            }
        }
    }

    /** Return a boolean value indicating whether all the places
     * on the board are occupied or not.
     * @return boolean isFull()
     */
    public boolean isFull() {
        // Checks if all boards are clear
        for (Player[] row: cells) {
            for (Player player: row){
                if (player == null)
                    return false;
            }
        }
        return true;
    }

    /**
     * Place a stone for the specified player at a specified
     * intersection (x, y) on the board.
     * @param player Player whose stone is to be placed
     * @param x 0-based column (vertical) index
     * @param y 0-based row (horizontal) index
     */
    public void placeStone(Player player, int x, int y) {
        if (x > 0 && x <= size && y > 0 && y <= size) {
            cells[y-1][x-1] = player; // Places a stone
            occupiedCellCount++;
            if (occupiedCellCount == maxOccupiedCellCount)
                isFull = true;
        }
    }

    /**
     * Return a boolean value indicating whether the specified
     * intersection (x, y) on the board is empty or not.
     *
     * @param x 0-based column (vertical) index
     * @param y 0-based row (horizontal) index
     */
    public boolean isCellEmpty(int x, int y) {
        // x and y must be offset by 1 because display coordinates are not zero based)
        return cells[y-1][x-1] == null;
    }

    /**
     * Evaluates the Players move request
     *
     * @param x 0-based column (vertical) index
     * @param y 0-based row (horizontal) index
     * @return message String specifying the result
     */
    public String evaluateMove(Player player, int x, int y) {
        if (!isCellOccupied(x, y)) {
            placeStone(player, x, y);

            // Checks what happens after stone is placed
            if (isWonBy(player))
                return "PLAYER_WIN";
            else if (isFull())
                return "BOARD_FULL";
            else
                return "STONE_PLACED";
        }
        return "CELL_UNAVAILABLE";
    }

    /**
     * Is the specified place on the board occupied?
     *
     * @param x 0-based column (vertical) index
     * @param y 0-based row (horizontal) index
     * @return boolean
     */
    public boolean isCellOccupied(int x, int y){
        // Check if coordinates are within the valid bounds
        if (x >= 1 && x <= cells.length && y >= 1 && y <= cells[0].length) {
            // Check if the cell is null (available)
            return cells[y-1][x-1] != null;
        }
        else {
            // Coordinates are out of bounds
            return false;
        }
    }

    /**
     * Rreturn a boolean value indicating whether the specified
     * intersection (x, y) on the board is occupied by the given
     * player or not.
     *
     * @param x 0-based column (vertical) index
     * @param y 0-based row (horizontal) index
     */
    public boolean isCellOccupiedBy(Player player, int x, int y) {
        // Checks if x y are within bounds
        if (x < 1 || x > cells.length || y < 1 || y > cells[0].length) {
            return false;
        }

        Player cellPlayer = cells[y - 1][x - 1];

        // Handles null player
        if (cellPlayer == null)
            return player == null;

        return cellPlayer.equals(player);

    }

    /**
     * Returns the player who occupies the specified intersection (x,
     y)
     * on the board. If the place is empty, this method returns null.
     *
     * @param x 0-based column (vertical) index
     * @param y 0-based row (horizontal) index
     */
    public Player playerAt(Player p1, Player p2,int x, int y) {
        // Fixed: Method Signature is altered to include Player object like isOccupiedBy() already does..
        // Otherwise the Board class would need refactoring.
        Player p0 = cells[y-1][x-1];
        if (p1.equals(p0))
            return p1;
        else if (p2.equals(p0)) {
            return p2;
        }
        return null;
    }

    /**
     * Return a boolean value indicating whether the given player
     * has a winning row on the board. A winning row is a consecutive
     * sequence of five or more stones placed by the same player in
     * a horizontal, vertical, or diagonal direction.
     */
    public boolean isWonBy(Player player) {
        // TODO Traverse cells neighbors depth first search style.
        return streakDiagonalRight(player) ||
                streakDiagonalLeft(player) ||
                streakRows(player) ||
                streakColumns(player);
    }

    /** Return the winning row
     * @return List<> of Place objects*/
    public List<Place> winningRow() {
        return winningRow;
     }

    public boolean streakDiagonalRight(Player player){
        int count = 0;
        // Loop through the 2D array 'cells'
        for (int col = 0; col < this.size; col++) {
            for (int row = 0; row < this.size; row++) {
                count = 0; // Resets count when starting a new right diagonal

                // Check for winning streak in diagonal right and downward  from current cell
                for (int offset = 0; offset < 5; offset++) {  // Winning streak is offset = 5
                    if (col + offset < this.size && row + offset < this.size) { // Stay within bounds
                        Player currentPlayer = cells[col + offset][row + offset];
                        if (currentPlayer != null && currentPlayer.equals(player)) {
                            winningRow.add(new Place(col + offset, row + offset));
                            count++;
                            if (count == 5)
                                return true; // Found 5 in a row!!!
                        }
                        else {
                            winningRow.clear();
                            count = 0; // Resets count, a non-matching cell is found
                        }
                    }
                }
            }
        }
        return false;
    }

    public boolean streakDiagonalLeft(Player player){
        int count = 0;
        // Loop through the 2D array 'cells'
        for (int col = (this. size - 1); col >= 0; col--) {
            for (int row = (this.size -1); row >= 0; row--) {
                count = 0; // Resets count when starting a new left diagonal

                // Check for winning streak in diagonal left and downward from current cell
                for (int offset = 0; offset < 5; offset++) {  // Winning streak is offset = 5
                    if (col - offset >= 0 && row - offset >= 0) { // Stay within bounds
                        Player currentPlayer = cells[col - offset][row - offset];
                        if (currentPlayer != null && currentPlayer.equals(player)) {
                            winningRow.add(new Place(col - offset, row - offset));
                            count++;
                            if (count == 5)
                                return true; // Found 5 in a row!!!
                        }
                        else {
                            winningRow.clear();
                            count = 0; // Resets count anytime a non-matching cell is found
                        }
                    }
                }
            }
        }
        return false;
    }

    public boolean streakRows(Player player){
        int count = 0;
        for (int col = 0; col < this.size; col++) {
            for (int row = 0; row < this.size; row++) {
                count = 0;  // Resets count when starting a new row

                // Check for winning streak right from current cell
                for (int offset = 0; offset < 5; offset++) {  // Winning streak is offset = 5
                    if (row + offset < size) { // Stay within bounds in row
                        Player currentPlayer = cells[col][row + offset]; // From column, checks down the row
                        if (currentPlayer != null && currentPlayer.equals(player)) {
                            winningRow.add(new Place(col, row + offset));
                            count++;
                            if (count == 5) return true; // Found 5 in a row!!!
                        }
                        else {
                            winningRow.clear();
                            count = 0; // Resets count anytime a non-matching cell is found
                        }
                    }
                }
            }
        }
        return false;
    }

    public boolean streakColumns(Player player){
        int count = 0;
        for (int col = (size - 1); col >= 0; col--) {
            for (int row = (size -1); row >= 0; row--) {
                count = 0; // Resets count when starting a new diagonal

                // Check for winning streak down from current cell
                for (int offset = 0; offset < 5; offset++) {  // Winning streak is offset = 5
                    if (col + offset < size) { // Stay within bounds in column
                        Player currentPlayer = cells[col + offset][row]; // From row, checks down the column
                        if (currentPlayer != null && currentPlayer.equals(player)) {
                            winningRow.add(new Place(col + offset,row));
                            count++;
                            if (count == 5) return true; // Found 5 in a row!!!
                        }
                        else {
                            winningRow.clear();
                            count = 0; // Resets count anytime a non-matching cell is found
                        }
                    }
                }
            }
        }
        return false;
    }

    public Player[][] getCells(){
        return cells;
    }
}
