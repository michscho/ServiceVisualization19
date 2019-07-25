package tum.franca.graph.cells;

import java.awt.Point;

public class RectangleUtil {
	
	/**
	 * Get low right Point of Rectangle.
	 * 
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 * @return
	 */
	public static Point getPointOfRechtangle(double x, double y, double width, double height) {
		double newX = x + width;
		double newY = y + height;
		return new Point((int) newX, (int) newY);
	}
	

	/**
	 * Method to check if two Rectangles do overlap.
	 * 
	 * @param l1
	 * @param r1
	 * @param l2
	 * @param r2
	 * @return
	 */
	public static boolean doOverlap(Point l1, Point r1, Point l2, Point r2) {
		if (l1.x > r2.x || l2.x > r1.x) {
			return false;
		}

		if (l1.y > r2.y || l2.y > r1.y) {
			return false;
		}

		return true;
	}

}
