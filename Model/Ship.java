package asteroids.model.representation;

import java.util.Set;
import java.util.HashSet;
import asteroids.model.exceptions.*;
import be.kuleuven.cs.som.annotate.*;

/**
 * A class representing a circular space ship dealing with
 * position, velocity, orientation, radius, density, mass, total mass and bullets.
 * 
 * @invar  The orientation of each ship must be a valid orientation for any ship.
 *       | isValidOrientation(getOrientation())
 * @invar  The minimal radius of each ship must be a valid minimal radius for any ship.
 *       | isValidMinimalRadius(getMinimalRadius())
 * @invar  The thruster force for this ship is a valid thruster force for any ship.
 *       | isValidThrusterForce(this.getThrusterForce())
 * @invar  Each ship must have proper bullets.
 * 		 | hasProperBullets()
 * 
 * @author Joris Ceulemans & Pieter Senden
 * @version 2.0
 *
 */


public class Ship extends Entity {
	
	/**
	 * Initialize this new ship with given position, velocity , radius, orientation, mass and thruster status.
	 * 
	 * @param xComPos
	 * 			xComponent of the position this new ship
	 * @param yComPos
	 * 			yComponent of the position this new ship
	 * @param xComVel
	 * 			xComponent of the velocity of this new ship
	 * @param yComVel
	 * 			yComponent of the velocity of this new ship
	 * @param radius
	 * 			radius of this new ship
	 * @param orientation
	 * 			orientation of this new ship
	 * @effect The position of this new ship is set to a new position with given xComponent and yComponent
	 * 			| setPosition(xComPos, yComPos)
	 * @effect The velocity of this new ship is set to a new velocity with given xComponent and yComponent
	 * 			| setVelocity(xComVel, yComVel)
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
	public Ship(double xComPos, double yComPos, double xComVel, double yComVel, double radius, double orientation,
			double mass, boolean thrusterStatus) throws IllegalComponentException, IllegalRadiusException {
		super(xComPos, yComPos, xComVel, yComVel, radius, mass);
		setThrust(thrusterStatus);
		setOrientation(orientation);
	}
	
	
	/**
	 * Initialize this new ship with position with given xComponent and yComponent, 
	 * @param xComPos
	 * @param yComPos
	 * @param xComVel
	 * @param yComVel
	 * @param radius
	 * @param orientation
	 * @throws IllegalComponentException
	 * @throws IllegalRadiusException
	 */
	@Raw 
	public Ship(double xComPos, double yComPos, double xComVel, double yComVel, double radius,
			double orientation) throws IllegalComponentException, IllegalRadiusException {
		this(xComPos, yComPos, xComVel, yComVel, radius, orientation, 10e20, false);
	}
	
	@Raw 
	public Ship(double xComPos, double yComPos, double xComVel, double yComVel, double radius,
			double orientation, double mass) throws IllegalComponentException, IllegalRadiusException {
		this(xComPos, yComPos, xComVel, yComVel, radius, orientation, mass, false);
	}
	
	/**
	 * Initialize a new ship with given xComponent, yComponent and radius.
	 * 
	 * @param xComponent
	 * 			xComponent of this new ship
	 * @param yComponent
	 * 			yComponent of this new ship
	 * @param radius
	 * 			radius of this new ship
	 * @effect This new ship is initialized with the given xComponent and yComponent as its position, the given radius as its radius,
	 * 			zero velocity and right-pointing orientation.
	 * 			| this(xComponent, yComponent, 0, 0, radius, 0)
	 */
	@Raw
	public Ship(double xComPos, double yComPos, double radius) throws IllegalComponentException, IllegalRadiusException {
		this(xComPos, yComPos, 0, 0, radius, 0);
	}
	
	/**
	 * Return a copy of this ship.
	 * 
	 * @return A copy of this ship.
	 * 			| @see implementation
	 * @throws TerminatedException
	 * 			| this.isTerminated()
	 */
	@Override
	public Ship copy() throws TerminatedException {
		if (isTerminated())
			throw new TerminatedException();
		return new Ship(getPosition().getxComponent(), getPosition().getyComponent(), getVelocity().getxComponent(),
				getVelocity().getyComponent(), getRadius(), getOrientation(), getMass(), hasThrusterActivated());
	}
	
