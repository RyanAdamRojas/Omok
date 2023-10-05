package noapplet.example;
import java.awt.*;

public class AnimateBalls extends AnimationNoApplet {
    // Change size at will
    private static final Bounceable[] balls = new Bounceable[5];
    protected int delay = 1; // Overridden!!!!!!!

    public static void buildBalls(){
        for (int i = 0; i < balls.length; i++) {
            balls[i] = new Ball();
        }
    }

    public void initAnimation() {
        super.initAnimation();
    }

    protected void paintComponent(Graphics brush) {
        brush.setColor(Color.BLACK);
        brush.fillRect(0, 0, dim.width, dim.height);

        // Updating all balls in array
        for (Bounceable ball: balls) {
            ball.collisionDetection(balls);
            ball.move(dim);
            ball.draw(brush);
        }
        // Adds title to the top right corner
        brush.setColor(Color.WHITE);
        String text = "Ryan Rojas - Bouncing Balls";
        int textX = 20;
        int textY = 40;
        brush.drawString(text, textX, textY);

    }

    public static void main(String[] args) {
        buildBalls();
        new AnimateBalls().run();
    }
}

// :)
