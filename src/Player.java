// Authors: Ryan Adam Rojas, Sophia Montenegro
import java.io.IOException;
import java.io.PrintStream;
import java.util.Objects;
import java.util.Scanner;

public abstract class Player {
    private final String name;
    private StoneColor stoneColor;

    Player(){
        name = null;
        stoneColor = null;
    }

    Player(String name, StoneColor stoneColor){
        this.name = name;
        this.stoneColor = stoneColor;
    }

    //Every abstract class must have at least one abstract method
    public abstract String requestMove(Board board, Scanner scanner, PrintStream printStream) throws IOException;

    //Getters & Setter
    public String getName() {
        return name;
    }
    public StoneColor getStoneColor() {
        return this.stoneColor;
    }

    public void setStoneColor(StoneColor stoneColor) {
        this.stoneColor = stoneColor;
    }

    @Override
    public boolean equals(Object other) {
        //Intellij automatically creates an override for equals
        if (this == other)
            return true;
        if (!(other instanceof Player player))
            return false;
        return Objects.equals(getName(), player.getName()) && Objects.equals(getStoneColor(), player.getStoneColor());
    }

    @Override
    public int hashCode() {
        //Intellij automatically creates an override for hashCode
        return Objects.hash(getName(), getStoneColor());
    }
}
