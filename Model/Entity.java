package asteroids.model.representation;

import asteroids.model.exceptions.*;
import be.kuleuven.cs.som.annotate.*;

/**
 * A class representing an entity floating in outer space.
 * 
 * @invar  Each entity can have its position as position.
 *       	| canHaveAsPosition(getPosition())
 * @invar  Each entity can have its velocity as velocity.
 *       	| canHaveAsVelocity(getVelocity())
 * @invar  Each entity can have its density as density.
 *       	| canHaveAsDensity(this.getDensity())
 * @invar  Each entity can have its mass as mass.
 *       	| canHaveAsMass(this.getMass())
 * @invar  Each entity can have its radius as radius.
 *       	| canHaveAsRadius(this.getRadius())
 * @invar  The speed limit of this entity is a valid speed limit for any entity.
 *     	    | isValidSpeedLimit(this.getSpeedLimit())
 * @invar  Each entity has a proper world.
 * 			| hasProperWorld()
 * 
 * @author Joris Ceulemans & Pieter Senden
 * @version 2.0
 */

public abstract class Entity {
	
	/**
	 * Initialize this new entity with given position, velocity, radius, density and mass.
	 * @param xComPos
	 * 			The xComponent of the position of this new entity.
	 * @param yComPos
	 * 			The yComponent of the position of this new entity.
	 * @param xComVel
	 * 			The xComponent of the velocity of this new entity.
	 * @param yComVel
	 * 			The yComponent of the velocity of this new entity.
	 * @param radius
	 * 			The radius of this new entity.
	 * @param density
	 * 			The density of this new entity.
	 * @param mass
	 * 			The mass of this new entity.
	 * @effect The position of this new entity is set to the position with given xComponent and yComponent.
	 * 			| setPosition(xComPos, yComPos)
	 * @effect The velocity of this new entity is set to the velocity with given xComponent and yComponent.
	 * 			| setVelocity(xComVel, yComVel)
	 * @post The radius of this new entity is equal to the given radius.
	 * 			| new.getRadius() == radius
	 * @post If this entity can have the given density as its density, then 
	 * 			the density of this new entity is equal to the given density.
	 * 		 Else, the density of this new entity is equal to getMinimalDensity() 
	 * 			| if canHaveAsDensity(density)
	 * 			|	then new.getDensity() == density
	 * 			| else new.getDensity() == getMinimalDensity()
	 * @post If this entity can have the given mass as its mass, then 
	 * 			the mass of this new entity is equal to the given mass.
	 * 		 Else, the mass of this new entity is equal to getMinimalMass() 
	 * 			| if canHaveAsMass(mass)
	 * 			|	then new.getMass() == mass
	 * 			| else new.getMass() == getVolume() * getDensity() 
	 * @throws The given radius is not a valid radius for any entity.
	 * 			| ! isValidRadius(radius)
	 */
	public Entity(double xComPos, double yComPos, double xComVel, double yComVel, double radius, double density, double mass)
			throws IllegalComponentException, IllegalPositionException, IllegalRadiusException {
		setPosition(xComPos, yComPos);
		setVelocity(xComVel, yComVel);
		if (! canHaveAsRadius(radius))
			throw new IllegalRadiusException();
		this.radius = radius;
		if (! canHaveAsDensity(density))
			density = getMinimalDensity();
		this.density = density;
		if (! canHaveAsMass(mass))
			mass = getVolume() * getDensity();
		setMass(mass);
	}
	
	public abstract Entity copy();
	
	/**
	 * Terminate this entity.
	 *
	 * @post   This entity  is terminated.
	 *       | new.isTerminated()
	 * @post   ...TODO
	 *       | ...
	 */
	public void terminate() {
		this.isTerminated = true;
	}
	
	/**
	 * Return a boolean indicating whether or not this entity is terminated.
	 */
	@Basic @Raw
	public boolean isTerminated() {
		return this.isTerminated;
	}
	
	/**
	 * Variable registering whether this person is terminated.
	 */
	private boolean isTerminated = false;
	
	public static final double ACCURACY_FACTOR = 0.99;
	
