package rsa.service;
import java.util.Comparator;
import rsa.shared.RideMatchInfo;

/**
 * <b>Interface RideMatchInfoSorter</b>
 * <br>A type proving a comparator of RideMatchInfo instances. This is part of the abstract component of the Factory Method design pattern. <br>
 * @author João Lucas Pires, Sara Ferreira
 */
public interface RideMatchInfoSorter {
	
	Comparator<RideMatchInfo> getComparator();
	
}