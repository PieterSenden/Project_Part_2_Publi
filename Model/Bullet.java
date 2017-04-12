package asteroids.model.representation;

import asteroids.model.exceptions.*;
import be.kuleuven.cs.som.annotate.*;

/**
 * A class representing a circular bullet dealing with position, velocity, radius, density, mass and number
 * of bounces. A bullet can also be loaded on a ship and fired by that ship.
 * 
 * @invar The minimal radius of each bullet must be a valid minimal radius for any bullet.
 *       | isValidMinimalRadius(getMinimalRadius())
 * @invar Each bullet can have its nbOfBounces as nbOfBounces
 * 		 | canHaveAsNbOfBounces(getNbOfBounces)
 * @invar Each bullet can have its maximal number of bounces as maximal number of bounces.
 *       | canHaveAsMaximalNbOfBounces(this.getMaximalNbOfBounces())
 * @invar Each bullet must have a proper ship.
 * 		 | hasProperShip()
 * @note In this class, when we state 'this bullet is associated to a ship', we mean that
 * 			the bullet is either loaded in the magazine of the ship or has been fired by the ship.
 * 			In the former case, the bullet is not associated to a world. In the latter case,
 * 			it is associated to a world (implemented in the superclass Entity).
 * 
 * @author Joris Ceulemans & Pieter Senden
 * @version 2.0
 */

public class Bullet extends Entity {
	/**
	 * Initialize this new Bullet with given position, velocity, radius, density and mass.
	 * 
	 * @param xComPos
	 * 			The xComponent of the position of this new bullet.
	 * @param yComPos
	 * 			The yComponent of the position of this new bullet.
	 * @param xComVel
	 * 			The xComponent of the velocity of this new bullet.
	 * @param yComVel
	 * 			The yComponent of the velocity of this new bullet.
	 * @param density
	 * 			The density of this new bullet.
	 * @param mass
	 * 			The mass of this new bullet.
	 * @effect	| super(xComPos, yComPos, xComVel, yComVel, radius, density, mass)
	 */
	@Raw
	public Bullet(double xComPos, double yComPos, double xComVel, double yComVel, double radius,
			double mass) throws IllegalComponentException, IllegalPositionException, IllegalRadiusException {
		super(xComPos, yComPos, xComVel, yComVel, radius, mass);
	}
	
	/**
	 * Initialize this new Bullet with given position, velocity and radius.
	 * 
	 * @param xComPos
	 * 			The xComponent of the position of this new bullet.
	 * @param yComPos
	 * 			The yComponent of the position of this new bullet.
	 * @param xComVel
	 * 			The xComponent of the velocity of this new bullet.
	 * @param yComVel
	 * 			The yComponent of the velocity of this new bullet.
	 * @param radius
	 * 			The radius of this new bullet.
	 * @effect	| this(xComPos, yComPos, xComVel, yComVel, radius, 7.8e12, 10e20)
	 */
	@Raw
	public Bullet(double xComPos, double yComPos, double xComVel, double yComVel, double radius) throws IllegalComponentException, 
																				IllegalPositionException, IllegalRadiusException{
		this(xComPos, yComPos, xComVel, yComVel, radius, 10e20);
		//The value of the mass appears to be set to 10e=20. However, this value is changed to the only possible mass for a bullet
		//with a given radius in the constructor of Entity.
	}
	
	/**
	 * Return a copy of this bullet.
	 * 
	 * @return A copy of this bullet.
	 * 			| @see implementation
	 * @throws TerminatedException
	 * 			| this.isTerminated()
	 */
	@Override
	public Bullet copy() throws TerminatedException {
		if (isTerminated())
			throw new TerminatedException();
		return new Bullet(getPosition().getxComponent(), getPosition().getyComponent(), getVelocity().getxComponent(),
				getVelocity().getyComponent(), getRadius(), getMass());
	}
	
	
	/**
	 * Terminate this bullet.
	 * 
	 * @effect	| super.terminate()
	 * @effect	| if (getShip != null)
	 * 			|	then getShip.removeBullet(this)
	 * TODO Probably breaking up the association between a world and an entity will be worked out in the method terminate() of the class
	 * 		Entity. In that case the implementation of terminate() in Bullet needs to be adapted and no additional effect clause is needed.
	 */
	@Override
	public void terminate() {
		if (!isTerminated()) {
			if (getShip() != null)
				getShip().removeBullet(this);
			if (getWorld() != null) {
				getWorld().removeEntity(this);
			}
			super.terminate();
		}
	}
	
	
	/** 
	 * Check whether this bullet can have the given density as its density
	 * 
	 * @return True iff the given density is equal to the minimal density
	 * 			| @see implementation
	 */
	@Override @Raw
	public boolean canHaveAsDensity(double density) {
		return density == getMinimalDensity();
	}
	
