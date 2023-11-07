// Authors: Ryan Adam Rojas, Sophia Montenegro

package noapplet.example;
import java.util.Random;

public class ComputerPlayer extends Player {

    public ComputerPlayer(String name, String symbol) {
        super(name, symbol);
    }

    @Override
    public String requestMove(Board board) {
        // Check horizontally, vertically, and diagonally
        String horizontalMove = findWinningMoveHorizontally(board, getSymbol());
        String verticalMove = findWinningMoveVertically(board, getSymbol());
        String diagonalMove = findWinningMoveDiagonal1(board, getSymbol());

        if (horizontalMove != null) {
            return horizontalMove;
        } else if (verticalMove != null) {
            return verticalMove;
        } else if (diagonalMove != null) {
            return diagonalMove;
        } else {
            // If no winning move is found, try to find an empty cell
            String emptyCell = findRandomEmptyCell(board);
            if (emptyCell != null) {
                return emptyCell;
            } else {
                // No valid moves are available, return a message indicating the computer cannot move
                return "NO_VALID_MOVE";
            }
        }
    }

    private String findRandomEmptyCell(Board board) {
        Random random = new Random();
        int size = board.getSize();

        // Try to find a valid empty cell within a reasonable number of attempts
        int maxAttempts = size * size * 2; // Adjust as needed
        for (int attempt = 0; attempt < maxAttempts; attempt++) {
            int x = random.nextInt(size);
            int y = random.nextInt(size);
            String validationMessage = board.validateMove(this.getSymbol(), x, y);

            if (!validationMessage.equals("CELL_UNAVAILABLE")) {
                // Found a valid empty cell
                return x + " " + y;
            }
        }

        // If no valid empty cell is found after many attempts, return null
        return null;
    }

    private String findWinningMoveDiagonal1(Board board, String symbol) {
        int boardSize = board.getSize();
        String[][] cells = board.getCells();

        for (int row = 0; row < boardSize - 4; row++) {
            for (int col = 0; col < boardSize - 4; col++) {
                boolean isPotentialWinningMove = true;
                for (int k = 0; k < 5; k++) {
                    if (cells[row + k][col + k] == null || !cells[row + k][col + k].equals(symbol)) {
                        isPotentialWinningMove = false;
                        break;
                    }
                }

                if (isPotentialWinningMove) {
                    // This is a winning move in the diagonal direction
                    // Return the coordinates as "row col"
                    return (row + 4) + " " + (col + 4);
                }
            }
        }

        return null; // No winning move found
    }

    private String findWinningMoveHorizontally(Board board, String symbol) {
        int boardSize = board.getSize();
        String[][] cells = board.getCells();

        for (int row = 0; row < boardSize; row++) {
            for (int col = 0; col < boardSize - 4; col++) {
                boolean isPotentialWinningMove = true;
                for (int k = 0; k < 5; k++) {
                    if (cells[row][col + k] == null || !cells[row][col + k].equals(symbol)) {
                        isPotentialWinningMove = false;
                        break;
                    }
                }

                if (isPotentialWinningMove) {
                    // This is a winning move horizontally
                    // Return the coordinates as "row col"
                    return row + " " + col;
                }
            }
        }

        return null; // No winning move found
    }

    private String findWinningMoveVertically(Board board, String symbol) {
        int boardSize = board.getSize();
        String[][] cells = board.getCells();

        for (int col = 0; col < boardSize; col++) {
            for (int row = 0; row < boardSize - 4; row++) {
                boolean isPotentialWinningMove = true;
                for (int k = 0; k < 5; k++) {
                    if (cells[row + k][col] == null || !cells[row + k][col].equals(symbol)) {
                        isPotentialWinningMove = false;
                        break;
                    }
                }

                if (isPotentialWinningMove) {
                    // This is a winning move vertically
                    // Return the coordinates as "row col"
                    return (row + 4) + " " + col;
                }
            }
        }

        return null; // No winning move found
    }
}

