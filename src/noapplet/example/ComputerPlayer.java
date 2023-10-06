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
        String randomName = names[random.nextInt(10)];
        this.name = randomName;
        this.symbol = "Symbol not set";
    }
    ComputerPlayer(String symbol) {
        // Design choice: Name is automatically set
        Random random = new Random();
        String randomName = names[random.nextInt(10)];
        this.name = randomName;
        this.symbol = symbol;
    }

    @Override
    public String requestMove(Board board){
        int[] coordinates = makeSmartMove(this, board);
        return board.validateMove(this, coordinates[0], coordinates[1]);
    }

    private int[] makeSmartMove(Player player, Board board){
        // FIXME Makes random move. Not smart move.
        Random random = new Random();
        int x = random.nextInt(board.getSize());
        int y = random.nextInt(board.getSize());
        String validate = board.validateMove(this, x, y);
//        Possible
//        "GAME_DRAW":
//        "PLAYER_WIN"
//        "STONE_PLACED":
//        "NOT_AVAILABLE":
        while (validate.equals("NOT_AVAILABLE")) {
            x = random.nextInt(board.getSize());
            y = random.nextInt(board.getSize());
            validate = board.validateMove(this, x, y);
        }

        return new int[] {x, y};
    }
}
