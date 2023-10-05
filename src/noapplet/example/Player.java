package noapplet.example;

public abstract class Player {
    private String name = "Name not set";

    Player(){
        this.name = "Name not set";
    }

    Player(String name){
        this.name = name;
    }

    public void requestMove(int x, int y){
        //Board.requestMove(this); // What happens with 'this'?
    }
}
