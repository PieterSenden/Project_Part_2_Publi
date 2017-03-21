package asteroids.model.representation;

import asteroids.model.exceptions.IllegalComponentException;
import asteroids.model.exceptions.IllegalRadiusException;
import asteroids.model.exceptions.OverlapException;
import be.kuleuven.cs.som.annotate.*;

/**
 * A class representing a circular space ship dealing with
 * position, velocity, orientation, radius, density, mass and total mass.
 * 
 * @invar  The position of each ship must be a valid position for any ship.
 *       | isValidPosition(getPosition())
 * @invar  The orientation of each ship must be a valid orientation for any ship.
 *       | isValidOrientation(getOrientation())
 * @invar  The radius of each ship must be a valid radius for any ship.
 *       | isValidRadius(this.getRadius())
 * @invar  The minimal radius of each ship must be a valid minimal radius for any ship.
 *       | isValidMinimalRadius(getMinimalRadius())
 * @invar  Each ship can have its velocity as velocity
 *       | canHaveAsVelocity(getVelocity())
 * @invar  Each ship can have its speed limit as speed limit .
 *       | canHaveAsSpeedLimit(this.getSpeedLimit())
 * 
 * @author Joris Ceulemans & Pieter Senden
 * @version 2.0
 *
 */


public class Ship extends Entity {
	
	/**
	 * Initialize this new ship with given xCoordinate, yCoordinate, velocity with xComponent and yComponent, radius and orientation
	 * @param xCoordinate
	 * 			xCoordinate of this new ship
	 * @param yCoordinate
	 * 			yCoordinate of this new ship
	 * @param xComponent
	 * 			xComponent of the velocity of this new ship
	 * @param yComponent
	 * 			yComponent of the velocity of this new ship
	 * @param radius
	 * 			radius of this new ship
	 * @param orientation
	 * 			orientation of this new ship
	 * @effect The position of this new ship is set to a new position with given xCoordinate and yCoordinate
	 * 			| setPosition(xCoordinate, yCoordinate)
	 * @effect The velocity of this new ship is set to a new velocity with given xComponent and yComponent
	 * 			| setVelocity(xComponent, yComponent)
	 * @effect The orientation of this new ship is set to the given orientation
	 * 			| setOrientation(orientation)
	 * @post   If the given radius is valid, the radius of this new ship is set to the given radius
	 * 			| if (isValidRadius(radius))
	 * 			|	then new.getRadius() == radius
	 * @throws	IllegalRadiusException
	 * 			The given radius is not valid
	 * 			| ! isValidRadius(radius)
	 */
	@Raw
	public Ship(double xCoordinate, double yCoordinate, double xComponent, double yComponent, 
								double radius, double orientation) throws IllegalComponentException, IllegalRadiusException {
		if (! isValidRadius(radius))
			throw new IllegalRadiusException();
		this.radius = radius;
		setPosition(xCoordinate, yCoordinate);
		setVelocity(xComponent, yComponent);
		setOrientation(orientation);
	}
	

	
	/**
	 * Initialize a new ship with given xCoordinate, yCoordinate and radius.
	 * @param xCoordinate
	 * 			xCoordinate of this new ship
	 * @param yCoordinate
	 * 			yCoordinate of this new ship
	 * @param radius
	 * 			radius of this new ship
	 * @effect This new ship is initialized with the given xCoordinate and yCoordinate as its position, the given radius as its radius,
	 * 			zero velocity and right-pointing orientation.
	 * 			| this(xCoordinate, yCoordinate, 0, 0, radius, 0)
	 */
	@Raw
	public Ship(double xCoordinate, double yCoordinate, double radius) throws IllegalComponentException, IllegalRadiusException {
		this(xCoordinate, yCoordinate, 0, 0, radius, 0);
	}
	
	/**
	 * @return A copy of this ship.
	 */
	@Override
	public Ship copy() {
		return new Ship(getPosition().getxComponent(), getPosition().getyComponent(), getVelocity().getxComponent(),
				getVelocity().getyComponent(), getRadius(), getOrientation());
	}
	
	
	/**
	 * Return the orientation of this ship in radians.
	 */
	@Basic @Raw
	public double getOrientation() {
		return this.orientation;
	}
	
