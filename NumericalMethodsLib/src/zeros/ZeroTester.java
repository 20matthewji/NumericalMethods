package zeros;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * This class is the tester for the Bisection and NewtonsMethod classes for finding zeroes. In 
 * order to test the bisection and newtons method for finding zero, it will call the findZero() 
 * method with a hardcoded function and print out the results for the user to check if the result 
 * is accurate.
 * 
 * @author Matthew Jin
 * @version 9/19/17
 */
public class ZeroTester {

	/**
	 * The main method runs the tests for the Bisection and NewtonsMethod classes by plugging a 
	 * function into each method and finding the function's zero. The main method also allows the 
	 * user to choose the maximum number of iterations to find the zero. The result returned from 
	 * the class will be printed out as either a zero, a horizontal or vertical asymptote, or an 
	 * error if the findZero function could not find a zero or an asymptote.
	 */
	public static void main(String[] args) {
		String function = "other";
		double direction = 1.0; //Direction is either 1.0 (right) or -1.0 (left)

		Bisection b = new Bisection(function);
		NewtonsMethod nm = new NewtonsMethod(function);
		
		Scanner scanner = new Scanner(System.in);

		System.out.println("Choose the maximum number of iterations: ");
		double maxiter = Math.abs(scanner.nextDouble());
		ArrayList<Double> list = new ArrayList<Double>();
//		list = b.findZero(0.0, direction, maxiter);
		list = nm.findZero(1.0, maxiter);

		if(list==null) {
			System.out.println("The y value at this starting point is not valid (vertical asymptote, slope=0 (Newton's Method), etc). Choose a different starting point.");
		} else if(list.get(1)!=-1){
			if(Double.isInfinite(list.get(2))) {
				System.out.println("This program has found a vertical asymptote and has terminated");
			} else if(list.get(1)>=maxiter) {
				System.out.println("This program could not determine if there is a zero or an asymptote after " + list.get(1) + " iterations.");
				System.out.println("Try changing the starting point or increasing the number of iterations.");
			} else {
				System.out.println("There is a zero at x=" + list.get(0) + " that was found after " + list.get(1) + " iterations.");
			}
		} else {
			System.out.println("There is no zero because there is an asymptote in that direction.");
		}
	}

}
