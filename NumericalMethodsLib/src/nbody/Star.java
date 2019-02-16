package nbody;

import function.Vector;

/**
 * The Star class is a subclass of the Body class that takes in parameters with different units. 
 * The units for position in the Star class are parsecs, the units for velocity is kilometers per 
 * second, and the mass is in solar masses. See the documentation for the Body class for more 
 * information.
 * 
 * @author Matthew Jin
 * @version 1/2/2018
 */
public class Star extends Body
{
 
    private final static double PARSEC_TO_METER = 3.086e+16;
    private final static double KMPS_TO_MPS = 1000;
    private final static double SOLARMASS_TO_KG = 1.98855e+30;

    /**
     * Constructor fo the Star class which converts the units of the position from parsecs to 
     * meters, velocity from kilometers per second to meters per second, and mass from solar 
     * masses to kilograms.
     * 
     * @param pos the initial position in parsecs
     * @param vel the initial velocity in kilometers per second
     * @param mass the mass in solar masses
     */
    public Star(Vector pos, Vector vel, double mass)
    {
        super(pos.mult(PARSEC_TO_METER), vel.mult(KMPS_TO_MPS), mass*SOLARMASS_TO_KG);
    }
    
    /**
     * Sets a random position, velocity, and mass for this body
     * 
     * @param minp the minimum position
     * @param maxp the maximum position
     * @param minv the minimum velocity
     * @param maxv the maximum velocity
     * @param minm the minimum mass
     * @param maxm the maximum mass
     */
    public void random(double minp, double maxp, double minv, double maxv, double minm, double maxm)
    {
        minp *= PARSEC_TO_METER;
        maxp *= PARSEC_TO_METER;
        
        minv *= KMPS_TO_MPS;
        maxv *= KMPS_TO_MPS;
        
        minm *= SOLARMASS_TO_KG;
        maxm *= SOLARMASS_TO_KG;
        
        super.random(minp, maxp, minv, maxv, minm, maxm);
    }
}
