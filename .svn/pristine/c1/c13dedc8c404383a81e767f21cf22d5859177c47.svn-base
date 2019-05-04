package rsa.quad;
import java.util.HashSet;
import java.util.Set;
import java.lang.String;

/**
 * <b>Class LeafTrie</b>
 * <br>
 * This class represents a Trie that represents a quadrant of its parent range.<br>
 * It stores the points on its range inside a set.<br>
 * Corresponds to the leaf element in the Composite Design Pattern.<br>
 * This class is parametrized by a type that extends the HasPoint interface.
 * @see rsa.quad.HasPoint HasPoint interface
 * @author João Lucas Pires, Sara Ferreira
 */
public class LeafTrie<T extends HasPoint> extends Trie<T>  {
		
	private Set<T> leafPoints;
	
	LeafTrie(double topLeftX, double topLeftY, double bottomRightX, double bottomRightY) {
		super(topLeftX,topLeftY,bottomRightX,bottomRightY);
		this.leafPoints = new HashSet<T>();
	}
	
	void collectAll(Set<T> nodes) {
		for (T aux : leafPoints) {
			nodes.add(aux);
		}
		return;
	}

	/**
	 * Collects points inside this leaf that are inside the range of the specified circle.<br>
	 * The overlaps method used inside receives the square of (circle_x - point_x) and (circle_y - point_y).<br>
	 * Uses an adaption of the Pythagorean Theorem to verify if the point is contained in the circle.
	 * @param x (double)
	 * @param y (double)
	 * @param radius (double)
	 * @param nodes (Set of T extends HasPoint)
	 */
	void collectNear(double x, double y, double radius, Set<T> nodes) {
		for (T aux : leafPoints) {
			if (overlaps(Math.pow(x - aux.getX(),2),Math.pow(y - aux.getY(),2),radius)) {
				nodes.add(aux);
			}
		}
		return;
	}
	
	void delete(T point) {
		for (T aux : leafPoints) {
			if (aux.getX() == point.getX() && aux.getY() == point.getY())
				leafPoints.remove(aux);
		}
		return;
	}
	
	T find(T point) {
		for (T aux : leafPoints) {
			if (aux.getX() == point.getX() && aux.getY() == point.getY() )
				return aux;
		}
		return null;
	}
	
	/**
	 * Inserts a point inside the leaf.<br>
	 * If the leaf has reached the max capacity, it will convert itself to a NodeTrie and will populate it with its points.<br>
	 * If the point isn't in the trie's range, it will throw a PointOutOfBoundException.
	 * @param point (T extends HasPoint)
	 * @return Trie of T extends HasPoint
	 * */
	Trie<T> insert(T point) {
		if (!this.checkRange(point)) {
			throw new PointOutOfBoundException("This point doesn't belong to this trie or subtries!");
		}
		if (leafPoints.size() >= Trie.getCapacity() ) {
			NodeTrie<T> newNode = new NodeTrie<T>(this.topLeftX, this.topLeftY, this.bottomRightX, this.bottomRightY);
			for (T aux : this.leafPoints) {
				newNode.insert(aux);
			}
			newNode.insert(point);
			return newNode;
		}
		leafPoints.add(point);
		return this;
	}
	
	/**
	 * Checks if a point with the same coordinates is inside the leaf, if so, removes it and adds a new one.
	 * If the point isn't in the trie's range, it will throw a PointOutOfBoundException.
	 * @param point (T extends HasPoint)
	 * @return Trie of T extends HasPoint
	 */
	Trie<T> insertReplace(T point) {	
		if (!checkRange(point)) {
				throw new PointOutOfBoundException("This point doesn't belong to this trie or subtries!");
		}
		leafPoints.remove(this.find(point));
		leafPoints.add(point);
		return this;
	}
	
	@Override
	public String toString() {
			return "LeafTrie with " + leafPoints.size() + "points";
	}

	public Set<T> getLeafPoints() {
		return leafPoints;
	}

	public void setLeafPoints(Set<T> leafPoints) {
		this.leafPoints = leafPoints;
	}

}
