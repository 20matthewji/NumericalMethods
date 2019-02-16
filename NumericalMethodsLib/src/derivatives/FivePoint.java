package derivatives;

import java.util.ArrayList;

import function.Vector;

/**
 * Calculates the derivative of a function using the five point stencil
 * method. The derivative is calculated in the method calcDeriv and stored
 * in an ArrayList of Points. Extends the Derivative class because the five
 * point stencil is a method used to calculate derivatives and therefore fits
 * under the Derivative category.
 * 
 * @author Matthew Jin
 * @version 9/5/17
 */
public class FivePoint extends Derivative {

	private ArrayList<Vector> func;

	/**
	 * Constructor for the FivePoint class. It sets the starting value, 
	 * ending value, and increment value by calling super to initialize the variables  
	 * and calculates the coordinates in the original function. The constructor also
	 * initializes the func ArrayList which stores the coordinates for the original 
	 * function.
	 * 
 	 * @precondition str is either "gaussian" or "sinc" or "polynomial"
	 * 
	 * @param start the value of the leftmost x coordinate
	 * @param end the value of the rightmost x coordinate
	 * @param inc the difference between each x coordinate
	 * @param str the name of the function
	 */
	public FivePoint(double start, double end, double inc, String str) {
		super(start, end, inc, str);
		func = calcFunc();
	}

	/**
	 * Calculates the derivative of the function using the five point stencil method. 
	 * Loops through coordinates in func and uses the five point stencil formula to 
	 * calculate the corresponding y value to the derivative 
	 * 
	 * @return Returns an ArrayList containing the points in the derivative.
	 */
	public ArrayList<Vector> calcDeriv() {
		ArrayList<Vector> deriv = new ArrayList<Vector>();

		for(int i=2; i<func.size()-2; i++) {
			double x2 = func.get(i-1).x, x3 = func.get(i).x;
			double y1 = func.get(i-2).y, y2 = func.get(i-1).y, y4 = func.get(i+1).y, y5 = func.get(i+2).y;
			double h = x3-x2;
			
			deriv.add(new Vector(x3, (-y5+8*y4-8*y2+y1)/(12*h)));
		}
		
		return deriv;
	}

}
