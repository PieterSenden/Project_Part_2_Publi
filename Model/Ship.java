package asteroids.model.model;

import asteroids.model.exceptions.IllegalComponentException;
import asteroids.model.exceptions.IllegalRadiusException;
import asteroids.model.exceptions.OverlapException;
import be.kuleuven.cs.som.annotate.*;

/**
 * A class representing a circular space ship dealing with
 * position, velocity, orientation and radius.
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
 * @version 1.0
 *
 */


public class Ship {
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
	public Ship clone() {
		return new Ship(getPosition().getxCoordinate(), getPosition().getyCoordinate(), getVelocity().getxComponent(),
				getVelocity().getyComponent(), getRadius(), getOrientation());
	}
	
	
	/**
	 * Return the position of this ship.
	 */
	@Basic @Raw
	public Position getPosition() {
		return this.position.clone();
	}
	
	/**
	 * Check whether the given position is a valid position for any ship.
	 *  
	 * @param  position
	 *         The position to check.
	 * @return true iff the given position is effective.
	 *       | result == (position != null)
	 */
	
	public static boolean isValidPosition(Position position) {
		return position != null;
	}
	
	/**
	 * Move this ship during a given time duration.
	 * 
	 * @param duration
	 * 			The length of the time interval during which the ship is moved.
	 * @effect Each new coordinate of the position of this ship is set to the sum of the old coordinate
	 * 			and the given duration times the corresponding component of the velocity of this ship.
	 * 			| setPosition(getPosition().getxCoordinate() + duration * getVelocity().getxComponent(),
	 * 			|				getPosition().getyCoordinate() + duration * getVelocity().getyComponent())
	 * @throws IllegalArgumentException
	 * 			The given duration is strictly less than 0.
	 * 			| duration < 0
	 */
	public void move(double duration) throws IllegalArgumentException, IllegalComponentException {
		if (duration < 0)
			throw new IllegalArgumentException();
		setPosition(getPosition().getxCoordinate() + duration * getVelocity().getxComponent(),
				getPosition().getyCoordinate() + duration * getVelocity().getyComponent());
	}
	
	
	/**
	 * Set the position of this ship to the given position.
	 * 
	 * @param  xCoordinate
	 *         The new xCoordinate for the position for this ship.
	 * @param  yCoordinate
	 *         The new yCoordinate for the position for this ship.
	 * @post   The xCoordinate of the position of this new ship is equal to the given xCoordinate.
	 *       | new.getPosition().getxCoordinate() == xCoordinate
	 * @post   The yCoordinate of the position of this new ship is equal to the given yCoordinate.
	 *       | new.getPosition().getyCoordinate() == yCoordinate
	 * @throws IllegalComponentException
	 * 		   One of the given coordinates is not valid
	 * 		 | ! Position.isValidCoordinate(xCoordinate) || ! Position.isValidCoordinate(yCoordinate)
	 */
	@Raw @Model
	private void setPosition(double xCoordinate, double yCoordinate) throws IllegalComponentException {
		try {
			this.position.setPosition(xCoordinate, yCoordinate);
		}
		catch(NullPointerException exc) {
			// For the moment, a NullPointerException will only be thrown if this 
			//  method is used within a constructor of the Ship class.
			this.position = new Position(xCoordinate, yCoordinate);
		}
	}
	
	/**
	 * Variable registering the position of this ship.
	 */
	private Position position;
	
	
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
	
	
	/**
	 * Return the radius of this ship.
	 */
	@Basic @Raw @Immutable
	public double getRadius() {
		return this.radius;
	}
	
	/**
	 * Check whether the given radius is a valid radius for any ship.
	 *  
	 * @param  radius
	 *         The radius to check.
	 * @return true iff the given radius is larger than the minimal radius for any ship.
	 *       | result == (radius >= getMinimalRadius())
	*/
	public static boolean isValidRadius(double radius) {
		return radius >= getMinimalRadius();
	}
	
