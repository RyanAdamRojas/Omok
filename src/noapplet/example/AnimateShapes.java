package noapplet.example;

import javax.swing.*;
import java.awt.*;

public class AnimateShapes extends AnimationNoApplet {
    int delay = 100; // Milliseconds

    public AnimateShapes(){
        Timer timer = new Timer(delay, e -> {
            // More code?
            repaint();});
    }

    @Override
    protected void paintComponent(Graphics brush){
        // Call the superclass method
        super.paintComponent(brush);

        // Paints the background
        brush.setColor(Color.BLACK);
        brush.fillRect(0, 0, dim.width, dim.height);

        // Adds title to the top right corner
        brush.setColor(Color.WHITE);
        String text = "Ryan Rojas - Shapes";
        int textX = 20;
        int textY = 40;
        brush.drawString(text, textX, textY);

        // Shapes
        Circle clementine = new Circle(60, 60, 40, Color.CYAN);
        Rectangle rex = new Rectangle(90, 270, 40, Color.RED);
        Triangle tribecca = new Triangle(350, 120, 40, Color.GREEN);

        clementine.draw(brush);
        rex.draw(brush);
        tribecca.draw(brush);

    }

    public static void main(String[] args) {
        new AnimateShapes().run();
    }
}
