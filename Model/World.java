package asteroids.model.representation;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import asteroids.model.exceptions.*;
import asteroids.part2.CollisionListener;
import be.kuleuven.cs.som.annotate.*;

/**
 * @invar  The given height must be a valid height for any world.
 *       | isValidHeight(this.getHeight())
 * @invar  The given width must be a valid width for any world.
 *       | isValidWidth(this.getWidth())
 * @invar  Each world must have proper entities.
 * 		 | hasProperEntities()
 */
public class World {
	/**
	 * Initialize this new world with given height and width.
	 * 
	 * @param  height
	 *         The height for this new world.
	 * @param  width
	 *         The width for this new world.
	 * @post   If the given height is a valid height for any world, the height of this new world is equal to the given
	 *         height. Otherwise, the height of this new world is equal to getMaxHeight().
	 *       | if (isValidHeight(height))
	 *       |   then new.getHeight() == height
	 *       | else new.getHeight() == getMaxHeight()
	 * @post   If the given width is a valid width for any world, the width of this new world is equal to the given
	 *         width. Otherwise, the width of this new world is equal to getMaxWidth().
	 *       | if (isValidWidth(width))
	 *       |   then new.getWidth() == width
	 *       | else new.getWidth() == getMaxWidth()
	 */
	@Raw
	public World(double height, double width) {
		if (! isValidHeight(height))
			height = getMaxHeight();
		this.height = height;
		if (! isValidWidth(width))
			width = getMaxWidth();
		this.width = width;
	}
	
	
	/**
	 * Return a boolean indicating whether or not this world is terminated.
	 */
	@Basic @Raw
	public boolean isTerminated() {
		return this.isTerminated;
	}
	
	/**
	 * Terminate this world.
	 * 
	 * @effect Each entity is removed from this world.
	 * 		 | for each entity in getEntities() : remove(entity)
	 * @post   This world is terminated.
	 *       | new.isTerminated()
	 */
	public void terminate() {
		if (!isTerminated()) {
			//Before removing all ships and bullets, the association between ships and fired bullets must be torn down.
			for (Ship ship: getShips()) {
				for (Bullet bullet: ship.getFiredBullets()) {
					ship.removeBullet(bullet);
				}
			}
			Set<Entity> entitiesClone = new HashSet<>(getEntities());
			for (Entity entity: entitiesClone) {
				removeEntity(entity);
			}
			this.isTerminated = true;
		}
	}
	
	/**
	 * Variable registering whether this world is terminated.
	 */
	private boolean isTerminated = false;
	 
	
	/**
	 * Return the height of this world.
	 */
	@Basic @Raw @Immutable
	public double getHeight() {
		return this.height;
	}
	
	/**
	 * Check whether the given height is a valid height for any world.
	 *  
	 * @param  height
	 *         The height to check.
	 * @return 
	 *       | result == (0 < height) && (height <= getMaxHeight())
	 */
	public static boolean isValidHeight(double height) {
		return (0 < height) && (height <= getMaxHeight());
	}
	
	/**
	 * Variable registering the height of this world.
	 */
	private final double height;
	
	
	/**
	 * Return the maximal height for any world.
	 */
	@Basic
	public static double getMaxHeight() {
		return maxHeight;
	}
	
	/**
	 * A variable registering the maximal height of any world.
	 */
	private static final double maxHeight = Double.MAX_VALUE;
	
	
	/**
	 * Return the width of this world.
	 */
	@Basic @Raw @Immutable
	public double getWidth() {
		return this.width;
	}
	
	/**
	 * Check whether the given width is a valid width for any world.
	 *  
	 * @param  width
	 *         The width to check.
	 * @return 
	 *       | result == (0 < width) && (width <= getMaxWidth())
	 */
	public static boolean isValidWidth(double width) {
		return (0 < width) && (width <= getMaxWidth());
	}
	
	/**
	 * Variable registering the width of this world.
	 */
	private final double width;
	
	
	/**
	 * Return the maximal width for any world.
	 */
	@Basic
	public static double getMaxWidth() {
		return maxWidth;
	}
	
	/**
	 * A variable registering the maximal width of any world.
	 */
	private static final double maxWidth = Double.MAX_VALUE;
	
	/**
	 * Return an array with the dimensions (width and height) of this world.
	 * 
	 * @return @see implementation
	 */
	@Raw
	public double[] getDimensions() {
		return new double[] {getWidth(), getHeight()};
	}
	
