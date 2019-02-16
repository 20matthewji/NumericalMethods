package zeros;

import java.util.ArrayList;

import derivatives.Derivative;
import function.Vector;

/**
 * The NewtonsMethod class uses Newton's Method to find a zero in a function given a range. 
 * 
 * @author Matthew Jin
 * @version 10/6/17
 */
public class NewtonsMethod extends Derivative {

	private static double MIN = Math.pow(10, -10);

	/**
	 * Constructor for the NewtonsMethod class. Calls super to initialize the string which contains 
	 * the name of the function. Sets the start, end, and inc of the derivative to zero because 
	 * those variables are not needed.
	 * 
	 * @param str the function to find the zero on
	 */
	public NewtonsMethod(String str) {
		super(0, 0, 0, str);
	}

	/**
	 * The findZero() method calculates the zero of a function using Newton's method. The function 
	 * finds the tangent line at a x value and finds x value of the zero of that tangent line. Then 
	 * it finds the tangent line at the new x value and repeats the process until the method finds 
	 * a zero.
	 * 
	 * @param start the starting x value on the function
	 * @param maxiter the maximum number of iterations
	 * @return an ArrayList of Doubles containing information about a zero found on the function. The 
	 * first value is the zero, the second is the number of iterations, and the third is the derivative.
	 */
	public ArrayList<Double> findZero(double start, double maxiter) {
		ArrayList<Double> zero = new ArrayList<Double>();
		double x = start, deriv = calcYDeriv(x), iter = 0;

		if(Double.isNaN(calcY(x)) || Double.isInfinite(calcY(x)) || deriv==0) {
			return null;
		}

		while(Math.abs(deriv)>MIN && iter<maxiter) {
//			System.out.println(x + ", " + deriv + ", " + calcY(x));
			Vector next = nextX(x);
			deriv = next.y;
			x = next.x;
			iter++;
		}
		zero.add(deriv);

		if(Math.abs(deriv)>MIN) {
			x = start; iter = 0;
			while(Math.abs(calcY(x))>MIN && x-calcY(x)/calcYDeriv(x)!=x) {
				Vector next = nextX(x);
				x = next.x;
				iter++;
//				System.out.println(x + " yval: " + calcY(x));
			}
			zero.add(0, iter);
			zero.add(0, x);
		} else {
			zero.add(0, -1.0);
			zero.add(0, 0.0);
		}

		return zero;
	}

	/**
	 * Finds the next x value by finding the tangent line (y-y0 = m(x-x0)) and finding the x intersect (y=0). 
	 * 
	 * @param x the starting x value
	 * @return a Point containing the x value and the slope of the line
	 */
	public Vector nextX(double x0) {
		double m = calcYDeriv(x0), y0 = calcY(x0);
		return new Vector(-y0/m+x0, m);
	}

}
