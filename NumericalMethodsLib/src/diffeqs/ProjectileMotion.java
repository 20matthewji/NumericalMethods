package diffeqs;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

import function.Vector;

/**
 * This class models the motion of a spherical projectile. It takes in a total of fourteen total 
 * parameters from an input file:
 * 1. model (air density model, 1 for constant density, 2 for adiabatic density profile, 3 for 
 * isothermal density profile, and any other number for no air)
 * 2. dt or ∆t (change in time in seconds)
 * 3. r (radius of the projectile in meters)
 * 4. m (mass of the projectile in kilograms)
 * 5. v (initial velocity of the projectile in meters/second)
 * 6. theta or θ (initial angle of the projectile in degrees)
 * 7. x (initial x position of the projectile in meters)
 * 8. y (initial y position of the projectile in meters)
 * 9. g (gravitational acceleration in meters/second^2)
 * 10. T0 (temperature at sea level in Kelvin; used in the adiabatic density profile)
 * 11. a (constant used to calculate air density in the adiabatic density profile in Kelvin/meter)
 * 12. alpha or α (constant used to calculate air density in the adiabatic density profile)
 * 13. irho or ρ0 (density of air at sea level in kilograms/meter^3; used in constant density and 
 * isothermal density profiles)
 * 14. iy or y0 (constant used to calculate air density in an isothermal density profile in meters)
 * The first eight parameters define the properties of the projectile, as well as the air density 
 * model and the delta time. These parameters are also the ones that are going to be changed more 
 * often. The last six parameters are constants that define the environment the projectile is 
 * traveling in and are kept as instance variables.
 * 
 * Parameters can also be changed in the interface. In order to update the parameters and run the 
 * simulation with the new parameters, change the values in the corresponding boxes and click 
 * "Run Simulation." Please let the simulation finish running before starting another one.
 * 
 * This class will print out the x and y positions of the projectile with respect to time as its 
 * output.
 * 
 * If the value of ∆t is too large, this program will provide inaccurate results. In order to 
 * ensure accurate results, it is recommended to keep the value of ∆t below 0.1.
 * 
 * For more information on this project, read the "Projectile Motion v2.pdf" file on athena.
 * 
 * @author Matthew Jin
 * @version 11/30/17
 */
public class ProjectileMotion
{

    private static final String FILEPATH_INPUT = "src/diffeqs/input.txt";
    private static final String FILEPATH_OUTPUT = "src/diffeqs/output.txt";
    
    private double g;
    private double iT;
    private double a;
    private double alpha;
    private double irho;
    private double iy;
    
    private ProjectileDisplay display;
    
    /**
     * Constructor for the ProjectileMotion class. It initializes all the constants that are 
     * related to the environment that the projectile is launched on. The ProjectileDisplay object 
     * is also initialized in the constructor.
     * 
     * @param g gravitational acceleration in meters/second^2
     * @param iT temperature at sea level in Kelvin
     * @param a constant used to calculate air density in an adiabatic density profile with units 
     * of Kelvin/meter
     * @param alpha constant used to calculate air density in the adiabatic density profile
     * @param irho density of air at sea level in kilograms/meter^3
     * @param iy constant used to calculate air density in the isothermal density profile with 
     * units of meters 
     */
    public ProjectileMotion(double g, double iT, double a, double alpha, double irho, double iy)
    {
        this.g = g;
        this.iT = iT;
        this.a = a;
        this.alpha = alpha;
        this.irho = irho;
        this.iy = iy;
        
        display = new ProjectileDisplay(this);
    }        
    
    /**
     * The move method calculates the position of the projectile with respect to time by using 
     * equations obtained by solving differential equations. The time starts at 0, increases by 
     * dt each time the calculation is repeated, and ends when it reaches the duration. The times 
     * and their corresponding position are stored in an TreeMap that maps Doubles to Points. The 
     * key of the TreeMap is the time and the value is the position of th projectile at that time.
     * 
     * Refer to "Projectile Motion v2.pdf" on athena for more information on the equations used to 
     * calculate the motion of the projectile.
     * 
     * @precondition theta is in degrees
     * @precondition dt is less or equal to 0.1
     * 
     * @param model the air density model used for the simulation
     * @param dt the change in time in seconds
     * @param R the radius of the projectile in meters
     * @param m the mass of the projectile in kilograms
     * @param v the initial velocity of the projectile in meters/second
     * @param theta the initial angle of the projectile in degrees
     * @param x the initial x position of the projectile in meters
     * @param y the initial y position of the projectile in meters
     * 
     * @return a TreeMap<Double, Point> that maps time to position
     */
    public TreeMap<Double, Vector> move(double model, double dt, double R, double m, double v, double theta, double x, double y)
    {
        theta = Math.toRadians(theta);
        
        TreeMap<Double, Vector> pos = new TreeMap<Double, Vector>();
        pos.put(0.0, new Vector(x, y));
        
        double t = 0;
        double vx = v*Math.cos(theta), vy = v*Math.sin(theta);
        while(y>=0) {
            t += dt;

            double rho = calcDensity(model, y);
           
            vx = vx - (1/m*2*Math.PI*R*R*rho*v*vx)*dt;
            vy = vy - (1/m*2*Math.PI*R*R*rho*v*vy+g)*dt;
            v = Math.sqrt(vx*vx + vy*vy);
            
            x = x + vx*dt;
            y = y + vy*dt;
            
            pos.put(t, new Vector(x, y));
        }
        
        return pos;
    }
    
