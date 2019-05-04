package rsa.quad;
import java.util.HashSet;
import java.util.Set;

/**
 * <b>Class PointQuadTree</b>
 * <br>
 * This class represents the entry point to a QuadTree implementation in 2D. (Facade Design Pattern)<br>
 * Most methods call similar methods from another classes. (Composite Design Pattern)<br>
 * This class is parametrized by a type that extends the HasPoint interface.
 * @see rsa.quad.HasPoint HasPoint interface
 * @author João Lucas Pires, Sara Ferreira
 */
public class PointQuadtree<T extends HasPoint> {
		
	private double topLeftX;
	private double topLeftY;
	private double bottomRightX;
	private double bottomRightY;
	private NodeTrie<T> root;
	
	public PointQuadtree(double topLeftX, double topLeftY, double bottomRightX, double bottomRightY) {
		this.topLeftX = topLeftX;
		this.topLeftY = topLeftY;
		this.bottomRightX = bottomRightX;
		this.bottomRightY = bottomRightY;
		this.root = new NodeTrie<T>(this.topLeftX,this.topLeftY,this.bottomRightX,this.bottomRightY);
	}
	
	public T find(T point) {
		return root.find(point);
	}
	
	public void insert(T point) {
		root.insert(point);
		return;
	}
	
	public void insertReplace(T point) {
		root.insertReplace(point);
		return;
	}

	public Set<T> findNear(double x, double y, double radius) {
		Set<T> set = new HashSet<T>();
		root.collectNear(x,y,radius,set);
		return set;
	}
	
	public void delete(T point) {
		root.delete(point);
	}
	
	public Set<T> getAll() {
		Set<T> set = new HashSet<T>();
		root.collectAll(set);
		return set;
	}
	
}
