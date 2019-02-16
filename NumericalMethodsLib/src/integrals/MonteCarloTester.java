package integrals;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;

import function.Vector;

/**
 * Tester for the MonteCarlo class. Uses the Monte Carlo method to calculate hte value of PI.
 * 
 * @author Matthew Jin
 * @version 11/7/17
 */
public class MonteCarloTester
{

	private static final String FILEPATH = "src/integrals/output.txt";
	public static final double PI = Math.acos(-1); 

	/**
	 * The main method runs the tests for the MonteCarlo class. It will calculate the value of PI 
	 * using the MonteCarlo method from 10000 iterations to 10000000 iterations.
	 */
	public static void main(String[] args) throws FileNotFoundException
	{

		double r = 1;
		double maxiters = 10000000;

		FileOutputStream fos = new FileOutputStream(FILEPATH);
		PrintStream out = new PrintStream(fos);
		
		MonteCarlo mc = new MonteCarlo();
		
		int[] randnums = mc.RNG(maxiters);
		
		for(int i=0; i<10; i++)
		{
			System.out.println(i + ": " + randnums[i]);
		}
		
		System.exit(0);

		System.out.println("Math PI: " + PI);

		int iter = 10000;
		while(iter<=maxiters)
		{
			System.out.println(iter);
			out.println(PI-mc.getPi(r, iter));
			iter += 10000;
		}

	}

}