    /**
     * Calculates the air density for a given air density model and projectile height.
     * 
     * @param model the model used to calculate air density; 1 for constant density, 2 for 
     * adiabatic density, 3 for isothermal density, and any other number for no air
     * @param y the height of the projectile
     * 
     * @return the density of the air in the given model at the given height
     */
    public double calcDensity(double model, double y)
    {
        if(model==1)
            return irho;
        else if(model==2)
            return irho*Math.pow((1-a*y/iT), alpha);
        else if(model==3)
            return irho*Math.exp(-y/iy);
        else 
            return 0;
    }
    
    /**
     * Sets the new a constant used to calculate air density in the adiabatic density profile.
     * 
     * @param a the new a constant for the adiabatic density profile in Kelvin/meter
     */
    public void setA(double a)
    {
        this.a = a;
    }
    
    /**
     * Sets the new alpha constant used to calculate air density in the adiabatic density profile.
     * 
     * @param alpha the new alpha constant for the adiabatic density profile
     */
    public void setAlpha(double alpha)
    {
        this.alpha = alpha;
    }
    
    /**
     * Sets the y0 constant used to calculate air density in the isothermal density profile.
     * 
     * @param iy the new y0 constant for the isothermal density profile in meters
     */
    public void setInitY(double iy)
    {
        this.iy = iy;
    }
    
    /**
     * Sets the temperature at sea level.
     * 
     * @param iT the new temperature at sea level in Kelvin
     */
    public void setInitT(double iT)
    {
        this.iT = iT;
    }
    
    /**
     * Sets the air density at sea level.
     * 
     * @param irho the new air density at sea level in kilograms/meter^3
     */
    public void setInitRho(double irho)
    {
        this.irho = irho;
    }
    
    /**
     * Sets the gravitational acceleration.
     * 
     * @param g the new gravitational acceleration in meters/second^2
     */
    public void setG(double g)
    {
        this.g = g;    
    }
    
    /**
     * Prints out the contents of the TreeMap, which maps time to the position of the projectile.
     * The results are printed out into an output file.
     * 
     * @param map the map containing the output of the move function
     */
    public void printOutput(TreeMap<Double, Vector> map) throws FileNotFoundException
    {
        FileOutputStream fos = new FileOutputStream(FILEPATH_OUTPUT);
        PrintStream out = new PrintStream(fos);
        
        Iterator<Map.Entry<Double, Vector>> it = map.entrySet().iterator();
        while(it.hasNext()) {
            Map.Entry<Double, Vector> pair = it.next();
            out.println(pair.getKey() + ": " + pair.getValue().x + ", " + pair.getValue().y);
            //System.out.println(pair.getValue().x);
            //out.println(pair.getValue().y);
        }
    }
    
    /**
     * The main method reads the fourteen parameters from the input file and calls the move method 
     * to calculate the motion of a projectile. The set of time and position pairs returned by the 
     * move method will be printed into an output file.
     */
    public static void main(String[] args) throws IOException
    {
        FileReader fr = new FileReader(FILEPATH_INPUT);
        BufferedReader br = new BufferedReader(fr);
        
        double model = Double.parseDouble(br.readLine());
        double dt = Double.parseDouble(br.readLine());
        double R = Double.parseDouble(br.readLine());
        double m = Double.parseDouble(br.readLine());
        double v = Double.parseDouble(br.readLine());
        double theta = Double.parseDouble(br.readLine());
        double x = Double.parseDouble(br.readLine());
        double y = Double.parseDouble(br.readLine());
        
        double g = Double.parseDouble(br.readLine());
        double iT = Double.parseDouble(br.readLine()); 
        double a = Double.parseDouble(br.readLine());
        double alpha = Double.parseDouble(br.readLine());
        double irho = Double.parseDouble(br.readLine());
        double iy = Double.parseDouble(br.readLine());
        
        ProjectileMotion proj = new ProjectileMotion(g, iT, a, alpha, irho, iy);
        
        TreeMap<Double, Vector> map = proj.move(model, dt, R, m, v, theta, x, y);
        
        proj.printOutput(map);
    }
    
}
