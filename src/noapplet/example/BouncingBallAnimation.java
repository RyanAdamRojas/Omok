package noapplet.example;

import java.awt.*;

/**
 * Sample use of the AnimationNoApplet class to bounce a ball.
 * Need to override only two methods: initAnimation() and paint().
 */
@SuppressWarnings("serial")
class BouncingBallAnimation extends AnimationNoApplet {

    private Color color = Color.GREEN;
    private int radius = 20;
    private int x, y;
    private int dx = -2, dy = -4;

    public BouncingBallAnimation(String[] args) {
        super(args);
    }

    @Override
    protected void initAnimation() {
        x = dim.width * 2 / 3;
        y = dim.height - radius;
    }

    /**
     * Display the current time.
     */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        // fill the background
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, dim.width, dim.height);

        // adjust the position of the ball
        if (x < radius || x > dim.width - radius) {
            dx = -dx;
        }
        if (y < radius || y > dim.height - radius) {
            dy = -dy;
        }
        x += dx;
        y += dy;

        // draw the ball and dump the off-screen image
        g.setColor(color);
        g.fillOval(x - radius, y - radius, radius * 2, radius * 2);
    }

}
