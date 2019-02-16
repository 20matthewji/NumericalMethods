package diffeqs;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;

import function.Vector;

/**
 * This class models radioactive decay for an element. It takes in four parameters from an input 
 * file: the first is the number of iterations, the second is the step between the iterations, the 
 * third is the half-life of the element, and the fourth is the initial particle count. The half 
 * life of the element is used to calculate the decay constant, tau, of the element. This class 
 * will print out the number of atoms left after t seconds. This output should fit a natural 
 * exponential function and will be printed into an output file.
 * 
 * The element used to model radioactive decay is the Strontium-90 isotope, which has a half life 
 * of 28.79 years.
 * 
 * @author Matthew Jin
 * @version 11/13/17
 */
public class Radioactivity
{
    
    private static final String FILEPATH_INPUT = "src/diffeqs/input.txt";
    private static final String FILEPATH_OUTPUT = "src/diffeqs/output.txt";
    
    /**
     * The decay method calculates the number of atoms left after the initial number of atoms have 
     * decayed after some time t by using an equation obtained by solving a differential equation. 
     * t starts at 0 and increases by step each time the calculation is repeated, ending at iter. 
     * The times and their corresponding number of atoms are stored in an ArrayList of Points, 
     * where the x value is the time and the y value is the number of atoms left after that amount 
     * of time.
     * 
     * @param iter the total number of time iterations
     * @param step the step between each time iteration
     * @param tau the decay constant of the element
     * @param init the initial number of atoms of the element
     * @return an ArrayList of Points where the x value is the time and the y value is the number 
     * of atoms left after that amount of time.
     */
    public ArrayList<Vector> decay(double iter, double step, double tau, double init)
    {
        ArrayList<Vector> atoms = new ArrayList<Vector>();
        
        double t = 0, n = init;
        while(t<iter)
        {
            atoms.add(new Vector(t, n));
            n = n*(1-step/tau);
            t += step;
        }
        
        return atoms;
    }
    
    /**
     * The main method reads the input from the input file and calculates the decay constant by 
     * dividing the half-life by the natural log of 2. It will call the decay method to get the 
     * set of points that model the radioactive decay of that element and print them in an output 
     * file.
     */
    public static void main(String[] args) throws IOException
    {
        FileReader fr = new FileReader(FILEPATH_INPUT);
        BufferedReader br = new BufferedReader(fr);
        
        FileOutputStream fos = new FileOutputStream(FILEPATH_OUTPUT);
        PrintStream out = new PrintStream(fos);
        
        double iterations = Double.parseDouble(br.readLine());
        double steps = Double.parseDouble(br.readLine());
        double halflife = Double.parseDouble(br.readLine());
        double atoms = Double.parseDouble(br.readLine());
        
        Radioactivity r = new Radioactivity();
        
        double tau = halflife/Math.log(2);
        ArrayList<Vector> list = r.decay(iterations, steps, tau, atoms);
        
        for(int i=0; i<list.size(); i++) {
            out.println(list.get(i).x + " " + list.get(i).y);
        }
    }
    
}
