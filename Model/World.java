package asteroids.model.representation;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import asteroids.model.exceptions.*;
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
	 *       |   else new.getHeight() == getMaxHeight()
	 * @post   If the given width is a valid width for any world, the width of this new world is equal to the given
	 *         width. Otherwise, the width of this new world is equal to getMaxWidth().
	 *       | if (isValidWidth(width))
	 *       |   then new.getWidth() == width
	 *       |   else new.getWidth() == getMaxWidth()
	 */
	public World(double height, double width) {
		if (! isValidHeight(height))
			height = getMaxHeight();
		this.height = height;
		if (! isValidWidth(width))
			width = getMaxWidth();
		this.width = width;
	}
	
	/**
	 * Terminate this world.
	 *
	 * @post   This world  is terminated.
	 *       | new.isTerminated()
	 * @post   ...TODO
	 *       | ...
	 */
	 public void terminate() {
		 if (!isTerminated()) {
			 Set<Entity> entitiesClone = new HashSet<>(getEntities());
			 for (Entity entity: entitiesClone) {
				 removeEntity(entity);
				 entity.setWorld(null);
			 }
			 this.isTerminated = true;
		 }
	 }
	 
	 /**
	  * Return a boolean indicating whether or not this world is terminated.
	  */
	 @Basic @Raw
	 public boolean isTerminated() {
		 return this.isTerminated;
	 }
	 
	 /**
	  * Variable registering whether this person is terminated.
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
	 * Check whether this world can have the given height as its height.
	 *  
	 * @param  height
	 *         The height to check.
	 * @return 
	 *       | result == (0 < height) && (height <= getMaxHeight())
	*/
	@Raw
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
	@Basic @Raw
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
	 * Check whether this world can have the given width as its width.
	 *  
	 * @param  width
	 *         The width to check.
	 * @return 
	 *       | result == (0 < width) && (width <= getMaxWidth())
	*/
	@Raw
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
	@Basic @Raw
	public static double getMaxWidth() {
		return maxWidth;
	}
	
	/**
	 * A variable registering the maximal height of any world.
	 */
	private static final double maxWidth = Double.MAX_VALUE;
	
	/**TODO
	 * @return
	 */
	public double[] getDimensions() {
		return new double[] {getWidth(), getHeight()};
	}
	
	/**
	 * Check whether the given position lies within the boundaries of this world.
	 * @param position
	 * 			The position to check.
	 * @return	| @see implementation
	 */
	public boolean hasWithinBoundaries(Position position) {
		if (position == null)
			return false;
		return (0 <= position.getxComponent()) && (position.getxComponent() <= getWidth())
				&& (0 <= position.getyComponent()) && (position.getyComponent() <= getHeight());
	}
	
	/**
	 * Check whether the boundaries of this world fully surround the given entity.
	 * @param entity
	 * 			The entity to check.
	 * @return	| @see implementation
	 */
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
	 * @param entity
	 * 			The entity to check.
	 * @return	| result == (entity != null) && this.boundariesSurround(entity)
	 */
	public boolean canHaveAsEntity(Entity entity) {
		return (entity != null) && !entity.isTerminated() && !this.isTerminated() && this.boundariesSurround(entity);
	}
	
	/**
	 * Check whether this world has proper entities.
	 * @return	| result == 
	 * 			|	(for each entity in getEntities():
	 * 			|		canHaveAsEntity(entity) && (entity.getWorld == this) && (getEntityAt(entity.getPosition()) == entity) &&
	 * 			|		(for each other in getEntities():
	 * 			|			(entity == other) || !Entity.overlap(entity, other)))
	 * 			|	&& (getOccupiedPositions().size() == getEntities().size())
	 */
	public boolean hasProperEntities() {
		if (getOccupiedPositions().size() != getEntities().size())
			//This means that at least one entity is the value of at least two different keys.
			return false;
		for(Entity entity: getEntities()) {
			if(!canHaveAsEntity(entity) || (entity.getWorld() != this) || (getEntityAt(entity.getPosition()) != entity))
				return false;
			for(Entity other: getEntities()) {
				if ((other != entity) && Entity.overlap(entity, other))
					return false;
			}
		}
		return true;
	}
	
	/**
	 * Return the entity, if any, whose centre coincides with the given position.
	 * @param position
	 * 			The position of which the method checks that there is an entity.
	 * @return	| (result == null)  || 
     *        	| ( (result.getPosition() == position) &&
     *        	|   (result.getWorld() == this) )
     * @return  | (result != null) ==
     *        	|   (for some entity in Entity:
     *        	|     (entity.getPosition() == position) && this.hasAsEntity(entity) )
	 */
	public Entity getEntityAt(Position position) {
		if (position == null)
			return null;
		return entities.get(position);
	}
	
	/**
	 * Return a set of all occupied positions in this world. Occupied positions are positions where the centre of an entity in this
	 * world is located.
	 */
	@Basic
	public Set<Position> getOccupiedPositions() {
		return entities.keySet();
	}
	
	/**
	 * Check whether this world contains the given entity.
	 * @param entity
	 * 			The entity to check.
	 * @return  | result ==
	 * 			|	(for some ent in getEntities():
	 * 			|		ent == entity)
	 */
	public boolean hasAsEntity(Entity entity) {
		return getEntities().contains(entity);
	}
	
	/**
	 * Returns a set of all entities contained in this world.
	 */
	@Basic
	public Set<Entity> getEntities() {
		return new HashSet<Entity>(entities.values());
	}
	
	/**
	 * Returns a set of all ships contained in this world.
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
	 * @param entity
	 * 			The entity to add to this world.
	 * @post	| new.hasAsEntity(entity)
	 * @post	| (new entity).getWorld() == this
	 * @throws NullPointerException
	 * 			| entity == null
	 * @throws IllegalArgumentException
	 * 			| !canHaveAsEntity(entity) || (entity.getWorld() != null) || hasAsEntity(entity)
	 * @throws OverlapException(entity, other)
	 * 			| for some other in getEntities:
	 * 			|	(entity != other) && Entity.overlap(entity, other)
	 */
	public void addEntity(Entity entity) throws NullPointerException, IllegalArgumentException, OverlapException, IllegalStateException {
		if (entity != null) {
			for(Entity other: getEntities()) {
				if((other != entity) && Entity.overlap(entity, other))
					throw new OverlapException(entity, other);
			}
		}
		if (this.isTerminated())
			throw new IllegalStateException();
		if (!canHaveAsEntity(entity) || (entity.getWorld() != null) || hasAsEntity(entity))
			throw new IllegalArgumentException();
		entities.put(entity.getPosition(), entity);
		entity.setWorld(this);
	}
	
	/**
	 * Remove the given entity from this world.
	 * @param entity
	 * 			The entity to remove from this world.
	 * @throws NullPointerException
	 * 			| entity == null
	 * @throws IllegalArgumentException
	 * 			| !hasAsEntity(entity)
	 * @throws IllegalMethodCallException
	 * 			| //TODO
	 * @note Ships can only be removed if they have no more fired bullets in this world. Similarly, a fired bullet can only be removed
	 * 			if its associated ship is null or it apparently collides with its associated ship. To remove a ship or a bullet that does
	 * 			not satisfy these requirements, first end the association between them.
	 */
	public void removeEntity(Entity entity) throws NullPointerException, IllegalArgumentException, IllegalMethodCallException {
		if (entity == null)
			throw new NullPointerException();
		if (!hasAsEntity(entity))
			throw new IllegalArgumentException();
		if ((entity instanceof Ship && ((Ship)entity).getNbOfFiredBullets() != 0) ||
				(entity instanceof Bullet && ((Bullet)entity).getShip() != null) &&
				!Entity.apparentlyCollide(entity, ((Bullet)entity).getShip()))
			throw new IllegalMethodCallException();
		entities.remove(entity.getPosition());
		entity.setWorld(null);
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
     *          world and involving a position which is identical to the associating key.
     *        | for each key in entities.keySet():
     *        |   (entities.get(key) != null) &&
     *        |   (! entities.get(key).isTerminated()) &&
     *        |   (entities.get(key).getWorld() == this) &&
     *        |   (entities.get(key).getPosition().equals(key))
	 */
	private Map<Position, Entity> entities = new HashMap<>();
	
	
	/**
	 * TODO write specification (declaratively is possible)
	 * @return
	 * @throws IllegalMethodCallException
	 */
	public double getTimeToFirstCollision() throws IllegalMethodCallException, IllegalStateException {
		if (isTerminated())
			throw new IllegalStateException();
		if (getEntities().isEmpty())
			throw new IllegalMethodCallException();
		double result = Double.POSITIVE_INFINITY;
		for (Entity entity: getEntities()) {
			result = Math.min(result, entity.getTimeToCollisionWithBoundary());
			for (Entity other: getEntities()) {
				if (other != entity)
					result = Math.min(result, Entity.getTimeToCollision(entity, other));
					//The method getTimeToCollision cannot throw an exception because of the class invariants of world.
			}
		}
		return result;
	}
	
	public Position getPositionFirstCollision() throws IllegalMethodCallException, IllegalStateException {
		if (isTerminated())
			throw new IllegalStateException();
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
						//The method getTimeToCollision cannot throw an exception because of the class invariants of world.
					}
			}
		}
		return result;
	}
	
	private void advance(double duration) throws IllegalArgumentException, IllegalStateException {
		if (isTerminated())
			throw new IllegalStateException();
		if (duration > getTimeToFirstCollision())
			throw new IllegalArgumentException();
		for (Entity entity: getEntities()) {
			entity.move(duration);
			if (entity instanceof Ship)
				((Ship)entity).thrust(duration);
		}
	}
	
	public Set<Set<Entity>> getCollisions() {
		Set<Set<Entity>> result = new HashSet<>();
		for (Entity entity: getEntities()) {
			if (entity.collidesWithBoundary()) {
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
	
	private void resolveCollisions() throws IllegalStateException {
		if (isTerminated())
			throw new IllegalStateException();
		Set<Set<Entity>> collisionSet = getCollisions();
		for (Set<Entity> collision: collisionSet) {
			if (collision.size() == 1) {
				Entity entity = (Entity)collision.toArray()[0];
				entity.bounceOfBoundary();
			}
			else if (collision.size() == 2) {
				Entity entity1 = (Entity)collision.toArray()[0];
				Entity entity2 = (Entity)collision.toArray()[1];
				Entity.resolveCollision(entity1, entity2);
			}
			else
				throw new IllegalCollisionException();
		}
	}
	
	public void evolve(double duration) throws IllegalArgumentException, IllegalMethodCallException, IllegalStateException {
		if (isTerminated())
			throw new IllegalStateException();
		if (duration < 0)
			throw new IllegalArgumentException();
		double timeToFirstCollision = getTimeToFirstCollision();
		while (timeToFirstCollision < duration && timeToFirstCollision > 0) {
			advance(timeToFirstCollision);
			resolveCollisions();
			duration -= timeToFirstCollision;
			timeToFirstCollision = getTimeToFirstCollision();
		}
		if (duration >= 0)
			advance(duration);
	}
}
