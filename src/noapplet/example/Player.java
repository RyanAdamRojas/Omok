package noapplet.example;

public abstract class Player {
    private String name;
    private String symbol;

    Player(){
        this.name = "Name not set";
        this.symbol = "Symbol not set";
    }

    Player(String name, String symbol){
        this.name = name;
        this.symbol = symbol;
    }

    public String requestMove(Board board, int x, int y){
        return board.validateMove(this, x, y);
//        USE THIS IN MAIN:
//        String result = player.requestMove(board, 5, 15);
//        switch(result){
//            case "GAME_DRAW":
//            case "PLAYER_WIN":
//            case "STONE_PLACED":
//            case "NOT_AVAILABLE":
//        }
    }

    // Boilerplate Below: Setters and Getters
    public void setName(String name) {
        this.name = name;
    }
    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }
    public String getName() {
        return name;
    }
    public String getSymbol() {
        return symbol;
    }
    @Override
    public String toString(){
        return "Player: " + this.getName() + " as " + this.getSymbol();
    }
}