	/**
	 * Check whether the given orientation is a valid orientation for any ship.
	 * @param  orientation
	 *         The orientation to check.
	 * @return true iff the value of orientation is contained in the interval [0, 2*Pi]
	 *       | result == (0 <= orientation) && (orientation <= 2*Math.PI)
	*/
	public static boolean isValidOrientation(double orientation) {
		return (0 <= orientation) && (orientation <= 2*Math.PI);
	}
	
	/**
	 * Turn this ship over a given angle.
	 * 
	 * @param angle
	 * 			The angle over which this ship must be turned.
	 * @effect The new orientation of this ship is set to the current orientation plus the given angle.
	 * 			| setOrientation(getOrientation() + angle)
	 */
	public void turn(double angle) {
		setOrientation(getOrientation() + angle);
	}
	
	/**
	 * Set the orientation of this ship to the given orientation.
	 * 
	 * @param  orientation
	 *         The new orientation for this ship.
	 * @pre    The given orientation must be a valid orientation for any ship.
	 *       | isValidOrientation(orientation)
	 * @post   The orientation of this ship is equal to the given orientation.
	 *       | new.getOrientation() == orientation
	 */	
	@Raw @Model
	private void setOrientation(double orientation) {
		assert isValidOrientation(orientation);
		this.orientation = orientation;
	}
	
	/**
	 * Variable registering the orientation of this ship in radians.
	 */
	private double orientation;
	
	@Override
	public boolean canHaveAsRadius(double radius) {
		return radius >= getMinimalRadius();
	}
	
	/**
	 * Return the minimal radius of any ship.
	 */
	@Basic
	public static double getMinimalRadius() {
		return minimalRadius;
	}
	
	/**
	 * Check whether the given minimal radius is a valid minimal radius for any ship.
	 *  
	 * @param  minimal radius
	 *         The minimal radius to check.
	 * @return true iff the given minimalRadius is greater than getMinimalRadius().
	 *       | result == minimalRadius >= getMinimalRadius()
	 */
	public static boolean isValidMinimalRadius(double minimalRadius) {
		return minimalRadius >= Ship.getMinimalRadius();
	}
	
	/**
	 * Set the minimal radius of any ship to the given minimal radius.
	 * 
	 * @param  minimalRadius
	 *         The new minimal radius for a ship.
	 * @post   The minimal radius of any ship is equal to the given minimal radius.
	 *       | Ship.getMinimalRadius() == minimalRadius
	 * @throws IllegalArgumentException
	 *         The given minimal radius is not a valid minimal radius for any ship.
	 *       | ! isValidMinimalRadius(getMinimalRadius())
	 */
	public static void setMinimalRadius(double minimalRadius) throws IllegalArgumentException {
		if (! isValidMinimalRadius(minimalRadius))
			throw new IllegalArgumentException();
		Ship.minimalRadius = minimalRadius;
	}
	
	/**
	 * Variable registering the minimal radius of this ship.
	 */
	private static double minimalRadius = 10;
	
	/**
	 * Check whether this ship can have the given mass as its mass
	 * @return True iff the given mass is greater than or equal to the volume of this ship times its density
	 * 			| @see implementation
	 */
	@Override
	public boolean canHaveAsMass(double mass) {
		return (mass >= getVolume() * getDensity());
	}
	/**
	 * Check whether this ship can have the given density as its density
	 * @return True iff the given density is greater than or equal to the minimal density
	 * 			| @see implementation
	 */
	@Override
	public boolean canHaveAsDensity(double density) {
		return density >= getMinimalDensity();
	}
	
	/**
	 * Return the minimal density of this ship.
	 */
	@Override
	public double getMinimalDensity() {
		return minimalDensity;
	}
	
	private final double minimalDensity;
	
	/**
	 * Change the velocity of this ship with a given amount.
	 * 
	 * @param amount
	 * 		The amount to be added to the velocity.
	 * @effect If amount is non-negative , the x component (resp. y component) of the new
	 * 			velocity of this ship is set to the sum of the current component
	 * 			plus amount times the cosine (resp. sine) of the orientation of this ship.
	 * 			| if (amount >= 0)
	 * 			|	then setVelocity(getVelocity().getxComponent() + amount * Math.cos(getOrientation()),
	 * 			|						getVelocity().getyComponent() + amount * Math.sin(getOrientation()))
	 * 			
	 */
	public void thrust(double amount) {
		if (amount >= 0)
			setVelocity(getVelocity().getxComponent() + amount * Math.cos(getOrientation()),
					 	getVelocity().getyComponent() + amount * Math.sin(getOrientation()));
	}

}
