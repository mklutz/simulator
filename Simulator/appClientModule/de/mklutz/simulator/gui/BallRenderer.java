package de.mklutz.simulator.gui;

import java.awt.Graphics;

import javax.swing.JPanel;

import de.mklutz.simulator.model.Ball;

public class BallRenderer {
	public static void render(JPanel panel, Ball ball) {
		print(panel.getGraphics(), ball);
	}

	static void print(Graphics gr, Ball ball) {
		gr.drawOval(ball.getX(), ball.getY(), ball.getRadius(), ball.getRadius());
	}

}
