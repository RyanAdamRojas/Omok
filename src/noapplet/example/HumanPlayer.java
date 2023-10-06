// Authors: Ryan Adam Rojas, Sophia Montenegro

package noapplet.example;

import java.util.Scanner;

public class HumanPlayer extends Player {

    HumanPlayer(String name, String symbol) {
        super(name, symbol);
    }
    @Override
    public String requestMove(Board board){
        Scanner read = new Scanner(System.in);
        System.out.println(this.getName() + ". Please input x and y.");
        int x = read.nextInt();
        int y = read.nextInt();
        return board.validateMove(this, x, y);
    }
}
