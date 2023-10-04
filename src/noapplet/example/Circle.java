package noapplet.example;

import java.awt.*;

public class Circle extends Shape{

    Circle(int x, int y, int size, Color color) {
        super(x, y, size, color);
    }

    public void draw(Graphics brush){
        brush.setColor(this.color);
        brush.fillOval(this.x, this.y, this.size, this.size);
    }
}
