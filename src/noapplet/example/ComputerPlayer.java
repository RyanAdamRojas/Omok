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
}
