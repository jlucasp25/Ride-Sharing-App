package rsa;

import java.util.Date;

/**
 * Test data used in unit tests
 * 
 * @author José Paulo Leal {@code zp@dcc.fc.up.pt}
 */
public class TestData {

		
	protected static final double SIDE = 1000;
	protected static final double MARGIN = 100;
	protected static final int STATUS = 1000;
	protected static final int RADIUS = 10;

	protected static final double TOP_LEFT_X = 0;
	protected static final double TOP_LEFT_Y = SIDE;
	protected static final double BOTTOM_RIGHT_X = SIDE;
	protected static final double BOTTOM_RIGHT_Y = 0;

	protected static final String INVALID_NICK = "User ZERO";
	protected static final String[] NICKS = { "U0", "U1", "U2" };
	protected static final String[] NAMES = { "User Zero", "User One", "User Two" };
	protected static final String[] PASSWORDS = { "Password0", "password1", "password2" };
	protected static final String[] COLORS = { "Black", "White", "Red", "Blue", "Green" };
	protected static final String[] MAKES = { "Opel", "Ford", "VW", "Fiat", "Renault" };
	protected static final String[] MODELS = { "Astra", "Focus", "Clio", "Corsa", "Golf" };
	protected static final String[] PLATES = { "OO-00-00", "00-OO-00", "00-00-OO" };
	protected static final float[]  COSTS  = { 0.0F, 1.0F, 2.0F };
	
	protected static int X1 = 200;
	protected static int Y1 = 200;
	
	protected static int X2 = 500;
	protected static int Y2 = 500;
	
	protected static int X3 = 700;
	protected static int Y3 = 700;

	protected static Date NOW = new Date();
	protected static int  SLACK = 100; 
	protected static Date[] DATES = { NOW, new Date(NOW.getTime()+SLACK) };
	protected static String[] TEXTS = { "hello", "two words", "some more words"}; 
	protected static int[] INTS = { 10, 20, 30 };
	protected static double[] DOUBLES = { -10, -1, 0, 1, 10 };
	protected static double DELTA = 1E-6; 		
	protected static float[] ANGLES = new float[] { 0, (float) Math.PI, (float) Math.PI/2 }; 
	
	protected static final int MANY_OBJECTS = 100000;
	protected static final int REPETITIONS = 10;
	
	protected static final int SIZE = 20;
}
