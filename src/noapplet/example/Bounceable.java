package noapplet.example;

import java.awt.*;

public interface Bounceable {
    default void draw(Graphics brush){}
    default void move(Dimension dimension){}
    default void changeDirections(){}

    default void collisionDetection(Bounceable[] balls){}
    default void exchangeMomentum(Bounceable otherBall, int distance){}
    default int triangulateDistance(int x1, int x2, int y1, int y2){
        return -1;
    }
    default int triangulateAngle(int x1, int x2, int y1, int y2){
        return -1;
    }
    default int getXPosition(){
        return -1;
    }
    default int getYPosition(){
        return -1;
    }
    default int getDX(){
        return -1;
    }
    default int getDY(){
        return -1;
    }
    default int getRadius(){
        return -1;
    }

}
