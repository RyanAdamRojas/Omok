// Authors: Ryan Adam Rojas, Sophia Montenegro

package noapplet.example;
import java.util.Random;

public class ComputerPlayer extends Player {
    private String name;
    private String symbol;

    private String[] names = {
            "Colonel Kernel",
            "Dr. Directory",
            "Daemon",
            "Shell",
            "Driver",
            "Linux",
            "Unix",
            "Macintosh",
            "Windows"
    };

    ComputerPlayer(){
        Random random = new Random();
        this.name = names[random.nextInt(10)];
        this.symbol = "Symbol not set";
    }
    ComputerPlayer(String symbol) {
        // Design choice: Name is automatically set
        Random random = new Random();
        this.name = names[random.nextInt(10)];
        this.symbol = symbol;
    }

    @Override
    public String requestMove(Board board){
        return makeSmartMove(this, board);
    }

    private String makeSmartMove(Player player, Board board){
        // FIXME Makes random move, not a smart move.
        Random random = new Random();
        int x = random.nextInt(board.getSize());
        int y = random.nextInt(board.getSize());
        String validationMessage = board.validateMove(this, x, y);
//        Possible validation Messages
//        "GAME_DRAW":
//        "PLAYER_WIN"
//        "STONE_PLACED":
//        "NOT_AVAILABLE":
        while (validationMessage.equals("NOT_AVAILABLE")) {
            x = random.nextInt(board.getSize());              // Gets new x
            y = random.nextInt(board.getSize());              // Gets new y
            validationMessage = board.validateMove(this, x, y);  // Tries new x and y
        }
        return validationMessage; // Stone placed
    }
}
