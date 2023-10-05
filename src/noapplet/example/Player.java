package noapplet.example;

public abstract class Player {
    private String name = "Name not set";
    private String symbol = null;

    Player(String name, String symbol){
        this.name = name;
        this.symbol = symbol;
    }

    public boolean requestMove(int x, int y, Board board){
        return board.requestMove(x, y, this.symbol); // FIXME insufficient parameter board
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }
}