	/**
	 * Return the position of this entity.
	 */
	@Basic @Raw
	public Position getPosition() {
		if (this.position == null)
			return null;
		return this.position;
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
		if (isTerminated())
			return false;
		return getWorld().boundariesSurround(this);
	}
	
	
	/**
	 * Move this entity during a given time duration.
	 * 
	 * @param duration
	 * 			The length of the time interval during which the entity is moved.
	 * @effect The new position of this entity is set to the position that is the result of the position of this entity moved with
	 * 			the velocity of this entity and during the given duration.
	 * 			| @see implementation
	 * @throws IllegalArgumentException
	 * 			The given duration is strictly less than 0.
	 * 			| duration < 0
	 */
	public void move(double duration) throws IllegalArgumentException, IllegalComponentException, IllegalStateException, 
																							IllegalPositionException {
		if (isTerminated())
			throw new IllegalStateException();
//		try {
		setPosition(getPosition().move(getVelocity(), duration));
//		}
//		catch (IllegalPositionException exc) {
//			Position newCollidingPosition = getPosition().move(getVelocity(), duration);
//			Position newPosition = null;
//			if (this.getPosition().getxComponent() <= this.getRadius() * Entity.ACCURACY_FACTOR)
//				newPosition = new Position(newCollidingPosition.getxComponent() + (1-ACCURACY_FACTOR)/2 * getRadius(), newCollidingPosition.getyComponent());
//			else if (this.getPosition().getyComponent() <= this.getRadius() * Entity.ACCURACY_FACTOR)
//				newPosition = new Position(newCollidingPosition.getxComponent(), newCollidingPosition.getyComponent() + (1-ACCURACY_FACTOR)/2 * getRadius());
//			else if (getWorld().getHeight() - this.getPosition().getyComponent() <= this.getRadius() * Entity.ACCURACY_FACTOR)
//				newPosition = new Position(newCollidingPosition.getxComponent(), newCollidingPosition.getyComponent() - (1-ACCURACY_FACTOR)/2 * getRadius());
//			else if (getWorld().getWidth() - this.getPosition().getyComponent() >= this.getRadius() * Entity.ACCURACY_FACTOR)
//				newPosition = new Position(newCollidingPosition.getxComponent() - (1-ACCURACY_FACTOR)/2 * getRadius(), newCollidingPosition.getyComponent());
//			setPosition(newPosition);
//		}
	}
	
	
	/**
	 * Set the position of this entity to position with given components.
	 * 
	 * @param  xComponent
	 *         The new xComponent for the position for this entity.
	 * @param  yComponent
	 *         The new yComponent for the position for this entity.
	 * @effect The position of this entity is set to the position with the given components.
	 * 			| @see implementation
	 */
	@Raw @Model
	protected void setPosition(double xComponent, double yComponent) throws IllegalComponentException, IllegalPositionException {
		setPosition(new Position(xComponent, yComponent));
	}
	
	/**
	 * @param position
	 * 			The new position for this entity.
	 * @post The new position of this entity is equal to the given position.
	 * 		 | new.getPosition().equals(position)
	 * @throws IllegalComponentException
	 * 			One of the given components is not valid
	 * 		 | ! Position.isValidComponent(position.getxComponent()) ||
	 * 		 |		! Position.isValidComponent(position.getyComponent())
	 * @throws IllegalPositionException
	 * 			This entity cannot have this position as its position.
	 * 		 | ! canHaveAsPosition(position)
	 */
	@Raw @Model
	protected void setPosition(Position position) throws IllegalComponentException, IllegalPositionException, IllegalStateException {
		if (isTerminated())
			throw new IllegalStateException();
		if (!canHaveAsPosition(position))
			throw new IllegalPositionException();
		this.position = position;
	}
	
