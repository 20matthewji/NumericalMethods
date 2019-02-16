package nbody;

import function.Vector;

/**
 * The Body class represents a body in the universe with a position vector, velocity vector, and 
 * mass. Radius is not included as a parameter because the bodies are considered to be points. The 
 * unit for the position vector is meters, the unit for the velocity vector is meters per second, 
 * and the unit for mass is kilograms.
 * 
 * @author Matthew Jin
 * @version 1/2/2018
 */
public class Body
{
    
    private Vector pos;
    private Vector vel;
    private double mass;
        
    /**
     * Constructor for the Point class which sets the position, velocity, and mass.
     * 
     * @param pos the position of the body in meters
     * @param vel the velocity of the body in meters per second
     * @param mass the mass of the body in kilograms
     */
    public Body(Vector pos, Vector vel, double mass)
    {
        this.pos = pos;
        this.vel = vel;
        this.mass = mass;
    }

    /**
     * Gets the position of the body
     * 
     * @return the position vector
     */
    public Vector getPos()
    {
        return pos;
    }

    /**
     * Sets the position of the body
     * 
     * @param pos the new position vector in meters
     */
    public void setPos(Vector pos)
    {
        this.pos = pos;
    }

    /**
     * Gets the velocity of the body
     * 
     * @return the velocity vector
     */
    public Vector getVel()
    {
        return vel;
    }

    /**
     * Sets the velocity of the body
     * 
     * @param vel the new velocity vector in meters per second
     */
    public void setVel(Vector vel)
    {
        this.vel = vel;
    }

    /**
     * Gets the mass of the body
     * 
     * @return the mass
     */
    public double getMass()
    {
        return mass;
    }

    /**
     * Sets the mass of the body
     * 
     * @param mass the new mass in kilograms
     */
    public void setMass(double mass)
    {
        this.mass = mass;
    }
    
    /**
     * Returns a string represting a Body
     * 
     * @return a string represting the body
     */
    public String toString()
    {
        return "[" + mass + "\t" + pos.toString() + "\t" + vel.toString() + "]";
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
        pos.random(minp, maxp);
        vel.random(minv, maxv);
        mass = Math.random()*maxm+minm;
    }
    
}
