package asteroids.model.representation;

import asteroids.model.exceptions.*;
import be.kuleuven.cs.som.annotate.*;

/**
 * A class representing a circular bullet dealing with
 * position, velocity, radius, density, and mass.
 * @invar  The minimal radius of each bullet must be a valid minimal radius for any bullet.
 *       | isValidMinimalRadius(getMinimalRadius())
 * @invar Each bullet must have a proper ship.
 * 		 | hasProperShip()
 * @note In this class, when we state 'this bullet is associated to a ship', we mean that
 * 			the bullet is either loaded in the magazine of the ship or has been fired by the ship.
 * 			In the former case, the bullet is not associated to a world. In the latter case,
 * 			it is associated to a world.
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
	
	/**
	 * Return the ship associated to this bullet, i.e. the ship that holds this bullet or fired it.
	 */
	@Basic
	public Ship getShip() {
		return this.ship;
	}
	
	
	/**
	 * Check whether this bullet can be associated to the given ship.
	 * @param ship
	 * 		The ship to check.
	 * @return 
	 * 			| result == ((ship == null) || ship.canHaveAsBullet(this)) 
	 */
	@Raw
	public boolean canHaveAsShip(Ship ship) {
		return ((ship == null) || ship.canHaveAsBullet(this));
	}
	
	/**
	 * Check whether this bullet has a proper ship.
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
	 */
	@Raw
	public boolean hasProperShip() {
		if (! canHaveAsShip(getShip()) )
			return false;
		else if (getShip() == null)
			return true;
		else if (getWorld() == null)
			// This bullet must be loaded on its ship.
			return getShip().hasLoadedInMagazine(this);
		else
			// This bullet must have been fired by its ship.
			// Because if canHaveAsShip() is true, getShip() != null and getWorld() != null
			// it must hold that (getWorld() == getShip().getWorld())
			return getShip().hasFired(this);
	}
	
	/**
	 * Set this bullet to fire configuration.
	 * @post | if (getShip() != null && getShip().hasLoadedInMagazine(this))
	 * 		 | 	then Entity.getDistanceBetweenCentres(new, getShip()) == (1 + 5 * (1 - ACCURACY_FACTOR)) * Entity.getSumOfRadii(this, getShip())
	 * 		 | 		&& (new.getPosition().getyComponent() - getShip().getPosition().getyComponent() ==
	 * 		 |			Math.tan(getShip().getOrientation()) * ((new.getPosition().getxComponent() - getShip().getPosition().getxComponent() )
	 * 		 |		&& (new.getVelocity().getSpeed() == 250 && new.getVelocity().getyComponent() ==
	 * 		 |			Math.tan(getShip().getOrientation()) * new.getVelocity().getxComponent()
	 * @note This method must only be invoked in the method fireBullet() of the class Ship
	 */
	void setToFireConfiguration() {
		if (getShip() != null && getShip().hasLoadedInMagazine(this)) {
			double newDistanceBetweenCentres = (1 + 5 * (1 - ACCURACY_FACTOR)) * Entity.getSumOfRadii(this, getShip());
			double angle = getShip().getOrientation();
			setPosition(getShip().getPosition().getxComponent() + newDistanceBetweenCentres * Math.cos(angle),
					getShip().getPosition().getyComponent() + newDistanceBetweenCentres * Math.sin(angle));
			setVelocity(250 * Math.cos(angle), 250 * Math.sin(angle));
		}
	}
	
	/**
	 * Set this bullet to the load configuration.
	 * @post | if (getShip() != null)
	 * 		 | 	then new.getPosition().equals(new Position(0, 0)) &&
	 * 		 |			new.getVelocity().equals(new Velocity(0, 0))
	 * @note This method must only be invoked in the method loadBullet() of the class Ship
	 */
	void setToLoadConfiguration() {
		if (getShip() != null && getShip().hasLoadedInMagazine(this)) {
			setPosition(0, 0);
			setVelocity(0, 0);
		}
	}
	
	/**
	 * Set the ship of this bullet to the given ship.
	 * @param ship
	 * 		The new ship for this bullet.
	 * @post | if ( (ship == null && (getShip() == null || ! getShip().hasAsBullet(this))))
	 * 		 | 	then new.getShip() == ship
	 * @post | if (ship != null && ship.hasAsBullet(this))
	 * 		 |	then new.getShip() == ship
	 * @throws IllegalMethodCallException
	 * 			| ( ( ship != null && ! ship.hasAsBullet(this)) ||
	 * 		   	|	(ship == null && getShip() != null && getShip().hasAsBullet(this))
	 * @note If this method is invoked with an effective ship and does not throw an exception,
	 * 			then the world of this bullet must be set to null.
	 */
	@Raw
	void setShip(Ship ship) throws IllegalMethodCallException {
		if (( ship != null && ! ship.hasAsBullet(this)) ||
				(ship == null && getShip() != null && getShip().hasAsBullet(this)))
			throw new IllegalMethodCallException();
		this.ship = ship;
	}
	
	private Ship ship;
}
