// Authors: Ryan Adam Rojas, Sophia Montenegro

package noapplet.example;
import java.util.Random;

public class ComputerPlayer extends Player {
    private String name;
    private String symbol;
    
    public ComputerPlayer(String name, String symbol) {
        super(name, symbol);
    }

    @Override
    public String requestMove(Board board){
        // Check horizontally, vertically, and diagonally
        String horizontalMove = findWinningMoveHorizontally(board, getSymbol());
        String verticalMove = findWinningMoveVertically(board, getSymbol());
        String diagonalMove = findWinningMoveDiagonal1(board, getSymbol());
        String result;
        // Prioritize winning moves, then blocking opponent, then any other move logic
        if (horizontalMove != null) {
            result = horizontalMove;
        } else if (verticalMove != null) {
            result = verticalMove;
        } else if (diagonalMove != null) {
            result = diagonalMove;
        } else {
            // If no winning or blocking move is found choose a random empty cell
            return findRandomEmptyCell(board);
        }
        String[] parts = result.split(" ");
        int x = Integer.parseInt(parts[0]);
        int y = Integer.parseInt(parts[1]);
        return board.validateMove(this.getSymbol(), x, y);//Calls validateMove method from Board class to see if x and y values are valid move
    }

    private String findRandomEmptyCell(Board board) {
        Random random = new Random();
        int x = random.nextInt(board.getSize());
        int y = random.nextInt(board.getSize());
        String validationMessage = board.validateMove(this.symbol, x, y);
//        Possible validation Messages
//        "GAME_DRAW":
//        "PLAYER_WIN"
//        "STONE_PLACED":
//        "NOT_AVAILABLE":
        while (validationMessage.equals("NOT_AVAILABLE")) {
            x = random.nextInt(board.getSize());              // Gets new x
            y = random.nextInt(board.getSize());              // Gets new y
            validationMessage = board.validateMove(this.symbol, x, y);  // Tries new x and y
        }
        return validationMessage; // Stone placed
    }

    private String findWinningMoveDiagonal1(Board board, String symbol) {
        int boardSize = board.getSize();
        String[][] cells = board.getCells();

        for (int row = 0; row < boardSize - 4; row++) {
            for (int col = 0; col < boardSize - 4; col++) {
                boolean isPotentialWinningMove = true;
                for (int k = 0; k < 5; k++) {
                    if (cells[row][col+k] == null || !cells[row][col+k].equals(symbol)) {
                        isPotentialWinningMove = false;
                        break;
                    }
                }

                if (isPotentialWinningMove) {
                    // This is a winning move in the diagonal direction
                    // Return the coordinates as a string, e.g., "x y"
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
                    if (cells[row][col+k] == null || !cells[row][col+k].equals(symbol)) {
                        isPotentialWinningMove = false;
                        break;
                    }
                }

                if (isPotentialWinningMove) {
                    // This is a winning move horizontally
                    // Return the coordinates as a string, "x y"
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
                    if (cells[row][col+k] == null || !cells[row][col+k].equals(symbol)) {
                        isPotentialWinningMove = false;
                        break;
                    }
                }

                if (isPotentialWinningMove) {
                    // This is a winning move vertically
                    // Return the coordinates as a string, "x y"
                    return (row + 4) + " " + col;
                }
            }
        }

        return null; // No winning move found
    }
}
