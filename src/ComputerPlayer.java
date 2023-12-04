// Authors: Ryan Adam Rojas, Sophia Montenegro

import java.io.IOException;
import java.io.PrintStream;
import java.util.Random;
import java.util.Scanner;

public class ComputerPlayer extends Player {


    ComputerPlayer() {
        Random random = new Random();
        String[] names = {
                "Kernel",
                "Directory",
                "Daemon",
                "Shell",
                "Driver",
                "Linux",
                "Unix",
                "Macintosh",
                "Windows",
                "Byte",
                "Pixel",
                "Cache",
                "Widget",
                "Gadget",
                "Router",
                "Compiler",
                "Cyborg",
                "Digital",
                "Ethernet",
                "Firewall",
                "Gateway",
                "Hyperlink",
                "Intranet",
                "Java",
                "Kilobyte",
                "Logic",
                "Megabyte",
                "Neural",
                "Opcode",
                "Protocol",
                "Quantum",
                "RAM",
                "Syntax",
                "Terabyte",
                "Uplink",
                "Virtual",
                "Webmaster",
                "Xenon",
                "Yottabyte",
                "Zettabyte"
        };
        this.setName(names[random.nextInt(0, names.length)]);
        this.setStoneColor(StoneColor.RED);
        this.setIsComputer(true);
    }

    ComputerPlayer(String name, StoneColor stoneColor) {
        super("ChatGPT", stoneColor, true);
    }

    public State makeSmartMove(Board board) {
        Random random = new Random();
        int x = random.nextInt(board.size());
        int y = random.nextInt(board.size());
        State state = board.evaluateMove(this, x, y);

        // Loops until an x y is available
        while (state.equals(State.CELL_UNAVAILABLE)) {
            x = random.nextInt(board.size());              // Gets new x
            y = random.nextInt(board.size());              // Gets new y
            state = board.evaluateMove(this, x, y);  // Tries new x and y
        }
        return state; // Stone placed
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
            state = board.evaluateMove(this, x, y);  // Tries new x and y
        }
        return state; // Stone placed
    }

    public State placeRandomEmptyCell(Board board) {
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
}