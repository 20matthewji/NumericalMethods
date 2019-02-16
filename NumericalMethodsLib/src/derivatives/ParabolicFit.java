package derivatives;

import java.util.ArrayList;

import function.Vector;

/**
 * Calculates the derivative of a function using the parabolic fit method. 
 * The derivative is calculated in the method calcDeriv and stored in an 
 * ArrayList of Points. Extends the Derivative class because the parabolic
 * fit is a method used to calculate derivatives and therefore fits under 
 * the Derivative category.
 * 
 * @author Matthew Jin
 * @version 9/13/17
 */
public class ParabolicFit extends Derivative {

	private ArrayList<Vector> func;

	/**
	 * Constructor for the ParabolicFit class. It sets the starting value, 
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
	public ParabolicFit(double start, double end, double inc, String str) {
		super(start, end, inc, str);
		func = calcFunc();
	}

	/**
	 * Calculates the derivative of the function using the parabolic fit method. Loops 
	 * through coordinates in func and uses the parabolic fit method to calculate the 
	 * corresponding y value in the derivative 
	 * 
	 * @precondition func has more than three points (func.size() >= 3)
	 * 
	 * @return Returns an ArrayList containing the points in the derivative.
	 */
	public ArrayList<Vector> calcDeriv() {
		ArrayList<Vector> deriv = new ArrayList<Vector>();

		for(int i=1; i<func.size()-1; i++) {
			Vector p1 = func.get(i-1), p2 = func.get(i), p3 = func.get(i+1);
			
			if(i==1) {
				deriv.add(parabolicFit(p1, p2, p3, p1.x));
			}
			
			deriv.add(parabolicFit(p1, p2, p3, p2.x));
			
			if(i==func.size()-2) {
				deriv.add(parabolicFit(p1, p2, p3, p3.x));
			}
		}

		return deriv;
	}
	
	/**
	 * Does a parabolic fit across three points and takes the derivative 
	 * at a given value x. Uses the calcA and calcB helper methods to 
	 * calculate a and b values of the parabola.
	 * 
	 * @param p1 the first point
	 * @param p2 the second point
	 * @param p3 the third point
	 * @param the point to take the derivative at
	 * @return a Point that contains the x value and the y value of the 
	 * derivative that corresponds to that x value
	 */
	private Vector parabolicFit(Vector p1, Vector p2, Vector p3, double x) {
		double a = calcA(p1, p2, p3), b = calcB(p1, p2, p3);
		return new Vector(x, 2*a*x+b);
	}
	
	/**
	 * Calculates the "a" value for the parabolic fit by plugging the given 
	 * points into a hardcoded math formula.
	 * 
	 * @param p1 the first point
	 * @param p2 the second point
	 * @param p3 the third point
	 * @return the value of a
	 */
	private double calcA(Vector p1, Vector p2, Vector p3) {
		return (p1.x*(p3.y-p2.y)+p2.x*(p1.y-p3.y)+p3.x*(p2.y-p1.y)) / 
				((p1.x-p2.x)*(p1.x-p3.x)*(p2.x-p3.x));
	}
	
	/**
	 * Calculates the "b" value for the parabolic fit by plugging the given 
	 * points into a hardcoded math formula.
	 * 
	 * @param p1 the first point
	 * @param p2 the second point
	 * @param p3 the third point
	 * @return the value of b
	 */
	private double calcB(Vector p1, Vector p2, Vector p3) {
		return (p1.x*p1.x*(p2.y-p3.y)+p2.x*p2.x*(p3.y-p1.y)+p3.x*p3.x*(p1.y-p2.y)) / 
				((p1.x-p2.x)*(p1.x-p3.x)*(p2.x-p3.x));
	}

	/**
	 * Calculates the "c" value for the parabolic fit by plugging the given 
	 * points into a hardcoded math formula.
	 * 
	 * @param p1 the first point
	 * @param p2 the second point
	 * @param p3 the third point
	 * @return the value of c
	 */
	private double calcC(Vector p1, Vector p2, Vector p3) {
		return (p2.x*(p1.x*p1.x*p3.y-p3.x*p3.x*p1.y)+p2.x*p2.x*(p3.x*p1.y-p1.x*p3.y)+p1.x*p3.x*p2.y*(p3.x-p1.x)) / 
				((p1.x-p2.x)*(p1.x-p3.x)*(p2.x-p3.x));
	}
}
