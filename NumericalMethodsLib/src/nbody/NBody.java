package nbody;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;

import function.Vector;

/**
 * This class models the motion a number of bodies in an empty space. It takes in parameters from 
 * an input file.
 * 
 * The first line of the input file determines whether the positions of the bodies will be entered 
 * manually or if they will be randomly determined. The second line is the time step for the 
 * simulation, and the third line is the maximum time to run. The fourth line is the time interval 
 * between printing the body positions. The fifth line is the number of bodies that are going to 
 * be simulated. If the bodies are manually inputted, every three lines after the fifth represent 
 * one body. The first of the three is the position vector, with each coordinate separated by a 
 * space. The next line is the velocity vector, and the final line is the mass. If the bodies are 
 * randomly generated, the next three lines represent the minimum and maximum values of the 
 * position, velocity, and mass respectively.
 * 
 * This class will print out the x, y, and z positions of all the bodies as its output.
 * 
 * For more information on this project, read the "N-Body v1.pdf" file on athena.
 * 
 * @author Matthew Jin
 * @version 1/2/2018
 */
public class NBody
{

    private final static String FILEPATH_INPUT = "src/nbody/input.txt";
    private final static String FILEPATH_OUTPUT = "src/nbody/output.txt";

    private final static double PARSEC_TO_METER = 3.086e+16;
    private final static double KMPS_TO_MPS = 1000;
    private final static double SOLARMASS_TO_KG = 1.98855e+30;
    private final static double G = 6.67408e-11;

    /**
     * The update method calculates the forces between all the different bodies in the system. 
     * After calculating the force, this method will update the velocities and positions of each 
     * body with their new velocities and positions. Then, it will check to see if any of the 
     * bodies have collided. If there is an collision, conservation of momentum will be used to 
     * calculate the velocity and position of the new body. The collisions are considered to be 
     * inelastic
     * 
     * Refer to "N-Body v1.pdf" on athena for more information on the equations used to simulate 
     * n-body problem.
     * 
     * @param bodies the ArrayList of Bodies
     * @param dt the change in time in seconds
     * 
     * @return an ArrayList of Bodies with the new positions, velocities, and masses 
     */
    public ArrayList<Body> update(ArrayList<Body> bodies, double dt)
    {
        ArrayList<Body> newBodies = new ArrayList<Body>();

        for(int j=0; j<bodies.size(); j++)
        {
            Vector sum = new Vector();
            for(int i=0; i<bodies.size(); i++)
            {
                if(i!=j)
                {
                    Vector jpos = bodies.get(j).getPos(), ipos = bodies.get(i).getPos();
                    double imass = bodies.get(i).getMass();

                    Vector disp = jpos.sub(ipos);

                    double scalar = G*imass/Math.pow(disp.magnitude(), 3);
                    sum = sum.add(disp.mult(scalar));
                }
            }
            Vector newVel = bodies.get(j).getVel().sub(sum.mult(dt));
            Vector newPos = bodies.get(j).getPos().add(newVel.mult(dt));
            newBodies.add(new Body(newPos, newVel, bodies.get(j).getMass()));
        } //Loops through the array of bodies and updates the velocity and position of the bodies

        for(int j=0; j<bodies.size(); j++)
        {
            for(int i=0; i<bodies.size(); i++)
            {
                if(i!=j && bodies.get(j).getPos().equal(bodies.get(i).getPos()))
                {
                    Vector jvel = newBodies.get(j).getVel(), ivel = newBodies.get(i).getVel();
                    double jmass = newBodies.get(j).getMass(), imass = newBodies.get(i).getMass();

                    Vector jP = jvel.mult(jmass), iP = ivel.mult(imass);

                    newBodies.get(j).setVel(jP.add(iP).mult(1/(jmass+imass)));

                    newBodies.get(j).setMass(jmass + imass);
                    newBodies.get(i).setMass(0);
                }
            }
        } //Loops through the array of bodies and checks for collisions

        return newBodies;
    }

