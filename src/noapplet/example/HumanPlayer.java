// Authors: Ryan Adam Rojas, Sophia Montenegro

package noapplet.example;

import java.util.Scanner;

import static java.lang.Integer.valueOf;

public class HumanPlayer extends Player {

    HumanPlayer(String name, String symbol) {
        super(name, symbol);
    }
    @Override
    public String requestMove(Board board){
        Scanner read = new Scanner(System.in);
        String coordinate;

        while (true) {
            System.out.println(this.getName() + " INPUT X AND Y VALUES SEPARATED BY A SPACE OR ENTER [STOP] TO EXIT GAME. EX: 2 5");
            coordinate = read.nextLine();
            if (coordinate.equals("STOP")){
                return "EXIT";
            }

            String[] parts = coordinate.split(" ");

            if (parts.length != 2) {
                System.out.println("INVALID INPUT. PLACE SPACES.");
                continue; // Continue the loop to prompt for input again
            }

            try {
                int x = Integer.parseInt(parts[0]);
                int y = Integer.parseInt(parts[1]);

                return board.validateMove(this.getSymbol(), x, y);
            }
            catch (NumberFormatException e) {
                System.out.println("INVALID. ENTER NUMERICAL VALUES.");
            }
        }

        /*
        Scanner read = new Scanner(System.in);
        System.out.println(this.getName() + " INPUT X AND Y VALUES SEPERATED BY A SPACE. EX: 2 5");
        String coordinate = read.nextLine();
        int x = valueOf(coordinate.substring(0));//Takes the first number
        int y = valueOf(coordinate.substring(-1));//Takes the last number
        return board.validateMove(this.getSymbol(), x, y); // Returns String*/
    }

}
