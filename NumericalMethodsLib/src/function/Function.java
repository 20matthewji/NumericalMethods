package function;

import java.util.ArrayList;

/**
 * This class calculates the coordinate values for the gaussian, sinc, and polynomial 
 * functions. The functions' equations are hardcoded and the coordinates are stored in 
 * ArrayLists of Points. The start, end, and increment variables define the x domain 
 * and are stored in instance variables. The instance variable str defines the function 
 * (gaussian, sinc, or polynomial) that is going to be calculated.
 * 
 * @author Matthew Jin
 * @version 9/5/17
 */
public class Function {

	protected double start;
	protected double end;
	protected double inc;
	protected String str;
	
	/**
	 * Constructor for the Function class. It initializes the starting value, 
	 * ending value, increment value, and str.
	 * 
	 * @precondition str is either "gaussian" or "sinc" or "polynomial"
	 * 
	 * @param start the value of the leftmost x coordinate
	 * @param end the value of the rightmost x coordinate
	 * @param inc the difference between each x coordinate
	 * @param str the name of the function
	 */
	public Function(double start, double end, double inc, String str) {
		this.start = start;
		this.end = end;
		this.inc = inc;
		this.str = str;
	}
	
	/**
	 * Calculates the y values of the gaussian, sinc, or polynomial function given the 
	 * x domain (start <= x <= end, ∆x = inc). This method uses the calcGaussian, calcSinc, 
	 * or calcPolynomial helper method to calculate the y values of the function.
	 * 
	 * @return an ArrayList of Points containing the x and y coordinates of the function
	 */
	public ArrayList<Vector> calcFunc() {
		ArrayList<Vector> func = new ArrayList<Vector>();
		
		for(double i=start; i<=end; i+=inc) {
			if(str.equalsIgnoreCase("sinc")) {
				func.add(new Vector(i, calcSinc(i)));
			} else if(str.equalsIgnoreCase("gaussian")) {
				func.add(new Vector(i, calcGaussian(i)));				
			} else {
				func.add(new Vector(i, calcOther(i)));
			}
		}
		
		return func;
	}
	
	/**
	 * Calculates the y value of the Gaussian function by plugging the x value into a 
	 * hardcoded math function.
	 * 
	 * @param x the x value
	 * @return the y value corresponding to the x value
	 */
	public double calcGaussian(double x) {
		return Math.pow(Math.E, -Math.pow(x, 2));
	}
	
	/**
	 * Calculates the y value of the Sinc function by plugging the x value into a 
	 * hardcoded math function.
	 * 
	 * @param x the x value
	 * @return the y value corresponding to the x value
	 */
	public double calcSinc(double x) {
		if(x!=0) {
			return Math.sin(x)/x;
		}
		return 1;
	}
	
	/**
	 * Calculates the y value of a polynomial function by plugging the x value 
	 * into a hardcoded math function
	 * 
	 * @param x the x value
	 * @return the y value corresponding to the x value
	 */
	public double calcOther(double x) {
		return x*x;
//		return 1+x*x;
//		return Math.pow(Math.E, -x*x);
//		return x*Math.pow(Math.E, -x*x);
//		return x*Math.pow(Math.E, x*x);
//		return x*Math.pow(Math.E, x);
//		return x*Math.pow(Math.E, -x);
	}
	
	/**
	 * Calculates the midpoint of two x values
	 * 
	 * @param x1 the first x value
	 * @param x2 the second x value
	 * @return the midpoint of the two x values
	 */
	public double calcMid(double x1, double x2) { return (x1+x2)/2; }
	
	/**
	 * Calculates the slope between two points
	 * 
	 * @param x1 the x value of the first point
	 * @param y1 the y value of the first point
	 * @param x2 the x value of the second point
	 * @param y2 the y value of the second point
	 * @return the slope between the two points
	 */
	public double calcSlope(Vector p1, Vector p2) { return (p2.y-p1.y)/(p2.x-p1.x); }
	
	/**
	 * Calculates the y value of a function given a x value by calling the Function class's 
	 * calcSinc(), calcGaussian(), and calcOther() methods.
	 * 
	 * @param x the x value
	 * @return the y value corresponding to the x value in the function
	 */
	public double calcY(double x) {
		if(str.equalsIgnoreCase("sinc")) {
			return calcSinc(x);
		} else if(str.equalsIgnoreCase("gaussian")) {
			return calcGaussian(x);				
		} else {
			return calcOther(x);
		}
	}
	
}
