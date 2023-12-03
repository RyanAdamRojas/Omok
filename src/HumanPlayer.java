// Authors: Ryan Adam Rojas, Sophia Montenegro
import java.io.IOException;
import java.io.PrintStream;
import java.util.Scanner;

public class HumanPlayer extends Player {

    HumanPlayer() {
        this("Name not set", StoneColor.BLUE);
    }
    HumanPlayer(String name, StoneColor stoneColor) {
        super(name, stoneColor, false);
    }

    @Override
    public State requestMove(Board board, Scanner scanner, PrintStream printStream) throws IOException {
        // Loops until a valid x-y coordinate is entered
        String userInput;
        while (true) {
            userInput = scanner.nextLine();//Takes in user input
            if (userInput.equals("STOP")){//To quit the game
                return State.EXIT_REQUEST;
            }

            // Handles non-integer or non-coordinate exceptions
            String[] parts = userInput.split(" ");
            if (parts.length != 2) { //Checks if input has only 2 integers
                System.out.println("INVALID INPUT");
                continue; // Continue the loop to prompt for input again
            }

            try {
                int x = Integer.parseInt(parts[0]);
                int y = Integer.parseInt(parts[1]);
                return board.evaluateMove(this, x, y);//Calls validateMove method from Board class to see if x and y values are valid move
            }
            catch (NumberFormatException e) {
                System.out.println("INVALID SELECTION! ENTER NUMERICAL VALUES.");
            }
        }
    }

}
