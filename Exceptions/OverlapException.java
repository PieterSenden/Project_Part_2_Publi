package asteroids.model.exceptions;

import asteroids.model.representation.*;
import be.kuleuven.cs.som.annotate.*;

/**
 * A class of exceptions signaling the overlapping state of two entities.
 * 
 * @author Joris Ceulemans & Pieter Senden
 * @version 2.0
 */
public class OverlapException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * Initialize this new overlap exception.
	 */
	public OverlapException() {
		super();
		entity1 = null;
		entity2 = null;
	}
	
	/**
	 * Initialize this new overlap exception with the given entities.
	 * @param entity1
	 * 			The first entity for this new overlap exception.
	 * @param entity2
	 * 			The second entity for this new overlap exception.
	 * @post The first entity of this new overlap exception is equal to the given first entity.
	 * 			| new.getFirstEntity() == entity1
	 * @post The second entity of this new overlap exception is equal to the given second entity.
	 * 			| new.getSecondEntity() == entity2
	 * @efffect This new overlap exception is further initialized as a new runtime exception involving no diagnostic message
	 * 			and no cause.
	 */
	public OverlapException(Entity entity1, Entity entity2) {
		super();
		this.entity1 = entity1;
		this.entity2 = entity2;
	}
	
	/**
	 * Return the first entity of this overlap exception.
	 */
	@Basic @Raw @Immutable
	public Entity getFirstEntity() {
		return entity1;
	}
	
	/**
	 * Variable registering the first entity of this overlap exception.
	 */
	private final Entity entity1;
	
	/**
	 * Return the second entity of this overlap exception.
	 */
	@Basic @Raw @Immutable
	public Entity getSecondEntity() {
		return entity2;
	}
	
	/**
	 * Variable registering the second entity of this overlap exception.
	 */
	private final Entity entity2;
}
