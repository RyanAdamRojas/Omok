import java.awt.*;
import java.util.Random;

public class Stone {

    public int mass;
    public double dx, dy;
    public int x, y;
    public int radius;
    public StoneColor color;
    public final double CLIP_TOLLERANCE_PERCENTAGE = 0.8;
    public final Color[] blueStonePalette = {
            new Color(  0,  10, 212 ),
            new Color( 63, 163, 255),
            new Color(255, 255, 255)
    };
    private final Color[] redStonePalette = {
            new Color(150, 0, 0),
            new Color(255, 100, 100),
            new Color(255, 255, 255)
    };
    private final Color[] greenStonePalette = {
            new Color(0, 114, 0),
            new Color(84, 208, 84),
            new Color(218, 255, 218)
    };
    private final Color[] whiteStonePalette = {
            new Color(162, 162, 162),
            new Color(232, 232, 232),
            new Color(255, 255, 255)
    };

    Stone(int mass, int v){
        v = 2;
        // Random mass
        Random random = new Random();
        this.mass = mass;
        // Randomly positive or negative speed
        this.dx = random.nextBoolean() ? random.nextInt(0, v) : random.nextInt(0, v) - v;
        this.dy = random.nextBoolean() ? random.nextInt(0, v) : random.nextInt(0, v) - v;
        this.radius = (this.mass / 2);

        // Random position within dimension
        this.x = random.nextInt(50, 500);
        this.y = random.nextInt(50, 700);

        // Random color
        StoneColor[] colors = {StoneColor.GREEN, StoneColor.RED, StoneColor.BLUE, StoneColor.WHITE};
        this.color = colors[random.nextInt(0,colors.length)];
    }
    
    public void draw(Graphics brush){
        // Assigns the appropriate pallet based on stones color
        Color[] pallet = whiteStonePalette;
        switch (color) {
            case BLUE -> pallet = blueStonePalette;
            case RED -> pallet = redStonePalette;
            case GREEN -> pallet = greenStonePalette;
        }

        // Artfully paints the dark bottom layer of stone
        int stoneDiameter = mass;
        int xOffset = x - radius;
        int yOffset = y - radius;
        brush.setColor(pallet[0]);
        brush.fillOval(xOffset, yOffset, mass, mass);

        // Artfully paints the colorful middle layer of stone
        int middleLayerOffset = stoneDiameter / 10;
        int middleLayerDiameter = stoneDiameter - middleLayerOffset;
        brush.setColor(pallet[1]);
        brush.fillOval(xOffset + middleLayerOffset, yOffset, middleLayerDiameter, middleLayerDiameter);

        // Artfully paints the 'shinny' top layer of stone
        int topLayerWidth = stoneDiameter / 12;         // Top layer is thin oval 1/12th the width of stone
        int topLayerHeight = stoneDiameter / 6;         // Top layer is tall oval 1/6th the height of stone
        int topLayerXOffset = (4 * stoneDiameter) / 5;  // Top layer is to the right 4/5th from left of stone
        int topLayerYOffset = stoneDiameter / 5;        // Top layer is above midline 1/5th from top of stone
        brush.setColor(pallet[2]);
        brush.fillOval(xOffset + topLayerXOffset, yOffset + topLayerYOffset, topLayerWidth, topLayerHeight);
    }
    
    public void move(Dimension dimension){
        // Wall collision
        if (this.x < this.radius || this.x > dimension.width - this.radius)
            this.dx = -this.dx; // sign inversion = direction inversion
        if (this.y < this.radius || this.y > dimension.height - this.radius)
            this.dy = -this.dy; // sign inversion = direction inversion
        this.x += this.dx; // nudge along x-axis
        this.y += this.dy; // nudge along y-axis
    }
    
    public void collisionDetection(Stone[] stones){
        for (Stone other: stones) {
            // Calculating distance between the origin of each ball FIXME Calculate from center, NOT origin
            int distance = triangulateDistance(this.x, other.x, this.y, other.y);
            int minDistance = this.radius + other.radius;
            if (distance == minDistance) { // Contact
                exchangeMomentum(other); // Collision made
            }
        }
    }

    public void exchangeMomentum(Stone other) {
        // Calculate the vector from this stone to the other stone
        double deltaX = other.x - this.x;
        double deltaY = other.y - this.y;

        // Calculate the distance between stones
        double distance = Math.hypot(deltaX, deltaY);

        // Check if the balls are overlapping to prevent sticking
        if (distance < this.radius + other.radius) {
            // Normalize the vector (to get the direction of the collision axis)
            double nx = deltaX / distance;
            double ny = deltaY / distance;

            // Calculate the relative velocity of the stones
            double dvx = other.dx - this.dx;
            double dvy = other.dy - this.dy;

            // Calculate the velocity along the normal (dot product)
            double velocityAlongNormal = dvx * nx + dvy * ny;

            // Do not resolve collision if velocities are separating
            if (velocityAlongNormal > 0) return;

            // Calculate restitution coefficient (1 for perfectly elastic)
            double restitution = 1.0;

            // Calculate impulse scalar
            double impulse = (1 + restitution) * velocityAlongNormal / (1 / this.mass + 1 / other.mass);

            // Apply impulse to the stones' velocity components along the normal direction
            double impulseX = impulse * nx;
            double impulseY = impulse * ny;

            this.dx -= impulseX / this.mass;
            this.dy -= impulseY / this.mass;
            other.dx += impulseX / other.mass;
            other.dy += impulseY / other.mass;

            // Separate the stones to prevent sticking
            double overlap = (this.radius + other.radius - distance) / 2;
            this.x -= overlap * nx;
            this.y -= overlap * ny;
            other.x += overlap * nx;
            other.y += overlap * ny;
        }
    }



    public int triangulateDistance(int x1, int x2, int y1, int y2){
        // sqrt(dx**2 + dy**2) FIXME ? Double casted as Int
        return (int) Math.sqrt(Math.pow(Math.abs(x1 - x2), 2) + Math.pow(Math.abs(y1 - y2), 2));
    }
    
    public int triangulateAngle(int x1, int x2, int y1, int y2){
        // theta = arc-tan(opp/adj)
        return (int) Math.atan((double) (x2 - x1) / (y2 - y1));
    }

    // DEBUG ZONE!!!
    public String toString() {
        return "mass: " + mass +
                "radius: " + radius +
                ", dx: " + dx +
                ", dy: " + dy +
                ", x: " + x +
                ", y: " + y +
                ", color: " + color;
    }
}