	/**
	 * Variable registering the position of this entity.
	 */
	private Position position;
	
	
	/**
	 * Return the velocity of this entity.
	 */
	@Basic @Raw
	public Velocity getVelocity() {
		if (this.velocity == null)
			return null;
		return this.velocity;
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
		if (isTerminated())
			return false;
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
	 * 			then the new velocity of this entity is equal to the velocity with given xComponent and yComponent.
	 *       | if (this.canHaveAsVelocity(new Velocity(xComponent,yComponent))
	 *       | 		then new.getVelocity().equals(new Velocity(xComponent, yComponent))
	 * @post   If this entity cannot have the velocity with the given xComponent and  given yComponent as its velocity, and
	 * 			the given xComponent and yComponent are valid components for any physical vector, then the new velocity of this entity 
	 * 			is equal to a velocity such that the direction corresponds with the velocity with given xComponent and yComponent,
	 * 			but the speed is set to the speedLimit.
	 * 			More concretely, the xComponent of the new velocity of this entity is set to (xComponent * getSpeedLimit() / speed) and the
	 *			yComponent of the new velocity of this entity is set to (yComponent * getSpeedLimit() / speed), where
	 *			speed is the speed corresponding to the velocity with given xComponent and yComponent.
	 *		 | if (PhysicalVector.isValidComponent(xComponent) && PhysicalVector.isValidComponent(yComponent) &&
	 *		 |			 ! this.canHaveAsVelocity(new Velocity(xComponent, yComponent))
	 *		 | 		then (new.getVelocity().getxComponent() == xComponent * getSpeedLimit / Math.hypot(xComponent, yComponent))
	 *		 |			&& (new.getVelocity().getyComponent() == yComponent * getSpeedLimit / Math.hypot(xComponent, yComponent))
	 * @post	If the current velocity of this entity is not effective and the given xComponent or yComponent are valid components
	 * 			for any physical vector, then the new velocity of this entity is equal to a velocity with 0 as its xComponent an yComponent.
	 * 		 | if (getVelocity() == null && (!PhysicalVector.isValidComponent(xComponent) || !PhysicalVector.isValidComponent(xyComponent))
	 * 		 |		then new.getVelocity().equals(new Velocity(0, 0)
	 */
	@Raw @Model
	protected void setVelocity(double xComponent, double yComponent) {
		if (!isTerminated()) {
			Velocity tempVelocity;
			try {
				tempVelocity = new Velocity(xComponent, yComponent);
			}
			catch(IllegalComponentException exc) {
				if (getVelocity() == null)
					tempVelocity = new Velocity(0, 0);
				else
					tempVelocity = getVelocity();
			}
			if (!canHaveAsVelocity(tempVelocity)) {
				double speed = tempVelocity.getSpeed();
				xComponent = xComponent * getSpeedLimit() / speed;
				yComponent = yComponent * getSpeedLimit() / speed;
				tempVelocity = new Velocity(xComponent, yComponent);
				//No exceptions are thrown here, because if xComponent or yComponent would be an invalid component, an exception would already
				//have been thrown and caught such that canHaveAsVelocity(tempVelocity) is always true.
			}
			this.velocity = tempVelocity;
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
	 * Return the mass of this entity.
	 */
	@Basic @Raw
	public double getMass() {
		return this.mass;
	}
	
	
	/**
	 * Check whether this entity can have the given mass as its mass.
	 *  
	 * @param  mass
	 *         The mass to check.
	 * @return 
	 *       | if (mass < getVolume() * getDensity())
	 *       |	then result == false
	*/
	@Raw
	public abstract boolean canHaveAsMass(double mass);
	
	/**
	 * Set the mass of this entity to the given mass.
	 * @param mass
	 * 			The new of this entity.
	 * @post If this entity can have the given mass as its mass, 
	 * 		 then the new mass of this entity is equal to the given mass.
	 * 		| if (canHaveAsMass(mass))
	 * 		|	then new.getMass() == mass
	 */
	private void setMass(double mass) {
		if (canHaveAsMass(mass) && !isTerminated())
			this.mass = mass;
	}
	
	/**
	 * Variable registering the mass of this entity.
	 */
	private double mass;
	
	
	/**
	 * Return the density of this entity.
	 */
	@Basic @Raw @Immutable
	public double getDensity() {
		return this.density;
	}
	
	/**
	 * Check whether this entity can have the given density as its density.
	 *  
	 * @param  density
	 *         The density to check.
	 * @return False if the given density is smaller than getMinimalDensity().
	 *       | if density < getMinimalDensity()
	 *       |	then result == false
	*/
	@Raw
	public abstract boolean canHaveAsDensity(double density);
	
	/**
	 * Variable registering the density of this entity.
	 */
	private final double density;
	
	
	/**
	 * Return the minimal density of this entity.
	 */
	@Basic @Raw
	public abstract double getMinimalDensity();
	
	
	/**
	 * Return the radius of this entity.
	 */
	@Basic @Raw @Immutable
	public double getRadius() {
		return this.radius;
	}
	
	/**
	 * Calculate the sum of the radii of the two given entities.
	 * 
	 * @param entity1
	 * 			The first entity
	 * @param entity2
	 * 			The second entity
	 * @return The sum of the radii of the two entities, if both are effective.
	 * 			| If ((entity1 != null) && (entity2!= null))
	 * 			|	then result == entity1.getRadius() + entity2.getRadius()
	 * @throws NullPointerException
	 * 			One of the entities is not effective
	 * 			| (entity1 == null) || (entity2 == null)
	 */
	public static double getSumOfRadii(Entity entity1, Entity entity2) throws NullPointerException {
		return entity1.getRadius() + entity2.getRadius();
	}
	
	/**
	 * Check whether this entity can have the given radius as radius.
	 *  
	 * @param  radius
	 *         The radius to check.
	 * @return false if radius is negative
	 * 			| if (radius <= 0)
	 * 			|	then result == false
	 */
	public abstract boolean canHaveAsRadius(double radius);

	
	/**
	 * @return The volume of this entity
	 * 			| @see implementation
	 */
	@Raw
	public double getVolume() {
		return 4.0 / 3 * Math.PI * Math.pow(getRadius(), 3);
	}
	
	/**
	 * Variable registering the radius of this entity.
	 */
	private final double radius;
	
	
	/**
	 * Calculate the distance between the centres of two entities
	 * @param entity1
	 * 			The first entity
	 * @param entity2
	 * 			The second entity
	 * @return If the two entities are effective, the distance between the centres of the two entities.
	 * 			| If ((entity1 != null) && (entity2!= null))
	 * 			|	then result == Position.getDistanceBetween(entity1.getPosition(), entity2.getPosition())
	 * @throws NullPointerException
	 * 			One of the entities is not effective
	 * 			| (entity1 == null) || (entity2 == null)
	 */
	public static double getDistanceBetweenCentres(Entity entity1, Entity entity2) throws NullPointerException, IllegalStateException {
		if (entity1.isTerminated() || entity2.isTerminated())
			throw new IllegalStateException();
		return Position.getDistanceBetween(entity1.getPosition(), entity2.getPosition());
	}
	
	/**
	 * Calculate the distance between two entities
	 * @param entity1
	 * 			The first entity
	 * @param entity2
	 * 			The second entity
	 * @return If the two entities are effective and different, the distance between the two entities (i.e. the distance
	 * 				between the two centres minus the sum of their radii).
	 * 			| If ((entity1 != null) && (entity2!= null) && (entity1 != entity2))
	 * 			|	then result == getDistanceBetweenCentres(entity1, entity2) - getSumOfRadii(entity1, entity2)
	 * @return If the two entities are effective and identical, zero.
	 * 			| If ((entity1 != null) && (entity1 == entity2))
	 * 			|	then result == 0
	 * @throws NullPointerException
	 * 			One of the entities is not effective
	 * 			| (entity1 == null) || (entity2 == null)
	 */
	public static double getDistanceBetween(Entity entity1, Entity entity2) throws NullPointerException, IllegalStateException{
		if (entity1.isTerminated() || entity2.isTerminated())
			throw new IllegalStateException();
		if ((entity1 != null) && (entity1 == entity2))
			return 0;
		return getDistanceBetweenCentres(entity1, entity2) - getSumOfRadii(entity1, entity2);
	}
	
	/**
	 * Determine whether two entities overlap
	 * @param entity1
	 * 			The first entity
	 * @param entity2
	 * 			The second entity
	 * @return If the two entities are effective and different, true iff the distance between their two centres is less than or equal
	 * 			to ACCURACY_FACTOR times the sum of their radii.
	 * 			| If ((entity1 != null) && (entity2!= null) && (entity1 != entity2))
	 * 			|	then result == (Entity.getDistanceBetween(entity1, entity2) <= (ACCURACY_FACTOR - 1) * (entity1.getRadius() +
	 * 			|																								 entity2.getRadius())
	 * @return If the two entities are effective and identical, true.
	 * 			| If ((entity1 != null) && (entity1 == entity2))
	 * 			|	then result == true
	 * @throws NullPointerException
	 * 			One of the entities is not effective
	 * 			| (entity1 == null) || (entity2 == null)
	 */
	public static boolean overlap(Entity entity1, Entity entity2) throws NullPointerException, IllegalStateException {
		if (entity1.isTerminated() || entity2.isTerminated())
			throw new IllegalStateException();
		if (entity1 != null && entity1 == entity2)
			return true;
		return (Entity.getDistanceBetween(entity1, entity2) <= (ACCURACY_FACTOR - 1) * getSumOfRadii(entity1, entity2));
	}
	
	/**
	 * Check whether the given entity lies fully within the bounds of this entity.
	 * @param other
	 * 			The entity to check.
	 *			//TODO Find a better formulation.
	 * @return True iff minimal distance between the centre of the given entity and a point on the boundary of this entity
	 * 			is greater than or equal to the radius of the given entity times the ACCURACY_FACTOR.
	 * 		| result == (min{ pos in Position | Position.getDistanceBetween(pos, this.getPosition()) == this.getRadius()
	 * 		|					: Position.getDistanceBetween(pos, other.getPosition())}) >= other.getRadius() * ACCURACY_FACTOR)
	 * @throws NullPointerException
	 * 			The given entity is not effective
	 * 		| other == null
	 */
	public boolean surrounds(Entity other) throws NullPointerException, IllegalStateException {
		if (this.isTerminated() || other.isTerminated())
			throw new IllegalStateException();
		return (this.getRadius() - getDistanceBetweenCentres(this, other) ) >= other.getRadius() * ACCURACY_FACTOR;
	}
	
	
	/**
	 * Check whether two entities apparently collide.
	 * @param entity1
	 * 			The first entity
	 * @param entity2
	 * 			The second entity
	 * @return false if one of the entities is not effective, is not associated to a world or if they are not associated to the same world.
	 * 			| if (entity1 == null || entity2 == null || entity1.getWorld() == null || entity2.getWorld() == null ||
	 * 			|																					 entity1.getWorld() != entity2.getWorld())
	 * 			|	then result == false.
	 * @return true if both entities are effective and associated to the same world and if the distance between the centres of the entities
	 * 			lies within the range determined by the sum of their radii multiplied with ACCURACY_FACTOR and 2 - ACCURACY_FACTOR respectively.
	 * 			| if ((entity1 != null) && (entity2!= null) && (entity1 != entity2) && (entity1.getWorld() != null) 
	 * 			|																	&& (entity1.getWorld() == entity2.getWorld()))
	 * 			|	then result == (ACCURACY_FACTOR * getSumOfRadii(entity1, entity2) <= getDistanceBetweenCentres(entity1, entity2)) &&
	 *			|		(getDistanceBetweenCentres(entity1, entity2) <= (2 - ACCURACY_FACTOR) * getSumOfRadii(entity1, entity2))
	 */
	public static boolean apparentlyCollide(Entity entity1, Entity entity2) throws IllegalStateException {
		if (entity1.isTerminated() || entity2.isTerminated())
			throw new IllegalStateException();
		if (entity1 == null || entity2 == null || entity1.getWorld() == null || entity2.getWorld() == null ||
				entity1.getWorld() != entity2.getWorld())
			return false;
		return (ACCURACY_FACTOR * getSumOfRadii(entity1, entity2) <= getDistanceBetweenCentres(entity1, entity2)) &&
				(getDistanceBetweenCentres(entity1, entity2) <= (2 - ACCURACY_FACTOR) * getSumOfRadii(entity1, entity2));
	}
	
	/**
	 * Check whether two entities will collide if they are moved during a certain duration.
	 * @param entity1
	 * 			The first entity
	 * @param entity2
	 * 			The second entity
	 * @param duration
	 * 			The duration during which the ships must be moved.
	 * @return false if one of the entities is not effective, is not associated to a world or if they are not associated to the same world.
	 * 			| if (entity1 == null || entity2 == null || entity1.getWorld() == null || entity2.getWorld() == null ||
	 * 			|																					 entity1.getWorld() != entity2.getWorld())
	 * 			|	then result == false.
	 * @return true if both entities are effective and associated to the same world and if they collide when their positions
	 * 			are set as if they moved during the given duration. It does not matter whether they collide during the
	 * 			process of moving the entities. Only the final positions are important.
	 * 			| if ((entity1 != null) && (entity2!= null) && (entity1 != entity2) && (entity1.getWorld() != null) 
	 * 			|																	&& (entity1.getWorld() == entity2.getWorld()))
	 * 			|	then result == (getSumOfRadii(entity1, entity2) == 
	 * 			|		Position.getDistanceBetween(entity1.getPosition().move(entity1.getVelocity(), duration),
	 * 			|											 entity2.getPosition().move(entity2.getVelocity(), duration)))
	 */
	public static boolean collideAfterMove(Entity entity1, Entity entity2, double duration) throws IllegalStateException {
		if (entity1.isTerminated() || entity2.isTerminated())
			throw new IllegalStateException();
		if (entity1 == null || entity2 == null || entity1.getWorld() == null || entity2.getWorld() == null ||
																							entity1.getWorld() != entity2.getWorld())
			return false;
		Position position1 = entity1.getPosition().move(entity1.getVelocity(), duration);
		Position position2 = entity2.getPosition().move(entity2.getVelocity(), duration);
		double distanceBetweenCentres = Position.getDistanceBetween(position1, position2);
		return getSumOfRadii(entity1, entity2) == distanceBetweenCentres;
	}
	
	/**
	 * Determine the time after which, if ever, two entities will collide.
	 * @param entity1
	 * 			The first entity
	 * @param entity2
	 * 			The second entity
	 * @return If both entities are effective, different and are associated to the same effective world the result is determined such that
	 * 			the two entities would collide after they would have moved during the given duration, but not earlier.
	 * 			| if ((entity1 != null) && (entity2!= null) && (entity1 != entity2) && (entity1.getWorld() != null) 
	 * 			|																	&& (entity1.getWorld() == entity2.getWorld()))
	 * 			|	then collideAfterMove(entity1, entity2, result) &&
	 * 			|		( for each t in { x in Real Numbers | 0 <= x < result } : !collideAfterMove(entity1, entity2, t)
	 * @throws NullPointerException
	 * 			One of the entities is non-effective.
	 * 			|	(entity1 == null) || (entity2 == null)
	 * @throws OverlapException
	 * 			The entities overlap
	 * 			| overlap(entity1, entity2)
	 */
	public static double getTimeToCollision(Entity entity1, Entity entity2) throws NullPointerException, 
																		OverlapException, IllegalStateException {
		if (entity1.isTerminated() || entity2.isTerminated())
			throw new IllegalStateException();
		if (overlap(entity1, entity2))
			throw new OverlapException();
		
		double dx, dy, dvx, dvy, discriminant, sumOfRadii, dvDotdr;
		dx = entity1.getPosition().getxComponent() - entity2.getPosition().getxComponent();
		dy = entity1.getPosition().getyComponent() - entity2.getPosition().getyComponent();
		dvx = entity1.getVelocity().getxComponent() - entity2.getVelocity().getxComponent();
		dvy = entity1.getVelocity().getyComponent() - entity2.getVelocity().getyComponent();
		sumOfRadii = entity1.getRadius() + entity2.getRadius();
		dvDotdr = dvx * dx + dvy * dy;
		
		if (dvDotdr >= 0)
			return Double.POSITIVE_INFINITY;
		
		discriminant = Math.pow(dvDotdr, 2) - Math.pow(Math.hypot(dvx, dvy), 2) *
							(Math.pow(Math.hypot(dx, dy),  2) - Math.pow(sumOfRadii, 2));
		if (discriminant <= 0)
			return Double.POSITIVE_INFINITY;
		double result = - (dvDotdr + Math.sqrt(discriminant)) / Math.pow(Math.hypot(dvx, dvy), 2);
		if (result < 0)
			return 0;
		return result;
	}
	
	/**
	 * Determine the position where, if ever, two entities will collide
	 * @param entity1
	 * 			The first entity.
	 * @param entity2
	 * 			The second entity.
	 * @return null, if the entities will not collide
	 * 			| if (getTimeToCollision(entity1, entity2) == Double.POSITIVE_INFINITY)
	 * 			|	then result == null 
	 * @return If the entities will collide, the result satisfies the following condition(s):
	 * 			After both entities are moved during the time getTimeToCollision(entity1, entity2), the distance between
	 * 			the result and the position of entity1 equals the radius of entity1 and the distance between
	 * 			the result and the position of entity2 equals the radius of entity2.
	 * 			| if ( Double.isFinite(getTimeToCollision(entity1, entity2)) )
	 * 			| 	then (Position.getDistanceBetween(result, entity1.getPosition().move(getTimeToCollision(entity1, entity2))) == 
	 * 			|		entity1.getRadius() ) 
	 * 			|	&& (Position.getDistanceBetween(result, entity2.getPosition().move(getTimeToCollision(entity1, entity2))) == 
	 * 			|		entity2.getRadius() )
	 * @throws NullPointerException
	 * 			One of the entities is non-effective.
	 * 			| (entity1 == null) || (entity2 == null)
	 * @throws OverlapException
	 * 			The entities overlap
	 * 			| overlap(entity1, entity2)
	 */
	public static Position getCollisionPosition(Entity entity1, Entity entity2) throws NullPointerException, 
																OverlapException, IllegalStateException {
		if (entity1.isTerminated() || entity2.isTerminated())
			throw new IllegalStateException();
		if (overlap(entity1, entity2))
			throw new OverlapException();
		
		double timeToCollision = getTimeToCollision(entity1, entity2);
		if (timeToCollision == Double.POSITIVE_INFINITY && !apparentlyCollide(entity1, entity2))
			//Due to rounding issues, it is possible that two entities already apparently collide, but the time to their collision is
			//calculated to be POSITIVE_INFINITY instead of zero.
			return null;
		
		Position position1, position2;
		
		if (!apparentlyCollide(entity1, entity2)) {
			position1 = entity1.getPosition().move(entity1.getVelocity(), timeToCollision);
			position2 = entity2.getPosition().move(entity2.getVelocity(), timeToCollision);
		}
		else {
			position1 = entity1.getPosition();
			position2 = entity2.getPosition();
		}
		
		double radius1 = entity1.getRadius();
		double radius2 = entity2.getRadius();
		double sumOfRadii = radius1 + radius2;
		
		return new Position( (position1.getxComponent() * radius2 + position2.getxComponent() * radius1) / sumOfRadii, 
				(position1.getyComponent() * radius2 + position2.getyComponent() * radius1) / sumOfRadii);
		
	}
	
	/**
	 * Check whether this entity collides with a horizontal boundary of its world.
	 * @return TODO
	 * 			| @see implementation
	 */
	public boolean collidesWithHorizontalBoundary() throws IllegalStateException {
		if (this.isTerminated())
			throw new IllegalStateException();
		if (getWorld() == null)
			return false;
		return (getPosition().getyComponent() <= getRadius() * (2 - ACCURACY_FACTOR))
				|| (getWorld().getHeight() - getPosition().getyComponent() <= getRadius() * (2 - ACCURACY_FACTOR));
	}
	
	/**
	 * Check whether this entity collides with a horizontal boundary of its world.
	 * @return TODO
	 * 			| @see implementation
	 */
	public boolean collidesWithVerticalBoundary() throws IllegalStateException {
		if (this.isTerminated())
			throw new IllegalStateException();
		if (getWorld() == null)
			return false;
		return (getPosition().getxComponent() <= getRadius() * (2 - ACCURACY_FACTOR))
				|| (getWorld().getWidth() - getPosition().getxComponent() <= getRadius() * (2 - ACCURACY_FACTOR));
	}
	
	/**
	 * Check whether this entity collides with the boundary of its world.
	 * @return true iff this entity collides with a horizontal or vertical boundary.
	 * 			| @see implementation
	 */
	public boolean collidesWithBoundary() throws IllegalStateException {
		if (this.isTerminated())
			throw new IllegalStateException();
		if (getWorld() == null)
			return false;
		return collidesWithHorizontalBoundary() || collidesWithVerticalBoundary();
	}
	
	/**
	 * Determine the time after which, if ever, this entity will collide with the boundary of its world.
	 * @return Double.POSITIVE_INFINITY if this entity is not associated to a world.
	 * 			| if (getWorld() == null)
	 * 			|	then result == Double.POSITIVE_INFINITY
	 * @return 
	 * 			| if (getWorld() != null)
	 * 			|	then ( !canHaveAsPosition(getPosition().move(getVelocity(), result).getxComponent(),
	 * 			|				getPosition().move(getVelocity(), result).getyComponent()) ) &&
	 * 			|	( for each t in { x in Real Numbers | 0 <= x < result } : 
	 * 			|				canHaveAsPosition(getPosition().move(getVelocity(), t).getxComponent(),
	 * 			|						getPosition().move(getVelocity(), t).getyComponent()))
	 */
	public double getTimeToCollisionWithBoundary() throws IllegalStateException {
		if (this.isTerminated())
			throw new IllegalStateException();
		if (getWorld() == null)
			return Double.POSITIVE_INFINITY;
		double result = Double.POSITIVE_INFINITY;
		if (getVelocity().getxComponent() < 0)
			result = Double.min(result, -(getPosition().getxComponent() - getRadius()) / getVelocity().getxComponent());
		else 
			result = Double.min(result, (getWorld().getWidth() - getPosition().getxComponent() - getRadius())
																										/ getVelocity().getxComponent());
		if (getVelocity().getyComponent() < 0)
			result = Double.min(result, -(getPosition().getyComponent() - getRadius()) / getVelocity().getyComponent());
		else 
			result = Double.min(result, (getWorld().getHeight() - getPosition().getyComponent() - getRadius())
																										/ getVelocity().getyComponent());
		return result;
	}
	
	/**
	 * Determine the position where the given entity will collide with the boundary of its world.
	 * @return null, if this entity is not associated with a world or it will not collide with a boundary.
	 * 			| if (getWorld() == null || getTimeToCollisionWithBoundary() == Double.POSITIVE_INFINITY)
	 * 			|	then result == null
	 * @return the position where this entity collides with its world's boundary.
	 * 			| @see implementation
	 */
	public Position getCollisionWithBoundaryPosition() throws IllegalStateException {
		if (this.isTerminated())
			throw new IllegalStateException();
		if (getWorld() == null || getTimeToCollisionWithBoundary() == Double.POSITIVE_INFINITY)
			return null;
		return getPosition().move(getVelocity(), getTimeToCollisionWithBoundary());
	}
	
	
	/**
	 * TODO specification en eventueel abstract met een prime object (robuuster)
	 * @param entity1
	 * @param entity2
	 * @throws IllegalMethodCallException
	 */
	public static void resolveCollision(Entity entity1, Entity entity2) throws IllegalMethodCallException, IllegalStateException {
		if (entity1.isTerminated() || entity2.isTerminated())
			throw new IllegalStateException();
		if (entity1.getWorld() == null || entity1.getWorld() != entity2.getWorld() || !Entity.apparentlyCollide(entity1, entity2))
			throw new IllegalMethodCallException();
		if (entity1 instanceof Ship && entity2 instanceof Ship) {
			Ship.resolveCollisionBetweenShips((Ship)entity1, (Ship)entity2);
		}
		else if (entity1 instanceof Bullet && entity2 instanceof Bullet) {
			entity1.terminate();
			entity2.terminate();
		}
		else if (entity1 instanceof Ship && entity2 instanceof Bullet) {
			if (((Ship)entity1).hasFired((Bullet)entity2))
				((Ship)entity1).loadBullet((Bullet)entity2);
			else {
				entity1.terminate();
				entity2.terminate();
			}
		}
		else if (entity1 instanceof Bullet && entity2 instanceof Ship) {
			if (((Ship)entity2).hasFired((Bullet)entity1))
				((Ship)entity2).loadBullet((Bullet)entity1);
			else {
				entity1.terminate();
				entity2.terminate();
			}
		}
	}
	
	/**TODO
	 * @throws IllegalMethodCallException
	 */
	public abstract void bounceOfBoundary() throws IllegalMethodCallException;
	
	/**
	 * Check whether this entity can have the given world as world.
	 * @param world
	 * 			The world to check.
	 * @return | @see implementation
	 */
	public boolean canHaveAsWorld(World world) {
		if (isTerminated())
			return false;
		return (world == null) || world.canHaveAsEntity(this);
	}
	
	/**
	 * Check whether this entity has a proper world.
	 * @return True iff the world associated to this entity is null or contains this entity at this entity's position.
	 * 			| result == (getWorld() == null) || (getWorld().getEntityAt(this.getPosition()) == this && 
	 * 			|				(for each position in getWorld().getOccupiedPositions():
	 * 			|					position == this.getPosition() || getWorld().getEntityAt(position) != this))
	 */
	public boolean hasProperWorld() {
		if (getWorld() == null)
			return true;
		if (!canHaveAsWorld(getWorld()))
			return false;
		if (getWorld().getEntityAt(this.getPosition()) != this)
			return false;
		else {
			for(Position position: getWorld().getOccupiedPositions()) {
				if (!position.equals(getPosition()) && getWorld().getEntityAt(position) == this)
					return false;
			}
		}
		return true;
	}
	
	/**
	 * Return the world in which this entity is contained.
	 */
	@Basic @Raw
	public World getWorld() {
		return this.world;
	}
	
	/**
	 * Set the world of this entity to the given world.
	 * @param world
	 * 			The new world for this entity.
	 * @post The new world of this entity is equal to the given world.
	 * 			| new.getWorld() == world
	 * @throws IllegalMethodCallException
	 * 			(The given world is effective but does not yet contain this entity) or 
	 * 			(the given world is not effective and the world of this entity is effective and still contains this entity).
	 * 			| (world != null && !world.hasAsEntity(this)) ||
	 * 			|	(world == null && getWorld() != null && getWorld().hasAsEntity(this))
	 * 
	 */
	void setWorld(World world) throws IllegalMethodCallException, IllegalStateException {
		if (isTerminated())
			throw new IllegalStateException();
		if (world != null && (!world.hasAsEntity(this) || world.isTerminated()))
			throw new IllegalMethodCallException();
		if (world == null && getWorld() != null && getWorld().hasAsEntity(this))
			throw new IllegalMethodCallException();
		this.world = world;
	}
	
	/**
	 * A variable registering the world in which this entity is contained.
	 */
	private World world;
}