	/**
	 * Check whether the given position lies within the boundaries of this world.
	 * 
	 * @param position
	 * 			The position to check.
	 * @return	| @see implementation
	 */
	@Raw
	public boolean hasWithinBoundaries(Position position) {
		if (position == null)
			return false;
		return (0 <= position.getxComponent()) && (position.getxComponent() <= getWidth())
				&& (0 <= position.getyComponent()) && (position.getyComponent() <= getHeight());
	}
	
	/**
	 * Check whether the boundaries of this world fully surround the given entity.
	 * 
	 * @param entity
	 * 			The entity to check.
	 * @return	True iff the given entity is effective and not terminated and this world is not terminated and the distance from the centre
	 * 			of the given entity to the boundary of this world is greater than or equal to the radius of the given entity multiplied
	 * 			with the accuracy factor given in the class Entity.
	 * 			| @see implementation
	 */
	@Raw
	public boolean boundariesSurround(Entity entity) {
		if (entity == null || entity.isTerminated() || this.isTerminated())
			return false;
		return (entity.getPosition().getxComponent() >= entity.getRadius() * Entity.ACCURACY_FACTOR) && (entity.getPosition().getyComponent() >= 
				entity.getRadius() * Entity.ACCURACY_FACTOR)
				&& (this.getHeight() - entity.getPosition().getyComponent() >= entity.getRadius() * Entity.ACCURACY_FACTOR)
				&& (this.getWidth() - entity.getPosition().getxComponent() >= entity.getRadius() * Entity.ACCURACY_FACTOR);
	}
	
	
	/**
	 * Check whether this world can contain the given entity.
	 * 
	 * @param entity
	 * 			The entity to check.
	 * @return	| result == (entity != null) && !entity.isTerminated() && !this.isTerminated() && this.boundariesSurround(entity)
	 */
	@Raw
	public boolean canHaveAsEntity(Entity entity) {
		return (entity != null) && !entity.isTerminated() && !this.isTerminated() && this.boundariesSurround(entity);
	}
	
	/**
	 * Check whether this world has proper entities.
	 * 
	 * @return	| result == 
	 * 			|	(for each entity in getEntities():
	 * 			|		canHaveAsEntity(entity) && (entity.getWorld == this) && (getEntityAt(entity.getPosition()) == entity) &&
	 * 			|		(for each other in getEntities():
	 * 			|			(entity == other) || !Entity.overlap(entity, other)))
	 * 			|	&& (getOccupiedPositions().size() == getEntities().size())
	 */
	@Raw
	public boolean hasProperEntities() {
		if (getOccupiedPositions().size() != getEntities().size())
			//This means that at least one entity is the value of at least two different keys.
			return false;
		for (Entity entity: getEntities()) {
			if (!canHaveAsEntity(entity) || (entity.getWorld() != this) || (getEntityAt(entity.getPosition()) != entity))
				return false;
			for (Entity other: getEntities()) {
				if ((other != entity) && Entity.overlap(entity, other))
					return false;
			}
		}
		return true;
	}
	
	/**
	 * Return the entity, if any, whose centre coincides with the given position.
	 * 
	 * If no entity has the given position as its centre, null is returned.
	 * If the given position is null, null is returned.
	 * 
	 * @param position
	 * 			The position of which the method checks that there is an entity.
	 */
	@Basic @Raw
	public Entity getEntityAt(Position position) {
		if (position == null)
			return null;
		return entities.get(position);
	}
	
	/**
	 * Return a set of all occupied positions in this world. Occupied positions are positions where the centre of an entity in this
	 * world is located.
	 * 
	 * @return	| { position in Position | hasWithinBoundaries(position) && (getEntityAt(position) != null) }
	 */
	@Raw
	public Set<Position> getOccupiedPositions() {
		return new HashSet<Position>(entities.keySet());
	}
	
	/**
	 * Check whether this world contains the given entity.
	 * 
	 * @param entity
	 * 			The entity to check.
	 * @return  | result == getEntities().contains(entity)
	 */
	@Raw
	public boolean hasAsEntity(Entity entity) {
		return getEntities().contains(entity);
	}
	
	/**
	 * Returns a set of all entities contained in this world.
	 * 
	 * @return | { position in getOccupiedPositions() | true : getEntityAt(position) }
	 */
	@Raw
	public Set<Entity> getEntities() {
		return new HashSet<Entity>(entities.values());
	}
	
	/**
	 * Returns a set of all ships contained in this world.
	 * 
	 * @return	| result == { e in getEntities() | (e instanceof Ship) : (Ship)e }
	 */
	public Set<Ship> getShips() {
		Set<Ship> result = new HashSet<>();
		for (Entity entity: getEntities()) {
			if (entity instanceof Ship)
				result.add((Ship)entity);
		}
		return result;
	}
	
