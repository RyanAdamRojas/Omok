package noapplet.example;

public class Board {
    private char[][] board;
    private int size = 15;
    private int activeStoneCount = 0;
    private int maxStoneCount = -1;
    private boolean isFull = false;
    private boolean cheatsActive = false;

    Board(){
        this.maxStoneCount = this.size * this.size;
    }
    Board(int size){
        if(size >= 15 && size <= 100) {
            this.size = size;
            this.maxStoneCount = this.size * this.size;
            this.board = new char[this.size][this.size];
        }
        else{
            this.maxStoneCount = this.size * this.size;
        }
    }
}
