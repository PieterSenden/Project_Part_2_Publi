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
	

//	public World(double height) {
//		if (! isValidHeight(height))
//			height = getMaxHeight();
//		this.height = height;
//	}
	
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
		if (entity == null)
			return false;
		return (entity.getPosition().getxComponent() >= entity.getRadius() * Entity.ACCURACY_FACTOR) && (entity.getPosition().getyComponent() >= 
				entity.getRadius() * Entity.ACCURACY_FACTOR)
				&& (this.getHeight() - entity.getPosition().getxComponent() >= entity.getRadius() * Entity.ACCURACY_FACTOR)
				&& (this.getWidth() - entity.getPosition().getyComponent() >= entity.getRadius() * Entity.ACCURACY_FACTOR);
	}
	
	
	/**
	 * Check whether this world can contain the given entity.
	 * @param entity
	 * 			The entity to check.
	 * @return	| result == (entity != null) && this.boundariesSurround(entity)
	 */
	public boolean canHaveAsEntity(Entity entity) {
		return (entity != null) && this.boundariesSurround(entity);
	}
	
	/**
	 * Check whether this world has proper entities.
	 * @return	| result == 
	 * 			|	(for each entity in getEntities():
	 * 			|		canHaveAsEntity(entity) && (entity.getWorld == this) && (getEntityAt(entity.getPosition()) == entity) &&
	 * 			|		(for each other in getEntities():
	 * 			|			(entity == other) || !Entity.overlap(entity, other)))
	 */
	public boolean hasProperEntities() {
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
	 * @throws OverlapException
	 * 			| for some other in getEntities:
	 * 			|	(entity != other) && Entity.overlap(entity, other)
	 */
	public void addEntity(Entity entity) throws NullPointerException, IllegalArgumentException, OverlapException {
		if (!canHaveAsEntity(entity) || (entity.getWorld() != null) || hasAsEntity(entity))
			throw new IllegalArgumentException();
		for(Entity other: getEntities()) {
			if((other != entity) && Entity.overlap(entity, other))
				throw new OverlapException();
		}
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
	 * @note Ships can only be removed if they have no more fired bullets in this world. Similarly, fired bullets can only be removed
	 * 			if their associated ship is null. To remove a ship or a bullet that does not satisfy these requirements, first end the
	 * 			association between them.
	 */
	public void removeEntity(Entity entity) throws NullPointerException, IllegalArgumentException, IllegalMethodCallException {
		if (entity == null)
			throw new NullPointerException();
		if (!hasAsEntity(entity))
			throw new IllegalArgumentException();
		if ((entity instanceof Ship && ((Ship)entity).getNbOfFiredBullets() != 0) ||
				(entity instanceof Bullet && ((Bullet)entity).getShip() != null))
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
}