	/**
	 * Returns a set of all bullets contained in this world.
	 * 
	 * @return	| result == { e in getEntities() | (e instanceof Bullet) : (Bullet)e }
	 */
	public Set<Bullet> getBullets() {
		Set<Bullet> result = new HashSet<>();
		for (Entity entity: getEntities()) {
			if (entity instanceof Bullet)
				result.add((Bullet)entity);
		}
		return result;
	}
	
	/**
	 * Add a given entity to this world.
	 * 
	 * @param entity
	 * 			The entity to add to this world.
	 * @post	| new.getEntityAt((new entity).getPosition) == (new entity)
	 * @effect	| entity.setWorld(this)
	 * @throws IllegalArgumentException
	 * 			| !canHaveAsEntity(entity) || (entity.getWorld() != null) || hasAsEntity(entity)
	 * @throws OverlapException(entity, other)
	 * 			| for some other in getEntities:
	 * 			|	(entity != other) && Entity.overlap(entity, other)
	 */
	public void addEntity(Entity entity) throws IllegalArgumentException, OverlapException {
		//We do not explicitly check for the termination of this world here, because that is already checked in canHaveAsEntity(entity).
		if (!canHaveAsEntity(entity) || (entity.getWorld() != null) || hasAsEntity(entity))
			throw new IllegalArgumentException();
		if (entity != null) {
			for(Entity other: getEntities()) {
				if((other != entity) && Entity.overlap(entity, other))
					throw new OverlapException(entity, other);
			}
		}
		entities.put(entity.getPosition(), entity);
		entity.setWorld(this);
		//Cannot throw TerminatedException because at this point canHaveAsEntity(entity) implies !entity.isTerminated()
	}
	
	/**
	 * Remove the given entity from this world.
	 * @param entity
	 * 			The entity to remove from this world.
	 * @post	| new.getEntityAt((new entity).getPosition) == null
	 * @effect	| entity.setWorld(null)
	 * @throws NullPointerException
	 * 			| entity == null
	 * @throws IllegalArgumentException
	 * 			| !hasAsEntity(entity)
	 * @throws IllegalMethodCallException
	 * 			| !entity.canBeRemovedFromWorld()
	 * @note	Ships and bullets can only be removed if they are not associated with another bullet or ship respectively. This means that
	 * 			ships can only be removed if they have no more fired bullets in this world. Similarly, a fired bullet can only be removed
	 * 			if its associated ship is null or it apparently collides with its associated ship. To remove a ship or a bullet that does
	 * 			not satisfy these requirements, first end the association between them.
	 */
	public void removeEntity(Entity entity) throws NullPointerException, IllegalArgumentException, IllegalMethodCallException {
		if (entity == null)
			throw new NullPointerException();
		if (!hasAsEntity(entity))
			throw new IllegalArgumentException();
		if (!entity.canBeRemovedFromWorld())
			throw new IllegalMethodCallException();
		entities.remove(entity.getPosition());
		entity.setWorld(null);
	}
	
	/**
	 * Update the map entities of this world such that each entity in this world can be accessed via its position (this may not be possible
	 * if the position of an entity in this world has changed).
	 * 
	 * @post	| if (hasasEntity(entity))
	 * 			|	then ((getEntityAt(entity.getPosition()) == entity) &&
	 * 			|	(for each position in { p in Position | hasWithinboundaries(p) } : 
	 * 			|		(position == entity.getPosition() || getEntityAt(position) != entity)))
	 * @throws	IllegalMethodCallException
	 * 			| !hasAsentity(entity)
	 * @throws	TerminatedException
	 * 			| isTerminated()
	 */
	@Raw
	public void updatePosition(Entity entity) throws IllegalMethodCallException, TerminatedException {
		if (isTerminated())
			throw new TerminatedException();
		if (!hasAsEntity(entity))
			throw new IllegalMethodCallException();
		for (Position pos: getOccupiedPositions()) {
			if (getEntityAt(pos) == entity)
				entities.remove(pos);
		}
		entities.put(entity.getPosition(), entity);
	}
	
