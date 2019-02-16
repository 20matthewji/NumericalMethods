package extrema;

import java.util.ArrayList;

import derivatives.Derivative;
import function.Vector;

/**
 * Min class
 * 
 * @author Matthew Jin
 * @version 10/17/17
 */
public class Min extends Derivative {

	private static double MIN = Math.pow(10, -10);

	/**
	 * Constructor for the Min class
	 */
	public Min(String str) {
		super(0, 0, 0, str);
	}

	/**
	 * Finds the minimum. Uses Newton's Method and the five point stencil method.
	 * 
	 * @precondition derivative and double derivative has to be continuous
	 * 
	 * @return the min within the given domain
	 */
	public ArrayList<Double> findExtrema(double start, double inc, double maxiter) {
		this.inc = inc;
		ArrayList<Double> extrema = new ArrayList<Double>();
		double x = start, iter = 0;

		while(Math.abs(calcDeriv(x))>MIN && !Double.isInfinite(calcY(x)) && iter<maxiter) {
			Vector next = nextX(x);
			x = next.x;
			iter++;
//			System.out.println(x + ", " + calcY(x));
		}
		extrema.add(x);
		
		if(calcDoubleDeriv(x)<0 || 
				(Double.isInfinite(calcY(x)) || Double.isNaN(calcY(x))) || 
				(calcDeriv(x-inc)<0 && calcDeriv(x)<0 && calcDeriv(x+inc)<0) || 
				(calcDeriv(x-inc)>0 && calcDeriv(x)>0 && calcDeriv(x+inc)>0)) {
			extrema.add(0.0);
		} else {
			extrema.add(-1.0);
		}
		extrema.add(iter);

		return extrema;
	}

	public Vector nextX(double x0) {
		double m = calcDoubleDeriv(x0), y0 = calcDeriv(x0);
		return new Vector(-y0/m+x0, m);
	}

	public double calcDeriv(double x) {
		double y1 = calcY(x-2*inc), y2 = calcY(x-inc), y4 = calcY(x+inc), y5 = calcY(x+2*inc);
		return (-y5+8*y4-8*y2+y1)/(12*inc);
	}

	public double calcDoubleDeriv(double x) {
		double y1 = calcY(x-2*inc), y2 = calcY(x-inc), y3 = calcY(x), y4 = calcY(x+inc), y5 = calcY(x+2*inc);
		return (-y5+16*y4-30*y3+16*y2-y1)/(12*inc*inc);
	}

}
