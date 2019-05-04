package rsa.shared;
import java.io.Serializable;

/**
 * <b>Enum RideRole</b>
 * <br>The role of the user in a ride. <br>
 * @author João Lucas Pires, Sara Ferreira
 */
public enum RideRole implements Serializable, Comparable<RideRole> {
	DRIVER,
	PASSENGER;
	
	public RideRole other() {
		if (this.equals(RideRole.DRIVER))
			return RideRole.PASSENGER;
		else
			return RideRole.DRIVER;
	}
}