	/**
	 * Variable registering the radius of this ship.
	 */
	private final double radius;
	
	
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
	 * @return true iff the given minimalRadius is positive
	 *       | result == minimalRadius > 0
	*/
	public static boolean isValidMinimalRadius(double minimalRadius) {
		return minimalRadius > 0;
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
	 * Return the velocity of this ship.
	 */
	@Basic @Raw
	public Velocity getVelocity() {
		if (this.velocity == null)
			return null;
		return this.velocity.clone();
	}
	
	/**
	 * Check whether this ship can have the given velocity as its velocity.
	 *  
	 * @param  velocity
	 *         The velocity to check.
	 * @return true iff the given velocity is effective and the associated speed does not exceed
	 *		the speedLimit of this ship.
	 *       | result == (velocity != null) && (Math.sqrt( Math.pow(velocity.getxComponent(),2) +
	 *	 |		Math.pow(velocity.getyComponent(),2) ) <= getSpeedLimit)
	*/
	public boolean canHaveAsVelocity(Velocity velocity) {
		if (velocity == null)
			return false;
		if (Math.hypot(velocity.getxComponent(), velocity.getyComponent()) > getSpeedLimit())
			return false;
		return true;
	}
	
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
	
	/**
	 * Set the velocity of this ship to the given velocity.
	 * 
	 * @param  xComponent
	 *         The new xComponent for the velocity for this ship.
	 * @param  yComponent
	 *         The new yComponent for the velocity for this ship.
	 * @post   If this ship can have the velocity with the given xComponent and  given yComponent as its velocity, 
	 * 			then the xComponent of the velocity of this new ship is equal to the given xComponent,
	 * 			and the yComponent of the velocity of this new ship is equal to the given yComponent.
	 *       | if (this.canHaveAsVelocity(new Velocity(xComponent,yComponent))
	 *       | 		then (new.getVelocity().getxComponent() == xComponent)
	 *       |			&& (new.getVelocity().getyComponent() == yComponent)
	 * @post   If this ship cannot have the velocity with the given xComponent and  given yComponent as its velocity,
	 * 			the new velocity of this ship is set to a velocity such that the direction corresponds with the
	 *			velocity with given xComponent and yComponent, but the speed is set to the speedLimit. More concretely,
	 *			the xComponent of the new velocity of this ship is set to (xComponent * getSpeedLimit() / speed) and the
	 *			yComponent of the new velocity of this ship is set to (yComponent * getSpeedLimit() / speed), where
	 *			speed is the speed corresponding to the velocity with given xComponent and yComponent.
	 *		 | if (! this.canHaveAsVelocity(new Velocity(xComponent, yComponent))
	 *		 | 		then (new.getVelocity().getxComponent() == xComponent * getSpeedLimit / Math.hypot(xComponent, yComponent))
	 *		 |			&& (new.getVelocity().getyComponent() == yComponent * getSpeedLimit / Math.hypot(xComponent, yComponent))
	 */
	@Raw @Model
	private void setVelocity(double xComponent, double yComponent) {
		if (this.getVelocity() == null)
			this.velocity = new Velocity(0, 0);
		if (this.canHaveAsVelocity(new Velocity(xComponent, yComponent)))
			this.velocity.setVelocity(xComponent, yComponent);
		else {
			double speed = Math.hypot(xComponent, yComponent);
			this.velocity.setxComponent(xComponent * getSpeedLimit() / speed);
			this.velocity.setyComponent(yComponent * getSpeedLimit() / speed);
		}
	}
	
	/**
	 * Variable registering the velocity of this ship.
	 */
	private Velocity velocity;
	
	
	/**
	 * Return the speed limit of this ship.
	 */
	@Basic @Raw @Immutable
	public double getSpeedLimit() {
		return this.speedLimit;
	}
	
	/**
	 * Check whether the given speedLimit is a valid speedLimit for any ship.
	 *  
	 * @param  speedLimit
	 *         The speed limit to check.
	 * @return True if and only if the given speed limit is strictly positive and not greater than the speed of light
	 *       | result == (0 < speedLimit) && (speedLimit <= SPEED_OF_LIGHT)
	*/
	public static boolean isValidSpeedLimit(double speedLimit) {
		return (0 < speedLimit) && (speedLimit <= SPEED_OF_LIGHT);
	}
	
	
	/**
	 * Variable registering the speed limit of this ship.
	 */
	private final double speedLimit = SPEED_OF_LIGHT;
	
	/**
	 * Constant representing the speed of light (i.e. 300000 km/s)
	 */
	
	public static final double SPEED_OF_LIGHT = 300000;
	
	
	/**
	 * Calculate the distance between two ships
	 * @param ship1
	 * 			The first ship
	 * @param ship2
	 * 			The second ship
	 * @return If the two ships are effective and different, the distance between the two ships (i.e. the distance
	 * 				between the two centres minus the sum of their radii).
	 * 			| If ((ship1 != null) && (ship2!= null) && (ship1 != ship2))
	 * 			|	then result == Position.getDistanceBetween(ship1.getPosition(), ship2.getPosition()) - (ship1.getRadius() + ship2.getRadius())
	 * @return If the two ships are effective and identical, zero.
	 * 			| If ((ship1 != null) && (ship1 == ship2))
	 * 			|	then result == 0
	 * @throws NullPointerException
	 * 			One of the ships is not effective
	 * 			| (ship1 == null) || (ship2 == null)
	 */
	public static double getDistanceBetween(Ship ship1, Ship ship2) throws NullPointerException{
		if ((ship1 != null) && (ship1 == ship2))
			return 0;
		return Position.getDistanceBetween(ship1.getPosition(), ship2.getPosition()) - (ship1.getRadius() + ship2.getRadius());
	}
	
	/**
	 * Determine whether two ships overlap
	 * @param ship1
	 * 			The first ship
	 * @param ship2
	 * 			The second ship
	 * @return If the two ships are effective and different, true iff the distance between the two ships is non-positive.
	 * 			| If ((ship1 != null) && (ship2!= null) && (ship1 != ship2))
	 * 			|	then result == (Ship.getDistanceBetween(ship1, ship2) <= 0)
	 * @return If the two ships are effective and identical, true.
	 * 			| If ((ship1 != null) && (ship1 == ship2))
	 * 			|	then result == true
	 * @throws NullPointerException
	 * 			One of the ships is not effective
	 * 			| (ship1 == null) || (ship2 == null)
	 */
	public static boolean overlap(Ship ship1, Ship ship2) throws NullPointerException {
		return (Ship.getDistanceBetween(ship1, ship2) <= 0);
	}
	
	/**
	 * Determine the time after which, if ever, two ships will collide.
	 * @param ship1
	 * 			The fist ship
	 * @param ship2
	 * 			The second ship
	 * @return If both ships are effective and different, the result satisfies the following conditions:
	 * 			1.	After both ships are moved during the returned duration, they will overlap.
	 * 			| if  ((ship1 != null) && (ship2!= null) && (ship1 != ship2))
	 * 			| 	then (overlap(ship1, ship2)) is true after the execution of the following code snippet:
	 * 			|			ship1.move(result);
	 * 			|			ship2.move(result);
	 * 			2.	After both ships are moved during a positive time less than the returned duration, they will not overlap.
	 * 			| if  ((ship1 != null) && (ship2!= null) && (ship1 != ship2))
	 * 			|	then for each duration in { time in the real numbers | 0 <= time < result}, 
	 * 			|		(overlap(ship1, ship2)) is false after the execution of the following code snippet:
	 * 			|			ship1.move(duration);
	 * 			|			ship2.move(duration);
	 * @throws NullPointerException
	 * 			One of the ships is non-effective.
	 * 			|	(ship1 == null) || (ship2 == null)
	 * @throws OverlapException
	 * 			The ships overlap
	 * 			| overlap(ship1, ship2)
	 */
	public static double getTimeToCollision(Ship ship1, Ship ship2) throws NullPointerException, OverlapException {
		if (overlap(ship1, ship2))
			throw new OverlapException();
		
		double dx, dy, dvx, dvy, discriminant, sumOfRadii, dvDotdr;
		dx = ship1.getPosition().getxCoordinate() - ship2.getPosition().getxCoordinate();
		dy = ship1.getPosition().getyCoordinate() - ship2.getPosition().getyCoordinate();
		dvx = ship1.getVelocity().getxComponent() - ship2.getVelocity().getxComponent();
		dvy = ship1.getVelocity().getyComponent() - ship2.getVelocity().getyComponent();
		sumOfRadii = ship1.getRadius() + ship2.getRadius();
		dvDotdr = dvx * dx + dvy * dy;
		
		if (dvDotdr >= 0)
			return Double.POSITIVE_INFINITY;
		
		discriminant = Math.pow(dvDotdr, 2) - Math.pow(Math.hypot(dvx, dvy), 2) *
							(Math.pow(Math.hypot(dx, dy),  2) - Math.pow(sumOfRadii, 2));
		if (discriminant <= 0)
			return Double.POSITIVE_INFINITY;
		return - (dvDotdr + Math.sqrt(discriminant)) / Math.pow(Math.hypot(dvx, dvy), 2);
	}
	
	/**
	 * Determine the position where, if ever, two ships will collide
	 * @param ship1
	 * 			The first ship.
	 * @param ship2
	 * 			The second ship.
	 * @return null, if the ships will not collide
	 * 			| if (getTimeToCollision(ship1, ship2) == Double.POSITIVE_INFINITY)
	 * 			|	then return null 
	 * @return If the ships will collide, the result satisfies the following condition(s):
	 * 			After both ships are moved during the time getTimeToCollision(ship1, ship2), the distance between
	 * 			the result and the position of ship1 equals the radius of ship1 and the distance between
	 * 			the result and the position of ship2 equals the radius of ship2.
	 * 			| if ( Double.isFinite(getTimeToCollision(ship1, ship2)) )
	 * 			| 	then (Position.getDistanceBetween(result, ship1.getPosition()) == ship1.getRadius() ) &&
	 * 			|			(Position.getDistanceBetween(result, ship2.getPosition()) == ship2.getRadius() )
	 * 			|	is true, after the execution of the following code snippet:
	 * 			|			double duration = getTimeToCollision(ship1, ship2);
	 * 			|			ship1.move(duration);
	 * 			|			ship2.move(duration);
	 * @throws NullPointerException
	 * 			One of the ships is non-effective.
	 * 			| (ship1 == null) || (ship2 == null)
	 * @throws OverlapException
	 * 			The ships overlap
	 * 			| overlap(ship1, ship2)
	 */
	public static Position getCollisionPosition(Ship ship1, Ship ship2) throws NullPointerException, OverlapException{
		if (overlap(ship1, ship2))
			throw new OverlapException();
		
		double timeToCollision = getTimeToCollision(ship1, ship2);
		if (timeToCollision == Double.POSITIVE_INFINITY)
			return null;
		
		Ship ship1Clone = ship1.clone();
		Ship ship2Clone = ship2.clone();
		ship1Clone.move(timeToCollision);
		ship2Clone.move(timeToCollision);
		
		Position position1 = ship1Clone.getPosition();
		Position position2 = ship2Clone.getPosition();
		
		double radius1 = ship1Clone.getRadius();
		double radius2 = ship2Clone.getRadius();
		double sumOfRadii = radius1 + radius2;
		
		return new Position( (position1.getxCoordinate() * radius2 + position2.getxCoordinate() * radius1) / sumOfRadii, 
				(position1.getyCoordinate() * radius2 + position2.getyCoordinate() * radius1) / sumOfRadii);
		
	}
	
	

}
