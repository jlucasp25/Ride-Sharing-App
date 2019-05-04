package rsa.shared;

import java.io.Serializable;

/**
 * <b>Class Car</b>
 * <br>A car used in rides, with a license plate (that can be used as key) a make, model and color. <br>
 * @author João Lucas Pires, Sara Ferreira
 */
public class Car implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private String plate;
	private String make;
	private String model;
	private String color;
	
	
	public Car(String plate, String make, String model, String color) {
		this.plate = plate;
		this.make = make;
		this.model = model;
		this.color = color;
	}
	
	public String getPlate() {
		return this.plate;
	}
	
	public void setPlate(String plate) {
		this.plate = plate;
	}
	
	public String getMake() {
		return this.make;
	}
	
	public void setMake(String make) {
		this.make = make;
	}
	
	public String getModel() {
		return this.model;
	}
	
	public void setModel(String model) {
		this.model = model;
	}
	
	public String getColor() {
		return this.color;
	}
	
	public void setColor(String color) {
		this.color = color;
	}
}
