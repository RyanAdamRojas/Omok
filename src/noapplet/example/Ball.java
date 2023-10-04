package noapplet.example;

import java.awt.*;
import java.util.Random;

public class Ball implements Bounceable {

    private int size;
    private int dx, dy;
    private int xPosition, yPosition;
    private int radius;
    private Color color = Color.LIGHT_GRAY; // Note: Light Gray ball indicates error

    // Change for giggles
    private final int MIN_BALL_SIZE = 20;
    private final int MAX_BALL_SIZE = 50;
    private final double CLIP_TOLLERANCE_PERCENTAGE = 0.8;
    private final Color[] PALETTE = {
            Color.RED,
            Color.ORANGE,
            Color.YELLOW,
            Color.GREEN,
            Color.CYAN,
            Color.BLUE,
            Color.MAGENTA,
            Color.PINK,
            Color.DARK_GRAY,
            Color.WHITE,
    };

    Ball(){
        // Random size
        Random random = new Random();
        this.size = random.nextInt(MAX_BALL_SIZE - MIN_BALL_SIZE + 1) + MIN_BALL_SIZE;
        this.dx = (random.nextBoolean() ? 1 : -1) * (100 / size); // Randomly positive or negative
        this.dy = (random.nextBoolean() ? 1 : -1) * (100 / size); // Randomly positive or negative
        this.radius = (this.size/2);

        // Random position within dimension
        this.xPosition = random.nextInt(50, 350);
        this.yPosition = random.nextInt(50, 350);

        // Random color
        this.color = PALETTE[random.nextInt(0, PALETTE.length)];

        // FIXME DELETE ME
        System.out.println(this.toString());
    }

    @Override
    public void draw(Graphics brush){
        brush.setColor(this.color);
        brush.fillOval(this.xPosition, this.yPosition, this.size, this.size);
    }

    @Override
    public void move(Dimension dimension){
        // Wall collision
        if (this.xPosition < this.radius || this.xPosition > dimension.width - this.radius) {
            this.dx = -this.dx; // sign inversion = direction inversion
        }
        if (this.yPosition < this.radius || this.yPosition > dimension.height - this.radius) {
            this.dy = -this.dy; // sign inversion = direction inversion
        }
        this.xPosition += this.dx; // nudge along x-axis
        this.yPosition += this.dy; // nudge along y-axis
    }

    @Override
    public void collisionDetection(Bounceable[] balls){
        // To Clip is to be inside another object. A little clipping is tolerable.
        for (Bounceable otherBall: balls) {
            // Calculating distance between the origin of each ball FIXME Calculate from center, NOT origin
            int distance = triangulateDistance(this.xPosition, otherBall.getXPosition(), this.yPosition, otherBall.getYPosition());
            int minDistance = (this.radius + otherBall.getRadius()) / 2;
            int maxClipDepth = (int) (minDistance * CLIP_TOLLERANCE_PERCENTAGE); // FIXME DELETE ME
            if(distance < minDistance && distance > maxClipDepth)
                exchangeMomentum(otherBall, distance); // Collision made
        }
    }

    @Override
    public void exchangeMomentum(Bounceable otherBall, int distance){
        // Sum of the vector components, Momentum of A on B and B on A.
        // Angle needed as dx might be more than dy and objects act at an angle
        int angle = triangulateAngle(this.xPosition, otherBall.getXPosition(), this.yPosition, otherBall.getYPosition());
        this.dx = this.dx + otherBall.getDX();
        this.dy = this.dy + otherBall.getDY();
        System.out.println("Momentum has exchanged!");
    }

    @Override
    public int triangulateDistance(int x1, int x2, int y1, int y2){
        // √(dx** + dy**) FIXME ? Double casted as Int
        return (int) Math.sqrt(Math.pow(Math.abs(x1 - x2), 2) + Math.pow(Math.abs(y1 - y2), 2));
    }

    @Override
    public int triangulateAngle(int x1, int x2, int y1, int y2){
        // theta = arc-tan(opp/adj)
        return (int) Math.atan((double) (x2 - x1) / (y2 - y1));
    }

    @Override
    public int getXPosition() {
        return xPosition;
    }

    @Override
    public int getYPosition() {
        return yPosition;
    }

    @Override
    public int getDX(){
        return dx;
    }

    @Override
    public int getDY(){
        return dy;
    }

    @Override
    public int getRadius(){
        return radius;
    }

    // DEBUG ZONE!!!
    @Override
    public String toString() {
        return "size: " + size +
                "radius: " + radius +
                ", dx: " + dx +
                ", dy: " + dy +
                ", xPosition: " + xPosition +
                ", yPosition: " + yPosition +
                ", color: " + color;
    }
}

