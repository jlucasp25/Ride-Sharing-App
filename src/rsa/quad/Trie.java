package rsa.quad;
import java.util.Set;
import java.lang.String;
import java.io.Serializable;
import java.lang.Math;

/**
 * <b>Abstract Class Trie</b>
 * <br>This class represents common elements on the various implementations of tries. (Component in Composite Design Pattern)<br>
 * Constructor, fields, abstract and concrete methods are declared here to be used on all the Trie elements. <br>
 * This class is parametrized by a type that extends the HasPoint interface.
 * @see rsa.quad.HasPoint HasPoint interface
 * @author João Lucas Pires, Sara Ferreira
 */
public abstract class Trie<T extends HasPoint> {
		
		/**
		 * <b>Enum Quadrant</b>
		 * <br>Implements all four quadrants to identify each child Trie in the current range.<br>
		 * This class implements the Serializable and Comparable interfaces.<br>
		 * Allows comparation with elements of the same type.
		 * @see java.io.Serializable Serializable
		 * @author João Lucas Pires, Sara Ferreira
		 */
		enum Quadrant implements Serializable, Comparable<Trie.Quadrant> {
			NW,
			NE,
			SE,
			SW;		
		}
	
	protected double bottomRightX;
	protected double bottomRightY;
	protected double topLeftX;
	protected double topLeftY;
	private static int capacity;
	private final static int DEFAULT_CAPACITY = 10;
	
	protected Trie(double topLeftX, double topLeftY, double bottomRightX, double bottomRightY) {
		this.topLeftX = topLeftX;
		this.topLeftY = topLeftY;
		this.bottomRightX = bottomRightX;
		this.bottomRightY = bottomRightY;
		Trie.capacity = DEFAULT_CAPACITY;
	}
	
	abstract void collectAll(Set<T> points);	
	
	abstract void collectNear(double x,double y, double radius, Set<T> points);
	
	/**
	 * Checks if a given point is inside the current Trie's 2D range.
	 * @param point (T extends HasPoint)
	 * @return a boolean value, true if is inside the range and vice-versa.
	 */
	protected boolean checkRange(T point) {
		if (point.getX() <= this.bottomRightX && point.getX() >= this.topLeftX && point.getY() <= this.topLeftY && point.getY() >= this.bottomRightY)
			return true;
		return false;
	}
	
	abstract void delete(T point);
	
	abstract T find(T point);
	
	static int getCapacity() {
		return capacity;
	}
	
	/**
	 * Returns the euclidean distance between two points on a 2D plane.
	 * @param x1 (double)
	 * @param y1 (double)
	 * @param x2 (double)
	 * @param y2 (double)
	 * @return The calculated distance. (double)
	 */
	public static double getDistance(double x1, double y1, double x2, double y2) {
		return Math.sqrt( Math.pow(x1-x2,2) + Math.pow(y1-y2, 2));
	}
	
	abstract Trie<T> insert(T point);
	
	abstract Trie<T> insertReplace(T point);
	
	/**
	 * Checks if a point in a 2D plane is inside a circle of given radius.<br>
	 * It expects that the given x and y values are already prepared by the calling method.
	 * @param x as (x_circle - x_point) (double)
	 * @param y as (y_circle - y_point) (double)
	 * @param radius (double)
	 * @return A boolean value, true if overlaps and vice-versa.
	 */
	protected boolean overlaps(double x, double y, double radius) {
		return (x+y <= Math.pow(radius,2));
	}
	
	public static void setCapacity(int _capacity) {
		capacity = _capacity;
	}
	
	@Override
	public String toString() {
		return "Trie";
	}

}
