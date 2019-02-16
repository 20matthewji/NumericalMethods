package leastsquares;

import java.util.ArrayList;

import function.Function;
import function.Vector;

/**
 * The LeastSquares class calculates the fit for a function over a given data set. It contains 
 * an ArrayList of points which hold the input data set and the number of parameters in the 
 * function. LeastSquares extends the Function class because the fit function is used to 
 * manipulate the parameters of a function to match a data set. 
 * 
 * @author Matthew Jin
 * @version 10/31/17
 */
public class LeastSquares extends Function {

	private ArrayList<Vector> points;
	private int numparams;

	private static double MIN = Math.pow(10, -10);

	/**
	 * Constructor for the LeastSquares class. It sets the derivative increment, the number of 
	 * parameters, and the number of data points. Calls super to initialize the derivative 
	 * increment.
	 * 
	 * @param inc the increment used to calculate the derivative of the error with respect to a 
	 * parameter
	 * @param numparams the number of parameters in the function that needs to be fit
	 * @param points the number of data points
	 */
	public LeastSquares(double inc, int numparams, ArrayList<Vector> points) {
		super(0, 0, inc, "");
		this.numparams = numparams;
		this.points = points;
	}

	/**
	 * Calculates the parameters for a function that would fit the input data set. The method used 
	 * to calculate the parameters is the steepest descent method. The function first initializes 
	 * all parameters to be equal to the initial parameter value. Then it will change the values 
	 * of the parameters until the error or the learning factor is close to zero, or if the number 
	 * of iterations has reached the maximum number of iterations allowed. The delta for each of 
	 * the parameters is calculated by taking the derivative of the error function with respect to 
	 * each parameter and multiplying that by the learning factor. Each parameter is then changed 
	 * by delta and the error function of the new set of parameters is calculated. If the error is 
	 * larger than the error of the original set of parameters, the change is reverted and the 
	 * learning factor is reduced by a factor of two. If the new error is smaller, the change is 
	 * kept and the learning factor is increased by a factor of two. Once one of the ending 
	 * criteria has been reached, an ArrayList containing the optimized parameters will be 
	 * returned.
	 * 
	 * @param maxiter the maximum number of iterations
	 * @param initparam the initial values of the parameters
	 * @return an ArrayList of doubles containing the values of each parameter
	 */
	public ArrayList<Double> fit(double maxiter, double initparam) {
		ArrayList<Double> params = new ArrayList<Double>();
		ArrayList<Double> deltas = new ArrayList<Double>();

		for(int i=0; i<numparams; i++) params.add(initparam);

		double lambda = 1; double err = Double.MAX_VALUE; double iter = 0;
		while(calcError(params)>MIN && lambda>MIN && iter<maxiter) {
			deltas.clear();
			err = calcError(params);
			for(int i=0; i<params.size(); i++) {
				deltas.add(lambda*calcDeriv(i, params));
				params.set(i, params.get(i)-deltas.get(i));
			}
			if(calcError(params)>err) {
				for(int i=0; i<params.size(); i++) {
					params.set(i, params.get(i)+deltas.get(i));
				}
				lambda /= 2;
			} else {
				lambda *= 2;
			}
			iter++;
//			System.out.println(iter + ": " + params + "\t" + calcError(params));
		}

		return params;
	}

	/**
	 * Calculates the error of the fit function versus the data set by summing the squaers of the 
	 * difference between the function's calculated y value and the y value of the data set.
	 * 
	 * @param params an ArrayList of parameters
	 * @return the value of the error function given the parameters
	 */
	public double calcError(ArrayList<Double> params) {
		double error = 0;
		for(int i=0; i<points.size(); i++) {
			double x = points.get(i).x, y = points.get(i).y;
			error += Math.pow(y-calcY(x, params), 2);
		}
		return error/2;
	}
	
	/**
	 * Calculates the derivative of the error function with respect to a parameter using the five 
	 * point stencil method.
	 * 
	 * @param index the index of the parameter
	 * @param vars an ArrayList of parameters
	 * @return the derivative of the error with respect to the parameter
	 */
	public double calcDeriv(int index, ArrayList<Double> vars) {
		ArrayList<Double> temp = (ArrayList<Double>) vars.clone();
		temp.set(index, vars.get(index)-2*inc);
		double y1 = calcError(temp);
		temp.set(index, vars.get(index)-inc);
		double y2 = calcError(temp);
		temp.set(index, vars.get(index)+inc);
		double y4 = calcError(temp);
		temp.set(index, vars.get(index)+2*inc);
		double y5 = calcError(temp);
		return (-y5+8*y4-8*y2+y1)/(12*inc);
	}
	
	/**
	 * Calculates the double derivative of the error function with respect to a parameter using 
	 * the five point stencil method.
	 * 
	 * @param index the index of the parameter
	 * @param vars an ArrayList of parameters
	 * @return the double derivative of the error with respect to the parameter
	 */
	public double calcDoubleDeriv(int index, ArrayList<Double> vars) {
		ArrayList<Double> temp = (ArrayList<Double>) vars.clone();
		temp.set(index, vars.get(index)-2*inc);
		double y1 = calcError(temp);
		temp.set(index, vars.get(index)-inc);
		double y2 = calcError(temp);
		temp.set(index, vars.get(index));
		double y3 = calcError(temp);
		temp.set(index, vars.get(index)+inc);
		double y4 = calcError(temp);
		temp.set(index, vars.get(index)+2*inc);
		double y5 = calcError(temp);
		return (-y5+16*y4-30*y3+16*y2-y1)/(12*inc*inc);
	}

	/**
	 * Calculates the y value of the fit function given a set of parameters and an x value. 
	 * 
	 * @param x the x value of the function
	 * @param params the list of parameters for the function
	 * @return the y value of the function given the x value and the parameters
	 */
	public double calcY(double x, ArrayList<Double> params) {
//		double y = 0;
//		for(int i=0; i<params.size(); i++) {
//			y += params.get(i)*Math.pow(x, i);
//		}
//		return y;
		return Math.pow(params.get(0), 2)*
				Math.pow(Math.E, -Math.pow(x-params.get(1), 2)/Math.pow(params.get(2), 2))
				+Math.pow(params.get(3), 2);
//		return params.get(0) 
//				+ params.get(1)*Math.pow(Math.E, -Math.pow(params.get(2)-x, 2)/Math.pow(params.get(3), 2))
//				+ params.get(4)*Math.pow(Math.E, -Math.pow(params.get(5)-x, 2)/Math.pow(params.get(6), 2));

	}
}
