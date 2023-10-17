// Authors: Ryan Adam Rojas, Sophia Montenegro
import java.io.IOException;
import java.io.PrintStream;
import java.util.Scanner;

public class HumanPlayer extends Player {

    HumanPlayer(String name, String symbol) {
        super(name, symbol);
    }

    @Override
    public String requestMove(Board board, Scanner scanner, PrintStream printStream) throws IOException {
        String coordinate;

        while (true) {
            printStream.write((getSymbol() +
                    "     " +
                    getName() +
                    " INPUT X AND Y VALUES OR ENTER [STOP] TO EXIT GAME. EX: \"2 5\"").getBytes());
            coordinate = scanner.nextLine();//Takes in user input
            if (coordinate.equals("STOP")){//To quit the game
                return "EXIT";
            }

            String[] parts = coordinate.split(" ");

            if (parts.length != 2) {//Checks if input has only 2 integers
                System.out.println("INVALID INPUT");
                continue; // Continue the loop to prompt for input again
            }

            try {
                int x = Integer.parseInt(parts[0]);
                int y = Integer.parseInt(parts[1]);

                return board.validateMove(this.getSymbol(), x, y);//Calls validateMove method from Board class to see if x and y values are valid move
            }
            catch (NumberFormatException e) {
                System.out.println("INVALID. ENTER NUMERICAL VALUES.");
            }
        }
    }

}
