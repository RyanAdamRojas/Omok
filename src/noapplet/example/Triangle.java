package noapplet.example;

import java.awt.*;

public class Triangle extends Shape {
    Triangle(int x, int y, int size, Color color) {
        super(x, y, size, color);
    }

    public void draw(Graphics brush){
        brush.setColor(this.color);

        int[] xPoints = {
                x,               // x-coordinate of the top
                x - (size / 2),  // x-coordinate of the bottom-left
                x + (size / 2)   // x-coordinate of the bottom-right
        };

        int[] yPoints = {
                y,               // y-coordinate of the top
                y + size,        // y-coordinate of the bottom-left
                y + size         // y-coordinate of the bottom-right
        };

        brush.fillPolygon(xPoints, yPoints, 3);
    }
}
