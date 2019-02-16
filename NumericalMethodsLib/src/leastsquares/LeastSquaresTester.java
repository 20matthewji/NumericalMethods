package leastsquares;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import function.Vector;

/**
 * This class tests the LeastSquares class by calling the LeastSquares fit function to calculate 
 * the parameters of a function given a data set. It reads the data set, the number of iterations, 
 * and the initial values of the parameters from an input file. The main method runs the tests for 
 * the LeastSquares class and prints out the results.
 * 
 * @author Matthew Jin
 * @version 10/31/17
 */
public class LeastSquaresTester {

	private static final String FILEPATH = "src/leastsquares/input.txt";
	private static double maxiters = 0, initparam = 0;

	/**
	 * This is the main method for the LeastSquaresTester class. When it reads in the input file, 
	 * the first two doubles are the maximum number of iterations and the initial value of the 
	 * parameters respectively. In order to test the LeastSquares class, it will call the fit 
	 * function over a set of data and print out the parameters for the function. It will also 
	 * print out the error of the fit to the given data set.
	 */
	public static void main(String[] args) throws IOException {

		ArrayList<Vector> points = new ArrayList<Vector>(); 

		FileReader fr = new FileReader(FILEPATH);
		BufferedReader br = new BufferedReader(fr);
		
		String str;
		str = br.readLine();
		maxiters = Double.parseDouble(str.substring(0, str.indexOf(" ")));
		initparam = Double.parseDouble(str.substring(str.indexOf(" " )+1)); 
		while((str = br.readLine()) != null) {
			Vector p = new Vector(Double.parseDouble(str.substring(0, str.indexOf(" "))), 
					Double.parseDouble(str.substring(str.indexOf(" ")+1)));
			points.add(p);
		}

		LeastSquares ls = new LeastSquares(0.01, 4, points);

		ArrayList<Double> params = ls.fit(maxiters, initparam);

		System.out.println("PARAMETERS: " + params);
		System.out.println("ERROR: " + ls.calcError(params));

//		for(int i=1; i<=41; i++) {
//			System.out.println(i + "\t" + ls.calcY(i, params));
//		}

	}

}
