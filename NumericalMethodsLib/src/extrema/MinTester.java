package extrema;

import java.util.ArrayList;

public class MinTester {

	public static void main(String[] args) {

		String function = "other";
		double maxiter = 10000;

		Min extrema = new Min(function);

		ArrayList<Double> minmax = extrema.findExtrema(-1, 0.1, maxiter);

//		for(int i=0; i<minmax.size(); i++) {
//			System.out.print(minmax.get(i) + ", ");
//		}
//		System.out.println("\n");

		if(minmax.get(1)==0 || minmax.get(2)==maxiter) {
			System.out.println("Could not find an minimum.");
		} else if(minmax.get(1)>0) {
			System.out.println("Found a local max at x=" + minmax.get(0));
		} else {
			System.out.println("Found a local min at x=" + minmax.get(0));
		}
		System.out.println("If the minimum is inaccurate, try changing the domain or the increment.");

	}

}
