public enum StoneColor {
    RED("R"),
    BLUE("B"),
    WHITE("W");

    private final String symbol;

    StoneColor(String symbol) {
        this.symbol = symbol;
    }

    public String getSymbol() {
        return symbol;
    }
}