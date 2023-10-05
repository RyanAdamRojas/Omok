package noapplet.example;

public class GUI {
    private int width, height;
    private char[][] board= new char[width][height];
    public GUI(){
        width = 15;
        height = 15;
    }
    public void drawBoard(){
        System.out.println("GAME BOARD");

        for (int i = 1; i < height; i++){
            for( int j = 1; j < width; j++){

            }
        }
    }
    public void drawStone(Player player, int x, int y){

    }
}
