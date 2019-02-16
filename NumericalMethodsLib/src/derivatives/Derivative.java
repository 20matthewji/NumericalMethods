package derivatives;

import java.util.ArrayList;

import function.*;

/**
 * This class calculates the coordinate values for the derivatives of the gaussian, sinc, 
 * and polynomial functions. The functions' equations are hardcoded and the coordinates 
 * are stored in ArrayLists of Points. The start, end, and increment variables define the 
 * x domain and are stored in instance variables. The instance variable str defines the 
 * function (gaussian, sinc, or polynomial) that is going to be calculated. Extends the 
 * Function class because this class calculates the function's derivative.
 * 
 * @author Matthew Jin
 * @version 9/5/17
 */
public class Derivative extends Function {

	/**
	 * Constructor for the Derivative class. It initializes the starting value, 
	 * ending value, increment value, and str by calling super.
	 * 
	 * @precondition str is either "gaussian" or "sinc" or "polynomial"
	 * 
	 * @param start the value of the leftmost x coordinate
	 * @param end the value of the rightmost x coordinate
	 * @param inc the difference between each x coordinate
	 * @param str the name of the function
	 */
	public Derivative(double start, double end, double inc, String str) {
		super(start, end, inc, str);
	}
	
	/**
	 * Calculates the y values of the derivative of the gaussian, sinc, or polynomial 
	 * function given the x domain (start <= x <= end, ∆x = inc). This method uses the 
	 * calcGaussianDeriv, calcSincDeriv, or calcPolynomialDeriv method to calculate the 
	 * y values of the derivative of the function.
	 * 
	 * @return an ArrayList of Points containing the x and y coordinates of the derivative
	 * of the function
	 */
	public ArrayList<Vector> calcDeriv() {
		ArrayList<Vector> deriv = new ArrayList<Vector>();

		for(double i=start; i<=end; i+=inc) {
			if(str.equalsIgnoreCase("sinc")) {
				deriv.add(new Vector(i, calcSincDeriv(i)));
			} else if(str.equalsIgnoreCase("gaussian")) {
				deriv.add(new Vector(i, calcGaussianDeriv(i)));
			} else {
				deriv.add(new Vector(i, calcOtherDeriv(i)));
			}
		}
		
		return deriv;
	}
	
	/**
	 * Calculates the y value of the derivative of the Gaussian function by plugging
	 * the x value into a hardcoded math function.
	 * 
	 * @param x the x value
	 * @return the y value corresponding to the x value
	 */
	public double calcGaussianDeriv(double x) {
		return -2*x*calcGaussian(x);
	}

	/**
	 * Calculates the y value of the derivative of the Sinc function by plugging 
	 * the x value into a hardcoded math function.
	 * 
	 * @param x the x value
	 * @return the y value corresponding to the x value
	 */
	public double calcSincDeriv(double x) {
		if(x!=0) {
			return (x*Math.cos(x)-Math.sin(x))/(x*x);
		}
		return 0;
	}
	
	/**
	 * Calculate the y value of the derivative of the function by plugging the x 
	 * value into a hardcoded math function.
	 * 
	 * @param x the x value
	 * @return the y value corresponding to the x value
	 */
	public double calcOtherDeriv(double x) {
		return -1/Math.pow(x, 2);
//		return 2*x;
//		return 5*Math.sin(5*x)+2;
//		return Math.pow(Math.E, x)+x*Math.pow(Math.E, x);
//		return 2*x;
	}

	/**
	 * Calculates the y value of a function given a x value by calling the Derivative class's 
	 * calcSincDeriv(), calcGaussianDeriv(), and calcOtherDeriv() methods.
	 * 
	 * @param x the x value
	 * @return the y value corresponding to the x value in the derivative
	 */
	public double calcYDeriv(double x) {
		if(str.equalsIgnoreCase("sinc")) {
			return calcSincDeriv(x);
		} else if(str.equalsIgnoreCase("gaussian")) {
			return calcGaussianDeriv(x);		
		} else {
			return calcOtherDeriv(x);
		}
	}
	
}
