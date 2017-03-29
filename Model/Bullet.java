package asteroids.model.representation;

import asteroids.model.exceptions.IllegalComponentException;
import asteroids.model.exceptions.IllegalPositionException;
import asteroids.model.exceptions.IllegalRadiusException;
import be.kuleuven.cs.som.annotate.Basic;

/**
 * A class representing a circular bullet dealing with
 * position, velocity, radius, density, and mass.
 * @invar  The minimal radius of each bullet must be a valid minimal radius for any bullet.
 *       | isValidMinimalRadius(getMinimalRadius())
 */

public class Bullet extends Entity {
	
	public Bullet(double xComPos, double yComPos, double xComVel, double yComVel, double radius, double density,
			double mass) throws IllegalComponentException, IllegalPositionException, IllegalRadiusException {
		super(xComPos, yComPos, xComVel, yComVel, radius, density, mass);
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * @return A copy of this bullet.
	 */
	@Override
	public Bullet copy() {
		return new Bullet(getPosition().getxComponent(), getPosition().getyComponent(), getVelocity().getxComponent(), getVelocity().getyComponent(),
				getRadius(), getDensity(), getMass());
	}
	
	/**
	 * Check whether this bullet can have the given mass as its mass.
	 * 
	 * @return true iff the given mass equals the volume of this bullet times its density.
	 * 			| @see implementation
	 */
	@Override
	public boolean canHaveAsMass(double mass) {
		return mass == getVolume() * getDensity();
	}
	
	/** 
	 * Check whether this bullet can have the given density as its density
	 * @return True iff the given density is equal to the minimal density
	 * 			| @see implementation
	 */
	@Override
	public boolean canHaveAsDensity(double density) {
		return density == getMinimalDensity();
	}
	
	/**
	 * Return the minimal density of this bullet.
	 */
	@Override
	public double getMinimalDensity() {
		return minimalDensity;
	}
	
	private final double minimalDensity = 7.8e12;
	
	/**
	 * @param radius
	 * 			The radius to check.
	 * @return @see implementation.
	 */
	@Override
	public boolean canHaveAsRadius(double radius) {
		return radius >= getMinimalRadius();
	}
	
	/**
	 * Return the minimal radius of any bullet.
	 */
	@Basic
	public static double getMinimalRadius() {
		return minimalRadius;
	}
	
	/**
	 * Check whether the given minimal radius is a valid minimal radius for any bullet.
	 *  
	 * @param  minimal radius
	 *         The minimal radius to check.
	 * @return true iff the given minimalRadius is greater than getMinimalRadius().
	 *       | result == minimalRadius >= getMinimalRadius()
	 */
	public static boolean isValidMinimalRadius(double minimalRadius) {
		return minimalRadius >= Bullet.getMinimalRadius();
	}
	
	/**
	 * Set the minimal radius of any bullet to the given minimal radius.
	 * 
	 * @param  minimalRadius
	 *         The new minimal radius for a bullet.
	 * @post   The minimal radius of any bullet is equal to the given minimal radius.
	 *       | Ship.getMinimalRadius() == minimalRadius
	 * @throws IllegalArgumentException
	 *         The given minimal radius is not a valid minimal radius for any bullet.
	 *       | ! isValidMinimalRadius(getMinimalRadius())
	 */
	public static void setMinimalRadius(double minimalRadius) throws IllegalArgumentException {
		if (! isValidMinimalRadius(minimalRadius))
			throw new IllegalArgumentException();
		Bullet.minimalRadius = minimalRadius;
	}
	
	/**
	 * Variable registering the minimal radius of this bullet.
	 */
	private static double minimalRadius = 1;
}
