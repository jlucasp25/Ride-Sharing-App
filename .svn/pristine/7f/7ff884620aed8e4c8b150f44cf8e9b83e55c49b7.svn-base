package rsa.quad;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.lang.String;

/**
 * <b>Class NodeTrie</b>
 * <br>
 * This class represents a Trie that has 4 child Tries each one represented by a quadrant. It stores the child Tries in a map.<br>
 * Corresponds to the Composite Design Pattern.<br>
 * This class is parametrized by a type that extends the HasPoint interface.
 * @see rsa.quad.HasPoint HasPoint interface
 * @author João Lucas Pires, Sara Ferreira
 */
public class NodeTrie<T extends HasPoint> extends Trie<T> {

	private Map< Trie.Quadrant, Trie<T> > tries;
	
	/**
	 * Constructs the NodeTrie, calling the super-constructor, creating a map, and populating it with each one of
	 * the four child Tries (LeafTries).
	 * @param topLeftX (double)
	 * @param topLeftY (double)
	 * @param bottomRightX (double)
	 * @param bottomRightY (double)
	 */
	protected NodeTrie(double topLeftX, double topLeftY, double bottomRightX, double bottomRightY) {
		super(topLeftX, topLeftY, bottomRightX, bottomRightY);
		this.tries = new HashMap< Trie.Quadrant, Trie<T> >();
		
		tries.put(Trie.Quadrant.NW, new LeafTrie<T>(topLeftX, topLeftY, (bottomRightX + topLeftX)/2, (topLeftY + bottomRightY)/2));
		tries.put(Trie.Quadrant.NE, new LeafTrie<T>((bottomRightX + topLeftX)/2, topLeftY, bottomRightX, (topLeftY + bottomRightY)/2));
		tries.put(Trie.Quadrant.SW, new LeafTrie<T>(topLeftX, (topLeftY + bottomRightY)/2, (bottomRightX + topLeftX)/2, bottomRightY));
		tries.put(Trie.Quadrant.SE, new LeafTrie<T>((bottomRightX + topLeftX)/2, (topLeftY + bottomRightY)/2, bottomRightX, bottomRightY));
	}
	
	/**
	 * Recursively collects all the points from the descendants of the current node and places them in the given Set.
	 * @param points (Set of "T extends HasPoint")
	 */
	void collectAll(Set<T> points) {		
		for (Map.Entry< Trie.Quadrant, Trie<T> > mapEntry : tries.entrySet() ) {
			mapEntry.getValue().collectAll(points);
		}
		return;
	}
	
	/**
	 * Recursively collects the points that are near the specified circle from the descendants of the current node and places them in the given Set.
	 * @param x (double)
	 * @param y (double)
	 * @param radius (double)
	 * @param nodes (Set of "T extends HasPoint")
	 */
	void collectNear(double x, double y, double radius, Set<T> nodes) {
		
		for (Map.Entry< Trie.Quadrant, Trie<T> > mapEntry : tries.entrySet() ) {
			mapEntry.getValue().collectNear(x,y,radius,nodes);
		}
		return;
	}
	
	void delete(T point) {
		tries.get(quadrantOf(point)).delete(point);
		return;
	}
	
	T find(T point) {
		return tries.get(quadrantOf(point)).find(point);
	}
	
	/**
	 * Inserts a point in the corresponding leafTrie. <br>
	 * Throws PointOutOfBoundException if the point's range doesn't match with the current Trie's.
	 * @param point (T extends HasPoint)
	 * @return The trie with the newly inserted point.
	 */
	Trie<T> insert(T point) throws PointOutOfBoundException {
		if (!checkRange(point)) {
			throw new PointOutOfBoundException("This point doesn't belong to this trie or subtries!");
		}
		Quadrant quadrant = quadrantOf(point);
		Trie<T> temp = tries.get(quadrant).insert(point);
		tries.put(quadrant,temp);
		return this;
	}
	
	/**
	 * Inserts a new point replacing a old one in the corresponding leafTrie. <br>
	 * Throws PointOutOfBoundException if the point's range doesn't match with the current Trie's.
	 * @param point (T extends HasPoint)
	 * @return The trie with the newly inserted point.
	 */
	Trie<T> insertReplace(T point) throws PointOutOfBoundException {
		if (!checkRange(point)) {
			throw new PointOutOfBoundException("This point doesn't belong to this trie or subtries!");
		}
		return tries.get(quadrantOf(point)).insertReplace(point);
	}

	/**
	 * Returns the quadrant of the given point in relation to the current range. <br>
	 * Throws PointOutOfBoundException if it doesn't find a suitable quadrant.
	 * @param point (T extends HasPoint)
	 * @return The point's quadrant in a Trie.Quadrant representation
	 */
	private Trie.Quadrant quadrantOf(T point) throws PointOutOfBoundException {
		if (point.getX() <= (bottomRightX + topLeftX)/2) {
			if (point.getY() <= (topLeftY + bottomRightY)/2)
				return Trie.Quadrant.SW;
			else
				return Trie.Quadrant.NW;
		}
		else if (point.getX() > (bottomRightX + topLeftX)/2) {
			if (point.getY() <= (topLeftY + bottomRightY)/2)
				return Trie.Quadrant.SE;
			else
				return Trie.Quadrant.NE;
		}
		else
			throw new PointOutOfBoundException("This point is out of bounds in the available quadrants!");

	}
	
	@Override
	public String toString() {
		return "NodeTrie";
	}

}
