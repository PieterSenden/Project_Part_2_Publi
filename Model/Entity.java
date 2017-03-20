package asteroids.model.model;

import asteroids.model.exceptions.IllegalComponentException;
import be.kuleuven.cs.som.annotate.Basic;
import be.kuleuven.cs.som.annotate.Immutable;
import be.kuleuven.cs.som.annotate.Model;
import be.kuleuven.cs.som.annotate.Raw;

/**
 * A class representing an entity floating in outer space.
 * 
 * @invar  Each entity can have its position as position.
 *       	| canHaveAsPosition(getPosition())
 * @invar  Each entity can have its velocity as velocity.
 *       	| canHaveAsVelocity(getVelocity())
 *       
 * 
 * @author Joris Ceulemans & Pieter Senden
 * @version 2.0
 */

public abstract class Entity {
	
	/**
	 * Return the position of this entity.
	 */
	@Basic @Raw
	public Position getPosition() {
		if (this.position == null)
			return null;
		return this.position.clone();
	}
	
	/**
	 * Check whether the given position is a valid position for this entity.
	 *  
	 * @param  position
	 *         The position to check.
	 * @return true iff the given position is effective and this entity can have the xComponent and yComponent of the given position
	 * 			as the xComponent and yComponent of its position.
	 *       | result == (position != null) && canHaveAsPosition(position.getxComponent(), position.getyComponent())
	 */
	
	public boolean canHaveAsPosition(Position position) {
		if (position == null)
			return false;
		return canHaveAsPosition(position.getxComponent(), position.getyComponent());
	}
	
	/**
	 * Check whether the given components can be components of a valid position for this entity.
	 * 
	 * @param xComponent
	 * 			The xComponent to check.
	 * @param yComponent
	 * 			The yComponent to check.
	 * @return true iff the world of this entity is not effective or (the world of this entity is effective and
	 * 			the distance between each boundary of the world and the centre of this entity is greater than the radius
	 * 			of this entity times the accuracy factor).
	 * 			| @see implementation
	 */
	public boolean canHaveAsPosition(double xComponent, double yComponent) {
		if (getWorld() == null)
			return true;
		if ((xComponent >= getRadius() * ACCURACY_FACTOR) && (yComponent >= getRadius() * ACCURACY_FACTOR) 
				&& (getWorld().getHeight() - xComponent >= getRadius() * ACCURACY_FACTOR)
				&& (getWorld().getWidth() - yComponent >= getRadius() * ACCURACY_FACTOR))
			return true;
		return false;
	}
	
	/**
	 * Move this entity during a given time duration.
	 * 
	 * @param duration
	 * 			The length of the time interval during which the entity is moved.
	 * @effect Each new component of the position of this entity is set to the sum of the old component
	 * 			and the given duration times the corresponding component of the velocity of this entity.
	 * 			| setPosition(getPosition().getxComponent() + duration * getVelocity().getxComponent(),
	 * 			|				getPosition().getyComponent() + duration * getVelocity().getyComponent())
	 * @throws IllegalArgumentException
	 * 			The given duration is strictly less than 0.
	 * 			| duration < 0
	 */
	public void move(double duration) throws IllegalArgumentException, IllegalComponentException {
		if (duration < 0)
			throw new IllegalArgumentException();
		setPosition(getPosition().getxComponent() + duration * getVelocity().getxComponent(),
				getPosition().getyComponent() + duration * getVelocity().getyComponent());
	}
	
	
	/**
	 * Set the position of this entity to the given position.
	 * 
	 * @param  xComponent
	 *         The new xComponent for the position for this entity.
	 * @param  yComponent
	 *         The new yComponent for the position for this entity.
	 * @post   The xComponent of the position of this new entity is equal to the given xComponent.
	 *       | new.getPosition().getxComponent() == xComponent
	 * @post   The yComponent of the position of this new entity is equal to the given yComponent.
	 *       | new.getPosition().getyComponent() == yComponent
	 * @throws IllegalComponentException
	 * 		   One of the given components is not valid
	 * 		 | ! Position.isValidComponent(xComponent) || ! Position.isValidComponent(yComponent)
	 */
	@Raw @Model
	private void setPosition(double xComponent, double yComponent) throws IllegalComponentException {
		try {
			this.position.setComponents(xComponent, yComponent);
		}
		catch(NullPointerException exc) {
			this.position = new Position(xComponent, yComponent);
		}
	}
	
