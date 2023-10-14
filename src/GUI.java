package noapplet.example;

public class GUI {
    private int width, height;
    private String[][] displayBoard;

    public GUI(){
        //Default constructor
        width = 15;
        height = 15;
        displayBoard = new String[(2 * height)+4][width + 3];
    }
    public GUI(int size){
        //Checks if the given size is 15 or greater than
        //If so uses the given size
        if(size >= 15) {
            this.width = size;
            this.height = size;
            this.displayBoard = new String[(2 * height)+4][width + 3];
        }
        //If not then sets the board to 15
        else{
            this.width = 15;
            this.height = 15;
            this.displayBoard = new String[(2 * height)+4][width + 3];
        }
    }

    //Should create empty Omok Board
    public void createBoard() {
        System.out.println("GAME BOARD:");
        //Creates numbers on the side for user
        displayBoard[0][0] = "  ";
        displayBoard[0][1] = "    ";
        displayBoard[1][0] = "  ";
        int num = 1;
        for(int i = 2; i < width + 2; i++){
            displayBoard[0][i] = String.valueOf(num) + "  ";
            num++;
            if (num < 10){
                displayBoard[0][i] += " ";
            }
        }
        displayBoard[0][width+2] = " ";
        num = 1;
        for(int i =  2; i < (2 * height) + 2; i++){
            if(i % 2 != 0){
                displayBoard[i][0] = String.valueOf(num);
                if(num < 10){
                    displayBoard[i][0] += " ";
                }
                num++;
            }
            else{
                displayBoard[i][0] = "  ";
            }
        }
        displayBoard[(2*height) + 2][0] = "  ";
        displayBoard[(2*height) + 3][0] = "  ";

        //Creates table/board
        for (int i = 1; i < (2 * height)+4; i++) {
            for (int j = 1; j < width + 3; j++) {
                if(i % 2 != 0){
                    if (j == height+2){
                        displayBoard[i][j] = "+";
                    }
                    else {
                        displayBoard[i][j] = "+---";
                    }
                }
                else{
                    displayBoard[i][j] = "|   ";
                }
            }
        }
    }
    public void drawBoard(){
        //The following method will print the board
        for (int i = 0; i < (2*height)+4; i++) {
            for (int j = 0; j < width+3; j++) {
                System.out.print(displayBoard[i][j] + " ");
            }
            System.out.println(); // Move to the next row
        }
    }
    public void drawStone(String player1Symbol, String player2Symbol, Board board){
        String[][] cells = board.getCells();
        int rowIndex = 3;//Starts at index 3

        for(int row = 0; row < cells.length; row++){//Adds player1's stones
            for(int col = 0; col < cells[row].length; col++){
                String currentCell = cells[row][col];

                if(currentCell != null && currentCell.equals(player1Symbol)){
                    int displayRow = rowIndex;
                    int displayCol = col + 2;
                    displayBoard[displayRow][displayCol] = player1Symbol + "---";
                }

                if(currentCell != null && currentCell.equals(player2Symbol)){//Adds player2's stones
                    int displayRow = rowIndex;
                    int displayCol = col + 2;
                    displayBoard[displayRow][displayCol] = player2Symbol + "---";
                }
            }
            rowIndex += 2; //Needs to skip a row
        }

    }
}