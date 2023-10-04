package noapplet.example;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;

import noapplet.NoApplet;

/**
 * Simple NoAppet app to draw a text and an image. The displayed image
 * is obtained from the file <code>res/rabbit.jpg</code>, where
 * <code>res</code> is the resource directory of your Java project.
 * Refer to your IDE to designate the resource directory for your project.
 */
@SuppressWarnings("serial")
public class HelloWorld extends NoApplet {

	public HelloWorld() {
	}

	public HelloWorld(String[] params) {
		super(params);
	}
	
    protected void paintComponent(Graphics g) {
        Dimension d = getSize();
        g.setColor(Color.LIGHT_GRAY);
        g.fillRect(0, 0, d.width, d.height);
        g.setFont(new Font("San-serif", Font.BOLD, 24));
        g.setColor(new Color(255, 215,0));
        g.drawString("OMOK by Ryan", 60, 40);
        //g.drawImage(getImage("rabbit.jpg"), 40, 60, this);

        // Line drawing set up
        g.setColor(Color.DARK_GRAY);
        int C = 15; // Number of Lines
        int D = 31; // Spacing of lines

        // Draws horizontal lines
        for(int i = 0; i <= C; i++){
           g.drawLine(0, i*D, 500, i*D);
        }

        // Draws vertical lines
        for(int i = 0; i <= C; i++){
            g.drawLine(i*D, 0, i*D,500);
        }

        // Draws player oval
        g.setColor(Color.blue);
        g.drawOval(240,240,15,15);
        g.fillOval(240,240,15,15);

        // Draws opponent oval
        g.setColor(Color.red);
        g.drawOval(271,271,15,15);
        g.fillOval(271,271,15,15);
    }

    public static void main(String[] args) {
    	//new HelloWorld().run();
    	// or specify optional parameters such as the window size
        new HelloWorld(new String[] {"width=500", "height=500"}).run();
    }
}
