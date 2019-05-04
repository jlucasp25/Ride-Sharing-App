package rsa.shared;
import java.io.Serializable;

/**
 * <b>Enum UserStars</b>
 * <br>Classification to a ride provided by the other user.<br>
 * @author João Lucas Pires, Sara Ferreira
 */
public enum UserStars implements Serializable, Comparable<UserStars> {
	
	FIVE_STARS,
	FOUR_STARS,
	THREE_STARS,
	TWO_STARS,
	ONE_STAR;
		
	public int getStars() {
		if (this.equals(FIVE_STARS))
			return 5;
		else if (this.equals(FOUR_STARS))
			return 4;
		else if (this.equals(THREE_STARS))
			return 3;
		else if (this.equals(TWO_STARS))
			return 2;
		else
			return 1;		
	}

}
