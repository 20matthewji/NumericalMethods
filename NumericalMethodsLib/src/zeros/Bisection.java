package zeros;

import java.util.ArrayList;

import derivatives.Derivative;
import function.*;

/**
 * The Bisection class uses the bisection method to find one zero in a function given a starting 
 * point and a direction. Uses the findZero() method to move the x value along a graph until it 
 * either finds an asymptote or reaches the maximum number of iterations. If there is no asymptote 
 * it will attempt to find the zero and return its result. This class extends the Derivative class 
 * because it needs to use the Derivative class's methods used to calculate the y values of the 
 * derivatives of functions.
 * 
 * @author Matthew Jin
 * @version 9/19/17
 */
public class Bisection extends Derivative {

	private static final double MIN = Math.pow(10, -10);

	/**
	 * Constructor for the Bisection class. Calls super to initialize the string which contains 
	 * the name of the function. Sets the start, end, and inc of the derivative to zero because 
	 * those variables are not needed.
	 * 
	 * @param str the function to find the zero on
	 */
	public Bisection(String str) {
		super(0, 0, 0, str);
	}

	/**
	 * The findZero() method calculates the zero for a specific function using the Bisection 
	 * method. The function first checks if the derivative of the function reaches zero (if it 
	 * is less than MIN) or if it reaches the maximum number of iterations. If the derivative is 
	 * less than MIN, the function returns an ArrayList with the number of iterations set to -1. 
	 * If the number of iterations reaches the maximum and the function has not found a zero, it 
	 * returns the the number of iterations as the maximum. If the function has found a zero, it 
	 * will return the x value of the zero and the number of iterations it took to find the zero.
	 * 
	 * @param start the starting x value on the function
	 * @param dir the direction to search for the zero
	 * @param maxiter the maximum number of iterations
	 * @return an ArrayList of Doubles containing information about a zero found on the function. The 
	 * first value is the zero, the second is the number of iterations, and the third is the derivative.
	 */
	public ArrayList<Double> findZero(double start, double dir, double maxiter) {
		ArrayList<Double> zero = new ArrayList<Double>();
		double x = start, dx = dir, prevx = x, iter = 0;

		double deriv = 1, sign = calcY(start)/Math.abs(calcY(start)); dx = dir;

		if(Double.isNaN(sign) || Double.isInfinite(sign)) {
			return null;
		}

		while((Math.abs(deriv)>MIN && (calcY(x)==0 || sign==calcY(x)/Math.abs(calcY(x)))) && iter<maxiter) {
			if(calcY(x)!=0) {
				prevx = x;
			}
			x += dx;
			deriv = (calcYDeriv(x)-calcYDeriv(prevx))/(x-prevx);
			iter++;
		}
		zero.add(deriv);
//		System.out.println(deriv + ", " + iter + " : " + sign + ", " + calcY(x)/Math.abs(calcY(x)));

		if(Math.abs(deriv)>MIN) {
			if(iter<maxiter) {
				x = start; iter = 0;
				while((calcY(x)!=0 && x+dx/2!=x)) {
					Vector p = moveX(x, dx);
					x = p.x; dx = p.y;
					iter++;
				}
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
	 * Moves the initial x a distance dx to the final x. If both the initial and final x values 
	 * are the same sign, the function returns the final x and the same dx. Ifi the initial and 
	 * final x values are different signs, the final x is returned and the dx returned is half of 
	 * the the initial dx and in the opposite direction (-dx/2). If neither of those conditions 
	 * are met, the function returns null.
	 * 
	 * @param x the initial x value
	 * @param dx the distance to move the x value
	 * @return a Point containing the new x value and the new dx value
	 */
	private Vector moveX(double x, double dx) {
		if((calcY(x)>0 && calcY(x+dx)>=0) || (calcY(x)<0 && calcY(x+dx)<=0)) {
			return new Vector(x+dx, dx);
		} else if((calcY(x)>0 && calcY(x+dx)<=0) || (calcY(x)<0 && calcY(x+dx)>=0)) {
			return new Vector(x+dx, -dx/2);
		} else {
			return null;
		}
	}
	
}