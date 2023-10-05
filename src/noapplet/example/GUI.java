package noapplet.example;

public class GUI {
    private int width, height;
    private String[][] board;
    public GUI(){
        width = 15;
        height = 15;
        board = new String[width+1][height+1];
    }
    public void createBoard(){
        //WARNING: the following code is ugly I shall fix it some point -Sophia :)
        System.out.println("GAME BOARD");

        board[0][0] = " ";

        String intersection = "╋";
        String leftEdge = "┣";
        String rightEdge = "┫";
        String bottomEdge = "┻";
        String bottomLeftC = "┗";
        String bottomRightC = "┛";
        String topRightC = "┓";
        String topLeftC = "┏";
        String topEdge = "┳";

        //The following fills the coordinate values for the user to understand
        for(int i = 1; i < height+1; i++){
            board[0][i] = String.valueOf(i);
            board[i][0] = String.valueOf(i);
            if(i < 10){
                board[i][0] += " ";
            }
        }

        //The following fills the corners
        board[1][1] = topLeftC;
        board[1][width] = topRightC;
        board[height][1] = bottomLeftC;
        board[height][width] = bottomRightC;

        //The following fills the edges
        for (int i = 2; i < height; i++) {
            board[i][1] = leftEdge;
            board[i][width] = rightEdge;
            board[1][i] = topEdge;
            board[width][i] = bottomEdge;
        }
        //The following should fill
        for (int i = 2; i < height; i++){
            for (int j = 2; j < width; j++){
                board[i][j] = intersection;
            }
        }
    }
    public void drawStone(Player player, int x, int y){
        //The following method will update stones
    }

    public void drawBoard(){
        //The following method will print the board
        for (int i = 0; i < width+1; i++) {
            for (int j = 0; j < height+1; j++) {
                System.out.print(board[i][j] + " ");
            }
            System.out.println(); // Move to the next row
        }
    }
    public static void main(String[] args) {
        GUI test = new GUI();
        test.createBoard();
        test.drawBoard();

    }

}
