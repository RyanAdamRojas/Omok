package noapplet.example;

public abstract class Player {
    private String name = "Name not set";
    private String symbol = null;

    Player(String name, String symbol){
        this.name = name;
        this.symbol = symbol;
    }

    public boolean requestMove(int x, int y, Board board){
        return board.requestMove(x, y, this.symbol); // FIXME parameter "board" may not work because its static
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getSymbol() {
        return symbol;
    }

    @Override
    public String toString(){
        return "Player: " + this.getName() + " as " + this.getSymbol();
    }
}
