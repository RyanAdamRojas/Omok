package noapplet.example;

public class GUI {
    private int width, height;
    private String[][] board;
    public GUI(){
        width = 15;
        height = 15;
        board = new String[(2 * height)+4][width + 3];
    }
    public GUI(int size){
        if(size >= 15) {
            this.width = size;
            this.height = size;
            this.board = new String[(2 * height)+4][width + 3];
        }
        else{
            this.width = 15;
            this.height = 15;
            this.board = new String[(2 * height)+4][width + 3];
        }
    }
    public void createBoard() {
        System.out.println("GAME BOARD:");
        //Creates numbers on the side for user
        board[0][0] = "  ";
        board[0][1] = "    ";
        board[1][0] = "  ";
        int num = 1;
        for(int i = 2; i < width + 2; i++){
            board[0][i] = String.valueOf(num) + "  ";
            num++;
            if (num < 10){
                board[0][i] += " ";
            }
        }
        board[0][width+2] = " ";
        num = 1;
        for(int i =  2; i < (2 * height) + 2; i++){
            if(i % 2 != 0){
                board[i][0] = String.valueOf(num);
                if(num < 10){
                    board[i][0] += " ";
                }
                num++;
            }
            else{
                board[i][0] = "  ";
            }
        }
        board[(2*height) + 2][0] = "  ";
        board[(2*height) + 3][0] = "  ";

        for (int i = 1; i < (2 * height)+4; i++) {
            for (int j = 1; j < width + 3; j++) {
                if(i % 2 != 0){
                    if (j == height+2){
                        board[i][j] = "+";
                    }
                    else {
                        board[i][j] = "+---";
                    }
                }
                else{
                    board[i][j] = "|   ";
                }
            }
        }
    }
    public void drawBoard(){
        //The following method will print the board
        for (int i = 0; i < (2*height)+4; i++) {
            for (int j = 0; j < width+3; j++) {
                System.out.print(board[i][j] + " ");
            }
            System.out.println(); // Move to the next row
        }
    }
    public void drawStone(int x, int y, String symbol){
        int xIndex = x + 1;
        int yIndex = y + 3;
        String replace = board[yIndex][xIndex].substring(0);
        replace = symbol;
        board[yIndex][xIndex] = replace + "---";
    }
    public void print(){
        System.out.println("{");
        for (int i = 0; i < board.length; i++) {
            System.out.print("{");
            for (int j = 0; j < board[i].length; j++) {
                System.out.print(board[i][j]);
                if (j < board[i].length - 1) {
                    System.out.print(", ");
                }
            }
            System.out.print("}");
            if (i < board.length - 1) {
                System.out.println(",");
            }
        }
        System.out.println("\n}");
    }
    public static void main(String[] args) {
        GUI test = new GUI(15);
        test.createBoard();
        test.drawBoard();
        test.drawStone(1,2, "X");
        test.drawStone(3,2, "0");
        test.drawBoard();
    }
}