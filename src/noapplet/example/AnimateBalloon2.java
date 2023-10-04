package noapplet.example;

import javax.swing.*;
import java.awt.*;

public class AnimateBalloon2 extends AnimateBalloon {
    protected int delay = 20;

    public AnimateBalloon2(){
        dim = getSize();
        timer = new Timer(delay, e -> { // Events occur every 20ms
            if (isGrowing) {
                if (diameter < dim.width) { diameter += 5; } // Growth of balloon
                else { isGrowing = false; }
            }
            else {
                if (diameter > 10) { diameter -= 5; } // Shrinkage of balloon
                else { isGrowing = true; }
            }
            repaint(); // Paints current state of balloon
        });
    }

    @Override
    protected void paintComponent(Graphics g){
        // Call the superclass method
        super.paintComponent(g);

        // Gets the size of the window
        dim = getSize();

        // Calculates the center of window to place balloon
        int x = (dim.width - diameter) / 2;
        int y = (dim.height - diameter) / 2;

        // Creating balloon
        g.setColor(Color.BLUE);
        g.fillOval(x, y, diameter, diameter);
    }

    public static void main(String[] args) {
        new AnimateBalloon2().run();
    }
}