	/**
	 * A map registering the entities contained in this world.
	 * 
	 * @invar   The referenced map is effective.
     *        | entities != null
     * @invar   Each key registered in the map is an effective position that lies within the boundaries of this world.
     *        | for each key in entities.keySet():
     *        |   (key != null) && hasWithinBoundaries(key)
     * @invar   Each value associated with a key in the map is an effective, non-terminated entity involving this
     *          world.
     *        | for each key in entities.keySet():
     *        |   (entities.get(key) != null) &&
     *        |   (! entities.get(key).isTerminated()) &&
     *        |   (entities.get(key).getWorld() == this)
	 */
	private Map<Position, Entity> entities = new HashMap<>();
	
	
	/**
	 * Calculate the time until the first collision (between entities or of an entity against the boundary) in this world.
	 * 
	 * @return (for some entity in getEntities() : entity.collidesWithBoundaryAfterMove(result)) ||
	 * 			(for some entity1, entity2 in getEntities() : (entity1 != entity2) && Entity.collideAfterMove(entity1, entity2, result))
	 * @return for each time in { t in RealNumbers | 0 <= t < result} :
	 * 			((for each entity in getEntities() : !entity.collidesWithBoudaryAfterMove(time)) &&
	 * 			 (for each entity1, entity2 in getEntities() : (entity1 == entity2) || !Entity.collideAfterMove(time)))
	 * @throws IllegalMethodCallException
	 * 			| getEntities().isEmpty()
	 * @throws TerminatedException
	 * 			| isTerminated()
	 */
	public double getTimeToFirstCollision() throws IllegalMethodCallException, TerminatedException {
		if (isTerminated())
			throw new TerminatedException();
		if (getEntities().isEmpty())
			throw new IllegalMethodCallException();
		double result = Double.POSITIVE_INFINITY;
		for (Entity entity: getEntities()) {
			result = Math.min(result, entity.getTimeToCollisionWithBoundary());
			for (Entity other: getEntities()) {
				if (other != entity) {
					if (Entity.overlap(entity, other))
						result = 0;
					else
						result = Math.min(result, Entity.getTimeToCollision(entity, other));
					//The method getTimeToCollision cannot throw an exception because of the class invariants of world.
				}
			}
		}
		if (result < 0)
			//result can be negative due to rounding errors.
			result = 0;
		return result;
	}
	
	/**
	 * Determine the position in this world where the first collision between two entities in this world will take place.
	 * 
	 * @return	| if (for some entity in getEntities() : entity.collidesWithBoundaryAfterMove(getTimeToFirstCollision())
	 * 			|	then (for some entity in { ent in getEntities() | entity.collidesWithBoundaryAfterMove(getTimeToFirstCollision()) } :
 	 * 			|				result == entity.getCollisionWithBoundaryPosition())
	 * @return	| if (for some entity1, entity2 in getEntities() : (entity1 != entity2) && Entity.collideAfterMove(entity1, entity2,
	 * 			|																							 getTimeToFirstCollision()))
	 * 			|	then (for some (entity1, entity2) in { (ent1, ent2) in getEntities() x getEntities() | (entity1 != entity2) && 
 	 * 			|		Entity.collideAfterMove(entity1, entity2, getTimeToFirstCollision()) } : 		
 	 * 			|			result == Entity.getCollisionPosition(entity1, entity2))
	 * @throws IllegalMethodCallException
	 * 			| getEntities().isEmpty()
	 * @throws TerminatedException
	 * 			| isTerminated
	 */
	public Position getPositionFirstCollision() throws IllegalMethodCallException, TerminatedException {
		if (isTerminated())
			throw new TerminatedException();
		if (getEntities().isEmpty())
			throw new IllegalMethodCallException();
		double minimalTime = Double.POSITIVE_INFINITY;
		Position result = null;
		for (Entity entity: getEntities()) {
			if (minimalTime >= entity.getTimeToCollisionWithBoundary()) {
				minimalTime = entity.getTimeToCollisionWithBoundary();
				result = entity.getCollisionWithBoundaryPosition();
			}
			for (Entity other: getEntities()) {
				if (other != entity)
					if (minimalTime >= Entity.getTimeToCollision(entity, other)) {
						minimalTime = Entity.getTimeToCollision(entity, other);
						result = Entity.getCollisionPosition(entity, other);
						//The methods getTimeToCollision and getCollisionPosition cannot throw an exception because of the
						// class invariants of world.
					}
			}
		}
		return result;
	}
	
