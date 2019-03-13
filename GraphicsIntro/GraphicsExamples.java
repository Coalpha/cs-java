/*
 * File: GraphicsExamples.java
 * -----------------
 * This file contains a few examples of how to draw
 * GObjects onto a GCanvas inside a GraphicsProgram, 
 * including GRect, GOval and GLabel.
 */


import acm.graphics.*;
import acm.program.*;
import java.awt.*;

public class GraphicsExamples extends GraphicsProgram {
	public void run() {
//		drawSingleRect();
//		drawSingleOval();
//		drawLabel();
//		drawThreeDifferentRects();
		drawMixedObjects();
	}

	public void drawSingleRect() {
		GRect rect = new GRect(50, 50, 200, 170);
		rect.setFilled(true);
		rect.setColor(Color.BLUE);
		add(rect);
	}

	public void drawSingleOval() {
		GOval oval = new GOval(50, 50, 200, 170);
		oval.setFilled(true);
		oval.setColor(Color.YELLOW);
		add(oval);
	}

	public void drawLabel() {
		GLabel text = new GLabel("dtech is cool!", 100, 90);
		text.setColor(Color.MAGENTA);
		add(text);
	}

	public void drawThreeDifferentRects() {
		GRect rect1 = new GRect(50, 50, 100, 70);
		rect1.setFilled(true);
		rect1.setColor(Color.BLUE);
		add(rect1);

		GRect rect2 = new GRect(0, 0, getWidth()/2, getHeight()/2);
		rect2.setFilled(false);
		rect2.setColor(Color.GREEN);
		add(rect2);

		GRect rect3 = new GRect(getWidth()/2, getHeight()/2, 30, 30);
		rect3.setFilled(true);
		rect3.setColor(Color.CYAN);
		add(rect3);
	}

	public void drawMixedObjects() {
		GRect rect = new GRect(50, 50, 100, 70);
		rect.setFilled(true);
		rect.setColor(Color.BLUE);
		GOval oval = new GOval(0, 0, getWidth(), getHeight());
		oval.setFilled(false);
		oval.setColor(Color.GREEN);
		GLabel text = new GLabel("this is not a label", 200, 200);
		add(text);
		add(rect);
		add(oval);
	}
}
