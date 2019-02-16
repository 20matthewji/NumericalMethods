package integrals;

import java.util.ArrayList;

import function.Vector;

/**
 * The MonteCarlo class uses the Monte Carlo method to calculate the area of regions.
 * 
 * @author Matthew Jin
 * @version 11/7/17
 */
public class MonteCarlo
{

    /**
     * Calculates the value of PI using the Monte Carlo method on a circle inscribed in a square.
     * 
     * @param r the radius of the circle inscribed in the square, also half the side length of the 
     * square
     * @param maxiters the number of points used in the Monte Carlo method
     * @return the calculated value of PI
     */
    public double getPi(double r, double maxiters)
    {
        double iter = 0; double points = 0;
        
        while(iter<maxiters)
        {
            Vector p = new Vector(); p.random(0, r);
            if(inCircle(p, r))
            {
                points++;
            }
            iter++;
        }
        
        return 4*points/maxiters;
    }
    
    /**
     * Checks to see if the randomly generated point is inside an inscribed circle of radius r.
     * 
     * @param p the randomly generated point
     * @param r the radius of the inscribed circle
     * @return true if the the point is inside the circle; otherwise, false
     */
    public boolean inCircle(Vector p, double r)
    {
        return p.x*p.x + p.y*p.y <= r*r;
    }
    
    /**
     * Generates a set of random numbers from 0 to 9 and returns an array with the frequency of 
     * each value.
     *  
     * @param maxiters number of random numbers to be generated
     * @return an array containing the frequencies from 0 to 9
     */
    public int[] RNG(double maxiters)
    {
        int[] randnums = new int[10];
        
        for(int i=0; i<maxiters; i++)
        {
            int rand = (int) Math.floor(Math.random()*10);
            randnums[rand]++;
        }
        
        return randnums;
    }
    
}