	/**
	 * Return the set of all collisions occurring in this world.
	 * A collision of an entity with the boundary of the world is represented as a set containing that entity.
	 * A collision between to entities is represented as a set containing those two entities.
	 * 
	 * @return | { entity in getEntities() | entity.collidesWithBoundary : {entity} } union
	 * 				{ (entity1, entity2) in getEntities() x getEntities() | Entity.apperentlyCollide(entity1, entity2) : {entity1, entity2} }
	 */
	public Set<Set<Entity>> getCollisions() {
		Set<Set<Entity>> result = new HashSet<>();
		for (Entity entity: getEntities()) {
			if (entity.apparentlyCollidesWithBoundary()) {
				Set<Entity> tempSet = new HashSet<>();
				tempSet.add(entity);
				result.add(tempSet);
			}
			for (Entity other: getEntities()) {
				if ((other != entity) && Entity.apparentlyCollide(entity, other)){
					Set<Entity> tempSet = new HashSet<>();
					tempSet.add(entity);
					tempSet.add(other);
					result.add(tempSet);
				}
			}
		}
		return result;
	}
	
	/**
	 * Show the collision of the given entity with the boundary of this world.
	 * This method does not check whether the collision actually occurs.
	 */
	public void showCollision(CollisionListener collisionListener, Entity entity) {
		if (collisionListener != null && !isTerminated())
			collisionListener.boundaryCollision(entity, entity.getPosition().getxComponent(), entity.getPosition().getyComponent());
	}
	
	/**
	 * Show the collision between the given entities in this world.
	 * This method does not check whether the collision actually occurs.
	 */
	public void showCollision(CollisionListener collisionListener, Entity entity1, Entity entity2) throws NullPointerException,
																								OverlapException, TerminatedException{
		if (collisionListener != null && !isTerminated()) {
			if (entity1.mustShowCollisionWith(entity2)) {
				Position collisionPosition = Entity.getCollisionPosition(entity1, entity2);
				collisionListener.objectCollision(entity1, entity2, collisionPosition.getxComponent(), collisionPosition.getyComponent());
			}
		}
	}
	
	/**
	 * Let this world evolve with the given duration.
	 */
	public void evolve(double duration, CollisionListener collisionListener) throws IllegalArgumentException, TerminatedException {
		if (isTerminated())
			throw new TerminatedException();
		if (duration < 0)
			throw new IllegalArgumentException();
		if (getEntities().isEmpty())
			return;
		double timeToFirstCollision = getTimeToFirstCollision();
		while (timeToFirstCollision <= duration) {
			advance(timeToFirstCollision);
			resolveCollisions(collisionListener);
			duration -= timeToFirstCollision;
			timeToFirstCollision = getTimeToFirstCollision();
		}
		if (duration > 0)
			advance(duration);
	}
	
	/**
	 * Advance this world with the given duration.
	 * This means that all entities in this world are moved during the given duration. There is no collision checking in this method.
	 */
	private void advance(double duration) throws IllegalArgumentException, TerminatedException {
		if (isTerminated())
			throw new TerminatedException();
		double timeToFirstCollision = getTimeToFirstCollision();
		if (duration > timeToFirstCollision && timeToFirstCollision >= 1e-10)
			//It is possible that due to rounding issues, timeToFirstCollision is smaller than 1e-10 and we still want to advance this world.
			throw new IllegalArgumentException(Double.toString(getTimeToFirstCollision()));
		for (Entity entity: getEntities()) {
			entity.move(duration);
			if (entity instanceof Ship)
				((Ship)entity).thrust(duration);
		}
	}
	
	/**
	 * Resolve the collisions (both between entities as between an entity and the boundary) in this world.
	 */
	private void resolveCollisions(CollisionListener collisionListener) throws TerminatedException, IllegalCollisionException {
		if (isTerminated())
			throw new TerminatedException();
		Set<Set<Entity>> collisionSet = getCollisions();
		if (collisionSet.isEmpty())
			throw new IllegalMethodCallException();
		for (Set<Entity> collision: collisionSet) {
			if (collision.size() == 1) {
				Entity entity = (Entity)collision.toArray()[0];
				if  (!entity.isTerminated() && entity.apparentlyCollidesWithBoundary()) {
					//It is possible that entity is terminated in a previous collision (that is handled in this invocation of resolveCollsions),
					// such that it still belongs to the collisionSet.
					showCollision(collisionListener, entity);
					entity.bounceOfBoundary();
				}
			}
			else if (collision.size() == 2) {
				Object[] collisionArray = collision.toArray();
				Entity entity1 = (Entity)collisionArray[0];
				Entity entity2 = (Entity)collisionArray[1];
				if (!entity1.isTerminated() && !entity2.isTerminated() && Entity.apparentlyCollide(entity1, entity2)) {
					//It is possible that entity1 or entity2 is terminated in a previous collision (that is handled in this invocation
					// of resolveCollsions), such that it still belongs to the collisionSet.
					showCollision(collisionListener, entity1, entity2);
					entity1.resolveCollision(entity2);
				}
			}
			else
				throw new IllegalCollisionException();
		}
}
}