	/**
	 * Terminate this ship.
	 * 
	 * @post All loaded bullets have been removed from the magazine of this ship.
	 * 		| getMagazine().isEmpty()
	 * @post All fired bullets have been removed from the collection of fired bullets of this ship.
	 * 		| getFiredBullets().isEmpty()
	 * @effect The super method is called to terminate this ship.
	 * 		| super.terminate()
	 * 
	 */
	@Override
	public void terminate() {
		if (!isTerminated()) {
			Set<Bullet> magazineClone = new HashSet<>(getMagazine());
			for (Bullet bullet: magazineClone) {
				removeBullet(bullet);
			}
			Set<Bullet> firedBulletsClone = new HashSet<>(getFiredBullets());
			for (Bullet bullet: firedBulletsClone) {
				removeBullet(bullet);
			}
			super.terminate();
		}
	}
	
	/**
	 * Set the position of this ship to the given position.
	 * 
	 * @effect The position of this ship is set to the given position.
	 * 		| super.setPosition(position) 
	 * @post Each bullet in the magazine of this ship has the given position as its position.
	 * 		| for each bullet in getMagazine():
	 * 		|	bullet.getPosition().equals(position)
	 */
	@Override
	protected void setPosition(Position position) throws IllegalPositionException, TerminatedException {
		super.setPosition(position);
		if (this.magazine != null) {
			// When initializing this ship, it is possible that magazine is null and we still want to invoke this method.
			for (Bullet bullet: getMagazine()) {
				bullet.setPosition(position);
			}
		}
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
	 * 
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
	 * Check whether this ship can have the given radius as its radius.
	 * 
	 * @param radius
	 * 			The radius to check.
	 * @return True iff the given radius is greater than or equal to the minimal radius of any ship.
	 * 		  |	@see implementation.
	 */
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
	 * @param  minimalRadius
	 *         The minimal radius to check.
	 * @return true iff the given minimal radius is greater than or equal to getMinimalRadius().
	 *       | result == (minimalRadius >= getMinimalRadius())
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
	 * Check whether this ship can have the given density as its density.
	 * 
	 * @return True iff the given density is greater than or equal to the minimal density of this ship.
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
	
	
	/**
	 * Variable registering the minimal density of this ship. 
	 */
	private final double minimalDensity = 1.42e12;
	
	/**
	 * Return the total mass of this ship.
	 * The total mass of a ship is the sum of its mass and the mass of the objects carried by that ship.
	 * @return The total mass of this ship.
	 * 			| result = getMass() + sum( { x in getMagazine() | true : x.getMass() } )
	 */
	public double getTotalMass() {
		double result = getMass();
		for(Bullet bullet: getMagazine()) {
			result += bullet.getMass();
		}
		return result;
	}
	
//	/**
//	 * Initialize this new ship with given thrusterForce.
//	 * 
//	 * @param  force
//	 *         The thrusterForce for this new ship.
//	 * @post   If the given thrusterForce is a valid thrusterForce for any ship,
//	 *         the thrusterForce of this new ship is equal to the given
//	 *         thrusterForce. Otherwise, the thrusterForce of this new ship is equal
//	 *         to 1.1e21.
//	 *       | if (isValidThrusterForce(force))
//	 *       |   then new.getThrusterForce() == force
//	 *       |   else new.getThrusterForce() == 1.1e21
//	 */
//	public Ship(double force) {
//		if (! isValidThrusterForce(force))
//			force = 1.1e21;
//		this.force = force;
//	}
	
	/**
	 * Return the thruster force of this ship.
	 */
	@Basic @Raw
	public double getThrusterForce() {
		return this.thrusterForce;
	}
	
	/**
	 * Return the acceleration of this ship.
	 * 
	 * @return 0 if the thruster of this ship is not activated, and the quotient of this ship's thruster force and total mass if the
	 * 				thruster is activated
	 * 			| if (hasThrusterActivated()) then result == getThrusterForce() / getTotalMass()
	 * 			|	else result == 0
	 */
	public double getAcceleration() {
		if (hasThrusterActivated())
			return getThrusterForce() / getTotalMass();
		else 
			return 0;
	}
	
	/**
	 * Check whether this ship can have the given thruster force as its thrusterForce.
	 *  
	 * @param  force
	 *         The thrusterForce to check.
	 * @return 
	 *       | result == (force >= 0)
	 */
	public static boolean isValidThrusterForce(double force) {
		return force >= 0;
	}
	
	/**
	 * Set the thruster force of this ship to the given force.
	 * @param force
	 * 			The new thruster force for this ship.
	 * @post If the given force is a valid thruster force for any ship, the new thruster force of this ship is equal to the given force.
	 * 			| if (isValidThrusterForce(force))
	 * 			|	then new.getThrusterForce() == force
	 */
	@Raw
	public void setThrusterForce(double force) {
		if (isValidThrusterForce(force))
			this.thrusterForce = force;
	}
	
	/**
	 * Variable registering the thruster force of this ship.
	 */
	private double thrusterForce = 1.1e21;
	
	
	/**
	 * Return the thruster status of this ship.
	 */
	@Basic @Raw
	public boolean hasThrusterActivated() {
		return thrusterStatus;
	}
	
	/**
	 * Set the thruster status of this ship to the given flag.
	 * @param flag
	 * 		the new thruster status of this ship.
	 * @post The new thruster status of this ship is equal to the given flag.
	 * 		| new.hasThrusterActivated() == flag
	 */
	@Raw
	public void setThrust(boolean flag) {
		thrusterStatus = flag;
	}
	
	/**
	 * Activate the thruster of this ship.
	 * @effect The thruster status of this ship is set to true.
	 * 			| setThrust(true)
	 */
	@Raw
	public void thrustOn() {
		setThrust(true);
	}
	
	/**
	 * Deactivate the thruster of this ship.
	 * @effect The thruster status of this ship is set to false.
	 * 			| setThrust(false)
	 */
	@Raw
	public void thrustOff() {
		setThrust(false);
	}
	
	/**
	 * A variable registering the thruster status of this ship.
	 */
	private boolean thrusterStatus;
	
	
	/**
	 * Change the velocity of this ship with during the given time interval.
	 * 
	 * @param duration
	 * 		The length of the time interval over which the ship has to be accelerated.
	 * @effect If duration is non-negative , the x component (resp. y component) of the new
	 * 			velocity of this ship is set to the sum of the current component
	 * 			plus duration times acceleration of this ship times the cosine (resp. sine) of the orientation of this ship.
	 * 			| if (duration >= 0)
	 * 			|	then setVelocity(getVelocity().getxComponent() + duration * getAcceleration() * Math.cos(getOrientation()),
	 * 			|						getVelocity().getyComponent() + duration * getAcceleration() * Math.sin(getOrientation()))
	 * 			
	 */
	public void thrust(double duration) {
		if (duration >= 0)
			setVelocity(getVelocity().getxComponent() + duration * getAcceleration() * Math.cos(getOrientation()),
					 	getVelocity().getyComponent() + duration * getAcceleration() * Math.sin(getOrientation()));
	}
	
	
	/** 
	 * Make this ship bounce of the boundary of its world.
	 * @effect	| if (apparentlyCollidesWithHorizontalBoundary())
	 * 			|	then setVelocity(getVelocity().getxComponent(), -getVelocity().getyComponent())
	 * @effect	| if (apparentlyCollidesWithVerticalBoundary())
	 * 			|	then setVelocity(-getVelocity().getxComponent(), getVelocity().getyComponent())
	 * @throws	TerminatedException
	 * 			This entity is terminated.
	 * 			| this.isTerminated()
	 * @throws	IllegalMethodCallException
	 * 			This entity is not associated to a world or this entity does not collide with the boundary of its world.
	 * 			| getWorld() == null || !apparentlyCollidesWithBoundary()
	 */
	@Override
	public void bounceOfBoundary() throws IllegalMethodCallException, TerminatedException {
		if (isTerminated())
			throw new TerminatedException();
		if (getWorld() == null || !apparentlyCollidesWithBoundary())
			throw new IllegalMethodCallException();
		else if (apparentlyCollidesWithHorizontalBoundary())
			setVelocity(getVelocity().getxComponent(), -getVelocity().getyComponent());
		else if (apparentlyCollidesWithVerticalBoundary())
			setVelocity(-getVelocity().getxComponent(), getVelocity().getyComponent());
	}
	
	/**
	 * Resolve a collision between this ship and another entity.
	 * Only collisions between ships and other ships or bullets are resolved.
	 * 
	 * @effect	If the other entity is a ship, resolve the collision between this ship and the other ship.
	 * 			| if (other instanceof Ship)
	 * 			|	then (Ship.resolveCollisionBetweenShips(this, (Ship)other))
	 * @effect	If the other entity is a bullet and this ship has not fired said bullet, terminate both entities.
	 * 			| if (other instanceof Bullet && !this.hasFired((Bullet)other))
	 * 			|	then this.terminate() && other.terminate()
	 * @effect	If the other entity is a bullet and this ship has fired said bullet, load the bullet on this ship.
	 * 			| if ( other instanceof Bullet && this.hasFired((Bullet)other) )
	 * 			|	then loadBullet((Bullet)other)
	 * @throws IllegalMethodCallException
	 * 			Either this entity or the other entity is not associated to a world, this entity and the other entity are not associated to the same world or
	 * 			this entity and the other entity do not apparently collide.
	 * 			| (getWorld() == null) || (getWorld() != other.getWorld()) || !Entity.apparentlyCollide(this, other))
	 * @throws TerminatedException
	 * 			One of the entities is terminated
	 * 			| this.isTerminated() || other.isTerminated()
	 */
	@Override
	public void resolveCollision(Entity other) throws IllegalMethodCallException, TerminatedException {
		if (isTerminated() || other.isTerminated())
			throw new TerminatedException();
		if (getWorld() == null || getWorld() != other.getWorld() || !Entity.apparentlyCollide(this, other))
			throw new IllegalMethodCallException();
		if (other instanceof Ship)
			Ship.resolveCollisionBetweenShips(this, (Ship)other);
		else if (other instanceof Bullet) {
			if (hasFired((Bullet)other))
				loadBullet((Bullet)other);
			else {
				terminate();
				other.terminate();
			}
		}
	}
	
	/**
	 * Resolve a collision between two ships.
	 * 
	 * @param ship1
	 * 			The first ship involved in a collision.
	 * @param ship2
	 * 			The second ship involved in a collision.
	 * @effect The velocities of both ships are adjusted according to the physical laws regarding conservation of momentum. 
	 * 			| @see implementation
	 * @throws IllegalMethodCallException
	 * 			The two ships do not apparently collide.
	 * 			| !Entity.apparentlyCollide(ship1, ship2)
	 * @throws TerminatedException
	 * 			One of the ships is terminated
	 * 			| ship1.isTerminated() || ship2.isTerminated()
	 * 
	 */
	static void resolveCollisionBetweenShips(Ship ship1, Ship ship2) throws IllegalMethodCallException, TerminatedException {
		if (!Entity.apparentlyCollide(ship1, ship2))
			throw new IllegalMethodCallException();
		double dx, dy, dvx, dvy, sumOfRadii, dvDotdr, m1, m2;
		dx = ship1.getPosition().getxComponent() - ship2.getPosition().getxComponent();
		dy = ship1.getPosition().getyComponent() - ship2.getPosition().getyComponent();
		dvx = ship1.getVelocity().getxComponent() - ship2.getVelocity().getxComponent();
		dvy = ship1.getVelocity().getyComponent() - ship2.getVelocity().getyComponent();
		sumOfRadii = ship1.getRadius() + ship2.getRadius();
		dvDotdr = dvx * dx + dvy * dy;
		m1 = ship1.getTotalMass();
		m2 = ship2.getTotalMass();
		
		double J = (2 * m1 * m2 * dvDotdr) / (sumOfRadii * (m1 + m2));
		double Jx = J * dx / sumOfRadii;
		double Jy = J * dy / sumOfRadii;
		
		ship1.setVelocity(ship1.getVelocity().getxComponent() - Jx / m1, ship1.getVelocity().getyComponent() - Jy / m1);
		ship2.setVelocity(ship2.getVelocity().getxComponent() + Jx / m2, ship2.getVelocity().getyComponent() + Jy / m2);
	}
	
	/**
	 * Check whether if a collision between this entity and the given other entity occurs, it must be shown.
	 * This method does not check if this entity and the other entity collide, only whether the collision must be shown if they do.
	 * 
	 * @param other
	 * 			The other entity.
	 * @return	True iff the other entity is not a bullet or (the other entity is a bullet and this ship has not fired said bullet)
	 * 			| !(other instanceof Bullet) || !hasFired((Bullet)other)
	 */
	@Override
	public boolean mustShowCollisionWith(Entity other) {
		if (other instanceof Bullet)
			return !hasFired((Bullet)other);
		return true;
	}
	
	/**
	 * Determine whether this ship can be removed from its world.
	 * 
	 * @return	True iff this ship is associated with a world and does not have fired bullets in this world anymore (all associations between
	 * 			this ship and its fired bullets must have been torn down).
	 * 			| result == (getWorld() != null) && (getNbOfFiredBullets() == 0)
	 */
	@Override
	public boolean canBeRemovedFromWorld() {
		if (getWorld() == null)
			return false;
		if (getNbOfFiredBullets() != 0)
			return false;
		return true;
	}
	
	
	/**
	 * Check whether this ship has loaded this bullet in its magazine.
	 */
	@Basic @Raw
	public boolean hasLoadedInMagazine(@Raw Bullet bullet) {
		return magazine.contains(bullet);
	}
	
	/**
	 * Add the given bullet to the magazine of this ship.
	 * @param bullet
	 * 		The bullet to be added to the magazine of this ship.
	 * @post If this ship can have the given bullet as bullet, then the bullet is added to the magazine of this ship.
	 * 		| if (canHaveAsBullet(bullet))
	 * 		|	then hasLoadedInMagazine(bullet)
	 * @throws IllegalBulletException
	 * 		This ship cannot have the given bullet as bullet.
	 * 		| ! canHaveAsBullet(bullet)
	 * @throws TerminatedException
	 * 		This ship is terminated
	 * 		| this.isTerminated()
	 */
	@Raw
	private void addAsLoadedBullet(Bullet bullet) throws IllegalBulletException, TerminatedException {
		if (this.isTerminated())
			throw new TerminatedException();
		if (! canHaveAsBullet(bullet))
			throw new IllegalBulletException();
		this.magazine.add(bullet);
	}
	
	/**
	 * Remove the given bullet from the magazine of this ship.
	 * @param bullet
	 * 		The bullet to remove from the magazine of this ship.
	 * @post If the given bullet is effective and is loaded on this ship, then
	 * 			the given bullet is removed from the magazine of this ship.
	 * 		| if (bullet != null && hasLoadedInMagazine(bullet))
	 * 		|	then ! hasLoadedInMagazine(bullet)
	 * @throws IllegalBulletException
	 * 			The given bullet is not loaded on this ship.
	 * 		| ! hasLoadedInMagazine(bullet)
	 * @throws NullPointerException
	 * 			The given bullet is not effective.
	 * 		| bullet == null
	 * @throws TerminatedException
	 * 			This ship is terminated
	 * 			| this.isTerminated()
	 */
	@Raw
	private void removeAsLoadedBullet(Bullet bullet) throws IllegalBulletException, NullPointerException, TerminatedException {
		if (! hasLoadedInMagazine(bullet))
			throw new IllegalBulletException();
		this.magazine.remove(bullet);
	}
	
	/**
	 * Check whether this ship has fired the given bullet. 
	 */
	@Basic @Raw
	public boolean hasFired(@Raw Bullet bullet) {
		return firedBullets.contains(bullet);
	}
	
	/**
	 * Add the given bullet to the collection of fired bullets of this ship.
	 * 
	 * @param bullet
	 * 		The bullet to be added to the collection of fired bullets of this ship.
	 * @post If this ship can have the given bullet as bullet, then the bullet is added to the collection of fired bullets of this ship.
	 * 		| if (canHaveAsBullet(bullet))
	 * 		|	then hasFired(bullet)
	 * @throws IllegalBulletException
	 * 		This ship cannot have the given bullet as bullet.
	 * 		| ! canHaveAsBullet(bullet)
	 * @throws TerminatedException
	 * 		This ship is terminated
	 * 		| this.isTerminated()
	 */
	@Raw
	private void addAsFiredBullet(Bullet bullet) throws IllegalBulletException, TerminatedException {
		if (this.isTerminated())
			throw new TerminatedException();
		if (! canHaveAsBullet(bullet))
			throw new IllegalBulletException();
		this.firedBullets.add(bullet);
	}
	
	/**
	 * Remove the given bullet from the collection of fired bullets of this ship.
	 * 
	 * @param bullet
	 * 		The bullet to remove from the collection of fired bullets of this ship.
	 * @post If the given bullet is effective and has been fired by this ship, then
	 * 			the given bullet is removed from the collection of fired bullets of this ship.
	 * 		| if (bullet != null && hasFired(bullet))
	 * 		|	then ! hasFired(bullet)
	 * @throws IllegalBulletException
	 * 			The given bullet has not been fired by this ship.
	 * 		| ! hasFired(bullet)
	 * @throws NullPointerException
	 * 			The given bullet is not effective.
	 * 		| bullet == null
	 * @throws TerminatedException
	 * 		This ship is terminated
	 * 		| this.isTerminated()
	 */
	@Raw
	private void removeAsFiredBullet(Bullet bullet) throws IllegalBulletException, NullPointerException, TerminatedException {
		if (this.isTerminated())
			throw new TerminatedException();
		if (! hasFired(bullet))
			throw new IllegalBulletException();
		this.firedBullets.remove(bullet);
	}
	
	/**
	 * Remove the given bullet from this ship.
	 * @param bullet
	 * 			The bullet to remove from this ship.
	 * @throws IllegalArgumentException
	 * 			This ship does not have the given bullet as bullet.
	 * 			| ! hasAsBullet(bullet)
	 * @throws TerminatedException
	 * 		This ship is terminated
	 * 		| this.isTerminated()
	 */
	void removeBullet(Bullet bullet) throws IllegalArgumentException, TerminatedException {
		if (this.isTerminated())
			throw new TerminatedException();
		if (! hasAsBullet(bullet))
			throw new IllegalArgumentException();
		else {
			if (hasLoadedInMagazine(bullet))
				removeAsLoadedBullet(bullet);
			else if (hasFired(bullet))
				removeAsFiredBullet(bullet);
			bullet.setShip(null);
			//Cannot throw IllegalMethodCallException because at this point certainly !hasAsBullet(bullet).
			//Cannot throw TerminatedException because we have that this.hasAsBullet(bullet) and then the class invariant implies that
			//	this.canHaveAsBullet(bullet).
		}
	}
	
	/**
	 * Check whether this ship is associated to the given bullet.
	 * @param bullet
	 * 			The bullet to check.
	 * @return True iff this ship has fired or loaded the given bullet.
	 * 			| @see implementation
	 */
	public boolean hasAsBullet(@Raw Bullet bullet) {
		return hasFired(bullet) || hasLoadedInMagazine(bullet);
	}
	
	/**
	 * Check whether this ship can be associated to the given bullet.
	 * @param bullet
	 * 		The bullet to check.
	 * @return True iff this ship is not terminated, the given bullet is effective, not terminated, and, if the bullet is associated to a world,
	 * 			this ship must be associated to the same world.
	 * 			| if (this.isTerminated())
	 * 			|	then result == false
	 * 			| if (bullet == null || bullet.isTerminated())
	 * 			|	then result == false
	 * 			| else if (bullet.getWorld() == null)
	 * 			|	then result == true
	 * 			| else if (bullet.getWorld() == this.getWorld())
	 * 			|	then result == true
	 * 			| else
	 * 			|	result == false
	 */
	@Raw
	public boolean canHaveAsBullet(Bullet bullet) {
		return (bullet != null && (bullet.getWorld() == null || getWorld() == bullet.getWorld()) && !isTerminated() && !bullet.isTerminated());
	}
	
	/**
	 * Check whether this ship has proper bullets associated to it.
	 * @return If this ship is terminated, true iff there are no bullets in the magazine and the collection of all bullets fired by this ship is empty.
	 * 			| if (this.isTerminated())
	 * 			|	result == (getMagazine().isEmpty() && getFiredBullets().isEmpty())
	 * @return If this ship is not terminated, true iff each bullet in magazine is effective, has not been fired by this ship, is not associated to any world
	 * 			and references this ship as its ship, 
	 * 			and each bullet that has been fired by this ship, is effective, is not loaded in the magazine of this ship,
	 * 			is associated to the same world as this ship and references this ship as its ship.
	 * 			| if (!this.isTerminated())
	 * 			| 	then result == 
	 * 			| 	 	(for each bullet in getMagazine():
	 * 			|			!canHaveAsBullet(bullet) && bullet.getShip() == this && ! hasFired(bullet) && bullet.getWorld() == null)
	 * 			|		&&
	 * 			|		(for each bullet in getFiredBullets():
	 * 			|			!canHaveAsBullet(bullet) && bullet.getShip() == this && ! hasLoadedInMagazine(bullet) && bullet.getWorld() == getWorld()) 			
	 */
	public boolean hasProperBullets() {
		if (!isTerminated()) {
			for (Bullet bullet : getMagazine()) {
				if (!canHaveAsBullet(bullet) || bullet.getShip() != this || hasFired(bullet)
						|| bullet.getWorld() != null)
					return false;
			}
			for (Bullet bullet : getFiredBullets()) {
				if (!canHaveAsBullet(bullet) || bullet.getShip() != this || hasLoadedInMagazine(bullet)
						|| bullet.getWorld() != getWorld())
					return false;
			}
			return true;
		}
		else {
			return (getMagazine().isEmpty() && getFiredBullets().isEmpty());
		}
	}
	
	/**
	 * Return the number of bullets loaded in the magazine of this ship. 
	 * @return The number of bullets loaded in the magazine of this ship.
	 * 			| @see implementation
	 */
	@Raw
	public int getNbOfBulletsInMagazine() {
		return getMagazine().size();
	}
	
	/**
	 * Return the number of bullets fires by this ship that are not destroyed. 
	 */
	@Basic @Raw
	public int getNbOfFiredBullets() {
		return firedBullets.size();
	}
	
	/**
	 * Return the magazine with the loaded bullets of this ship.
	 */
	@Basic
	public Set<Bullet> getMagazine() {
		return new HashSet<Bullet>(this.magazine);
	}
	
	/**
	 * Return the set of all fired, non-terminated bullets by this ship.
	 */
	@Basic
	public Set<Bullet> getFiredBullets() {
		return new HashSet<Bullet>(this.firedBullets);
	}
	
	/**
	 * Fire a bullet from the magazine of this ship.
	 * @post If this ship is not terminated and if the magazine of this ship is not empty and this ship is contained in a world,
	 * 			then a random bullet randomBullet is removed from the magazine
	 * 			and added to the world containing this ship, if any, and hasFired(randomBullet) is true.
	 * 		| if (getNbOfBulletsInMagazine() != 0 && getWorld() != null)
	 * 		|	then for precisely one bullet in getMagazine():
	 * 		|		new.hasFired((new bullet)) && ! new.hasLoadedInMagazine((new bullet))
	 * @effect If this ship is not terminated and if the magazine of this ship is not empty, said random bullet is set to fire configuration.
	 * 		| randomBullet.setToFireConfiguration()
	 * @effect If this ship is not terminated and if the magazine of this ship is not empty and
	 * 			if said random bullet is placed partially outside the world of this ship after setting it to fire configuration,
	 * 			then that random bullet is immediately destroyed.
	 * 			| if (! getWorld().boundariesSurround((new randomBullet)))
	 * 			|	then randomBullet.terminate();
	 * @effect If this ship is not terminated and if the magazine of this ship is not empty and
	 * 			if said random bullet is overlapping with another entity in the world of this ship after setting it to fire configuration,
	 * 			then that random bullet and said entity are immediately destroyed.
	 * 			| if ( for some entity in getWorld().getEntities() : overlap(new randomBullet, entity)) 
	 * 			|	then entity.terminate()
	 * 			|		and randomBullet.terminate() 
	 */
	public void fireBullet() {
		if (! this.isTerminated()) {
			if (getNbOfBulletsInMagazine() != 0 && getWorld() != null) {
				Bullet bulletToFire = (Bullet) getMagazine().toArray()[0];
				try {
					bulletToFire.setToFireConfiguration();
					// Cannot throw IllegalPositionException because this bullet is not contained in a world yet.
					removeAsLoadedBullet(bulletToFire);
					// Cannot throw IllegalBulletException, since bulletToFire was loaded in the magazine. 
					addAsFiredBullet(bulletToFire);
					// Cannot throw IllegalBulletException, since canHaveAsBullet(bulletToFire) was already true by class invariant.
					try {
						getWorld().addEntity(bulletToFire);
					} catch (IllegalArgumentException exc) {
						//The bullet to fire is placed outside of the boundaries of the world containing this ship. The bullet to fire is
						//therefore destroyed.
						bulletToFire.terminate();
					} catch (OverlapException exc) {
						//The bullet to fire overlaps with another entity in the world of this ship. Both the bullet and that entity are destroyed.
						exc.getFirstEntity().terminate();
						exc.getSecondEntity().terminate();
					}
				} catch (IllegalComponentException exc) {
					/* An IllegalComponentException can only thrown if the method setToFireConfiguration tried to set a component of the position
					 * of the bullet to fire to infinity or NaN. This can never be a valid position for a world so the bullet to fire must be
					 * destroyed (note that a bullet can only be fired by a ship that is contained in a world).
					 */
					bulletToFire.terminate();
				}
			} 
		}
	}
	
	/**
	 * Load a bullet in the magazine of this ship.
	 * 
	 * @param bullet
	 * 		The bullet to be loaded in the magazine this ship.
	 * @effect The ship of the given bullet is set to this ship.
	 * 		| bullet.setShip(this)
	 * @effect The bullet is set to the load configuration.
	 * 		| bullet.setToLoadConfiguration()
	 * @post This ship has the given bullet loaded in its magazine and this ship has not fired this bullet.
	 * 		| new.hasLoadedInMagazine(bullet) && ! new.hasFired(bullet)
	 * @throws IllegalBulletException
	 * 			This ship cannot have the given bullet as bullet,
	 * 				or (this ship cannot fully surround the given bullet),
	 * 				or (the bullet has been fired by this ship but does not apparently collide with this ship), 
	 * 				or (the ship associated to the given bullet is effective but different from this ship).
	 * 			| @see implementation
	 * @throws TerminatedException
	 * 		This ship is terminated
	 * 		| this.isTerminated()
	 */
	public void loadBullet(Bullet bullet) throws IllegalBulletException, TerminatedException {
		if (this.isTerminated())
			throw new TerminatedException();
		if (! canHaveAsBullet(bullet) || ! canSurround(bullet) || 
				(hasFired(bullet) && ! Entity.apparentlyCollide(this, bullet)) ||
				(bullet.getShip() != null && bullet.getShip() != this))
			throw new IllegalBulletException();
		if (hasFired(bullet))
			removeAsFiredBullet(bullet);
		addAsLoadedBullet(bullet);
		bullet.setShip(this);
		//Cannot throw IllegalMethodCallException because this ship is effective and the given bullet has been loaded in the magazine.
		//Cannot throw TerminatedException because canHaveAsBullet(bullet) implies !bullet.isTerminated().
		if (bullet.getWorld() != null)
			getWorld().removeEntity(bullet);
			//The method removeEntity cannot throw an exception because all conditions to throw exceptions are false in this case.
		bullet.setToLoadConfiguration();
	}
	
	/**
	 * Load multiple bullets in the magazine of this ship.
	 * @param bullets
	 * 		The bullets to be loaded in the magazine of this ship.
	 * @effect Every single bullet is loaded in the magazine of this ship.
	 * 			| for each bullet in bullets:
	 * 			|	loadBullet(bullets)
	 */
	public void loadBullets(Bullet... bullets) throws TerminatedException {
		if (isTerminated())
			throw new TerminatedException();
		for (Bullet bullet : bullets) {
			loadBullet(bullet);
		}
	}
	
	/**
	 * Set representing the bullets loaded on this ship.
	 * @invar The set of bullets loaded on this ship is effective
	 * 		| magazine != null
	 * @invar Each element in the magazine references a bullet
	 * 			that is an acceptable bullet for this ship.
	 * 		| for each bullet in magazine: canHaveAsBullet(bullet)
	 * @invar Each bullet in the magazine references this ship as the ship on which it is loaded.
	 * 		| for each bullet in magazine: bullet.getShip() == this
	 */
	private Set<Bullet> magazine = new HashSet<>();
	
	/**
	 * Set representing the bullets fired by this ship.
	 * @invar The set of bullets fired by this ship is effective
	 * 		| firedBullets != null
	 * @invar Each element in the set of fired bullets references a bullet
	 * 			that is an acceptable bullet for this ship.
	 * 		| for each bullet in firedBullets: canHaveAsBullet(bullet)
	 * @invar Each bullet in the set of fired bullets references this ship
	 * 			as the ship by which it has been fired.
	 * 		| for each bullet in firedBullets: bullet.getShip() == this
	 */
	private Set<Bullet> firedBullets = new HashSet<>();
	
}