    /**
     * The simulate method repeatedly calls the update method in order to update the position 
     * vectors of the body. It run from t=0 to t=maxtime, with an interval of dt. It will also 
     * print the positions of the bodies into an output file.
     * 
     * @param bodies the ArrayList of bodies
     * @param dt the time step
     * @param maxtime the maximum time to run
     * @param print the time interval before printing the positions
     */
    public void simulate(ArrayList<Body> bodies, double dt, double maxtime, double printtime) throws FileNotFoundException
    {
        FileOutputStream fos = new FileOutputStream(FILEPATH_OUTPUT);
        PrintStream out = new PrintStream(fos);

        double t = 0; double count = printtime;
        while(t<=maxtime)
        {
            System.out.println(t);
            if(count<=printtime)
            {
                count += dt;
            } else {
                printPos(out, bodies);
                count = 0;
            }

            bodies = update(bodies, dt);
            t += dt;
        }
        printPos(out, bodies);
    }

    /**
     * Prints the positions of all the bodies
     * 
     * @param out the PrintStream that will print the output into the file
     * @param bodies the ArrayList of bodies
     */
    public void printPos(PrintStream out, ArrayList<Body> bodies) throws FileNotFoundException
    {
        for(int j=0; j<bodies.size(); j++)
        {
            out.println(bodies.get(j).getPos().mult(1/PARSEC_TO_METER) + ", ");
        }
//        out.println(bodies.get(1).getPos() + ", ");
    }
    
    /**
     * The main method reads the parameters from the input file and creates the ArrayList of 
     * Bodies. Then it calls the simulate method to simulate the movement of the bodies.
     */
    public static void main(String[] args) throws IOException
    {
        FileReader fr = new FileReader(FILEPATH_INPUT);
        BufferedReader br = new BufferedReader(fr);

        String str = br.readLine();

        double dt = Double.parseDouble(br.readLine());
        double maxt = Double.parseDouble(br.readLine());
        
        int printtime = Integer.parseInt(br.readLine());
        int numbodies = Integer.parseInt(br.readLine());
        
        ArrayList<Body> bodies = new ArrayList<Body>();
        
        if(str.equals("man"))
        {
            for(int i=0; i<numbodies; i++)
            {
                String p = br.readLine();
                int p1 = p.indexOf(" "), p2 = p.indexOf(" ", p1+1);

                Vector pos = new Vector(
                        Double.parseDouble(p.substring(0, p1)), 
                        Double.parseDouble(p.substring(p1, p2)),
                        Double.parseDouble(p.substring(p2)));

                String v = br.readLine();
                int v1 = v.indexOf(" "), v2 = v.indexOf(" ", v1+1);

                Vector vel = new Vector(
                        Double.parseDouble(v.substring(0, v1)), 
                        Double.parseDouble(v.substring(v1, v2)), 
                        Double.parseDouble(v.substring(v2)));

                double mass = Double.parseDouble(br.readLine());

                Star star = new Star(pos, vel, mass);

                bodies.add(star);
            } //Reads in the position, velocity, and mass for each body
        } else {
            String pos = br.readLine();
            String vel = br.readLine();
            String mass = br.readLine();
            
            double minp = Double.parseDouble(pos.substring(0, pos.indexOf(" ")));
            double maxp = Double.parseDouble(pos.substring(pos.indexOf(" ")+1));
            
            double minv = Double.parseDouble(vel.substring(0, vel.indexOf(" ")));
            double maxv = Double.parseDouble(vel.substring(vel.indexOf(" ")+1));
            
            double minm = Double.parseDouble(mass.substring(0, mass.indexOf(" ")));
            double maxm = Double.parseDouble(mass.substring(mass.indexOf(" ")+1));
            
            for(int i=0; i<numbodies; i++)
            {
                Star star = new Star(new Vector(), new Vector(), 0);
                
                star.random(minp, maxp, minv, maxv, minm, maxm);
                
                bodies.add(star);
            }
        }
        
        NBody nb = new NBody();
        
        for(int j=0; j<bodies.size(); j++)
        {
            System.out.println(bodies.get(j).getPos().mult(1/PARSEC_TO_METER) + ", ");
        }
        
        nb.simulate(bodies, dt, maxt, printtime);
    }

}
