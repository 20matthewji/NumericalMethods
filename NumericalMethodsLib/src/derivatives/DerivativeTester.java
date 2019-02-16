package derivatives;

import java.util.ArrayList;

import function.Function;
import function.Vector;

/**
 * This class compares the two point, three point, and five point derivatives with the 
 * function's actual derivative. It will call the functions' various calcDeriv() methods 
 * and store the results in an ArrayList of Points. This class also has functions which 
 * can calculate different errors such as the difference between the y values and the RMS 
 * error. The results can be printed out for evaluation and comparison.
 * 
 * @author Matthew Jin
 * @version 9/5/17
 */
public class DerivativeTester {

	/**
	 * The main method runs the code to calculate the derivatives and stores them 
	 * in ArrayLists. This method will store values for the actual derivative, the 
	 * two point derivative, the three point derivative, and the five point derivative.
	 */
	public static void main(String[] args) {
		String function = "gaussian";
		
		Derivative d = new Derivative(-10, 10, 0.1, function);
		TwoPoint d2 = new TwoPoint(-10.05, 10.05, 0.1, function);
		ThreePoint d3 = new ThreePoint(-10.1, 10.1, 0.1, function);
		FivePoint d5 = new FivePoint(-10.2, 10.2, 0.1, function);
		ParabolicFit pfit = new ParabolicFit(-10, 10, 0.1, function);
		
		ArrayList<Vector> deriv = d.calcDeriv();
		ArrayList<Vector> deriv2 = d2.calcDeriv();
		ArrayList<Vector> deriv3 = d3.calcDeriv();
		ArrayList<Vector> deriv5 = d5.calcDeriv();
		ArrayList<Vector> pfitderiv = pfit.calcDeriv();
		
		ArrayList<Vector> diff = difference(pfitderiv, deriv);

//		printMap(func);
//		printMap(deriv);
		
//		printMap(deriv2);
//		printMap(deriv3);
//		printMap(deriv5);
//		printMap(pfitderiv);
		
		printMap(diff);
		
//		System.out.println("2 point: " + RMS(deriv2, deriv) + ", " + deriv2.size());
//		System.out.println("3 point: " + RMS(deriv3, deriv) + ", " + deriv3.size());
//		System.out.println("5 point: " + RMS(deriv5, deriv) + ", " + deriv5.size());
//		System.out.println("parabolic fit: " + RMS(pfitderiv, deriv) + ", " + deriv2.size());
	}
	
	/**
	 * Prints out the coordinate points in the list in the format (x, y). It can 
	 * also print out the x and y values separately for copy-pasting. 
	 * 
	 * @param list the list of coordinate points 
	 */
	public static void printMap(ArrayList<Vector> list) {
		for(int i=0; i<list.size(); i++) {
//			System.out.println("(" + list.get(i).x + ", " + list.get(i).y + ")");
//			System.out.println(list.get(i).x);
			System.out.println(list.get(i).y);
		}
		System.out.println();
	}
	
	/**
	 * Calculates the difference between the y values of corresponding x values 
	 * between two sets of coordinates.
	 * 
	 * @precondition the observed and expected list should have the same size and x values.
	 * 
	 * @param obs the observed observed derivative coordinaets (from two, three, and five point derivatives)
	 * @param exp the expected derivative coordinates (from the derivative of the function)
	 * @return An ArrayList of Points with each y value as the difference between the corresponding 
	 * x values in the observed and expected lists.
	 */
	public static ArrayList<Vector> difference(ArrayList<Vector> obs, ArrayList<Vector> exp) {
		ArrayList<Vector> diff = new ArrayList<Vector>();
		
		for(int i=0; i<obs.size(); i++) {
			diff.add(new Vector(obs.get(i).x, obs.get(i).y-exp.get(i).y));
		}
		
		return diff;
	}
	
	/**
	 * Calculates the Root Mean Square deviation between the observed and expected derivative coordinates.
	 * 
	 * @precondition the observed and expected list should have the same size and x values.
	 * 
	 * @param obs the observed list of coordinates
	 * @param exp the expected list of coordinates
	 * @return the Root Mean Square error between the observed and expected coordinates
	 */
	public static double RMS(ArrayList<Vector> obs, ArrayList<Vector> exp) {
		double sumsquares = 0;
		
		for(int i=0; i<obs.size(); i++) {
			sumsquares += Math.pow(obs.get(i).y-exp.get(i).y, 2);
		}
		
		return Math.sqrt(sumsquares/obs.size());
	}

}
