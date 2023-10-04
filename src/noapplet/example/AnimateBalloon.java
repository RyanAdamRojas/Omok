package noapplet.example;
import java.awt.*;
import javax.swing.Timer;
import java.awt.Color;

/**
 * Ryans extention on Professor Cheons NoApplet.
 * <p/>
 * <pre>
 *HELLO WORLD!!!
 * </pre>
 *
 * @author Ryan Rojas
 */
public class AnimateBalloon extends noapplet.NoApplet {
    protected Dimension dim;
    protected Timer timer; // Animation timer

    protected int diameter = 1;
    protected int delay = 100; // Milliseconds
    protected boolean isGrowing = true;

    public AnimateBalloon(){
        dim = getSize();
        timer = new Timer(delay, e -> { // Events occur every 100ms
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
    public void start(){ timer.start();}

    public void stop(){timer.stop();}

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
        g.setColor(Color.GREEN);
        g.fillOval(x, y, diameter, diameter);
    }

    public static void main(String[] args) {
        new AnimateBalloon().run();
    }
}


