
package rsa.quad;

/**
 * <b>Interface HasPoint</b>
 * <br>
 * The type implementing this interface has to at least store and be able to retrieve two double numeric values for spatial coordinates.
 * @author João Lucas Pires, Sara Ferreira
 */
public interface HasPoint {
	
	public abstract double getX();
	
	public abstract double getY();
}