	/**
	 * Variable registering the position of this entity.
	 */
	private Position position;
	private static final double ACCURACY_FACTOR = 0.99;
	
	
	/**
	 * Return the velocity of this entity.
	 */
	@Basic @Raw
	public Velocity getVelocity() {
		if (this.velocity == null)
			return null;
		return this.velocity.clone();
	}
	
	/**
	 * Check whether this entity can have the given velocity as its velocity.
	 *  
	 * @param  velocity
	 *         The velocity to check.
	 * @return true iff the given velocity is effective and the associated speed does not exceed
	 *		the speedLimit of this entity.
	 *       | @see implementation
	 */
	public boolean canHaveAsVelocity(Velocity velocity) {
		if (velocity == null)
			return false;
		if (velocity.getSpeed() > getSpeedLimit())
			return false;
		return true;
	}
	
	/**
	 * Set the velocity of this entity to the given velocity.
	 * 
	 * @param  xComponent
	 *         The new xComponent for the velocity for this entity.
	 * @param  yComponent
	 *         The new yComponent for the velocity for this entity.
	 * @post   If this entity can have the velocity with the given xComponent and  given yComponent as its velocity, 
	 * 			then the xComponent of the velocity of this new entity is equal to the given xComponent,
	 * 			and the yComponent of the velocity of this new entity is equal to the given yComponent.
	 *       | if (this.canHaveAsVelocity(new Velocity(xComponent,yComponent))
	 *       | 		then (new.getVelocity().getxComponent() == xComponent)
	 *       |			&& (new.getVelocity().getyComponent() == yComponent)
	 * @post   If this entity cannot have the velocity with the given xComponent and  given yComponent as its velocity,
	 * 			the new velocity of this entity is set to a velocity such that the direction corresponds with the
	 *			velocity with given xComponent and yComponent, but the speed is set to the speedLimit. More concretely,
	 *			the xComponent of the new velocity of this entity is set to (xComponent * getSpeedLimit() / speed) and the
	 *			yComponent of the new velocity of this entity is set to (yComponent * getSpeedLimit() / speed), where
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
	 * Variable registering the velocity of this entity.
	 */
	private Velocity velocity;
	
	
	/**
	 * Return the speed limit of this entity.
	 */
	@Basic @Raw @Immutable
	public double getSpeedLimit() {
		return this.speedLimit;
	}
	
	/**
	 * Check whether the given speedLimit is a valid speedLimit for any entity.
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
	 * Variable registering the speed limit of this entity.
	 */
	private final double speedLimit = SPEED_OF_LIGHT;
	
	/**
	 * Constant representing the speed of light (i.e. 300000 km/s)
	 */
	
	public static final double SPEED_OF_LIGHT = 300000;
	
	
	/**
	 * Return the radius of this entity.
	 */
	@Basic @Raw @Immutable
	public double getRadius() {
		return this.radius;
	}
	
	/**
	 * Check whether the given radius is a valid radius for any entity.
	 *  
	 * @param  radius
	 *         The radius to check.
	 * @return true iff the given radius is larger than the minimal radius for any entity.
	 *       | result == (radius >= getMinimalRadius())
	*/
	public static boolean isValidRadius(double radius) {
		return radius >= getMinimalRadius();
	}
	
	/**
	 * Variable registering the radius of this entity.
	 */
	private final double radius;
	
	
	
	
	
	public World getWorld() {
		return this.world;
	}
	
	private World world;
}
