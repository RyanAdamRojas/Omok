public enum StoneColor {
    RED("R"),
    BLUE("B"),
    GREEN("G"),
    WHITE("W");

    private final String symbol;

    StoneColor(String symbol) {
        this.symbol = symbol;
    }

    public String getSymbol() {
        return symbol;
    }
}
