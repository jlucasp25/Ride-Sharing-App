package rsa.shared;
import java.io.Serializable;

/**
 * <b>Enum PreferredMatch</b>
 * <br>Preferred way to sort matches. Users will set their preferences using this values. <br>
 * @author João Lucas Pires, Sara Ferreira
 */

public enum PreferredMatch implements Serializable, Comparable<PreferredMatch>  {
	BETTER,
	CHEAPER,
	CLOSER;
}