	/**
	 * Return the minimal density of this bullet.
	 */
	@Override @Basic @Immutable @Raw
	public double getMinimalDensity() {
		return minimalDensity;
	}
	
	/**
	 * A variable registering the minimal density for any bullet. Note that a bullet's density cannot change and will always be equal
	 * 	to the minimal density.
	 */
	private final double minimalDensity = 7.8e12;
	
	
	/**
	 * Check whether this bullet can have the given radius as its radius.
	 * 
	 * @param radius
	 * 			The radius to check.
	 * @return @see implementation.
	 */
	@Override @Raw
	public boolean canHaveAsRadius(double radius) {
		return radius >= getMinimalRadius();
	}
	
	/**
	 * Return the minimal radius of any bullet.
	 */
	@Basic @Raw
	public static double getMinimalRadius() {
		return minimalRadius;
	}
	
	/**
	 * Check whether the given minimal radius is a valid minimal radius for any bullet.
	 *  
	 * @param  minimalRadius
	 *         The minimal radius to check.
	 * @return true iff the given minimal radius is positive and less than or equal to the current minimal radius.
	 *       | result == (minimalRadius > 0) && (minimalRadius <= getMinimalRadius())
	 * @note   The reason why only values less than or equal to the current minimal radius are valid, is that according to the class invariant
	 * 			regarding the radius (in Entity), each bullet's radius must be greater than or equal to the minimal radius. This invariant
	 * 			could be broken if we allowed the minimal radius to increase.
	 */
	public static boolean isValidMinimalRadius(double minimalRadius) {
		return (minimalRadius > 0) && (minimalRadius <= Bullet.getMinimalRadius());
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
	 * Variable registering the minimal radius for any bullet.
	 */
	private static double minimalRadius = 1;
	
	
	/**
	 * Return the number of bounces of this bullet against a boundary of its world.
	 * 
	 * If a bullet is not associated to a world, this value will be equal to zero. Also, if a bullet is loaded on a ship, this value is
	 * reset to zero.
	 */
	@Basic @Raw
	public int getNbOfBounces() {
		return nbOfBounces;
	}
	
	/**
	 * Check whether this bullet can have its number of bounces as number of bounces.
	 * 
	 * @param number
	 * 			The number to check.
	 * @return @see implementation.
	 */
	@Raw
	public boolean canHaveAsNbOfBounces(int number) {
		return (0 <= number) && (number <= getMaximalNbOfBounces());
	}
	
	/**
	 * Set the number of bounces of this bullet to the given number.
	 * 
	 * @param number
	 * 			The new number of bounces for this bullet.
	 * @post	| new.getNbOfBounces() == number
	 * @throws TerminatedException
	 * 			| this.isTerminated()
	 * @throws IllegalArgumentException
	 * 			| ! canHaveAsNbOfBounces(number)
	 */
	private void setNbOfBounces(int number) throws TerminatedException, IllegalArgumentException {
		if (isTerminated())
			throw new TerminatedException();
		if (!canHaveAsNbOfBounces(number))
			throw new IllegalArgumentException();
		nbOfBounces = number;
	}
	
	/**
	 * Increment the number of bounces of this bullet with one.
	 * 
	 * @effect	| setNbOfBouces(getNbOfBounces() + 1)
	 */
	private void stepNbOfBounces() throws TerminatedException, IllegalArgumentException {
		setNbOfBounces(getNbOfBounces() + 1);
	}
	
	/**
	 * Reset the number of bounces of this bullet.
	 * 
	 * @effect	| setNbOfBounces(0)
	 */
	private void resetNbOfBounces() throws TerminatedException {
		setNbOfBounces(0);
	}
	
	/**
	 * Variable registering the number of times this bullet has collided with the boundary of its world.
	 */
	private int nbOfBounces = 0;
	
	
	/**
	 * Return the maximal number of bounces of this bullet.
	 * 
	 * If this bullet has reached its maximal number of bounces and collides with the boundary of its world, this bullet is destroyed.
	 */
	@Basic @Raw @Immutable
	public int getMaximalNbOfBounces() {
		return this.maximalNbOfBounces;
	}
	
	/**
	 * Check whether this bullet can have the given maximal number of bounces as its maximal number of bounces.
	 *  
	 * @param  maximalNbOfBounces
	 *         The maximal number of bounces to check.
	 * @return 
	 *       | result == (0 <= maximalNbOfBounces)
	*/
	@Raw
	public boolean canHaveAsMaximalNbOfBounces(int maximalNbOfBounces) {
		return 0 <= maximalNbOfBounces;
	}
	
	/**
	 * Variable registering the maximal number of bounces of this bullet.
	 */
	private final int maximalNbOfBounces = 2;
	
	
	/**
	 * Make this bullet bounce of the boundary of its world.
	 * 
	 * @effect	| if (getNbOfBouces() >= getMaximalNbOfBounces())
	 * 			|	then terminate()
	 * @effect	| if (getNbOfBouces() < getMaximalNbOfBounces())
	 * 			|	then stepNbOfBounces
	 * @effect	| if (getNbOfBouces() < getMaximalNbOfBounces() && apparentlyCollidesWithHorizontalBoundary())
	 * 			|	then setVelocity(getVelocity().getxComponent(), -getVelocity().getyComponent())
	 * @effect	| if (getNbOfBouces() < getMaximalNbOfBounces() && apparentlyCollidesWithVerticalBoundary())
	 * 			|	then setVelocity(-getVelocity().getxComponent(), getVelocity().getyComponent())
	 * @throws	TerminatedException
	 * 			| isTerminated()
	 * @throws	IllegalMethodCallException
	 * 			| getWorld() == null || !apparentlyCollidesWithBoundary()
	 */
	@Override
	public void bounceOfBoundary() throws TerminatedException, IllegalMethodCallException {
		if (isTerminated())
			throw new TerminatedException();
		if (getWorld() == null || !apparentlyCollidesWithBoundary())
			throw new IllegalMethodCallException();
		if (getNbOfBounces() >= getMaximalNbOfBounces())
			terminate();
		else {
			stepNbOfBounces();
			if (apparentlyCollidesWithHorizontalBoundary())
				setVelocity(getVelocity().getxComponent(), -getVelocity().getyComponent());
			else if (apparentlyCollidesWithVerticalBoundary())
				setVelocity(-getVelocity().getxComponent(), getVelocity().getyComponent());
		}
	}
	
	/**
	 * Resolve a collision between a bullet and another entity.
	 * Only collisions between bullets and other bullets or ships are resolved.
	 * 
	 * @effect	| if (other instanceof Ship && ((Ship)other).hasFired(this))
	 * 			|	then ((Ship)other).loadBullet(this)
	 * @effect	| if ((other instanceof Ship && !((Ship)other).hasFired(this)) || other instancof Bullet)
	 * 			|	then this.terminate() && other.terminate()
	 * @throws	TerminatedException
	 * 			| this.isTerminated() || other.isTerminated()
	 * @throws	IllegalMethodCallException
	 * 			| (getWorld() == null) || (getWorld() != other.getWorld()) || !Entity.apparentlyCollide(this, other)
	 */
	@Override
	public void resolveCollision(Entity other) throws IllegalMethodCallException, TerminatedException {
		if (isTerminated() || other.isTerminated())
			throw new TerminatedException();
		if (getWorld() == null || getWorld() != other.getWorld() || !Entity.apparentlyCollide(this, other))
			throw new IllegalMethodCallException();
		if (other instanceof Ship)
			if (((Ship)other).hasFired(this))
				((Ship)other).loadBullet(this);
			else {
				terminate();
				other.terminate();
			}
		else if (other instanceof Bullet) {
			terminate();
			other.terminate();
		}
	}
	
	/**
	 * Check whether if a collision between this entity and the given other entity occurs, it must be shown.
	 * This method does not check if this entity and the other entity collide, only whether the collision must be shown if they do.
	 * 
	 * @param other
	 * 			The other entity.
	 * @return	| !(other instanceof Ship) || !((Ship)other).hasFired(this)
	 */
	@Override
	public boolean mustShowCollisionWith(Entity other) {
		if (other instanceof Ship)
			return !((Ship)other).hasFired(this);
		return true;
	}
	
	
	/**
	 * Determine whether this bullet can be removed from its world.
	 * 
	 * @return	| result == (getWorld() != null) && ((getShip() == null) || Entity.apparentlyCollide(this, getShip()))
	 */
	@Override
	public boolean canBeRemovedFromWorld() {
		if (getWorld() == null)
			return false;
		if ((getShip() != null) && !Entity.apparentlyCollide(this, getShip()))
			return false;
		return true;
	}
	
	
	/**
	 * Return the ship associated to this bullet, i.e. the ship that holds this bullet or fired it.
	 */
	@Basic @Raw
	public Ship getShip() {
		return this.ship;
	}
	
	/**
	 * Check whether this bullet can be associated to the given ship.
	 * 
	 * @param ship
	 * 		The ship to check.
	 * @return 
	 * 			| result == ((ship == null) || ship.canHaveAsBullet(this))
	 * TODO Check that ship is not terminated (in ship.canHaveAsBullet(this)) 
	 */
	@Raw
	public boolean canHaveAsShip(Ship ship) {
		return ((ship == null) || ship.canHaveAsBullet(this));
	}
	
	/**
	 * Check whether this bullet has a proper ship.
	 * 
	 * @return 
	 * 			| if (canHaveAsShip(getShip())
	 * 			|	if (getShip() == null)
	 * 			|		then result == true
	 * 			|	else if (getWorld() == null)
	 * 			|		then result == getShip().hasLoadedInMagazine(this)
	 * 			|	else if (getWorld() == getShip().getWorld())
	 * 			|		then result == getShip().hasFired(this)
	 * 			| else
	 * 			|	then result == false
	 * 
	 */
	@Raw
	public boolean hasProperShip() {
		if (! canHaveAsShip(getShip()) )
			return false;
		else if (getShip() == null)
			return true;
		else if (getWorld() == null)
			// This bullet must be loaded on its ship, because it is associated to a ship but not to a world.
			return getShip().hasLoadedInMagazine(this);
		else
			// This bullet must have been fired by its ship.
			// At this point we know that canHaveAsShip(getShip()) is true, getShip() != null and getWorld() != null,
			// so it must hold that (getWorld() == getShip().getWorld()). We therefore do not have to check this equality.
			return getShip().hasFired(this);
	}
	
	/**
	 * Set this bullet to fire configuration.
	 * 
	 * @post | if (!isTerminated() && getShip() != null && getShip().hasLoadedInMagazine(this))
	 * 		 | 	then Entity.getDistanceBetweenCentres(new, getShip()) == (1 + 5 * (1 - ACCURACY_FACTOR)) * Entity.getSumOfRadii(this, getShip())
	 * 		 | 		&& (new.getPosition().getyComponent() - getShip().getPosition().getyComponent() ==
	 * 		 |			Math.tan(getShip().getOrientation()) * ((new.getPosition().getxComponent() - getShip().getPosition().getxComponent() )
	 * 		 |		&& (new.getVelocity().getSpeed() == 250 && new.getVelocity().getyComponent() ==
	 * 		 |			Math.tan(getShip().getOrientation()) * new.getVelocity().getxComponent()
	 * @note This method must only be invoked in the method fireBullet() of the class Ship
	 */
	void setToFireConfiguration() throws IllegalComponentException, IllegalPositionException {
		if (!isTerminated() && getShip() != null && getShip().hasLoadedInMagazine(this)) {
			double newDistanceBetweenCentres = (1 + 5 * (1 - ACCURACY_FACTOR)) * Entity.getSumOfRadii(this, getShip());
			double angle = getShip().getOrientation();
			setPosition(getShip().getPosition().getxComponent() + newDistanceBetweenCentres * Math.cos(angle),
					getShip().getPosition().getyComponent() + newDistanceBetweenCentres * Math.sin(angle));
			setVelocity(250 * Math.cos(angle), 250 * Math.sin(angle));
		}
	}
	
	/**
	 * Set this bullet to the load configuration.
	 * 
	 * @post | if (!isTerminated() && (getShip() != null) && getShip().hasLoadedInMagazine(this))
	 * 		 | 	then new.getPosition().equals(getShip().getPosition()) &&
	 * 		 |			new.getVelocity().equals(new Velocity(0, 0)) && (new.getNbOfBounces() == 0)
	 * @note This method must only be invoked in the method loadBullet() of the class Ship
	 */
	void setToLoadConfiguration() {
		if (!isTerminated() && (getShip() != null) && getShip().hasLoadedInMagazine(this)) {
			setPosition(getShip().getPosition());
			//Cannot throw IllegalComponentException or IllegalPositionException because getShip() is on a legal position (and if this bullet
			// is associated to a world, getShip() is associated to the same world because of the class invariant).
			setVelocity(0, 0);
			resetNbOfBounces();
		}
	}
	
	/**
	 * Set the ship of this bullet to the given ship.
	 * 
	 * @param ship
	 * 		The new ship for this bullet.
	 * @post    | new.getShip() == ship
	 * @throws IllegalMethodCallException
	 * 			| ( ( ship != null && ! ship.hasAsBullet(this)) ||
	 * 		   	|	(ship == null && getShip() != null && getShip().hasAsBullet(this))
	 * @note If this method is invoked with an effective ship and does not throw an exception,
	 * 			then the world of this bullet must be set to null.
	 */
	@Raw
	void setShip(Ship ship) throws IllegalMethodCallException {
		if (isTerminated())
			throw new TerminatedException();
		if ((ship != null && ! ship.hasAsBullet(this)) ||
				(ship == null && getShip() != null && getShip().hasAsBullet(this)))
			throw new IllegalMethodCallException();
		this.ship = ship;
	}
	
	/**
	 * A variable registering the ship to which this bullet is associated (contained in or fired by).
	 */
	private Ship ship;
}
