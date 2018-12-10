package main.java.entity;

// TODO: Auto-generated Javadoc
/**
 * The Class Point.
 */
public class Point {
	
	/** The x. */
	private double x;
	
	/** The y. */
	private double y;
	
	/**
	 * Create a point which coordinates are (x,y).
	 *
	 * @param x the x
	 * @param y the y
	 */
	public Point(double x, double y){ 
		this.x = x;
		this.y = y;
	}

	/**
	 * Gets the x.
	 *
	 * @return the x
	 */
	public double getX() {
		return x;
	}
	
	/**
	 * Gets the y.
	 *
	 * @return the y
	 */
	public double getY() {
		return y;
	}
}
