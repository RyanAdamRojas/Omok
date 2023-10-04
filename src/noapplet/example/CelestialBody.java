package noapplet.example;
import java.awt.*;

public class CelestialBody {

    CelestialBody parent; // this with null parent is the center of the solar system.
    Color color;
    double size;
    double positionX;
    double positionY;
    double angle;

    CelestialBody(CelestialBody parent, Color color, double size, double angle){
        this.parent = parent;
        this.color = color;
        this.size = size;
        this.angle = angle;
        if (parent != null) {
            this.move();
        } else {
            // HARDCODED: Body without parent is the center of solar system without an orbit
            this.positionX = 200;
            this.positionY = 200;
        }
    }

    public void draw(Graphics brush){
        int visualOffsetX = (int) (this.positionX - this.size/2); // Offset left
        int visualOffsetY = (int) (this.positionY - this.size/2); // Offset up
        brush.setColor(this.color);
        brush.fillOval(visualOffsetX, visualOffsetY, (int) this.size, (int) this.size);
    }

    public void move(){
        // Base case
        if (this.parent == null) return;

        // Inspiration: hypotenuse cos(0) = adjacent
        this.positionX = (int) this.parent.positionX + 3 * this.parent.size * Math.cos(this.angle);
        this.positionY = (int) this.parent.positionY + 3 * this.parent.size * Math.sin(this.angle);
        double speed =  0.3 / size; // Inspiration: acceleration = force / mass
        angle += speed;
        if (angle > 360)
            angle = 0;
    }
}