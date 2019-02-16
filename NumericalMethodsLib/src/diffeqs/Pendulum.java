package diffeqs;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;

import function.Vector;

/**
 * This class models the motion of a pendulum. It takes in six parameters from an input file: the 
 * first is the duration of the run, the second is delta time, the third is the length of the 
 * pendulum, the fourth is the gravitational acceleration, the fifth is the initial angle of the 
 * pendulum, and the sixth is its initial angular speed. This class will print out the angular 
 * position of the pendulum after each iteration. The output should fit a sine or cosine function 
 * and will be printed into an output file.
 * 
 * If the value of step is too large, this program will provide meaningless results. In order to 
 * ensure accurate results, keep the value of step below 0.1.
 * 
 * For more information on this project, reference the "Simple Pendulum v2.pdf" handout on athena.
 * 
 * @author Matthew Jin
 * @version 11/17/17
 */
public class Pendulum
{

    private static final String FILEPATH_INPUT = "src/diffeqs/input.txt";
    private static final String FILEPATH_OUTPUT = "src/diffeqs/output.txt";
    
    /**
     * The swing method calculates the angular position of the pendulum after some time t by using 
     * an equation obtained by solving a differential equation. The time starts at 0, increases by 
     * delta_t each time the calculation is repeated and ends when it reaches the duration. The 
     * times and their corresponding position are stored in an ArrayList of Points, where the x 
     * value is the time and the y value is the position of the pendulum at that time.
     * 
     * @precondition init_theta is in degrees
     * @precondition step is less or equal to 0.1
     * 
     * @param iter the maximum number of iterations
     * @param step the step between each iteration
     * @param l the length of the pendulum
     * @param g the gravitational acceleration
     * @param init_theta the initial position of the pendulum
     * @param init_omega the initial speed of the pendulum
     * @return an ArrayList of Points containing the time in the x value and the angular position 
     * in the y value
     */
    public ArrayList<Vector> swing(double duration, double delta_t, double l, double g, 
                                  double init_theta, double init_omega)
    {
        ArrayList<Vector> pos = new ArrayList<Vector>();
        
        double t = 0, theta = Math.toRadians(init_theta), omega = init_omega;
        while(t<duration) {
            pos.add(new Vector(t, Math.toDegrees(theta)));
            omega = omega - g/l*Math.sin(theta)*delta_t;
            theta = theta + omega*delta_t;
            t += delta_t;
        }
        
        return pos;
    }
    
    /**
     * The main method reads the six parameters from the input file and calls the swing method to 
     * calculate the motion of a pendulum. The set of points returned by the swing method will be 
     * printed into an output file.
     */
    public static void main(String[] args) throws IOException
    {
        FileReader fr = new FileReader(FILEPATH_INPUT);
        BufferedReader br = new BufferedReader(fr);
        
        FileOutputStream fos = new FileOutputStream(FILEPATH_OUTPUT);
        PrintStream out = new PrintStream(fos);
        
        double duration = Double.parseDouble(br.readLine());
        double delta_t = Double.parseDouble(br.readLine());
        double length = Double.parseDouble(br.readLine());
        double gravconst = Double.parseDouble(br.readLine());
        double theta = Double.parseDouble(br.readLine());
        double omega = Double.parseDouble(br.readLine());
        
        Pendulum p = new Pendulum();
        
        ArrayList<Vector> list = p.swing(duration, delta_t, length, gravconst, theta, omega);
        
        for(int i=0; i<list.size(); i++) {
            //System.out.println(list.get(i).x);
            //out.println(list.get(i).y);
            out.println(list.get(i).x + " " + list.get(i).y);
        }
    }
    
}
