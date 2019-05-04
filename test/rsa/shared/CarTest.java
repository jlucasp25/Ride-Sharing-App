package rsa.shared;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import rsa.TestData;

/**
 * Tests on Car, represented by plate, make, model and color
 * 
 * @author Jos&eacute; Paulo Leal {@code zp@dcc.fc.up.pt}
 */
public class CarTest extends TestData {
	Car car;
	
	@Before
	public void setUp() throws Exception {
		car = new Car(PLATES[0],MAKES[0],MODELS[0],COLORS[0]);
	}

	/**
	 * Check is car was created
	 */
	@Test
	public void testCar() {
		assertNotNull(car);
	}

	/**
	 * Check setting and getting plates
	 */
	@Test
	public void testPlate() {
		assertEquals(PLATES[0],car.getPlate());
		
		for(String plate: PLATES) {
			car.setPlate(plate);
			assertEquals(plate,car.getPlate());
		}
	}

	/**
	 * Check setting and getting makes
	 */
	@Test
	public void testMake() {
		assertEquals(MAKES[0],car.getMake());
		
		for(String make: MAKES) {
			car.setMake(make);;
			assertEquals(make,car.getMake());
		}
	}

	/**
	 * Check setting and getting models
	 */
	@Test
	public void testModel() {
		assertEquals(MODELS[0],car.getModel());
		
		for(String model: MODELS) {
			car.setModel(model);;
			assertEquals(model,car.getModel());
		}
	}

	/**
	 * Check setting and getting colors
	 */
	@Test
	public void testColor() {
		assertEquals(COLORS[0],car.getColor());
		
		for(String color: COLORS) {
			car.setColor(color);;
			assertEquals(color,car.getColor());
		}
	}

}
