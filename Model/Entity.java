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
	 * @post   The position of this entity is equal to the position with given xComponent and yComponent.
	 * 		 | new.getPosition().equals(new Position(xComponent, yComponent))
	 * @throws IllegalComponentException
	 * 		   One of the given components is not valid
	 * 		 | ! Position.isValidComponent(xComponent) || ! Position.isValidComponent(yComponent)
	 * @throws IllegalPositionException
	 * 		
	 */
	@Raw @Model
	private void setPosition(double xComponent, double yComponent) throws IllegalComponentException, IllegalPositionException {
		this.position = new Position(xComponent, yComponent);
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
	protected void setVelocity(double xComponent, double yComponent) {
		if (this.getVelocity() == null)
			this.velocity = new Velocity(0, 0);
		if (this.canHaveAsVelocity(new Velocity(xComponent, yComponent)))
			this.velocity = new Velocity(xComponent, yComponent);
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
	
	

//	/**
//	 * Initialize this new entity with given mass.
//	 * 
//	 * @param  mass
//	 *         The mass for this new entity.
//	 * @post   If the given mass is a valid mass for any entity,
//	 *         the mass of this new entity is equal to the given
//	 *         mass. Otherwise, the mass of this new entity is equal
//	 *         to getVolume() * getDensity().
//	 *       | if (isValidMass(mass))
//	 *       |   then new.getMass() == mass
//	 *       |   else new.getMass() == getVolume() * getDensity()
//	 */
//	public Entity(double mass) {
//		if (! canHaveAsMass(mass))
//			mass = getVolume();
//		this.mass = mass;
//	}
	
	
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
		if (canHaveAsMass(mass))
			this.mass = mass;
	}
	
	/**
	 * Variable registering the mass of this entity.
	 */
	private double mass;



//	/**
//	 * Initialize this new entity with given density.
//	 * 
//	 * @param  density
//	 *         The density for this new entity.
//	 * @post   If the given density is a valid density for any entity,
//	 *         the density of this new entity is equal to the given
//	 *         density. Otherwise, the density of this new entity is equal
//	 *         to default_value_Java.
//	 *       | if (isValidDensity(density))
//	 *       |   then new.getDensity() == density
//	 *       |   else new.getDensity() == getMinimalDensity()
//	 */
//	public Entity(double density) {
//		if (! canHaveAsDensity(density))
//			density = getMinimalDensity();
//		this.density = density;
//	}
	
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
	@Raw
	public abstract double getMinimalDensity();
	
	
	/**
	 * Return the radius of this entity.
	 */
	@Basic @Raw @Immutable
	public double getRadius() {
		return this.radius;
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
	 * Calculate the distance between two entities
	 * @param entity1
	 * 			The first entity
	 * @param entity2
	 * 			The second entity
	 * @return If the two entities are effective and different, the distance between the two entities (i.e. the distance
	 * 				between the two centres minus the sum of their radii).
	 * 			| If ((entity1 != null) && (entity2!= null) && (entity1 != entity2))
	 * 			|	then result == Position.getDistanceBetween(entity1.getPosition(), entity2.getPosition()) - (entity1.getRadius() + entity2.getRadius())
	 * @return If the two entities are effective and identical, zero.
	 * 			| If ((entity1 != null) && (entity1 == entity2))
	 * 			|	then result == 0
	 * @throws NullPointerException
	 * 			One of the entities is not effective
	 * 			| (entity1 == null) || (entity2 == null)
	 */
	public static double getDistanceBetween(Entity entity1, Entity entity2) throws NullPointerException{
		if ((entity1 != null) && (entity1 == entity2))
			return 0;
		return Position.getDistanceBetween(entity1.getPosition(), entity2.getPosition()) - (entity1.getRadius() + entity2.getRadius());
	}
	
	/**
	 * Determine whether two entities overlap
	 * @param entity1
	 * 			The first entity
	 * @param entity2
	 * 			The second entity
	 * @return If the two entities are effective and different, true iff the distance between the two entities is non-positive.
	 * 			| If ((entity1 != null) && (entity2!= null) && (entity1 != entity2))
	 * 			|	then result == (Entity.getDistanceBetween(entity1, entity2) <= 0)
	 * @return If the two entities are effective and identical, true.
	 * 			| If ((entity1 != null) && (entity1 == entity2))
	 * 			|	then result == true
	 * @throws NullPointerException
	 * 			One of the entities is not effective
	 * 			| (entity1 == null) || (entity2 == null)
	 */
	public static boolean overlap(Entity entity1, Entity entity2) throws NullPointerException {
		return (Entity.getDistanceBetween(entity1, entity2) <= 0);
	}
	
	/**
	 * Determine the time after which, if ever, two entities will collide.
	 * @param entity1
	 * 			The fist entity
	 * @param entity2
	 * 			The second entity
	 * @return If both entities are effective and different, the result satisfies the following conditions:
	 * 			1.	After both entities are moved during the returned duration, they will overlap.
	 * 			| if  ((entity1 != null) && (entity2!= null) && (entity1 != entity2))
	 * 			| 	then (overlap(entity1, entity2)) is true after the execution of the following code snippet:
	 * 			|			entity1.move(result);
	 * 			|			entity2.move(result);
	 * 			2.	After both entities are moved during a positive time less than the returned duration, they will not overlap.
	 * 			| if  ((entity1 != null) && (entity2!= null) && (entity1 != entity2))
	 * 			|	then for each duration in { time in the real numbers | 0 <= time < result}, 
	 * 			|		(overlap(entity1, entity2)) is false after the execution of the following code snippet:
	 * 			|			entity1.move(duration);
	 * 			|			entity2.move(duration);
	 * @throws NullPointerException
	 * 			One of the entities is non-effective.
	 * 			|	(entity1 == null) || (entity2 == null)
	 * @throws OverlapException
	 * 			The entities overlap
	 * 			| overlap(entity1, entity2)
	 */
	public static double getTimeToCollision(Entity entity1, Entity entity2) throws NullPointerException, OverlapException {
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
		return - (dvDotdr + Math.sqrt(discriminant)) / Math.pow(Math.hypot(dvx, dvy), 2);
	}
	
	/**
	 * Determine the position where, if ever, two entities will collide
	 * @param entity1
	 * 			The first entity.
	 * @param entity2
	 * 			The second entity.
	 * @return null, if the entities will not collide
	 * 			| if (getTimeToCollision(entity1, entity2) == Double.POSITIVE_INFINITY)
	 * 			|	then return null 
	 * @return If the entities will collide, the result satisfies the following condition(s):
	 * 			After both entities are moved during the time getTimeToCollision(entity1, entity2), the distance between
	 * 			the result and the position of entity1 equals the radius of entity1 and the distance between
	 * 			the result and the position of entity2 equals the radius of entity2.
	 * 			| if ( Double.isFinite(getTimeToCollision(entity1, entity2)) )
	 * 			| 	then (Position.getDistanceBetween(result, entity1.getPosition()) == entity1.getRadius() ) &&
	 * 			|			(Position.getDistanceBetween(result, entity2.getPosition()) == entity2.getRadius() )
	 * 			|	is true, after the execution of the following code snippet:
	 * 			|			double duration = getTimeToCollision(entity1, entity2);
	 * 			|			entity1.move(duration);
	 * 			|			entity2.move(duration);
	 * @throws NullPointerException
	 * 			One of the entities is non-effective.
	 * 			| (entity1 == null) || (entity2 == null)
	 * @throws OverlapException
	 * 			The entities overlap
	 * 			| overlap(entity1, entity2)
	 */
	public static Position getCollisionPosition(Entity entity1, Entity entity2) throws NullPointerException, OverlapException{
		if (overlap(entity1, entity2))
			throw new OverlapException();
		
		double timeToCollision = getTimeToCollision(entity1, entity2);
		if (timeToCollision == Double.POSITIVE_INFINITY)
			return null;
		
		Entity entity1Clone = entity1.copy();
		Entity entity2Clone = entity2.copy();
		entity1Clone.move(timeToCollision);
		entity2Clone.move(timeToCollision);
		
		Position position1 = entity1Clone.getPosition();
		Position position2 = entity2Clone.getPosition();
		
		double radius1 = entity1Clone.getRadius();
		double radius2 = entity2Clone.getRadius();
		double sumOfRadii = radius1 + radius2;
		
		return new Position( (position1.getxComponent() * radius2 + position2.getxComponent() * radius1) / sumOfRadii, 
				(position1.getyComponent() * radius2 + position2.getyComponent() * radius1) / sumOfRadii);
		
	}
	
	public World getWorld() {
		return this.world;
	}
	
	private World world;
}