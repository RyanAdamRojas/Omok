// Authors: Ryan Adam Rojas, Sophia Montenegro

import java.io.IOException;
import java.io.PrintStream;
import java.util.Random;
import java.util.Scanner;

public class ComputerPlayer extends Player {
    private String name;
    private StoneColor stoneColor;

    ComputerPlayer(){
        super();
    }
    ComputerPlayer(String name, StoneColor stoneColor) {
        super("ChatGPT", stoneColor);
    }

    @Override
    public State requestMove(Board board, Scanner scanner, PrintStream printStream) throws IOException {
        // FIXME Makes random move, not a smart one.
        // Gets random x and y
        Random random = new Random();
        int x = random.nextInt(board.size());
        int y = random.nextInt(board.size());
        printStream.write(("Computer is trying coordinates: " + x + y).getBytes());
        State state = board.evaluateMove(this, x, y);

        // Loops until an x y is available
        while (state.equals(State.CELL_UNAVAILABLE)) {
            x = random.nextInt(board.size());              // Gets new x
            y = random.nextInt(board.size());              // Gets new y
            printStream.write(("Computer is trying coordinates: " + x + y).getBytes());
            state = board.evaluateMove(this, x, y);  // Tries new x and y
        }
        return state; // Stone placed
    }

//    public String requestMove(Board board){
//        // Check horizontally, vertically, and diagonally
//        String horizontalMove = findWinningMoveHorizontally(board, getSymbol());
//        String verticalMove = findWinningMoveVertically(board, getSymbol());
//        String diagonalMove = findWinningMoveDiagonal1(board, getSymbol());
//        String result;
//        // Prioritize winning moves, then blocking opponent, then any other move logic
//        if (horizontalMove != null) {
//            result = horizontalMove;
//        } else if (verticalMove != null) {
//            result = verticalMove;
//        } else if (diagonalMove != null) {
//            result = diagonalMove;
//        } else {
//            // If no winning or blocking move is found choose a random empty cell
//            return findRandomEmptyCell(board);
//        }
//        String[] parts = result.split(" ");
//        int x = Integer.parseInt(parts[0]);
//        int y = Integer.parseInt(parts[1]);
//
//        return board.validateMove(this.getSymbol(), x, y);//Calls validateMove method from Board class to see if x and y values are valid move
//    }

    private State findRandomEmptyCell(Board board) {
        Random random = new Random();
        int x = random.nextInt(board.size());
        int y = random.nextInt(board.size());
        State state = board.evaluateMove(this, x, y);
        while (state.equals(State.CELL_UNAVAILABLE)) {
            x = random.nextInt(board.size());              // Gets new x
            y = random.nextInt(board.size());              // Gets new y
            state = board.evaluateMove(this, x, y);  // Tries new x and y
        }
        return state; // Stone placed
    }

    private String findWinningMoveDiagonal1(Board board, String stoneColor) {
        int boardSize = board.size();
        Player[][] cells = board.getCells();

        for (int row = 0; row < boardSize - 4; row++) {
            for (int col = 0; col < boardSize - 4; col++) {
                boolean isPotentialWinningMove = true;
                for (int k = 0; k < 5; k++) {
                    if (cells[row][col+k] == null || !cells[row][col+k].equals(this)) {
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
    private String findWinningMoveHorizontally(Board board, String stoneColor) {
        int boardSize = board.size();
        Player[][] cells = board.getCells();

        for (int row = 0; row < boardSize; row++) {
            for (int col = 0; col < boardSize - 4; col++) {
                boolean isPotentialWinningMove = true;
                for (int k = 0; k < 5; k++) {
                    if (cells[row][col+k] == null || !cells[row][col+k].equals(this)) {
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

    private String findWinningMoveVertically(Board board, String stoneColor) {
        int boardSize = board.size();
        Player[][] cells = board.getCells();

        for (int col = 0; col < boardSize; col++) {
            for (int row = 0; row < boardSize - 4; row++) {
                boolean isPotentialWinningMove = true;
                for (int k = 0; k < 5; k++) {
                    if (cells[row][col+k] == null || !cells[row][col+k].equals(this)) {
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
