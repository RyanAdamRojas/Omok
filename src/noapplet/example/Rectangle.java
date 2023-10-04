package noapplet.example;

import java.awt.*;

public class Rectangle extends Shape {

    Rectangle(int x, int y, int size, Color color) {
        super(x, y, size, color);
    }

    public void draw(Graphics brush){
        brush.setColor(this.color);
        brush.drawRect(this.x, this.y, this.size, this.size);
    }
}
