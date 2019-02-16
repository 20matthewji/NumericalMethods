package function;

/**
 * The Vector class serves as a container which holds the x, y, and z coordinates. Data structures 
 * that need to store coordinates can use this class instead of creating seperate coordinate lists.
 * 
 * Name changed from Point class to Vector class on 1/2/18. Any documentation with referring to the 
 * "Point" class is referring to this class.
 * 
 * @author Matthew Jin
 * @version 9/5/17
 */
public class Vector
{
	
	public double x;
	public double y;
	public double z;
	
	/**
	 * Default constructor for the Point class which sets the x and y values to the origin (0, 0).
	 */
	public Vector()
	{
		x = 0;
		y = 0;
		z = 0;
	}
	
	/**
	 * Constructor for the Point class which sets the x and y values.
	 * 
	 * @param x the x coordinate
	 * @param y the y coordinate
	 */
	public Vector(double x, double y)
	{
		this.x = x;
		this.y = y;
		this.z = 0;
	}
	
	/**
	 * Constructor for the Point class which sets the x, y, and z values.
	 * 
	 * @param x the x coordinate
	 * @param y the y coordinate
	 * @param z the z coordinate
	 */
	public Vector(double x, double y, double z)
	{
	    this.x = x;
	    this.y = y;
	    this.z = z;
	}
	
	/**
	 * Sets the x and y values of the Point to the new x and y values.
	 * 
	 * @param x the new x value
	 * @param y the new y value
	 */
	public void set(double x, double y)
	{
	    this.x = x;
		this.y = y;
	}
	
	/**
	 * Sets the x, y, and z values of the Point to the new x, y, and z values.
	 * 
	 * @param x the new x value
	 * @param y the new y value
	 * @param z the new z value
	 */
	public void set(double x, double y, double z)
	{
	    this.x = x;
	    this.y = y;
	    this.z = z;
	}
	
	/**
	 * Sets the x and y values of the Point to a random x and y value between min and max.
	 * (min <= x < max && min <= y < max)
	 * 
	 * @param min the minimum of the x, y, and z values
	 * @param max the maximum of the x, y, and z values
	 */
	public void random(double min, double max)
	{
		x = Math.random()*max + min;
		y = Math.random()*max + min;
		z = Math.random()*max + min;
	}
	
	/**
	 * Calculates the magnitude of a vector by taking the square root of the sum of the squares 
	 * of each component.
	 * 
	 * @return the magnitude of the vector
	 */
	public double magnitude()
	{
	    return Math.sqrt(x*x+y*y+z*z);
	}
	
	/**
	 * Checks to see if the position of this vector is equal to the position of another vector.
	 * 
	 * @return true if their positions are equal; otherwise, false
	 */
	public boolean equal(Vector v)
	{
	    return x==v.x && y==v.y && z==v.z;
	}
	
	/**
	 * Adds two vectors together by adding their x, y, and z components.
	 * 
	 * @param v the vector being added
	 * @return the sum of the vectors
	 */
	public Vector add(Vector v)
	{
	    return new Vector(x+v.x, y+v.y, z+v.z);
	}
	
	/**
	 * Subtracts two vectors by subtracting their x, y, and z components.
	 * 
	 * @param v the vector being subtracted
	 * @return the difference of the vectors
	 */
	public Vector sub(Vector v)
	{
	    return new Vector(x-v.x, y-v.y, z-v.z);
	}
	
	/**
	 * Multiplies the vector by a scalar by multiplying each vector component by the scalar.
	 * 
	 * @param s the scalar
	 * @return the vector multiplied by the scalar
	 */
	public Vector mult(double s)
	{
	    return new Vector(s*x, s*y, s*z);
	}
	
	/**
	 * Returns a string representing a Vector
	 * 
	 * @return a string representing the vector
	 */
	public String toString()
	{
	    return "{" + x + ", " + y + ", " + z + "}";
	}
	
}
