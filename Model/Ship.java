package asteroids.model.representation;

import java.util.Set;
import java.util.HashSet;
import asteroids.model.exceptions.*;
import be.kuleuven.cs.som.annotate.*;

/**
 * A class representing a circular space ship dealing with
 * position, velocity, orientation, radius, density, mass and total mass.
 * 
 * @invar  The orientation of each ship must be a valid orientation for any ship.
 *       | isValidOrientation(getOrientation())
 * @invar  The minimal radius of each ship must be a valid minimal radius for any ship.
 *       | isValidMinimalRadius(getMinimalRadius())
 * @invar  The thrusterForce for this ship is a valid thrusterForce for any ship.
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
	public Ship(double xComPos, double yComPos, double xComVel, double yComVel, double radius, double orientation, double density,
			double mass, boolean thrusterStatus) throws IllegalComponentException, IllegalRadiusException {
		super(xComPos, yComPos, xComVel, yComVel, radius, density, mass);
		setThrust(thrusterStatus);
		setOrientation(orientation);
	}
	
	@Raw 
	public Ship(double xComPos, double yComPos, double xComVel, double yComVel, double radius,
			double orientation) throws IllegalComponentException, IllegalRadiusException {
		this(xComPos, yComPos, xComVel, yComVel, radius, orientation, 1.42e12, 10e20, false);
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
	public Ship(double xComPos, double yComPos, double radius) throws IllegalComponentException, IllegalRadiusException {
		this(xComPos, yComPos, 0, 0, radius, 0);
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
	 * Return the acceleration of this ship (in km / s^2)
	 * @return 0 if the thruster of this ship is not activated, and the quotient of this ship's thruster force and mass if the
	 * 				thruster is activated
	 * 			| if (hasThrusterActivated()) then result == getThrusterForce() / (getMass() * 1000)
	 * 			|	else result == 0
	 */
	public double getAcceleration() {
		if (hasThrusterActivated())
			return getThrusterForce() / (getMass() * 1000);
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
	@Raw
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
	 * Check whether this ship has loaded this bullet in its magazine.
	 */
	@Basic
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
	 */
	@Raw
	private void addAsLoadedBullet(@Raw Bullet bullet) throws IllegalBulletException {
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
	 */
	@Raw
	void removeAsLoadedBullet(Bullet bullet) throws IllegalBulletException, NullPointerException {
		if (! hasLoadedInMagazine(bullet))
			throw new IllegalBulletException();
		this.magazine.remove(bullet);
	}
	
	
	/**
	 * Check whether this ship has fired the given bullet. 
	 */
	@Basic
	public boolean hasFired(@Raw Bullet bullet) {
		return firedBullets.contains(bullet);
	}
	
	/**
	 * Add the given bullet to the collection of fired bullets of this ship.
	 * @param bullet
	 * 		The bullet to be added to the collection of fired bullets of this ship.
	 * @post If this ship can have the given bullet as bullet, then the bullet is added to the collection of fired bullets of this ship.
	 * 		| if (canHaveAsBullet(bullet))
	 * 		|	then hasFired(bullet)
	 * @throws IllegalBulletException
	 * 		This ship cannot have the given bullet as bullet.
	 * 		| ! canHaveAsBullet(bullet)
	 */
	@Raw
	private void addAsFiredBullet(@Raw Bullet bullet) throws IllegalBulletException {
		if (! canHaveAsBullet(bullet))
			throw new IllegalBulletException();
		this.firedBullets.add(bullet);
	}
	
	/**
	 * Remove the given bullet from the collection of fired bullets of this ship.
	 * @param bullet
	 * 		The bullet to remove the collection of fired bullets of this ship.
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
	 */
	@Raw
	void removeAsFiredBullet(Bullet bullet) throws IllegalBulletException, NullPointerException {
		if (! hasFired(bullet))
			throw new IllegalBulletException();
		this.firedBullets.remove(bullet);
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
	 * @return True iff the given bullet is effective and, if the bullet is associated to a world,
	 * 			this ship must be associated to the same world.
	 * 			| if (bullet == null)
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
		return (bullet != null && (bullet.getWorld() == null || getWorld() == bullet.getWorld()));
	}
	
	/**
	 * Check whether this ship has proper bullets associated to it.
	 * @return True iff each bullet in magazine is effective, has not been fired by this ship, is not associated to any world
	 * 			and references this ship as its ship, 
	 * 			and each bullet that has been fired by this ship, is effective, is not loaded in the magazine of this ship,
	 * 			is associated to the same world as this ship and references this ship as its ship.
	 * 			| result == 
	 * 			| 	(for each bullet in magazine:
	 * 			|		bullet != null && bullet.getShip() == this && ! hasFired(bullet) && bullet.getWorld() == null)
	 * 			|	&&
	 * 			|	(for each bullet in firedBullets:
	 * 			|		bullet != null && bullet.getShip() == this && ! hasLoadedInMagazine(bullet) && bullet.getWorld() == getWorld()) 			
	 */
	public boolean hasProperBullets() {
		for (Bullet bullet : magazine) {
			if (bullet == null || bullet.getShip() != this || hasFired(bullet) || bullet.getWorld() != null)
				return false;
		}
		for (Bullet bullet : firedBullets) {
			if (bullet == null || bullet.getShip() != this || hasLoadedInMagazine(bullet) || bullet.getWorld() != getWorld())
				return false;
		}
		return true;
	}
	
	/**
	 * Return the number of bullets loaded in the magazine of this ship. 
	 */
	@Basic @Raw
	public int getNbOfBulletsInMagazine() {
		return magazine.size();
	}
	
	/**
	 * Return the magazine with the loaded bullets of this ship.
	 */
	@Model @Basic
	private Set<Bullet> getMagazine() {
		return this.magazine;
	}
	
	/**
	 * Fire a bullet from the magazine of this ship.
	 * @post If the magazine of this ship is not empty and this ship is contained in a world,
	 * 			then a random bullet randomBullet is removed from the magazine
	 * 			and added to the world containing this ship, if any, and hasFired(randomBullet) is true.
	 * 		| if (getNbOfBulletsInMagazine() != 0 && getWorld() != null)
	 * 		|	then for precisely one bullet in getMagazine():
	 * 		|		hasFired((new bullet)) && ! hasLoadedInMagazine((new bullet))
	 * @effect If the magazine of this ship is not empty, said random bullet is set to fire configuration.
	 * 		| randomBullet.setToFireConfiguration() 
	 */
	public void fireBullet() {
		if ( getNbOfBulletsInMagazine() != 0 && getWorld() != null){
			Bullet bulletToFire = (Bullet)getMagazine().toArray()[0];
			// TODO: associate bullet to world of getShip() + catch possible exception in setToFireConfiguration()
			bulletToFire.setToFireConfiguration();
			removeAsLoadedBullet(bulletToFire);
			// Cannot throw IllegalBulletException, since bulletToFire was loaded in the magazine. 
			addAsFiredBullet(bulletToFire);
			// Cannot throw IllegalBulletException, since canHaveAsBullet(bulletToFire) was already true by class invariant.
		}
	}
	
	/**
	 * Load a bullet in the magazine of this ship.
	 * @param bullet
	 * 		The bullet to be loaded in the magazine this ship.
	 * @effect The ship of the given bullet is set to this ship.
	 * 		| bullet.setShip(this)
	 * @effect The bullet is set to the load configuration.
	 * 		| bullet.setToLoadConfiguration()
	 * @post This ship has the given bullet loaded in its magazine and this ship has not fired this bullet.
	 * 		| hasLoadedInMagazine(bullet) && ! hasFired(bullet)
	 * @throws IllegalBulletException
	 * 			This ship cannot have the given bullet as bullet,
	 * 				or (the given bullet does not lie fully within the bounds of this ship and this ship has not fired the given bullet),
	 * 				or (the ship associated to the given bullet is effective but different from this ship).
	 * 			| @see implementation
	 */
	public void loadBullet(Bullet bullet) throws IllegalBulletException {
		if (! canHaveAsBullet(bullet) || (! surrounds(bullet) && ! hasFired(bullet) ) || (bullet.getShip() != null && bullet.getShip() != this))
			throw new IllegalBulletException();
		if (hasFired(bullet))
			removeAsFiredBullet(bullet);
		addAsLoadedBullet(bullet);
		bullet.setShip(this);
		// TODO: associate bullet to null world
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
	public void loadBullets(Bullet... bullets) {
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