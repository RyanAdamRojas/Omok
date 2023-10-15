import java.util.Random;
import java.util.Scanner;

public class ConsoleUI {
    private Player player1, player2, currentPlayer;
    private Board board;
    public ConsoleUI(){
        Scanner scan = new Scanner(System.in);
        //Basic output to retrieve user's input
        System.out.println("WELCOME TO OMOK!");
        System.out.println("PLEASE SELECT A GAME MODE [P] FOR PLAYER VS PLAYER AND [C] FOR PLAYER VS COMPUTER. INVALID KEY WILL BE DEFAULT TO PLAYER VS COMPUTER:");
        String choice = scan.nextLine();
        System.out.println("ENTER BOARD SIZE.  MUST BE 15 OR GREATER. IF NOT THE DEFAULT WILL BE SET TO 15:");
        int size = scan.nextInt();
        //Creates Omok game
        board = new Board(size);
        //Sets Player 1
        player1 = new HumanPlayer("Player1", "●");
        //Sets Player 2 depending on user input
        if (choice.equals("P")) {
            System.out.println("STARTING PLAYER VS PLAYER OMOK GAME ---------------------------------------");
            player2 = new HumanPlayer("Player2", "■");
        }
        else{
            System.out.println("STARTING PLAYER VS COMPUTER OMOK GAME ---------------------------------------");
            player2 = new ComputerPlayer("Computer", "X");
        }
        GUI displayBoard = new GUI(size); //Creates display board
        displayBoard.createBoard();
        setCurrentPlayer();
        //The game will start and continue unless there is a winner, draw, or the user wishes to exit
        while(true){
            displayBoard.drawBoard(); //Will print board
            String result = currentPlayer.requestMove(board);//Asks for the current players move

            switch(result){//The following is some conditions that might happen
                case "PLAYER_WIN":
                    player1.setSymbol("★");
                    player2.setSymbol("★");
                    displayBoard.drawStone(player1.getSymbol(), player2.getSymbol(), board);//needs parameters //needs to be fixed
                    displayBoard.drawBoard(); //Will print board
                    System.out.println(currentPlayer.getName() + " WINS!");
                    return; //Game ends
                case "BOARD_FULL":
                    System.out.println("DRAW!");
                    return;//Game ends
                case "STONE_PLACED":
                    displayBoard.drawStone(player1.getSymbol(), player2.getSymbol(), board);//needs parameters //needs to be fixed
                    System.out.println("STONE PLACED FOR " + currentPlayer.getName());
                    swapCurrentPlayer();
                    break; //next player's turn
                case "CELL_UNAVAILABLE":
                    System.out.println("INVALID. TRY AGAIN");
                    break;//turn repeats
                case "EXIT":
                    System.out.println("GAME EXITING...");
                    return;//End game
            }

        }
    }
    public void setCurrentPlayer(){
        //Randomly selects the first player
        Random coinToss = new Random();
        if(coinToss.nextBoolean()) currentPlayer = player1;
        else currentPlayer = player2;
    }
    public void swapCurrentPlayer(){
         // Swaps players
        if (currentPlayer.equals(player1))
            currentPlayer = player2;
        else currentPlayer = player1;
    }
    public static void main(String[] args) {
        new ConsoleUI();
    }
}
