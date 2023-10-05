package noapplet.example;

import java.util.Scanner;

public class MainOmok {
    public MainOmok(){
        Scanner scan = new Scanner(System.in);
        System.out.println("WELCOME TO OMOK!");
        System.out.println("PLEASE SELECT A GAME MODE [P] FOR PLAYER VS PLAYER AND [C] FOR PLAYER VS COMPUTER:");
        String choice = scan.nextLine();

        // Example Code:
        // Game game = new Game("P", 15);

    }
    public static void main(String[] args) {
        new MainOmok();
    }
}
