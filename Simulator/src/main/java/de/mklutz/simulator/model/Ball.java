package de.mklutz.simulator.model;

import java.util.PrimitiveIterator.OfInt;
import java.util.concurrent.ThreadLocalRandom;

public class Ball {
	int x, y;
	int radius;

	public Ball(int x, int y, int radius) {
		super();
		this.x = x;
		this.y = y;
		this.radius = radius;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public int getRadius() {
		return radius;
	}

	static public Ball newBall() {

		OfInt iterator = ThreadLocalRandom.current().ints(5, 100).iterator();

		return new Ball(iterator.nextInt(), iterator.nextInt(), iterator.nextInt());
	}

}
