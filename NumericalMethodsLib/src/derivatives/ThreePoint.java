package derivatives;

import java.util.ArrayList;

import function.Vector;

/**
 * Calculates the derivative of a function using the three point method. 
 * The derivative is calculated in the method calcDeriv and stored in an 
 * ArrayList of Points. Extends the Derivative class because the three 
 * point method used to calculate derivatives and therefore fits under 
 * the Derivative category.
 * 
 * @author Matthew Jin
 * @version 9/5/17
 */
public class ThreePoint extends Derivative {

	private ArrayList<Vector> func;

	/**
	 * Constructor for the ThreePoint class. It sets the starting value,  
	 * ending value, and increment value by calling super to initialize the variables  
	 * and calculates the coordinates in the original function. The constructor 
	 * also initializes the func ArrayList which stores the coordinates for the original 
	 * function.
	 * 
	 * @precondition str is either "gaussian" or "sinc" or "polynomial"
	 * 
	 * @param start the value of the leftmost x coordinate
	 * @param end the value of the rightmost x coordinate
	 * @param inc the difference between each x coordinate
	 * @param str the name of the function
	 */
	public ThreePoint(double start, double end, double inc, String str) {
		super(start, end, inc, str);
		func = calcFunc();
	}

	/**
	 * Calculates the derivative of the function using the three point method. 
	 * Loops through coordinates in func and takes the slope from the first 
	 * point to the third point and sets the y value corresponding to the 
	 * middle point as the slope. The slope is calculated using the inherited 
	 * calcMid and calcSlope methods from the Derivative class.
	 * 
	 * @return Returns an ArrayList containing the points in the derivative.
	 */
	public ArrayList<Vector> calcDeriv() {
		ArrayList<Vector> deriv = new ArrayList<Vector>();

		for(int i=1; i<func.size()-1; i++) {
			Vector p1 = func.get(i-1), p2 = func.get(i), p3 = func.get(i+1);
			deriv.add(new Vector(p2.x, calcSlope(p1, p3)));
		}
		
		return deriv;
	}

}
