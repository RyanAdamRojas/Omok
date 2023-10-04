package noapplet.example;
import javax.swing.*;
import java.awt.*;

public class AnimatePlanets extends AnimationNoApplet {
    int delay = 100; // Milliseconds

    static CelestialBody blackHole = new CelestialBody(null, Color.GRAY, 5, 0);
    static Star sun = new Star(blackHole, Color.YELLOW, 50, 45);
    static Planet earth = new Planet(sun, Color.CYAN, 15, 95);
    static Moon moon = new Moon(earth, Color.LIGHT_GRAY, 8, 332);
    static Moon satelite = new Moon(moon, Color.GREEN, 5, 12);

    public AnimatePlanets(){
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

        // Paints all bodies
        blackHole.draw(brush);
        blackHole.move();
        sun.draw(brush);
        sun.move();
        earth.draw(brush);
        earth.move();
        moon.draw(brush);
        moon.move();
        satelite.draw(brush);
        satelite.move();

        // Adds title to the top right corner
        brush.setColor(Color.WHITE);
        String text = "Ryan Rojas - The Solar System";
        int textX = 20;
        int textY = 40;
        brush.drawString(text, textX, textY);
    }

    public static void main(String[] args) {
        new AnimatePlanets().run();
    }
}
